package Pages.ProductDetailPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class OrderConfirmationItemPage {
    //product
    @FindBy(css = ".order-line span:not(.image)")
    private WebElement productName;
    @FindBy(css = ".order-line .row div:first-child.text-sm-center")
    private WebElement productPrice;
    @FindBy(css = ".order-line .row div:nth-child(2).text-sm-center")
    private WebElement productQuantity;


    public OrderConfirmationItemPage(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    public String getProductName() {
        String name = productName.getText();
        name = name.replaceAll(" - .*", "");
        return name;
    }

    public WebElement getProductPrice() {
        return productPrice;
    }

    public WebElement getProductQuantity() {
        return productQuantity;
    }
}


