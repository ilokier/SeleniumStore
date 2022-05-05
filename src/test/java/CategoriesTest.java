import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

public class CategoriesTest extends BaseTest {
    List<Double> filteredList = new ArrayList<>();

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

    @ParameterizedTest
    @CsvSource({"9, 10", "9, 29", "27,29"})
    public void addingFilterShouldReturnMatchesProducts(double minPrice, double maxPrice) {

        homePage.goToCategoryPage(2);
        categoriesPage.setPriceFilter(minPrice, maxPrice);
        categoriesPage.getCurrentFilterValues();
        filteredList = categoriesPage.getProductPriceList();
        categoriesPage.clearFilter();
        assertValueIsBetween(filteredList, minPrice, maxPrice);
    }

    private static boolean assertValueIsBetween(List<Double> values, double min, double max) {
        boolean condition = false;
        if (values.isEmpty()) {
            condition = true;
        } else {
            for (Double value : values) {
                if (value >= min && value <= max) {
                    condition = true;
                }
            }
        }
        return condition;
    }


}