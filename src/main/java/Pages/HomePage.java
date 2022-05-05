package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("HomePage.class");
    // List<WebElement> subcategories = new ArrayList<>();

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".user-info .material-icons")
    WebElement signInButton;
    @FindBy(css = ".product-title a")
    List<WebElement> productsList;
    @FindBy(css = "[name='s']")
    private WebElement searchInput;
    @FindBy(css = "button .search")
    private WebElement searchButton;
    @FindBy(css = "#search .product-title")
    private List<WebElement> searchResults;
    @FindBy(css = ".ui-menu-item .product")
    private List<WebElement> dropdownSearchList;
    // @FindBy(css = ".category a:not(.dropdown-submenu)")
    @FindBy(css = "#top-menu>.category")
    private List<WebElement> mainCategoriesList;


    // @FindBy(css = ".dropdown-submenu")
    @FindBy(css = ".category-sub-menu a")
    private List<WebElement> subCategoriesList;


    @FindBy(css = ".account .hidden-sm-down")
    private WebElement signName;

    //categoryPage
    @FindBy(css = ".category-top-menu>li>a")
    private WebElement categoryName;
    @FindBy(css = "#search_filters")
    private WebElement filterMenu;

    //Footer
    @FindBy(css = "#link-product-page-prices-drop-1")
    private WebElement pricesDrop;

    public HomePage goToLoginPage() {
        clickOnElement(signInButton);
        return this;
    }

    public void goToCategoryPage(int categoryIndex) {
        clickOnElement(mainCategoriesList.get(categoryIndex));
    }

    public HomePage goToRandomCategory() {
        getRandomListEl(mainCategoriesList).click();
        return this;
    }

    public void goToMainCategory(int categoryIndex) {
        clickOnElement(mainCategoriesList.get(categoryIndex));
    }

    public void goToSubcategory(int index) {
        clickOnElement(subCategoriesList.get(index));
    }

    public int getSubCategoriesListSize() {
        log.info("ListSize: " + subCategoriesList.size());
        if (subCategoriesList.isEmpty()) {
            log.info("There is no subcategory for category: " + getCategoryName());
        }
        return subCategoriesList.size();
    }


    public String searchRandomProduct() {
        String searchText = getRandomListElementText(productsList);
        sendKeysToElement(searchInput, searchText);
        return searchText;
    }

    public String getSearchResultText() {
        clickOnElement(searchButton);
        String resultItem = null;
        for (WebElement searchResult : searchResults) {
            resultItem = searchResult.getText();
            log.info("Result item: " + resultItem);
        }
        return resultItem;
    }

    public List<String> getSearchResultDropdownText() {
        List<String> resultItems = new ArrayList<>();
        waitToListVisible(dropdownSearchList);
        for (WebElement searchResult : dropdownSearchList) {
            String resultItem = searchResult.getText();
            log.info("Result item: " + resultItem);
            resultItems.add(resultItem);
        }
        return resultItems;
    }

    public int getMainCategoriesSize() {
        return mainCategoriesList.size();
    }


    public String getCategoryName() {
        String menuName = (getElementText(categoryName));
        log.info("Chosen category is: " + menuName);
        return menuName;
    }

    public String getSubcategoryName(int i) {
        String subcatName = getElementText(subCategoriesList.get(i));
        return subcatName;
    }

    public void goToPricesDrop() {
        scrollToElement(pricesDrop);
        clickOnElement(pricesDrop);
    }

    public String getUserSignedName() {
        return getElementText(signName);
    }
}




