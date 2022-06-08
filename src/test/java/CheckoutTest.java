import Models.Order;
import Pages.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTest extends BaseTest {
    @BeforeEach
    public void before() {
        homePage.goToLoginPage();
        loginPage.goToRegistrationForm();
        registerPage.fillRegisterForm(new UserFactory(driver).getRandomUser());
        registerPage.submitRegistrationForm();
        order = new Order();
        historyOrder = new Order();
    }

    @Test
    public void shouldGoToOrderCheckoutWithSucess() throws InterruptedException {
        addRandomProductsToCart(5);
        order.setProducts(cartPage.getCartProducts());
        productPage.proceedToCheckout();
        checkoutPage.fillAdressForm()
                .setOrderDetails(order, checkoutPage);
        softAssertions.assertThat(checkoutPage.hasAllTermsOfServiceText()).isTrue();
        checkoutPage.acceptTerms().confirmPayment();
        orderConfirmed = orderConfirmationPage.getOrderDetails(orderConfirmationPage.getOrderProducts());
        orderConfirmationPage.goToOrderHistory();
        historyOrder = orderHistoryPage.getHistoryOrderDetails(orderHistoryPage.searchOrder(order.getOrderReference()));

        assertThat(order.getPaymentMethod()).containsIgnoringCase("bank");
        assertThat(orderConfirmed.getPaymentMethod()).contains(historyOrder.getPaymentMethod());
        assertThat(historyOrder.getPaymentMethod()).containsIgnoringCase("bank");
        assertThat(order.getShippingMethod().contains("TesterSii"));
        assertThat(order.getInvoiceAddress()).isEqualTo(historyOrder.getInvoiceAddress());
        assertThat(order.getDeliveryAddress()).isEqualTo(historyOrder.getDeliveryAddress());
        assertThat(orderConfirmed.getProducts()).isEqualTo(historyOrder.getProducts());
        assertThat(order.getProducts()).isEqualTo(historyOrder.getProducts());
        assertThat(historyOrder.getDate()).isEqualTo(basePage.getTodayDate());
        assertThat(historyOrder.getStatus().contains(order.getPaymentMethod()));
        assertThat(historyOrder.getStatus()).containsIgnoringCase("awaiting");
        softAssertions.assertAll();
    }
}
