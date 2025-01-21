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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.ChatHandler;
import org.example.whatsapp.Objects.Conexion;
import org.example.whatsapp.Objects.Mensaje;
import org.example.whatsapp.Variables.Variables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    Thread listenMessage;

    @FXML
    public void initialize(){
        try{
            ArrayList<Mensaje> listaMensajes = pedirMensajes();

            ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(listaMensajes);

            listenMessage = new Thread(new ChatHandler(Conexion.getEntrada(), this));
            listenMessage.setDaemon(true);
            listenMessage.start();

            chatListView.setItems(mensajes);
            chatListView.setCellFactory(lv -> new ListCell<>() {

                private Node chatRecibidoNode;
                private Node chatEnviadoNode;
                private ChatRecibidoController chatRecibidoController;
                private ChatEnviadoController chatEnviadoController;

                {
                    try {
                        FXMLLoader chatRecibidoLoader = new FXMLLoader(getClass()
                                .getResource("/org/example/whatsapp/ChatRecibidoView.fxml"));
                        chatRecibidoNode = chatRecibidoLoader.load();
                        chatRecibidoController = chatRecibidoLoader.getController();

                        FXMLLoader chatEnviadoLoader = new FXMLLoader(getClass()
                                .getResource("/org/example/whatsapp/ChatEnviadoView.fxml"));
                        chatEnviadoNode = chatEnviadoLoader.load();
                        chatEnviadoController = chatEnviadoLoader.getController();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void updateItem(Mensaje mensaje, boolean empty) {
                    super.updateItem(mensaje, empty);
                    if (empty || mensaje == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (mensaje.getRemitente() == Variables.getIdCliente()) {
                            chatEnviadoController.setText(mensaje.getMensaje(), mensaje.getFecha());
                            setGraphic(chatEnviadoNode);
                        } else {
                            chatRecibidoController.setText(Variables.getNombreContacto(), mensaje.getMensaje(), mensaje.getFecha());
                            setGraphic(chatRecibidoNode);
                        }
                    }
                }
            });

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void onClickEnviarMensaje(ActionEvent event) throws IOException{
        String mensajeTexto = chatTextField.getText();
        if (mensajeTexto.isEmpty()) return;

        mandarMensaje(mensajeTexto);

        LocalDateTime now = LocalDateTime.now();
        String fecha = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Mensaje mensaje = new Mensaje(0, Variables.getIdCliente(), Variables.getIdContacto(), mensajeTexto, fecha);

        ObservableList<Mensaje> mensajes = chatListView.getItems();
        mensajes.add(mensaje);

        chatTextField.clear();
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

            chatListView.setItems(null);

            listenMessage.interrupt();

            if (listenMessage.isInterrupted()){
                ContactosController controller = loader.getController();
                controller.setStage(stage);

                stage.setHeight(855);
                stage.setWidth(500);
                stage.centerOnScreen();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Mensaje> pedirMensajes() {
        try {
            ObjectOutputStream salida = Conexion.getSalida();
            ObjectInputStream entrada = Conexion.getEntrada();

            salida.reset();

            //Ponemos los datos en un Map
            Map<String, String> datosMensajes = new HashMap<>();
            datosMensajes.put("peticion", "peticion-mensajes");
            datosMensajes.put("idCliente", String.valueOf(Variables.getIdCliente()));
            datosMensajes.put("idContacto", String.valueOf(Variables.getIdContacto()));

            salida.writeObject(datosMensajes);

            salida.flush();

            //Esperamos la respuesta del JSON para deserializarlo
            String jsonMensajes = (String) entrada.readObject();

            Gson gsonMensajes = new Gson();
            TypeToken<ArrayList<Mensaje>> typeToken = new TypeToken<>() {};
            ArrayList<Mensaje> mensajes = gsonMensajes.fromJson(jsonMensajes, typeToken);

            return mensajes;
        } catch (Exception e) {
            System.out.println("Error al pedir " + e.getMessage());
            ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
            return mensajes;
        }
    }

    public void mandarMensaje(String mensaje) throws IOException {
        ObjectInputStream entrada = Conexion.getEntrada();
        ObjectOutputStream salida = Conexion.getSalida();

        LocalDateTime now = LocalDateTime.now();
        String fecha = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, String> datosMensajeEnviado = new HashMap<>();
        datosMensajeEnviado.put("peticion", "envio-mensaje");
        datosMensajeEnviado.put("mensaje", mensaje);
        datosMensajeEnviado.put("fecha", fecha);

        salida.writeObject(datosMensajeEnviado);

        salida.flush();
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
