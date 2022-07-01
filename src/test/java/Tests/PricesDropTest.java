package Tests;

import org.testng.annotations.Test;

public class PricesDropTest extends BaseTest {
    @Test
    public void shouldGoToSaleSiteAndGiveDiscountValues() {
        homePage.goToPricesDrop();
        int productsAmount = categoriesPage.countNumberOfProductsInCategory();
        for (int i = 0; i < productsAmount; i++) {
            softAssertions.assertThat(categoriesPage.getSalePageCategoryTitle().equals(System.getProperty("saleCategoryName")));
            softAssertions.assertThat(categoriesPage.isDiscountPriceDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.isRegularPriceDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.isLabelWithDiscountDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.getDiscountLabelText()).contains(System.getProperty("discount"));
            softAssertions.assertThat(categoriesPage.getProductPriceList().get(i).equals(categoriesPage.getRegularProductPriceList().get(i) * categoriesPage.getDiscountValue().get(i)));
        }
        softAssertions.assertAll();
    }
}

