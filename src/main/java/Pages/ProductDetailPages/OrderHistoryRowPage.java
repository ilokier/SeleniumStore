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
    @FindBy(xpath = "//td[1]")
    private WebElement orderDate;
    @FindBy(xpath = "//td[2]")
    private WebElement orderTotalPrice;
    @FindBy(xpath = "//td[3]")
    private WebElement paymentOption;
    @FindBy(xpath = "//td[4]")
    private WebElement status;
    @FindBy(xpath = "//td[6] /a[1]")
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
