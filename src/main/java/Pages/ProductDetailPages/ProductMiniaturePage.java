package Pages.ProductDetailPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductMiniaturePage {
    private static Logger log = LoggerFactory.getLogger("ProductMiniaturePage.class");

    public ProductMiniaturePage(WebElement productMiniature) {
        PageFactory.initElements(new DefaultElementLocatorFactory(productMiniature), this);
    }

    @FindBy(css = ".product-title a")
    private WebElement productTitle;
    @FindBy(css = ".product-price-and-shipping .price")
    private WebElement productPrice;
    @FindBy(css = ".regular-price")
    private WebElement productRegularPrice;
    @FindBy(css = ".discount")
    private WebElement discount;

    public double getProductPriceValue() {
        String price = productPrice.getText();
        price = price.replace("$", "");
        double priceValue = Double.parseDouble(price);
        log.info("Price is: " + priceValue);
        return priceValue;
    }

    public double getRegularPriceValue() {
        String price = productRegularPrice.getText();
        price = price.replace("$", "");
        double priceValue = Double.parseDouble(price);
        log.info("Regular price was: " + priceValue);
        return priceValue;
    }

    public int getDiscountValue() {
        String discount = getDiscount();
        discount = discount.replace("%", "").replace("-", "");
        //String discount2 = discount.replace("-", "");
        log.info(discount);
        int priceValue = Integer.parseInt(discount);
        log.info("Discount is: " + priceValue);
        return priceValue;
    }

    public String getProductTitle() {
        String title = productTitle.getText();
        return title;
    }

    public String getDiscount() {
        return discount.getText();
    }

    public boolean isPriceDisplayed() {
        productPrice.isDisplayed();
        return true;
    }

    public boolean isDiscountDisplayed() {
        discount.isDisplayed();
        return true;
    }

    public boolean isRegularPriceDisplayed() {
        productRegularPrice.isDisplayed();
        return true;
    }

}
