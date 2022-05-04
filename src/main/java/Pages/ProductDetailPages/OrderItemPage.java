package Pages.ProductDetailPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class OrderItemPage {
    @FindBy(css = ".product-name")
    private WebElement productName;
    @FindBy(css = ".product-price")
    private WebElement productPrice;
    @FindBy(css = ".product-quantity")
    private WebElement productQuantity;

    public OrderItemPage(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    public WebElement getProductName() {
        return productName;
    }

    public WebElement getProductPrice() {
        return productPrice;
    }

    public WebElement getProductQuantity() {
        return productQuantity;
    }
}
