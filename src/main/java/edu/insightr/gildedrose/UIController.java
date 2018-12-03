package edu.insightr.gildedrose;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.*;

import org.apache.commons.io.FileUtils;
import com.google.gson.annotations.SerializedName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    private Inventory inventory = new Inventory();

    @FXML
    private TableView<Item> tableView1;
    @FXML
    private TableColumn<Item, String> itemName;
    @FXML
    private TableColumn<Item, String> itemSellIn;
    @FXML
    private TableColumn<Item, String> itemQuality;
    @FXML
    private TableColumn<Item, String> itemDate;
    @FXML
    private TextField Name;
    @FXML
    private TextField SellIn;
    @FXML
    private TextField Quality;
    @FXML
    private TextField Date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemSellIn.setCellValueFactory(new PropertyValueFactory<Item, String>("sellIn"));
        itemQuality.setCellValueFactory(new PropertyValueFactory<Item, String>("quality"));
        itemDate.setCellValueFactory(new PropertyValueFactory<Item, String>("date"));

        tableView1.getItems().setAll(inventory.getItems());

        List<String> inv = new ArrayList<String>();
        inv.add("+5 Dexterity Vest");
        inv.add("Aged Brie");
        inv.add("Elixir of the Mongoose");
        inv.add("Sulfuras, Hand of Ragnaros");
        inv.add("Backstage passes to a TAFKAL80ETC concert");
        inv.add("Conjured Mana Cake");
    }

    @FXML
    public void updateInventory(ActionEvent event){
        inventory.updateQuality();
        tableView1.refresh();
    }

    @FXML
    public void add(ActionEvent event){
        String name;
        int sellin;
        int qual;
        Date date;

        name = Name.getText();
        sellin = Integer.parseInt(SellIn.getText());
        qual = Integer.parseInt(Quality.getText());
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
        date = new SimpleDateFormat("yyyy-mm-dd").parse(Date.toString());
            Item newItem = new Item(name, sellin, qual, date);




        Item[] items = new Item[this.inventory.getItems().length+1];

        for( int i = 0; i < this.inventory.getItems().length; i++)
        {
            items[i] = this.inventory.getItems()[i];
        }

        items[this.inventory.getItems().length] = newItem;


        inventory = new Inventory(items);

        tableView1.getItems().setAll(inventory.getItems());
        tableView1.getItems();
        tableView1.refresh();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }





    public void SellInBarChart(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Stage stage1 = new Stage();

        Scene scene = new Scene(new Group());
        stage.setWidth(500);
        stage.setHeight(500);
        scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
        stage.setTitle("Gilded Rose UI");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();

        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);

        final BarChart<String, Number> bc1 =
                new BarChart<String, Number>(xAxis1, yAxis1);

        bc.setTitle("Historical SellIn");

        bc1.setTitle("Historical Date");

        xAxis.setLabel("sellIn");
        yAxis.setLabel("number of items");

        xAxis1.setLabel("Date");
        yAxis1.setLabel("number of items");

        Item[] it = inventory.getItems();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Date Sellin");
        series1.getData().add(new XYChart.Data(Integer.toString(it[0].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[1].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[2].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[3].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[4].getSellIn()), 1));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Date");
        series2.getData().add(new XYChart.Data(it[0].getDate().toString(), 1));
        series2.getData().add(new XYChart.Data(it[1].getDate().toString(), 1));
        series2.getData().add(new XYChart.Data(it[2].getDate().toString(), 1));
        series2.getData().add(new XYChart.Data(it[3].getDate().toString(), 1));
        series2.getData().add(new XYChart.Data(it[4].getDate().toString(), 1));

        Scene scene2 = new Scene(bc, 800, 600);
        Scene scene3 = new Scene(bc1,800,600);
        bc.getData().addAll(series1);
        bc1.getData().addAll(series2);
        stage.setScene(scene2);
        stage1.setScene(scene3);
        stage.show();
        stage1.show();
    }

    public void displayInventory(ActionEvent actionEvent) {

        Stage stage2 = new Stage();
        Scene scene2 = new Scene(new Group());
        stage2.setWidth(500);
        stage2.setHeight(500);
        scene2.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
        stage2.setTitle("Gilded Rose UI");

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Vest", 1),
                        new PieChart.Data("Aged Brie", 1),
                        new PieChart.Data("Elixir", 2),
                        new PieChart.Data("Sulfuras", 1),
                        new PieChart.Data("Backstage Pass", 1),
                        new PieChart.Data("Conjured", 2));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Inventory piechart");

        ((Group) scene2.getRoot()).getChildren().add(chart);
        //secondaryLayout.getChildren().add(chart);

        // Specifies the modality for new window.
        stage2.initModality(Modality.WINDOW_MODAL);

        stage2.setTitle("GUI");
        stage2.setScene(scene2);

        stage2.show();
    }


    public void json(){
        ArrayList<org.json.simple.JSONObject> json=new ArrayList<org.json.simple.JSONObject>();
        org.json.simple.JSONObject obj;
        // The name of the file to open.
        String fileName = "src/main/ressources/inventory.json";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                json.add(obj);
                System.out.println((String)obj.get("name")+":"+
                        (String)obj.get("date"));
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}


