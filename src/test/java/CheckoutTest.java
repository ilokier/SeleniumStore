import Models.Product;
import Pages.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckoutTest extends BaseTest {
    List<Product> cartProducts = new ArrayList<>();
    List<Product> orderProducts = new ArrayList<>();
    List<Product> orderConfirmationProducts = new ArrayList<>();
    List<Product> lastOrderProducts = new ArrayList<>();
    // Order orderConfirmed;
    //Order historyOrder;

    @BeforeEach
    public void before() {
        homePage.goToLoginPage();
        loginPage.goToRegistrationForm();
        registerPage.fillRegisterForm(new UserFactory(driver).getRandomUser());
        registerPage.submitRegistrationForm();
    }

    @Test
    public void shouldGoToOrderCheckoutWithSucess() {
        addRandomProductsToCart(3);//change to 5
        cartProducts = cartPage.getCartProducts();
        productPage.proceedToCheckout();
        checkoutPage.fillAdressForm();
        String chosenAddress = checkoutPage.getAddress();
        orderProducts = checkoutPage.getOrderProducts();
        String chocenShippingMethod = checkoutPage.selectShippingMethod();
        checkoutPage.confirmShipping();
        String chosenPaymentMethod = checkoutPage.selectPaymentMethod();
        softAssertions.assertThat(checkoutPage.hasAllTermsOfServiceText()).isTrue();
        checkoutPage.acceptTerms().confirmPayment();
        orderConfirmed = orderConfirmationPage.getOrderDetails(orderConfirmationProducts = orderConfirmationPage.getOrderProducts());//do assertion, add date
        orderConfirmationPage.goToOrderHistory();
        String orderReference = orderConfirmed.getOrderReference();
        lastOrderProducts = orderHistoryPage.getChosenOrderProducts(orderHistoryPage.searchOrder(orderReference));
        String invoiceAddress = orderHistoryPage.getInvoiceAddress();
        String deliveryAddress = orderHistoryPage.getDeliveryAddress();
        orderHistoryPage.goBack();
        historyOrder = orderHistoryPage.getChosenOrderDetails(lastOrderProducts, orderHistoryPage.searchOrder(orderReference));

        softAssertions.assertThat(historyOrder.getDate()).isEqualTo(basePage.getTodayDate());
        softAssertions.assertThat(historyOrder.getStatus().contains(chosenPaymentMethod));
        softAssertions.assertThat(orderConfirmed.getPaymentMethod()).containsIgnoringCase("bank");
        softAssertions.assertThat(chosenPaymentMethod).containsIgnoringCase("bank");
        softAssertions.assertThat(orderConfirmed.getShippingMethod().contains(chocenShippingMethod));
        softAssertions.assertThat(chosenAddress).isEqualTo(invoiceAddress);
        softAssertions.assertThat(chosenAddress).isEqualTo(deliveryAddress);
        softAssertions.assertThat(orderConfirmed.getProducts().toString()).isEqualTo(historyOrder.getProducts().toString());
        softAssertions.assertThat(cartProducts.toString()).isEqualTo(orderProducts.toString());
        softAssertions.assertAll();
    }
}
