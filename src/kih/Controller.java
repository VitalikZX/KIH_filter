package kih;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class Controller {
    private String [] sRadix = {"dec", "hex"};
    private String [] sFilterType = {"ФНЧ", "ФВЧ", "ПП", "ПЗ"};
    private String [] sPaintType = {"Поверх", "Заново"};
    private String [] coreName = {"Наттала", "Кайзера", "Хемминга", "Блэкмана", "Харриса", "Ланцоша", "Чебышева"};
    private String [] coreOrder = {"3", "5", "7", "9"};

    private ObservableList<String> radix = FXCollections.observableArrayList(sRadix);
    private ObservableList<String> filterType = FXCollections.observableArrayList(sFilterType);
    private ObservableList<String> paintType = FXCollections.observableArrayList(sPaintType);
    private ObservableList<String> name = FXCollections.observableArrayList(coreName);
    private ObservableList<String> order = FXCollections.observableArrayList(coreOrder);

    private File file;
    private BufferedReader in;
    private BufferedWriter out;
    private String buffer;

    private ArrayList<Long> in_data = new ArrayList<>();
    private ArrayList<Long> coefficient = new ArrayList<>();

    private long[] out_data;

    @FXML
    private Button bttnCount;

    @FXML
    private TextField in_data_file_path;
    @FXML
    private TextField h_file_path;
    @FXML
    private TextField out_data_file_path;

    @FXML
    private TextField in_data_file_name;
    @FXML
    private TextField h_file_name;
    @FXML
    private TextField out_data_file_name;

    @FXML
    private ChoiceBox in_data_radix;
    @FXML
    private ChoiceBox h_file_radix;
    @FXML
    private ChoiceBox out_data_radix;

    @FXML
    private ChoiceBox filter_type;
    @FXML
    private ChoiceBox paint_type;
    @FXML
    private ChoiceBox core_save_radix;
    @FXML
    private ChoiceBox core_name;
    @FXML
    private ChoiceBox core_order;

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

        filter_type.setItems(filterType);
        filter_type.setValue(sFilterType[2]);

        paint_type.setItems(paintType);
        paint_type.setValue(sPaintType[0]);

        core_save_radix.setItems(radix);
        core_save_radix.setValue(sRadix[0]);

        core_name.setItems(name);
        core_name.setValue(coreName[0]);

        core_order.setItems(order);
        core_order.setValue(coreOrder[1]);
    }

    @FXML
    private void count(){
        try {
            //show not filtered signal
            file = new File(in_data_file_path.getText() + in_data_file_name.getText());
            in = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            XYChart.Series series_data = new XYChart.Series();

            int radix = 10;
            if (in_data_radix.getValue().equals("hex")) {
                radix = 16;
            } else {
                radix = 10;
            }
            while ((buffer = in.readLine()) != null){
                in_data.add(Long.parseLong(buffer, radix));
            }

            for (int i = 0; i < in_data.size(); i++) {
                series_data.getData().add(new XYChart.Data(i, in_data.get(i)));
            }
            data.getData().addAll(series_data);
            data.setLegendVisible(false);
            data.setCreateSymbols(false);
            in.close();

            //show core filter
            file = new File(h_file_path.getText() + h_file_name.getText());
            in = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            XYChart.Series series_coefficient = new XYChart.Series();

            if (h_file_radix.getValue().equals("hex")) {
                radix = 16;
            } else {
                radix = 10;
            }
            while ((buffer = in.readLine()) != null){
                coefficient.add(Long.parseLong(buffer, radix));
            }

            for (int i = 0; i < coefficient.size(); i++) {
                series_coefficient.getData().add(new XYChart.Data(i, coefficient.get(i)));
            }
            core.getData().addAll(series_coefficient);
            core.setLegendVisible(false);
            core.setCreateSymbols(false);
            in.close();

            //filtration
            out_data = new long[in_data.size() + coefficient.size()];
            for(int n=0; n < in_data.size(); ++n) {
                for (int m = 0; m < coefficient.size(); ++m) {
                    out_data[n+m] += coefficient.get(m)*in_data.get(n);
                }
            }
            XYChart.Series series_out_data = new XYChart.Series();
            for (int i = 0; i < out_data.length; i++) {
                series_out_data.getData().add(new XYChart.Data(i, out_data[i]));
            }
            afh.getData().addAll(series_out_data);
            afh.setLegendVisible(false);
            afh.setCreateSymbols(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void save(){
        try {
            file = new File(out_data_file_path.getText() + out_data_file_name.getText());
            out = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            for (int i = 0; i < out_data.length; i++) {
                if (out_data_radix.getValue().equals("hex")) {
                    out.write(String.valueOf(Long.toHexString(out_data[i])+"\n"));
                } else {
                    out.write(String.valueOf(out_data[i]+"\n"));
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
