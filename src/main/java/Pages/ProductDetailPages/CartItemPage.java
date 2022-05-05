package Pages.ProductDetailPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartItemPage {
    private static Logger log = LoggerFactory.getLogger("CartItemPage.class");

    public CartItemPage(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    @FindBy(css = ".product-line-info a")
    private WebElement itemName;
    @FindBy(css = ".current-price span")
    private WebElement currentPrice;
    @FindBy(css = ".js-cart-line-product-quantity")
    private WebElement quantityInput;
    @FindBy(css = ".product-price strong")
    private WebElement currentAmmountPrice;
    @FindBy(css = ".touchspin-down")
    private WebElement substractQuantityArrow;
    @FindBy(css = ".bootstrap-touchspin-up")
    private WebElement addQuantityArrow;
    @FindBy(css = ".remove-from-cart i")
    private WebElement removeicon;

    public WebElement getSubstractQuantityArrow() {
        return substractQuantityArrow;
    }

    public WebElement getAddQuantityArrow() {
        return addQuantityArrow;
    }


    public String getItemName() {
        return itemName.getText();
    }

    public WebElement getCurrentPrice() {
        return currentPrice;
    }

    public WebElement getQuantityInput() {
        return quantityInput;
    }

    public int getQuantityInputValue() {
        String quantity = quantityInput.getAttribute("value");
        return Integer.parseInt(quantity);
    }

    public WebElement getCurrentAmmountPrice() {
        return currentAmmountPrice;
    }

    public WebElement getRemoveicon() {
        return removeicon;
    }
}
