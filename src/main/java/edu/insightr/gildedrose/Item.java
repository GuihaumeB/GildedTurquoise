package edu.insightr.gildedrose;

import java.util.Date;

public class Item {

    private String name;
    private int sellIn;
    private int quality;
    private Date date;

    public Item(String name, int sellIn, int quality, Date date) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", sellIn=" + sellIn +
                ", quality=" + quality +
                ", date=" + date.toString() +
                '}';
    }
}