package Tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class FilterTest extends BaseTest {
    private List<Double> filteredList = new ArrayList<>();

    @DataProvider(name = "filterValues")
    public Object[][] createData1() {
        return new Object[][]{
                {9.0, 10.0},
                {9.0, 29.0},
                {27.0, 29.0}
        };

    }

    @Test(dataProvider = "filterValues")
    public void addingFilterShouldReturnMatchesProducts(double minPrice, double maxPrice) {
        homePage.goToCategoryPage(2);
        categoriesPage.setPriceFilter(minPrice, maxPrice)
                .getCurrentFilterValues();
        filteredList = categoriesPage.getProductPriceList();
        categoriesPage.clearFilter();
        assertValueIsBetween(filteredList, minPrice, maxPrice);

    }
}

