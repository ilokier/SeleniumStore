import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CategoriesTest extends BaseTest {
    private List<Double> filteredList;
    List<WebElement> subcategories = new ArrayList<>();

    @Test
    public void shouldGoToChosenCategoryPage() {
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

    //  @Test
//    public void shouldGoToClothesSubcategoryPage() throws InterruptedException {
//
//        homePage.moveToMainCategory(0);
//        // int subCategoriesAmount = homePage.getSubcategoryAmount(subcategories, 0);
//        subcategories = homePage.getSubcategoryAmount(0);
//        SoftAssertions softAssertions = new SoftAssertions();
//        for (int i = 0; i < subcategories.size(); i++) {
//            homePage.moveToMainCategory(0);
//            homePage.goToSubcategory(i);
//            softAssertions.assertThat(homePage.getSubcategoryName(i).equals(categoriesPage.getCategoryName()));
//            softAssertions.assertThat(categoriesPage.checkIfFilerMenuIsDisplayed()).isTrue();
//            softAssertions.assertThat(categoriesPage.getLabelWithNumberOfProductsText().contains(categoriesPage.countNumberOfProductsInCategory()));
//        }
//        softAssertions.assertAll();
//    }
//
//    @Test
//    public void shouldGoToAccesoriesSubcategoryPage() throws InterruptedException {
//        subcategories = homePage.getSubcategoryAmount(1);
//        homePage.moveToMainCategory(1);
//        // int subCategoriesAmount = homePage.getSubcategoryAmount(1);
//        SoftAssertions softAssertions = new SoftAssertions();
//        for (int i = 0; i < subcategories.size(); i++) {
//            // driver.navigate().refresh();
//            homePage.moveToMainCategory(1);
//            homePage.goToSubcategory(i);
//            softAssertions.assertThat(homePage.getSubcategoryName(i).equals(categoriesPage.getCategoryName()));
//            softAssertions.assertThat(categoriesPage.checkIfFilerMenuIsDisplayed()).isTrue();
//            softAssertions.assertThat(categoriesPage.getLabelWithNumberOfProductsText().contains(categoriesPage.countNumberOfProductsInCategory()));
//        }
//        softAssertions.assertAll();
//    }


    @ParameterizedTest
    @CsvSource({"9, 10", "9, 29", "27,29"})
    public void addingFilterShouldReturnMatchesProducts(double minPrice, double maxPrice) {
        homePage.goToCategoryPage(2);
        categoriesPage.setPriceFilter(minPrice, maxPrice);
        categoriesPage.getCurrentFilterValues();
        filteredList = categoriesPage.getProductPriceList();
        categoriesPage.clearFilter();
        assertvalueIsBetween(filteredList, minPrice, maxPrice);
    }

    private static boolean assertvalueIsBetween(List<Double> values, double min, double max) {
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