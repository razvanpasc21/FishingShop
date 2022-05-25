package com.mycompany.cart;

import com.mycompany.product.Product;

import java.util.List;

public class Cart {
    private Integer userId;
    private List<ProductWithCartId> productList;
    private Double totalPrice;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ProductWithCartId> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductWithCartId> productList) {
        this.productList = productList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
