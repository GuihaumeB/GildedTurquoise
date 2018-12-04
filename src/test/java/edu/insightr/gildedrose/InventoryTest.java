package edu.insightr.gildedrose;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void updateQuality() {

        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Item itemTest = new Item("+5 Dexterity Vest", 9, 19, textFormat.parse("2017-10-05"));

            Item[] itemsTest = new Item[1];
            itemsTest[0] = itemTest;
            Inventory inventTest = new Inventory(itemsTest);


            inventTest.updateQuality();

            assertEquals(18, inventTest.getItems()[0].getQuality());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void sellInLessThanZero() {
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Item[] items = new Item[1];
        Item vest = new Item("+5 Dexterity Vest", 1, 6, textFormat.parse("2017-10-05") );
        items[0] = vest;
        Inventory inventTest = new Inventory(items);

        vest.setSellIn(0);
        inventTest.updateQuality();

        assertEquals(4, vest.getQuality());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update_Conjured_Mana_Cake() {
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Item itemTest = new Item("Conjured Mana Cake", 5, 10, textFormat.parse("2018-03-27"));
        Item[] itemsTest = new Item[1];
        itemsTest[0] = itemTest;
        Inventory inventTest = new Inventory(itemsTest);


        inventTest.updateQuality();

        assertEquals(8, inventTest.getItems()[0].getQuality());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update_Backstage_passes_4_days() {
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Item itemTest = new Item("Backstage passes to a TAFKAL80ETC concert", 4, 30, textFormat.parse("2018-08-07"));
        Item[] itemsTest = new Item[1];
        itemsTest[0] = itemTest;
        Inventory inventTest = new Inventory(itemsTest);


        inventTest.updateQuality();

        assertEquals(33, inventTest.getItems()[0].getQuality());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update_Backstage_passes_8_days() {
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Item itemTest = new Item("Backstage passes to a TAFKAL80ETC concert", 8, 30, textFormat.parse("2018-08-07"));
        Item[] itemsTest = new Item[1];
        itemsTest[0] = itemTest;
        Inventory inventTest = new Inventory(itemsTest);


        inventTest.updateQuality();

        assertEquals(32, inventTest.getItems()[0].getQuality());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update_Backstage_passes_12_days() {

        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Item itemTest = new Item("Backstage passes to a TAFKAL80ETC concert", 12, 30, textFormat.parse("2018-08-07"));
        Item[] itemsTest = new Item[1];
        itemsTest[0] = itemTest;
        Inventory inventTest = new Inventory(itemsTest);


        inventTest.updateQuality();

        assertEquals(31, inventTest.getItems()[0].getQuality());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update_Aged_Brie() {
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

        Item itemTest = new Item("Aged Brie", 10, 30, textFormat.parse("2018-11-07"));
        Item[] itemsTest = new Item[1];
        itemsTest[0] = itemTest;
        Inventory inventTest = new Inventory(itemsTest);


        inventTest.updateQuality();

        assertEquals(31, inventTest.getItems()[0].getQuality());

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}