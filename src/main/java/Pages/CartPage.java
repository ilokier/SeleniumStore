package Pages;

import Models.Product;
import Pages.ProductDetailPages.CartItemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("CartPage.class");

    @FindBy(css = ".cart-item")
    private List<WebElement> cartItem;
    @FindBy(css = ".cart-total span:last-child")
    private WebElement totalCartPrice;
    @FindBy(css = "#cart-subtotal-shipping span:nth-child(2)")
    private WebElement shippingPrice;
    @FindBy(css = ".cart-summary-line")
    private List<WebElement> shippingDetails;

    public CartPage(WebDriver driver) {
        super(driver);
    }


    public List<Product> getCartProducts() {
        List<Product> cartProducts = new ArrayList<>();
        for (WebElement product : cartItem) {
            cartProducts.add(new Product((new CartItemPage(product).getItemName()),
                    getElementAttribute((new CartItemPage(product).getQuantityInput()), "value"),
                    getElementText(new CartItemPage(product).getCurrentPrice())));
        }
        log.info("Cart product list: " + cartProducts);
        return cartProducts;
    }

    public Map<String, Integer> getCartProductsToMap(List<WebElement> cartItem) {
        Map<String, Integer> items = new HashMap<>();
        for (WebElement item : cartItem) {
            String itemName = new CartItemPage(item).getItemName();
            int quantity = (new CartItemPage(item).getQuantityInputValue());
            items.put(itemName, quantity);
        }
        log.info("Cart products: " + items);
        return items;
    }

    public List<WebElement> getCartItemList() {
        return cartItem;
    }

    public void changeProducyQuantity(List<WebElement> cartItem) {
        sendKeysCombination(new CartItemPage(cartItem.get(0)).getQuantityInput(), "5");
        driver.navigate().refresh();
    }

    public double getTotalBasketPrice() {
        double totalBasketPrice = getDoubleValueFromPrice(totalCartPrice.getText());
        log.info("basket total: " + totalBasketPrice);
        return totalBasketPrice;
    }

    public double getShippingPrice() {
        double shipping = 0;
        if (shippingDetails.size() > 2) {
            if (shippingPrice.getText().equals("Free")) {
                shipping = 0;
            } else {
                shipping = getDoubleValueFromPrice(shippingPrice.getText());
            }
            log.info("Shipping: " + shipping);
        } else {
            log.info("Shipping is not present");
        }
        return shipping;
    }

    public double countTotalBasketValue(List<WebElement> cartItem) {
        double sum = 0;
        for (WebElement item : cartItem) {
            double totalItemPrice = new CartItemPage(item).getQuantityInputValue() * getDoubleValueFromPrice(new CartItemPage(item).getCurrentPrice().getText());
            sum = sum + totalItemPrice;
        }
        sum = Math.round(sum * 100.00) / 100.00;
        log.info("sum:" + sum);
        return sum;
    }

    public void substractQuantity(List<WebElement> cartItem) {
        clickOnElement(new CartItemPage(cartItem.get(0)).getSubstractQuantityArrow());
        driver.navigate().refresh();
    }

    public void addQuantity(List<WebElement> cartItem) {
        clickOnElement(new CartItemPage(cartItem.get(0)).getAddQuantityArrow());
        driver.navigate().refresh();
    }

    public int getQuantity(List<WebElement> cartItem) {
        int quantity = new CartItemPage(cartItem.get(0)).getQuantityInputValue();
        log.info("<<<<Quantity: " + quantity);
        return quantity;
    }

    public void deleteItem(int index) {
        clickOnElement(new CartItemPage(cartItem.get(index - 1)).getRemoveicon());
        driver.navigate().refresh();
        log.info("There is " + cartItem.size() + " items in cart");
    }

    public void makeBasketEmpty() {
        int i;
        for (i = cartItem.size(); i > 0; i--) {
            clickOnElement(new CartItemPage(cartItem.get(i - 1)).getRemoveicon());
            driver.navigate().refresh();
        }

    }
}
