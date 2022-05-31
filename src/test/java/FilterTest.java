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
}

