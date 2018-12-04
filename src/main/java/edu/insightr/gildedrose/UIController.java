package edu.insightr.gildedrose;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.security.Key;
import java.util.Date;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    private Inventory inventory = jsonDeserialize();

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
    private DatePicker Date;

    @FXML
    protected Inventory jsonDeserialize()
    {
        Inventory inv = new Inventory();

        String jsonContent = "";

        try
        {
            BufferedReader br = new BufferedReader(new FileReader("src/main/ressources/inventory.json"));
            String st;
            while ((st = br.readLine())!= null) {
                jsonContent += st;
            }
            ItemList items = new Gson().fromJson(jsonContent, ItemList.class);
            inv.setItems(items);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return inv;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemSellIn.setCellValueFactory(new PropertyValueFactory<Item, String>("sellIn"));
        itemQuality.setCellValueFactory(new PropertyValueFactory<Item, String>("quality"));
        itemDate.setCellValueFactory(new PropertyValueFactory<Item, String>("date"));

        tableView1.getItems().setAll(inventory.getItems());
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
        date = java.sql.Date.valueOf(Date.getValue());
        Item newItem = new Item(name, sellin, qual, date);

        Item[] items = new Item[this.inventory.getItems().length+1];

        for( int i = 0; i < this.inventory.getItems().length; i++)
        {
            items[i] = this.inventory.getItems()[i];
        }

        items[this.inventory.getItems().length] = newItem;

        inventory.setItems(items);

        tableView1.getItems().setAll(inventory.getItems());
        tableView1.getItems();
        tableView1.refresh();
    }

    public int dateCounter(Item[] items){
        int occurences = 0;
        for (int i = 0; i < items.length; i++){

            for (int j = 0; j < i; j++){
                if(items[i].getDate().compareTo(items[j].getDate()) == 0){
                    occurences++;
                }
            }
        }

        return occurences;
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

        xAxis.setLabel("SellIn");
        yAxis.setLabel("Number of items");

        xAxis1.setLabel("Date");
        yAxis1.setLabel("Number of items");

        Item[] it = inventory.getItems();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Date Sellin");
        for (Item item : it) {
            series1.getData().add(new XYChart.Data(Integer.toString(item.getSellIn()),1));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Date");

        for (Item item : it) {
            int occurences = dateCounter(it);
            System.out.println(occurences);
            series2.getData().add(new XYChart.Data(item.getDate().toString(), occurences));
        }

        Scene scene2 = new Scene(bc, 800, 600);
        Scene scene3 = new Scene(bc1,800,600);
        bc.getData().addAll(series1);
        bc1.getData().addAll(series2);
        stage.setScene(scene2);
        stage1.setScene(scene3);
        stage.show();
        stage1.show();
    }

    public int KeyWordCounter(String keyword)
    {
        Item[] it = inventory.getItems();
        int compteur = 0;
        for (int i=0; i<it.length; i++)
        {

            if (it[i].getName().matches(".*"+keyword+".*"))
            {
                compteur++;
            }
        }
        return compteur;
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
                        new PieChart.Data("Vest", KeyWordCounter("Vest")),
                        new PieChart.Data("Brie", KeyWordCounter("Brie")),
                        new PieChart.Data("Elixir", KeyWordCounter("Elixir")),
                        new PieChart.Data("Conjured", KeyWordCounter("Conjured")),
                        new PieChart.Data("Sulfuras", KeyWordCounter("Sulfuras")),
                        new PieChart.Data("Backstage Pass", KeyWordCounter("Backstage")));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Inventory piechart");

        ((Group) scene2.getRoot()).getChildren().add(chart);

        // Specifies the modality for new window.
        stage2.initModality(Modality.WINDOW_MODAL);

        stage2.setTitle("GUI");
        stage2.setScene(scene2);

        stage2.show();
    }
}


