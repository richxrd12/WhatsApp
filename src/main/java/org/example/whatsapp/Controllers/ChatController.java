package org.example.whatsapp.Controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;
import org.example.whatsapp.Objects.Mensaje;
import org.example.whatsapp.Objects.Usuario;
import org.example.whatsapp.Variables.Variables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatController {

    private Stage stage;

    @FXML
    private ListView<Mensaje> chatListView;

    @FXML
    private TextField chatTextField;

    @FXML
    private Label nombreLabel;

    @FXML
    private ImageView usuarioImage;

    @FXML
    public void initialize(){
        try{
            ArrayList<Mensaje> listaMensajes = pedirMensajes();

            ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(listaMensajes);

            chatListView.setItems(mensajes);
            chatListView.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Mensaje mensaje, boolean empty) {
                    super.updateItem(mensaje, empty);
                    if (empty || mensaje == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            // Cargar el FXML para cada tarjeta
                            FXMLLoader chatRecibido = new FXMLLoader(getClass()
                                    .getResource("/org/example/whatsapp/ChatRecibidoView.fxml"));

                            FXMLLoader chatEnviado = new FXMLLoader(getClass()
                                    .getResource("/org/example/whatsapp/ChatEnviadoView.fxml"));

                            if (mensaje.getRemitente() == Variables.getIdCliente()){
                                HBox chatEnviadoRoot = chatEnviado.load();

                                ChatEnviadoController controller = chatEnviado.getController();
                                controller.setText(mensaje.getMensaje(), mensaje.getFecha());

                                setGraphic(chatEnviadoRoot);
                            } else {
                                HBox chatRecibidoRoot = chatRecibido.load();

                                ChatRecibidoController controller = chatRecibido.getController();
                                controller.setText(Variables.getNombreContacto(), mensaje.getMensaje(),
                                        mensaje.getFecha());

                                setGraphic(chatRecibidoRoot);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            setText("Error cargando contacto");
                            setGraphic(null);
                        }
                    }
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void onClickEnviarMensaje(ActionEvent event) {

    }

    @FXML
    void onClickGoContactos(MouseEvent event) {
        final String FXML = "/org/example/whatsapp/ContactosView.fxml";

        try {
            FXMLLoader loader;
            Parent root;

            if (Variables.getContactosLoader() == null) {
                loader = new FXMLLoader(getClass().getResource(FXML));
                root = loader.load();
                Variables.setContactosLoader(loader);
            } else {
                loader = Variables.getContactosLoader();
                root = loader.getRoot();

                if (root.getScene() != null) {
                    root.getScene().setRoot(new StackPane());
                }
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            ContactosController controller = loader.getController();
            controller.setStage(stage);

            stage.setHeight(855);
            stage.setWidth(500);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Mensaje> pedirMensajes() throws IOException, ClassNotFoundException {
        ObjectInputStream entrada = Conexion.getEntrada();
        ObjectOutputStream salida = Conexion.getSalida();

        //Ponemos los datos en un Map
        Map<String, String> datosMensajes = new HashMap<>();
        datosMensajes.put("peticion", "peticion-mensajes");
        datosMensajes.put("idCliente", String.valueOf(Variables.getIdCliente()));
        datosMensajes.put("idContacto", String.valueOf(Variables.getIdContacto()));

        salida.writeObject(datosMensajes);

        //Esperamos la respuesta del JSON para deserializarlo
        String jsonMensajes = (String) entrada.readObject();

        Gson gsonMensajes = new Gson();
        TypeToken<ArrayList<Mensaje>> typeToken = new TypeToken<>(){};
        ArrayList<Mensaje> mensajes = gsonMensajes.fromJson(jsonMensajes, typeToken);

        return mensajes;
    }

    void setNombreLabel(String nombre){
        nombreLabel.setText(nombre);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
