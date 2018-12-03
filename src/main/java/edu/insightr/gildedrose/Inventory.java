package edu.insightr.gildedrose;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;


import org.apache.commons.io.FileUtils;
import com.google.gson.annotations.SerializedName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




public class Inventory {

    private Item[] items;

    public Inventory(Item[] items) {
        super();
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) { this.items = items; }

    public Inventory() {
        super();
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            items = new Item[]{
                    new Item("+5 Dexterity Vest", 10, 20, textFormat.parse("2017-10-05")),
                    new Item("Aged Brie", 2, 0, textFormat.parse("2018-11-07")),
                    new Item("Elixir of the Mongoose", 5, 7, textFormat.parse("2016-10-07")),
                    new Item("Sulfuras, Hand of Ragnaros", 0, 80, textFormat.parse("2018-10-17")),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20, textFormat.parse("2018-08-07")),
                    new Item("Conjured Mana Cake", 3, 6, textFormat.parse("2018-03-27"))
            };
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }

    public void updateQuality() {
        for (Item item : items) {
            if ("Sulfuras, Hand of Ragnaros".equals(item.getName())) continue;
            if ("Aged Brie".equals(item.getName())) {
                item.setSellIn(item.getSellIn() - 1);
                increaseQuality(item);
                if (item.getSellIn() < 0) increaseQuality(item);
            } else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
                item.setSellIn(item.getSellIn() - 1);
                increaseQuality(item);

                if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
                    if (item.getSellIn() < 10) increaseQuality(item);

                    if (item.getSellIn() < 5) increaseQuality(item);

                    if (item.getSellIn() < 0) item.setQuality(0);
                }
            }

            //Looking for names containing "Conjured"
            else if (item.getName().matches(".*Conjured.*")) {
                item.setSellIn(item.getSellIn() - 1);
                decreaseQuality(item);
                decreaseQuality(item);
            } else {
                item.setSellIn(item.getSellIn() - 1);
                decreaseQuality(item);

                if (item.getSellIn() < 0) decreaseQuality(item);
            }

        }
    }

    protected void decreaseQuality(Item item) {
        if (item.getQuality() > 0) {
            item.setQuality(item.getQuality() - 1);
        }
    }

    protected void increaseQuality(Item item) {
        if (item.getQuality() < 50) {
            item.setQuality(item.getQuality() + 1);
        }
    }

    static void json(){

        JSONObject object = new JSONObject ();
        JSONArray keys = object.names ();

        for (int i = 0; i < keys.length (); ++i) {

            String key = keys.getString (i); // Here's your key
            String value = object.getString (key); // Here's your value

        }


    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        for (int i = 0; i < 10; i++) {
            inventory.updateQuality();
            inventory.printInventory();
        }

        json();


    }
}
