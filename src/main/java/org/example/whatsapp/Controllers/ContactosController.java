package org.example.whatsapp.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.whatsapp.Objects.Usuario;

import java.io.IOException;

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
        // Crear lista de contactos
        ObservableList<Usuario> contactos = FXCollections.observableArrayList(
                new Usuario(1, "Juan Pérez", "Disponible", "https://via.placeholder.com/50"),
                new Usuario(2, "Ana López", "Ocupado", "https://via.placeholder.com/50"),
                new Usuario(3, "Carlos Gómez", "En una llamada", "https://via.placeholder.com/50")
        );

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
}
