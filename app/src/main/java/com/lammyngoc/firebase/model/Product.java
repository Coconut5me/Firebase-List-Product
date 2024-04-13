package com.lammyngoc.firebase.model;

import java.io.Serializable;

public class Product implements Serializable {
    private Long productId; // Changed from int to Long if necessary
    private String productName;
    private Long unitPrice; // Changed from int to Long if necessary
    private String imgLink;

    public Product() {
    }

    public Product(Long productId, String productName, Long unitPrice, String imgLink) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.imgLink = imgLink;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
