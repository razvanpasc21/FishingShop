package com.mycompany.cart;

import com.mycompany.product.Product;

public class AddToCartRequest {
    private Integer userId;
    private Integer productId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProduct(Integer productId) {
        this.productId = productId;
    }
}
