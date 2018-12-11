package edu.insightr.gildedrose;

import java.util.Date;

public class Item {

    private String name;
    private int sellIn;
    private int quality;
    private Date buyDate;
    private Date sellDate;
    private int buyPrice;
    private int sellPrice;

    public Item(){
        super();
        name = "tmp";
        sellIn = 0;
        quality = 0;
        buyDate = null;
        sellDate = null;
        buyPrice = 0;
        sellPrice = 0;
    }
    public Item(String name, int sellIn, int quality, Date BuyDate) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.buyDate = BuyDate;
        sellDate = null;
        buyPrice = 0;
        sellPrice = 0;
    }
    public Item(String name, int sellIn, int quality, Date BuyDate, Date sellDate,int BuyPrice, int sellPrice) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.buyDate = BuyDate;
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
        return buyDate;
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
        this.buyDate = date;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", sellIn=" + sellIn +
                ", quality=" + quality +
                ", buyDate=" + buyDate.toString() +
                ", sellDate=" + sellDate.toString() +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }
}