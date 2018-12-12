package edu.insightr.gildedrose;

import java.util.Date;

public class Item {

    private String name;
    private int sellIn;
    private int quality;
    private Date date;
    private Date sellDate;
    private int buyPrice;
    private int sellPrice;

    public Item(){
        super();
        name = "tmp";
        sellIn = 0;
        quality = 0;
        date = null;
        sellDate = null;
        buyPrice = 0;
        sellPrice = 0;
    }
    public Item(String name, int sellIn, int quality, Date date) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.date = date;
        sellDate = null;
        buyPrice = 0;
        sellPrice = 0;
    }
    public Item(String name, int sellIn, int quality, Date date, Date sellDate, int BuyPrice, int sellPrice) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.date = date;
        this.sellDate = sellDate;
        this.buyPrice = BuyPrice;
        this.sellPrice = sellPrice;
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

    public Date getSellDate() {
        return sellDate;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
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
                ", sellDate=" + sellDate.toString() +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }
}