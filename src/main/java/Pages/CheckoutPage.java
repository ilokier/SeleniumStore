package Pages;

import Models.Product;
import Pages.ProductDetailPages.OrderItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("CheckoutPage.class");
    @FindBy(css = "[name=submitCreate]+[name=continue]")
    private WebElement continueRegisterButton;
    //adress
    @FindBy(css = "[name=address1]")
    private WebElement adress;
    @FindBy(css = "[name=address2]")
    private WebElement adressComplement;
    @FindBy(css = "id_state")
    private WebElement selectState;//US
    @FindBy(css = "[name=id_country]")
    private WebElement selectCountry;
    @FindBy(css = "[name=postcode]")
    private WebElement postalCode;
    @FindBy(css = "[name=city]")
    private WebElement city;
    @FindBy(css = "#use_same_address")
    private WebElement checkSameAdressForInvoice;
    @FindBy(css = "#checkout-addresses-step .step-edit")
    private WebElement editAddress;
    @FindBy(css = ".address")
    private WebElement chosenAddress;
    @FindBy(css = "[name=confirm-addresses]")
    private WebElement confirmAddressButton;

    @FindBy(css = ".cart-summary-products a")
    private WebElement cartSummaryCollapse;
    @FindBy(css = ".media-body")
    private List<WebElement> productsSummaryList;

    @FindBy(css = ".delivery-option")
    private List<WebElement> deliveryOptionRow;
    @FindBy(css = ".delivery-options .custom-radio")
    private List<WebElement> deliveryOption;


    @FindBy(css = "[name=confirmDeliveryOption]")
    private WebElement confirmShippingButton;

    @FindBy(css = "#payment-option-2")
    private WebElement paymentBankWire;

    @FindBy(css = ".payment-option")
    private List<WebElement> paymentOptionsRow;


    @FindBy(css = ".payment-options .custom-radio")
    private List<WebElement> paymentOptions;
    @FindBy(css = "#cta-terms-and-conditions-0")
    private WebElement termsOfConditionLink;
    @FindBy(css = ".modal-content>button.close")
    private WebElement closeAlertButton;
    @FindBy(css = ".custom-checkbox")
    private WebElement checkTermsOFService;
    @FindBy(css = ".js-modal-content p")
    private List<WebElement> termsRuleList;
    @FindBy(css = "#payment-confirmation button")
    private WebElement paymentConfirmationButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

//    public CheckoutPage submitRegisterForm() {
//        clickOnElement(continueRegisterButton);
//        return this;
//    }

    public CheckoutPage fillAdressForm() {
        sendKeysToElement(adress, getRandomAdress());
        sendKeysToElement(city, getRandomCity());
        sendKeysToElement(postalCode, getRandomPostalCode());
        new Select(selectCountry).selectByValue("14");
        return this;
    }

    public String selectShippingMethod() {
        WebElement random = getRandomListEl(deliveryOptionRow);
        WebElement randomRadio = random.findElement(By.cssSelector(".custom-radio"));
        String shippingMethod = random.findElement(By.cssSelector(".carrier-name")).getText();
        log.info("Selected shipping method: " + shippingMethod);
        randomRadio.click();
        return shippingMethod;
    }

    public String selectPaymentMethod() {
        WebElement payment = paymentOptionsRow.get(1);
        WebElement paymentRadio = payment.findElement(By.cssSelector(".custom-radio"));
        String paymentMethod = payment.findElement(By.cssSelector("label span")).getText();
        paymentMethod = paymentMethod.replace("Pay by ", "");
        log.info("Selected payment method: " + paymentMethod);
        paymentRadio.click();
        clickOnElement(termsOfConditionLink);
        driver.switchTo().defaultContent();
        return paymentMethod;
    }


    public CheckoutPage confirmShipping() {
        clickOnElement(confirmShippingButton);
        return this;
    }


    public CheckoutPage acceptTerms() {
        clickOnElement(closeAlertButton);
        boolean isSelected = checkTermsOFService.isSelected();
        if (isSelected == false) {
            clickOnElement(checkTermsOFService);
        }
        log.info("checkbox selected");
        return this;
    }

    public CheckoutPage confirmPayment() {
        clickOnElement(paymentConfirmationButton);
        return this;
    }

    public String getAddress() {
        clickOnElement(editAddress);
        String address = getElementText(chosenAddress);
        clickOnElement(confirmAddressButton);
        log.info("Chosen address: " + address);
        return address;

    }

    public CheckoutPage continueCheckout() {
        clickOnElement(confirmAddressButton);
        return this;
    }

    public boolean hasAllTermsOfServiceText() {
        List<String> ruleTextList = new ArrayList<>();
        boolean hasText = false;
        for (WebElement termsRule : termsRuleList) {
            if (!getElementText(termsRule).isEmpty()) {
                ruleTextList.add(getElementText(termsRule));
            } else {
                log.info("Rule text missing");
            }
        }
        if (ruleTextList.size() == termsRuleList.size()) {
            hasText = true;
        }
        return hasText;
    }

    public List<Product> getOrderProducts() {
        clickOnElement(cartSummaryCollapse);
        List<Product> products = new ArrayList<>();
        for (WebElement product : productsSummaryList) {
            products.add(new Product(getElementText(new OrderItemPage(product).getProductName()),
                    getQuantityValueOfProduct(getElementText(new OrderItemPage(product).getProductQuantity())),
                    getElementText(new OrderItemPage(product).getProductPrice())));
        }
        log.info("Order product list: " + products);
        return products;
    }


}
