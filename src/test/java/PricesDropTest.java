import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class PricesDropTest extends BaseTest {


    @Test
    public void shouldGoToSaleSiteAndGiveDiscountValues() {
        homePage.goToPricesDrop();
        int productsAmount = categoriesPage.countNumberOfProductsInCategory();
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < productsAmount; i++) {
            softAssertions.assertThat(categoriesPage.getSalePageCategoryTitle().equals("ON SALE"));
            softAssertions.assertThat(categoriesPage.isDiscountPriceDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.isRegularPriceDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.isLabelWithDiscountDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.getDiscountLabelText()).contains("-20%");
            softAssertions.assertThat(categoriesPage.getProductPriceList().get(i).equals(categoriesPage.getRegularProductPriceList().get(i) * categoriesPage.getDisountValue().get(i)));
        }
        softAssertions.assertAll();
    }
}

