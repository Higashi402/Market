package com.example.market.Entities;

import java.sql.Timestamp;

public class Order {
    private String card;
    private Integer number;
    private String status;
    private String nomenclature;
    private Timestamp creationDate;
    private Timestamp deliveryDate;

    // Геттеры и сеттеры
    public String getCard() { return card; }
    public void setCard(String card) { this.card = card; }
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNomenclature() { return nomenclature; }
    public void setNomenclature(String nomenclature) { this.nomenclature = nomenclature; }
    public Timestamp getCreationDate() { return creationDate; }
    public void setCreationDate(Timestamp creationDate) { this.creationDate = creationDate; }
    public Timestamp getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Timestamp deliveryDate) { this.deliveryDate = deliveryDate; }
}
