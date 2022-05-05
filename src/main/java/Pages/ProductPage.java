package Pages;

import Models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("ProductPage.class");
    @FindBy(css = "h1[itemprop=name]")
    private WebElement productTitle;
    @FindBy(css = ".current-price span")
    private WebElement currentPrice;
    @FindBy(css = "#quantity_wanted")
    private WebElement quantityInput;
    @FindBy(css = ".add-to-cart")
    private WebElement addToCartButton;
    @FindBy(css = ".btn-secondary")
    private WebElement continueShoppingButton;
    @FindBy(css = "a.btn-primary")
    private WebElement proceedToCheckoutButton;
    @FindBy(css = ".shopping-cart")
    private WebElement cartButton;

    //popup
    @FindBy(css = "#myModalLabel i")
    private WebElement popUpTitle;
    @FindBy(css = ".product-name")
    private WebElement popUpProductName;
    @FindBy(css = "p.product-price")
    private WebElement popUpProductPrice;
    @FindBy(css = ".product-quantity strong")
    private WebElement popUpProductQuantity;
    @FindBy(css = "p.cart-products-count")
    private WebElement labelWithCartQuantity;
    @FindBy(css = ".product-total .value")
    private WebElement totalValue;
    @FindBy(css = ".product-message")
    private WebElement customProductText;
    @FindBy(css = "[name=submitCustomizedData]")
    private WebElement saveCustomizationButton;


    @FindBy(css = "span.cart-products-count")
    private WebElement cartIcon;

    public ProductPage(WebDriver driver) {
        super(driver);
    }


    public String setRandomQuantity(int maxQuantity) {
        String randomQuantity = getRandomNumberValue(5);
        if (productTitle.getText().contains("CUSTOMIZABLE")) {
            setCustomText();
        }
        scrollToElement(quantityInput);
        sendKeysToElement(quantityInput, randomQuantity);
        return randomQuantity;
    }


    public ProductPage addToCart() {
        scrollToElement(addToCartButton);
        clickOnElement(addToCartButton);
        return this;
    }

    public void proceedToCheckout() {
        clickOnElement(proceedToCheckoutButton);
    }

    public void goToCart() {
        clickOnElement(cartButton);
    }

    public void continueShoping() {
        clickOnElement(continueShoppingButton);
    }

    public int getCartQuantity() {
        String cartQuantity = getElementText(cartIcon);
        String quantity = cartQuantity.replace("(", "").replace(")", "");
        int numberOfItems = Integer.parseInt(quantity);
        log.info("Actual cart quantity: " + quantity);
        return numberOfItems;
    }

    public Product getRandomProduct() throws InterruptedException {
        waitForElementToBeClickable(addToCartButton);
        Product randomProduct = new Product(
                getElementText(productTitle),
                getElementAttribute(quantityInput, "value"),
                getElementText(currentPrice));
        log.info("Random product: " + randomProduct);
        return randomProduct;
    }

    public Product getPopUpProduct() {
        Product popupProduct = new Product(
                getElementText(popUpProductName),
                getElementText(popUpProductQuantity),
                getElementText(popUpProductPrice));
        log.info("Pop up product: " + popupProduct);
        return popupProduct;
    }

    public String getLabelWithQuantity() {
        String label = getElementText(labelWithCartQuantity);
        log.info("Label: " + label);
        return label;
    }

    public Map<String, Integer> countQuantity(List<Product> products) {
        Map<String, Integer> quantityMap = new HashMap<>();
        for (Product product : products) {
            String name = product.getName();
            int quantity = quantityMap.containsKey(name) ? quantityMap.get(name) : 0;
            quantity += Integer.parseInt(product.getQuantity());
            quantityMap.put(name, quantity);
        }
        log.info("Added products: " + quantityMap);
        return quantityMap;
    }

    private void setCustomText() {
        sendKeysToElement(customProductText, getRandomFirstName());
        clickOnElement(saveCustomizationButton);
    }

//     public List<Product> getProducts(Product product) {
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//        log.info("Product list: " + products);
//        return products;
//    }

}
