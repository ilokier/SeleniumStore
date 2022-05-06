import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class CategoriesTest extends BaseTest {

    @Test
    public void shouldDisplayEveryCategoryPage() {
        int categoriesAmount = homePage.getMainCategoriesSize();
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < categoriesAmount; i++) {
            homePage.goToCategoryPage(i);
            softAssertions.assertThat(homePage.getCategoryName().equals(categoriesPage.getCategoryName()));
            softAssertions.assertThat(categoriesPage.checkIfFilerMenuIsDisplayed()).isTrue();
            softAssertions.assertThat(categoriesPage.getLabelWithNumberOfProductsTextList().contains(String.valueOf(categoriesPage.countNumberOfProductsInCategory())));
        }
        softAssertions.assertAll();
    }

    @Test
    public void shouldDisplayEverySubcategoryPage() {
        int categoriesAmount = homePage.getMainCategoriesSize();
        SoftAssertions softAssertions = new SoftAssertions();
        for (int j = 0; j < categoriesAmount; j++) {
            homePage.goToMainCategory(j);
            int subCategoriesAmount = homePage.getSubCategoriesListSize();
            for (int i = 0; i < subCategoriesAmount; i++) {
                String subCategoryName = homePage.getSubcategoryName(i);
                homePage.goToSubcategory(i);
                softAssertions.assertThat(subCategoryName.equals(categoriesPage.getCategoryName()));
                softAssertions.assertThat(categoriesPage.checkIfFilerMenuIsDisplayed()).isTrue();
                softAssertions.assertThat(categoriesPage.getLabelWithNumberOfProductsTextList().contains(String.valueOf(categoriesPage.countNumberOfProductsInCategory())));
                driver.navigate().back();
            }
        }
        softAssertions.assertAll();
    }
}