package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private RadioButton code;
    @FXML
    private RadioButton decode;
    @FXML
    private ChoiceBox<Integer> decalage;
    @FXML
    private TextArea entree;
    @FXML
    private TextArea sortie;

    private ObservableList<Integer> decalages;

    private StringProperty entreeProperty;
    private StringProperty sortieProperty;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        decalages = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25);
        decalage.setItems(decalages);
        decalage.setValue(1);

        entreeProperty = new SimpleStringProperty();
        sortieProperty = new SimpleStringProperty();


        entreeProperty.bind(entree.textProperty());
        sortie.textProperty().bind(sortieProperty);

        entreeProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sortieProperty.setValue(decale(newValue));
            }
        });

        code.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sortieProperty.setValue(decale(entree.getText()));
            }
        });

        decode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sortieProperty.setValue(decale(entree.getText()));
            }
        });

        decalage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sortieProperty.setValue(decale(entree.getText()));
            }
        });

    }

    private String decale(String s){
        Integer i = code.isSelected() ? decalage.getValue() : - decalage.getValue();
        String result = "";
        result = s.chars().mapToObj(a -> String.valueOf((char) (a + i))).collect(Collectors.joining());
        return result;
    }
}
