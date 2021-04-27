package kih;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Controller {
    private String [] sRadix = {"dec", "hex"};
    private ObservableList<String> radix = FXCollections.observableArrayList(sRadix);

    @FXML
    private Button bttnCount;

    @FXML
    private TextField h_file_path;
    @FXML
    private TextField in_data_file_path;
    @FXML
    private TextField out_data_file_path;

    @FXML
    private TextField h_file_name;
    @FXML
    private TextField in_data_file_name;
    @FXML
    private TextField out_data_file_name;

    @FXML
    private ChoiceBox in_data_radix;
    @FXML
    private ChoiceBox h_file_radix;
    @FXML
    private ChoiceBox out_data_radix;

    @FXML
    private LineChart<Number,Number> data;
    @FXML
    private LineChart<Number,Number> core;
    @FXML
    private LineChart<Number,Number> afh;

    @FXML //запускается только при старте
    private void initialize() {
        in_data_radix.setItems(radix);
        in_data_radix.setValue(sRadix[0]);

        h_file_radix.setItems(radix);
        h_file_radix.setValue(sRadix[0]);

        out_data_radix.setItems(radix);
        out_data_radix.setValue(sRadix[0]);

    }

    @FXML
    private void count(){
        System.out.println("count");
    }

    @FXML
    private void save(){
        System.out.println("save");
    }
}
