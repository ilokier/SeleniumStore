import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SearchTest extends BaseTest {

    @Test
    public void shouldFindSearchedProductOnPage() {
        String randomProductName = homePage.searchRandomProduct();
        String searchResult = homePage.getSearchResultText();
        assertThat(searchResult, equalTo(randomProductName));
    }

    @Test
    public void shouldFindSearchedProductInDropdown() {
        String randomProductName = homePage.searchRandomProduct();
        List<String> searchResults = homePage.getSearchResultDropdownText();
        assertThat((searchResults), Matchers.hasItem(randomProductName));
    }
}
