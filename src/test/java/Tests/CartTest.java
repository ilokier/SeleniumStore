package Tests;

import Models.Product;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.getProperty;
import static javax.swing.UIManager.getInt;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CartTest extends BaseTest {
    private static Logger log = LoggerFactory.getLogger("Tests.CartTest.class");
    private List<Product> randomProducts = new ArrayList<>();
    private List<WebElement> cartProducts = new ArrayList<>();
    private Map<String, Integer> productQuantityMap;
    private Map<String, Integer> cartProductQuantityMap;

    @Test
    public void shouldHaveChosenProductsInCart() throws InterruptedException {
        homePage.goToRandomCategory();
        categoriesPage.goToRandomProduct();
        productPage.setRandomQuantity(getInt("maxQuantity"));
        randomProduct = productPage.getRandomProduct();
        productPage.addToCart();
        popUpProduct = productPage.getPopUpProduct();
        String label = productPage.getLabelWithQuantity();
        productPage.continueShoping();
        assertThat(randomProduct).usingRecursiveComparison().isEqualTo(popUpProduct);
        assertThat(label.contains(String.valueOf(productPage.getCartQuantity())));

    }

    @org.testng.annotations.Test
    public void shouldAddChosenProductsToCartMultipleTimes() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            homePage.goToRandomCategory();
            categoriesPage.goToRandomProduct();
            productPage.setRandomQuantity(getInt("maxQuantity"));
            int cartQuantityBefore = productPage.getCartQuantity();
            randomProduct = productPage.getRandomProduct();
            productPage.addToCart();
            popUpProduct = productPage.getPopUpProduct();
            String label = productPage.getLabelWithQuantity();
            productPage.continueShoping();
            driver.get(getProperty("appUrl"));
            int cartQuantityAfter = productPage.getCartQuantity();
            softAssertions.assertThat(popUpProduct.getName()).isEqualTo(randomProduct.getName());
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
            productPage.setRandomQuantity(getInt("maxQuantity"));
            randomProducts.add(productPage.getRandomProduct());
            productPage.addToCart()
                    .continueShoping();
            driver.get(getProperty("appUrl"));
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
    }

}
