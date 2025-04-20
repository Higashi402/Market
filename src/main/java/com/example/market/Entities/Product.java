package com.example.market.Entities;

public class Product {
    private String articleNumber;
    private String title;
    private String manufacturer;
    private String description;
    private double price;
    private int image;
    private int deliveryDate;

    public Product(String articleNumber, String title, String manufacturer, String description, double price, int image, int deliveryDate) {
        this.articleNumber = articleNumber;
        this.title = title;
        this.manufacturer = manufacturer;
        this.description = description;
        this.price = price;
        this.image = image;
        this.deliveryDate = deliveryDate;
    }

    public Product() { }

    public String getArticleNumber() { return articleNumber; }
    public String getTitle() { return title; }
    public String getManufacturer() { return manufacturer; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImage() { return image; }
    public int getDeliveryDate() { return deliveryDate; }
}

