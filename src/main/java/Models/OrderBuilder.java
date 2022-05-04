package Models;

import java.util.List;

public class OrderBuilder {
    private String orderReference;
    private List<Product> products;
    private String totalCost;
    private String paymentMethod;
    private String shippingMethod;
    private String shippingCost;
    private String status;
    private String date;
    private String address;


    public OrderBuilder orderReference(String orderReference) {
        this.orderReference = orderReference;
        return this;
    }

    public OrderBuilder products(List<Product> products) {
        this.products = products;
        return this;
    }

    public OrderBuilder totalCost(String totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderBuilder paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderBuilder shippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
        return this;
    }

    public OrderBuilder shippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderBuilder status(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder date(String date) {
        this.date = date;
        return this;
    }

    public OrderBuilder address(String address) {
        this.address = address;
        return this;
    }

    public Order build() {
        return new Order(orderReference, products, totalCost, paymentMethod, shippingMethod, shippingCost, status, date);
    }

}


