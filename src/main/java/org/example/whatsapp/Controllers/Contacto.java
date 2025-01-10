package org.example.whatsapp.Controllers;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Contacto {

    private String nombre;
    private String rutaImagen;

    public Contacto(String nombre, String imagePath) {
        this.nombre = nombre;
        this.rutaImagen = imagePath;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getImagePath() {
        return rutaImagen;
    }

    public void setImagePath(String imagePath) {
        this.rutaImagen = imagePath;
    }

    public static class ContactosCell extends ListCell<Contacto> {

        @Override
        protected void updateItem(Contacto item, boolean empty){
            super.updateItem(item, empty);
            if (item != null) {
                ImageView imageView = new ImageView(item.getImagePath());
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                Label labelNombre = new Label(item.getNombre() + " " + item.getImagePath());

                HBox hBox = new HBox(imageView, labelNombre);
                setGraphic(hBox);
            }else{
                setGraphic(null);
            }
        }


    }
}
