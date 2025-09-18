package com.samsung.fakestore.dto;

import java.util.List;

public class CartDTO {
    private int id;
    private int userId;
    private String date;
    private List<CartProductRef> products;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public List<CartProductRef> getProducts() { return products; }
    public void setProducts(List<CartProductRef> products) { this.products = products; }
}
