package Models;

import java.util.List;

public class Order {
    private String orderReference;
    private List<Product> products;
    private String totalCost;
    private String paymentMethod;
    private String shippingMethod;
    private String shippingCost;
    private String status;
    private String date;


    public Order(String orderReference, List<Product> products, String totalCost, String paymentMethod, String shippingMethod, String shippingCost, String status, String date) {
        this.orderReference = orderReference;
        this.products = products;
        this.totalCost = totalCost;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.shippingCost = shippingCost;
        this.status = status;
        this.date = date;

    }

    public Order(String orderReference, List<Product> products, String paymentMethod, String shippingMethod, String shippingCost) {
        this.orderReference = orderReference;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.shippingCost = shippingCost;

    }


    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderReference='" + orderReference + '\'' +
                ", products=" + products.toString() +
                ", totalCost='" + totalCost + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", shippingCost='" + shippingCost + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
