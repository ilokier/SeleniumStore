package Pages.ProductDetailPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class OrderHistoryItemPage {

    public OrderHistoryItemPage(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    @FindBy(css = "strong a")
    private WebElement productName;
    @FindBy(css = "td:nth-child(3)")
    private WebElement productPrice;
    @FindBy(css = "td:nth-child(2)")
    private WebElement productQuantity;

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
