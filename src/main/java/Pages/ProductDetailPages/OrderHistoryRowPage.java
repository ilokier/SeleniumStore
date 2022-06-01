package Pages.ProductDetailPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderHistoryRowPage {
    private static Logger log = LoggerFactory.getLogger("OrderHistoryRowPage.class");

    public OrderHistoryRowPage(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    @FindBy(css = "[scope=row]")
    private WebElement orderReference;
    @FindBy(css = "td:nth-child(2)")
    private WebElement orderDate;
    @FindBy(css = "td:nth-child(3)")
    private WebElement orderTotalPrice;
    @FindBy(css = "td:nth-child(4)")
    private WebElement paymentOption;
    @FindBy(css = "td:nth-child(5)")
    private WebElement status;
    @FindBy(css = "td:nth-child(7) a:first-child")
    private WebElement orderDetailsLink;

    public String getOrderReference() {
        return orderReference.getText();
    }

    public String getOrderDate() {
        log.info("date: " + orderDate.getText());
        return orderDate.getText();
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice.getText();
    }

    public String getStatus() {
        return status.getText();
    }

    public WebElement getOrderDetailsLink() {
        return orderDetailsLink;
    }

    public String getPaymentOption() {
        return paymentOption.getText();
    }
}
