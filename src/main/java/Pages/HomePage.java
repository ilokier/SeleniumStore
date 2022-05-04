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
    List<WebElement> subcategories = new ArrayList<>();

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
    @FindBy(css = ".dropdown-submenu")
    private List<WebElement> subCategoriesList;

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

    public void moveToMainCategory(int categoryIndex) throws InterruptedException {
        // moveToElement(driver.findElement(By.cssSelector(".top-menu")));
        moveToElement(mainCategoriesList.get(categoryIndex));
        log.info("im in category");
    }

    public void goToSubcategory(int i) throws InterruptedException {
        clickOnElement(subcategories.get(i));
    }

    public List<WebElement> getSubcategoryAmount(int index) throws InterruptedException {
        moveToElement(mainCategoriesList.get(index));
        for (WebElement webElement : subCategoriesList) {
            if (webElement.isDisplayed()) {
                subcategories.add(webElement);

            }
        }
        log.info("list size is: " + subcategories.size());

        return subcategories;
    }
//    public String searchRandomElement() {
//        String randomProduct = getRandomListElementText(productsList);
//        log.info(randomProduct);
//        return randomProduct;
//    }

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

    //nazwa kategorii z głównego menu
    public String getCategoryName() {
        String menuName = (getElementText(categoryName));
        log.info("Chosen category is: " + menuName);
        return menuName;
    }

    public String getSubcategoryName(int i) {
        String subcatName = getElementText(subCategoriesList.get(i));
        return subcatName;
    }


    // TODO: 21.04.2022 same for subcategories
    public void goToPricesDrop() {
        scrollToElement(pricesDrop);
        clickOnElement(pricesDrop);
    }


}




