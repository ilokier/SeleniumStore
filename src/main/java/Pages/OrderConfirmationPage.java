package Pages;

import Models.Order;
import Models.OrderBuilder;
import Models.Product;
import Pages.ProductDetailPages.OrderConfirmationItemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationPage extends BasePage {
    Order order;
    private static Logger log = LoggerFactory.getLogger("OrderConfirmationPage.class");

    @FindBy(css = ".order-line")
    private List<WebElement> orderProductsList;
    @FindBy(css = "#order-details li")
    private List<WebElement> orderDetails;
    @FindBy(css = "[title=Orders]")
    private WebElement orders;

    @FindBy(xpath = "//tr[1]/td[2]")
    private WebElement subtotalPrice;

    @FindBy(xpath = "//tr[3]/td[2]")
    private WebElement totalOrderValue;

    @FindBy(xpath = "//tr[2]/td[2]")
    private WebElement shippingPrice;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public void goToOrderHistory() {
        clickOnElement(orders);
    }

    public List<Product> getOrderProducts() {
        List<Product> products = new ArrayList<>();
        for (WebElement product : orderProductsList) {
            products.add(new Product((new OrderConfirmationItemPage(product).getProductName()),
                    getQuantityValueOfProduct(getElementText(new OrderConfirmationItemPage(product).getProductQuantity())),
                    getElementText(new OrderConfirmationItemPage(product).getProductPrice())));
        }
        return products;
    }

    public Order getOrderDetails(List<Product> products) {
        Order order = new OrderBuilder()
                .orderReference(getOrderNumber())
                .products(products)
                .totalCost(getTotalCost())
                .paymentMethod(getPaymentMethod())
                .shippingMethod(getShippingMethod())
                .shippingCost(getShippingCost()).build();
        log.info("Confirmed order details: " + order);
        return order;
    }

    public String getOrderNumber() {
        String orderNumber = getElementText(orderDetails.get(0));
        String orderNumber2 = orderNumber.replaceAll(".*: ", "");
        log.info("Order reference: " + orderNumber2);
        return orderNumber2;
    }

    public String getPaymentMethod() {
        return getElementText(orderDetails.get(1));//substring
    }

    public String getShippingMethod() {
        return getElementText(orderDetails.get(2));//substring
    }

    private String getShippingCost() {
        String shippingCost = getElementText(shippingPrice);
        return shippingCost;
    }

    private String getTotalCost() {
        String totalCost = getElementText(totalOrderValue);
        return totalCost;
    }

    private String getSubtotalCost() {
        String subtotal = getElementText(subtotalPrice);
        return subtotal;
    }

}
