package org.example.whatsapp.Controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;
import org.example.whatsapp.Objects.ListaUsuarios;
import org.example.whatsapp.Objects.Usuario;
import org.example.whatsapp.Variables.Variables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ContactosController {

    private Stage stage;

    @FXML
    private Label contactosLabel;

    @FXML
    private ListView<Usuario> listView;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        //Hacer la petición de Usuarios
        ObservableList<Usuario> contactos = pedirLista();

        //Para poner el número de contactos
        String contactosText = contactosLabel.getText();
        contactosText = contactosText + " " + contactos.size();
        contactosLabel.setText(contactosText);

        listView.setItems(contactos);

        /**
         * Para meter cada tarjeta de contacto en la ListView.
         */
        listView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                if (empty || usuario == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        // Cargar el FXML para cada tarjeta
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/whatsapp/Contacto.fxml"));
                        HBox root = loader.load();

                        // Obtener el controlador de la tarjeta
                        TarjetaContactoController controller = loader.getController();
                        controller.setContacto(usuario.getId(), usuario.getNombre(), usuario.getEstado(), "",
                                usuario.isOnline());

                        // Establecer la tarjeta como gráfico de la celda
                        setGraphic(root);
                    } catch (IOException e) {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    /**
     * Metodo para pedir lista de Usuarios al servidor para cargarlos en la pantalla
     */
    public ObservableList<Usuario> pedirLista(){
        try {
            ObjectInputStream entrada = Conexion.getEntrada();
            ObjectOutputStream salida = Conexion.getSalida();

            //Ponemos los datos en un Map
            Map<String, String> datosContacto = new HashMap<>();
            datosContacto.put("peticion", "contactos");

            salida.writeObject(datosContacto);
            salida.flush();

            //Esperamos la respuesta del servidor
            String respuesta = (String) entrada.readObject();

            Gson gson = new Gson();
            Type type = new TypeToken<ListaUsuarios>() {}.getType();

            ListaUsuarios listaUsuarios = gson.fromJson(respuesta, type);

            ObservableList<Usuario> usuarios = FXCollections.observableArrayList(listaUsuarios.getUsuarios());

            return usuarios;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    @FXML
    void onClickGoLogin(MouseEvent event) {
        final String FXML = "/org/example/whatsapp/LoginView.fxml";

        try {
            FXMLLoader login = new FXMLLoader(getClass().getResource(FXML));
            Parent root = login.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene =  new Scene(root);
            stage.setScene(scene);

            stage.setHeight(750);
            stage.setWidth(1000);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

}
