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
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    private Inventory inventory = jsonDeserialize("src/main/ressources/inventory.json");
    private Inventory ListBuy = jsonDeserialize("src/main/ressources/StockBuy.json");

    private List<Integer> buyHistory = new ArrayList<>();
    private Inventory ListSell = new Inventory(new Item[0]);
    // private Inventory ListSell = jsonDeserialize("src/main/ressources/StockSell.json");

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
    private TableColumn<Item, String> itemPrice;
    @FXML
    private TextField Name;
    @FXML
    private TextField SellIn;
    @FXML
    private TextField Quality;
    @FXML
    private DatePicker Date;
    @FXML
    private TextField Price;
    @FXML
    private TextField Name2;

    @FXML
    private Inventory jsonDeserialize(String file)
    {
        Inventory inv = new Inventory();
        String jsonContent = "";

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine())!= null) {
                jsonContent += st;
            }
            ItemList items = new Gson().fromJson(jsonContent, ItemList.class);
            inv.setItems(items);
            br.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return inv;
    }

    public void jsonSerialize(ActionEvent event){
        Item[] it = inventory.getItems();
        ItemList items = new ItemList();
        items.setItems(it);

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/ressources/inventory.json"));
            String st;
            st = new Gson().toJson(items, ItemList.class);
            bw.write(st);
            bw.close();
            System.out.println("Save complete.");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    private void jsonSerialize2(String file){
        Item[] it = ListBuy.getItems();
        ItemList items = new ItemList();
        items.setItems(it);

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String st;
            st = new Gson().toJson(items, ItemList.class);
            bw.write(st);
            bw.close();
            System.out.println("Save complete.");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    private void jsonSerialize3(String file){
        Item[] it = ListSell.getItems();
        ItemList items = new ItemList();
        items.setItems(it);

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String st;
            st = new Gson().toJson(items, ItemList.class);
            bw.write(st);
            bw.close();
            System.out.println("Save complete.");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemSellIn.setCellValueFactory(new PropertyValueFactory<>("sellIn"));
        itemQuality.setCellValueFactory(new PropertyValueFactory<>("quality"));
        itemDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));

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
        int price;

        name = Name.getText();
        sellin = Integer.parseInt(SellIn.getText());
        qual = Integer.parseInt(Quality.getText());
        price = Integer.parseInt(Price.getText());

        //converting local date to java.util.date
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = Date.getValue();
        date = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        Item newItem = new Item(name, sellin, qual, date, price);

        Item[] items = new Item[this.inventory.getItems().length+1];

        for( int i = 0; i < this.inventory.getItems().length; i++)
        {
            items[i] = this.inventory.getItems()[i];
        }

        items[this.inventory.getItems().length] = newItem;

        inventory.setItems(items);

        //Area to create a new inventory StockBuy, fill it and serialize it
        Item[] buyListitems = new Item[ListBuy.getItems().length+1];

        for( int i = 0; i < ListBuy.getItems().length; i++)
        {
            buyListitems[i] = ListBuy.getItems()[i];
        }

        buyListitems[ListBuy.getItems().length] = newItem;

        ListBuy.setItems(buyListitems);
        jsonSerialize2("src/main/ressources/StockBuy.json");

        tableView1.getItems().setAll(inventory.getItems());
        tableView1.getItems();
        tableView1.refresh();
    }

    private int dateCounter(Item[] items){
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

    private void BuyDateCounter() {
        int cpt = 0;
        for(int i = 0; i < ListBuy.getItems().length; i++){
            for (int j = 0; j < ListBuy.getItems().length; j++){
                if (ListBuy.getItems()[j].getDate() == ListBuy.getItems()[i].getDate()){
                    cpt++;
                }
            }
            buyHistory.add(cpt);
        }
    }

    public void SellInBarChart(ActionEvent actionEvent) {
        BuyDateCounter();

        Stage stage = new Stage();
        Stage stage1 = new Stage();
        Stage stage3 = new Stage();

        Scene scene = new Scene(new Group());
        stage.setWidth(500);
        stage.setHeight(500);
        scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
        stage.setTitle("Gilded Rose UI");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();

        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();

        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);

        final BarChart<String, Number> bc1 =
                new BarChart<>(xAxis1, yAxis1);

        final BarChart<String, Number> bc2 = new BarChart<>(xAxis2, yAxis2);

        bc.setTitle("Historical SellIn");

        bc1.setTitle("Historical Date");

        bc2.setTitle("Buy History");

        xAxis.setLabel("SellIn");
        yAxis.setLabel("Number of items");

        xAxis1.setLabel("Date");
        yAxis1.setLabel("Number of items");

        xAxis2.setLabel("Date");
        yAxis2.setLabel("Number of items bought");

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
            series2.getData().add(new XYChart.Data(item.getDate().toString(), occurences));
        }

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Buy History");
        for (int i = 0; i < buyHistory.size(); i++) {
            series3.getData().add(new XYChart.Data(ListBuy.getItems()[i].getDate().toString(), buyHistory.get(i)));
        }

        Scene scene2 = new Scene(bc, 800, 600);
        Scene scene3 = new Scene(bc1,800,600);
        Scene scene4 = new Scene(bc2, 800,600);
        bc.getData().addAll(series1);
        bc1.getData().addAll(series2);
        bc2.getData().addAll(series3);
        stage.setScene(scene2);
        stage1.setScene(scene3);
        stage3.setScene(scene4);
        stage.show();
        stage1.show();
        stage3.show();
    }

    private int KeyWordCounter(String keyword)
    {
        Item[] it = inventory.getItems();
        int compteur = 0;
        for (Item i : it)
        {
            if (i.getName().matches(".*"+keyword+".*"))
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

    public void sell(ActionEvent actionEvent) {
        String Name = Name2.getText();
        Item[] It = inventory.getItems();
        Item[] Init = new Item[ListSell.getItems().length + 1];
        System.out.println(Init.length);
        for(int i = 0; i<ListSell.getItems().length; i++){
            Init[i] = ListSell.getItems()[i];
        }
        Item[] It2 = new Item[inventory.getItems().length - 1];
        for (Item i: It){
            if(i.getName().matches(Name)){
                Init[Init.length - 1] = i;
                boolean marker = false;
                for(int j = 0; j<It.length - 1; j++){
                    if(It[j].getName().matches(Name) || marker){
                        It2[j] = It[j+1];
                        marker = true;
                    }
                    else
                        It2[j] = It[j];
                }
                inventory.setItems(It2);
            }
        }
        ListSell.setItems(Init);
        jsonSerialize3("src/main/ressources/StockSell.json");
    }
}