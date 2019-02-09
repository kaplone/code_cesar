package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

public class Controller implements Initializable {

    @FXML
    private RadioButton code;
    @FXML
    private RadioButton decode;
    @FXML
    private ComboBox<String> decalage;
    @FXML
    private TextArea entree;
    @FXML
    private TextArea sortie;
    @FXML
    private Button inverse;

    @FXML
    private CheckBox cesar;

    private StringProperty sortieProperty;

    private String tampon;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tampon = "";
        ObservableList<String> decalages = FXCollections.observableArrayList("1", "2", "288", "879", "1003", "8916", "8966", "15493", "116732", "136802");
        decalage.setItems(decalages);
        decalage.setValue("1");

        StringProperty entreeProperty = new SimpleStringProperty();
        sortieProperty = new SimpleStringProperty();

        entree.setStyle("-fx-background-color:#38ee00;");
        sortie.setStyle("-fx-background-color:orangered;");

        entreeProperty.bind(entree.textProperty());
        sortie.textProperty().bind(sortieProperty);

        entreeProperty.addListener((observable, oldValue, newValue) -> {
            if (cesar.isSelected()){
                newValue = newValue.chars().filter(a -> a == 32 || (a >= 65 && a <= 90) || (a >= 97 && a <= 122)).mapToObj(a -> String.valueOf((char) a)).collect(Collectors.joining());
                entree.setText(newValue);
            }
            sortieProperty.setValue(decale(newValue));
        });

        code.setOnAction(event -> {
            sortieProperty.setValue(decale(entree.getText()));
            entree.setStyle("-fx-background-color:#38ee00;");
            sortie.setStyle("-fx-background-color:orangered;");
        });

        decode.setOnAction(event -> {
            sortieProperty.setValue(decale(entree.getText()));
            sortie.setStyle("-fx-background-color:#38ee00;");
            entree.setStyle("-fx-background-color:orangered;");

        });

        decalage.setOnAction(event -> sortieProperty.setValue(decale(entree.getText())));

        cesar.setOnAction(event -> {
            if (cesar.isSelected()){
                String newValue = entreeProperty.get().chars().filter(a -> a == 32 || (a >= 65 && a <= 90) || (a >= 97 && a <= 122)).mapToObj(a -> String.valueOf((char) a)).collect(Collectors.joining());
                entree.setText(newValue);
            }
            sortieProperty.setValue(decale(entree.getText()));
        });

        inverse.setOnAction(event -> {
            tampon = sortie.getText();
            entree.setText(tampon);
            if (code.isSelected()){
                decode.setSelected(true);
                sortie.setStyle("-fx-background-color:#38ee00;");
                entree.setStyle("-fx-background-color:orangered;");
            }
            else {
                code.setSelected(true);
                entree.setStyle("-fx-background-color:#38ee00;");
                sortie.setStyle("-fx-background-color:orangered;");
            }
            sortieProperty.setValue(decale(entree.getText()));
        });

    }


    private String decale(String s){
        int in = 1;
        try {
            in = code.isSelected() ? valueOf(decalage.getValue()) : - valueOf(decalage.getValue());
        }
        catch (NumberFormatException nfe) {
            in = 1;
            decalage.setValue("1");
        }

        int i = in;
        String result = "";
        if (cesar.isSelected()){

            result = s.chars().mapToObj(a -> {
                if (a == 32){
                    return  " ";
                }
                else if(a >= 65 && a <= 90){
                    return String.valueOf((char) (((a - 65 + 260 + i) % 26) + 65 ));
                }
                else {
                    return String.valueOf((char) (((a - 97 + 260 + i) % 26) + 97 ));
                }
            }).collect(Collectors.joining());
        }
        else {
            result = s.chars().mapToObj(a -> String.valueOf((char) (a + i))).collect(Collectors.joining());
        }
        return result;
    }
}
