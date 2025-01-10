package org.example.whatsapp.Controllers;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.ListaUsuarios;
import org.example.whatsapp.Objects.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ContactosController {

    private Stage stage;
    private Socket socket;

    @FXML
    private ListView<Usuario> listView;

    public void setStage(Stage stage) {
        this.stage = stage;

        // Configurar el título del Stage (opcional, ya que la escena se configura desde la clase principal)
        stage.setTitle("Lista de Contactos");
    }

    public void setSocket(Socket socket){
        this.socket = socket;
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

        // Configurar CellFactory para ListView
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
        final String HOST = "10.11.1.201";
        final int PORT = 5000;

        try {
            //Ponemos los datos en un Map
//            Map<String, String> datosLogin = new HashMap<>();
//            datosLogin.put("estado", "contactos");
//
//            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
//            salida.writeObject(datosLogin);
//            salida.flush();

            //Esperamos la respuesta del servidor
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            String respuesta = (String) entrada.readObject();

            Gson gson = new Gson();
            ListaUsuarios listaUsuarios = gson.fromJson(respuesta, ListaUsuarios.class);

            return (ObservableList<Usuario>) listaUsuarios.getUsuarios();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
}
