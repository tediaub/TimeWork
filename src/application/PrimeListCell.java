package application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class PrimeListCell extends ListCell<Timer> {

    private Node renderer;
    private TaskListUIController rendererController;

    public PrimeListCell() {
        super();
        // Chargement du FXML.
        try {
            final URL fxmlURL = getClass().getResource("taskListUI.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            renderer = (Node) fxmlLoader.load();
            rendererController = (TaskListUIController) fxmlLoader.getController();
        } catch (IOException ex) {
            Logger.getLogger(PrimeListCell.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Override
    protected void updateItem(Timer value, boolean empty) {
        super.updateItem(value, empty);
        String text = null;
        Node graphic = null;
        if (!empty && value != null) {
            // Si c'est un nombre premier, on utilise le nœud provenant du FXML.
            if (renderer != null) {
                graphic = renderer;
                rendererController.setTimer(value);
            } else {
                text = String.valueOf(value);
            }
        }
        setText(text);
        setGraphic(graphic);
    }
}