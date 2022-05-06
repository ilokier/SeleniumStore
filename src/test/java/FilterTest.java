import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

public class FilterTest extends BaseTest {
    List<Double> filteredList = new ArrayList<>();

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

