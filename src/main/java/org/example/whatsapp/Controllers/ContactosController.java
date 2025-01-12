package org.example.whatsapp.Controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Conexion;
import org.example.whatsapp.Objects.ListaUsuarios;
import org.example.whatsapp.Objects.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactosController {

    private Stage stage;

    @FXML
    private ListView<Usuario> listView;

    public void setStage(Stage stage) {
        this.stage = stage;

        // Configurar el título del Stage (opcional, ya que la escena se configura desde la clase principal)
        stage.setTitle("Lista de Contactos");
    }

    @FXML
    public void initialize() {
        //Hacer la petición de Usuarios
        ObservableList<Usuario> contactos = pedirLista();

        // Crear lista de contactos
//        ObservableList<Usuario> contactos = FXCollections.observableArrayList(
//                new Usuario(1, "Juan Pérez", "Disponible", "https://via.placeholder.com/50"),
//                new Usuario(2, "Ana López", "Ocupado", "https://via.placeholder.com/50"),
//                new Usuario(3, "Carlos Gómez", "En una llamada", "https://via.placeholder.com/50")
//        );


        listView.setItems(contactos);
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                if (empty || usuario == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        // Cargar el FXML para cada tarjeta
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/whatsapp/contacto.fxml"));
                        HBox root = loader.load();

                        // Obtener el controlador de la tarjeta
                        TarjetaContactoController controller = loader.getController();
                        controller.setContacto(usuario.getNombre(), "", "");

                        // Establecer la tarjeta como gráfico de la celda
                        setGraphic(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                        setText("Error cargando contacto");
                        setGraphic(null);
                    }
                }
            }
        });
    }

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

            System.out.println(respuesta);

            ListaUsuarios listaUsuarios = gson.fromJson(respuesta, type);

            ObservableList<Usuario> usuarios = FXCollections.observableArrayList(listaUsuarios.getUsuarios());

            return usuarios;

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Da error aquí");
            return null;
        }

    }
}
