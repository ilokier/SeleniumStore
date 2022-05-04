import Models.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartTest extends BaseTest {
    private static Logger log = LoggerFactory.getLogger("CartTest.class");
    Product randomProduct;
    Product popUpProduct;
    List<Product> randomProducts = new ArrayList<>();
    List<WebElement> cartProducts = new ArrayList<>();
    Map<String, Integer> productQuantityMap;
    Map<String, Integer> cartProductQuantityMap;
    SoftAssertions softAssertions = new SoftAssertions();

    @Test
    public void shouldHaveChosenProductsInCart() throws InterruptedException {
        homePage.goToRandomCategory();
        categoriesPage.goToRandomProduct();
        productPage.setRandomQuantity(5);
        int cartQuantityBefore = productPage.getCartQuantity();
        randomProduct = productPage.getRandomProduct();
        productPage.addToCart();
        popUpProduct = productPage.getPopUpProduct();
        String label = productPage.getLabelWithQuantity();
        productPage.continueShoping();
        softAssertions.assertThat(randomProduct.toString().equals(popUpProduct.toString()));
        softAssertions.assertThat(cartQuantityBefore).isEqualTo(productPage.getCartQuantity() - Integer.parseInt(randomProduct.getQuantity()));
        softAssertions.assertThat(label.contains(String.valueOf(productPage.getCartQuantity())));
        softAssertions.assertAll();
    }

    @Test
    public void shouldAddChosenProductsToCartMultipleTimes() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            homePage.goToRandomCategory();
            categoriesPage.goToRandomProduct();
            productPage.setRandomQuantity(5);
            int cartQuantityBefore = productPage.getCartQuantity();
            randomProduct = productPage.getRandomProduct();
            productPage.addToCart();
            popUpProduct = productPage.getPopUpProduct();
            String label = productPage.getLabelWithQuantity();
            productPage.continueShoping();
            driver.get(System.getProperty("appUrl"));
            int cartQuantityAfter = productPage.getCartQuantity();
            softAssertions.assertThat(randomProduct.toString().equals(popUpProduct.toString()));
            softAssertions.assertThat(cartQuantityBefore).isEqualTo(cartQuantityAfter - Integer.parseInt(randomProduct.getQuantity()));
            softAssertions.assertThat(label.contains(String.valueOf(cartQuantityAfter)));

        }
        softAssertions.assertAll();
    }

    @Test
    public void shouldHaveCorrectProductsInBasketWithChosenQuantity() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            homePage.goToRandomCategory();
            categoriesPage.goToRandomProduct();
            productPage.setRandomQuantity(5);
            randomProducts.add(productPage.getRandomProduct());
            productPage.addToCart()
                    .continueShoping();
            driver.get(System.getProperty("appUrl"));
        }
        productQuantityMap = productPage.countQuantity(randomProducts);
        productPage.goToCart();
        cartProducts = cartPage.getCartItemList();
        cartProductQuantityMap = cartPage.getCartProductsToMap(cartPage.getCartItemList());
        softAssertions.assertThat(cartProductQuantityMap).isEqualTo(productQuantityMap);
        cartPage.changeProducyQuantity(cartProducts);
        softAssertions.assertThat(cartPage.getTotalBasketPrice()).isEqualTo(cartPage.countTotalBasketValue(cartProducts) + cartPage.getShippingPrice());
        int quantity = cartPage.getQuantity(cartPage.getCartItemList());
        cartPage.substractQuantity(cartPage.getCartItemList());
        softAssertions.assertThat(cartPage.getQuantity(cartProducts)).isEqualTo(quantity - 1);
        cartPage.addQuantity(cartPage.getCartItemList());
        softAssertions.assertThat(cartPage.getQuantity(cartProducts)).isEqualTo(quantity);
        softAssertions.assertThat(cartPage.getTotalBasketPrice()).isEqualTo(cartPage.countTotalBasketValue(cartProducts) + cartPage.getShippingPrice());
        for (int i = cartProducts.size(); i > 0; i--) {
            cartPage.deleteItem(i);
            softAssertions.assertThat(cartPage.getTotalBasketPrice()).isEqualTo(cartPage.countTotalBasketValue(cartProducts) + cartPage.getShippingPrice());
        }
        softAssertions.assertAll();
        //todo checkout
    }

}
