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





import java.net.URL;
import java.util.ArrayList;
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
    private TextField Name;
    @FXML
    private TextField SellIn;
    @FXML
    private TextField Quality;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemSellIn.setCellValueFactory(new PropertyValueFactory<Item, String>("sellIn"));
        itemQuality.setCellValueFactory(new PropertyValueFactory<Item, String>("quality"));

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

        name = Name.getText();
        sellin = Integer.parseInt(SellIn.getText());
        qual = Integer.parseInt(Quality.getText());

        Item newItem = new Item(name, sellin, qual);

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



    public void SellInBarChart(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setWidth(500);
        stage.setHeight(500);
        scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
        stage.setTitle("Gilded Rose UI");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Historical SellIn");
        xAxis.setLabel("sellIn");
        yAxis.setLabel("number of items");

        Item[] it = inventory.getItems();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Date Sellin");
        series1.getData().add(new XYChart.Data(Integer.toString(it[0].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[1].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[2].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[3].getSellIn()), 1));
        series1.getData().add(new XYChart.Data(Integer.toString(it[4].getSellIn()), 1));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Date Sellin");
        series2.getData().add(new XYChart.Data(Integer.toString(it[0].getSellIn()), 1));
        series2.getData().add(new XYChart.Data(Integer.toString(it[1].getSellIn()), 1));
        series2.getData().add(new XYChart.Data(Integer.toString(it[2].getSellIn()), 1));
        series2.getData().add(new XYChart.Data(Integer.toString(it[3].getSellIn()), 1));
        series2.getData().add(new XYChart.Data(Integer.toString(it[4].getSellIn()), 1));

        Scene scene2 = new Scene(bc, 800, 600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene2);
        stage.show();
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
}


