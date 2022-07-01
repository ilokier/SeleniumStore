package Tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class SearchTest extends BaseTest {
    @Test
    public void shouldFindSearchedProductOnPage() {
        String randomProductName = homePage.searchRandomProduct();
        assertThat((homePage.getSearchResultDropdownText()), Matchers.hasItem(randomProductName));
        assertThat(homePage.getSearchResultText(), Matchers.hasItem(randomProductName));
    }
}
