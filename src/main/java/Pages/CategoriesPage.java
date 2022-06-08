package Pages;

import Pages.ProductDetailPages.ProductMiniaturePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriesPage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("CategoriesPage.class");
    @FindBy(css = ".block-category h1")
    private WebElement categoryPageName;
    @FindBy(css = "#search_filters")
    private WebElement filterMenu;
    @FindBy(css = ".faceted-slider")
    private WebElement sliderValues;
    @FindBy(css = ".ui-slider-horizontal  a:last-child")
    private WebElement maxSlider;
    @FindBy(css = ".ui-slider-horizontal  a:nth-last-child(2)")
    private WebElement minSlider;
    @FindBy(css = ".total-products")
    WebElement totalProductsAmount;
    @FindBy(css = ".product")
    private List<WebElement> productMiniature;
    @FindBy(css = ".js-search-filters-clear-all")
    private WebElement clearFilterButton;

    @FindBy(css = "#js-product-list-header")
    private WebElement salePageTitle;

    public CategoriesPage(WebDriver driver) {
        super(driver);
    }


    public String getCategoryName() {
        String name = getElementText(categoryPageName);
        log.info("Current category is: " + name);
        return name;
    }

    public boolean checkIfFilerMenuIsDisplayed() {
        boolean isFilterDisplayed = filterMenu.isDisplayed();
        if (isFilterDisplayed) {
            log.info("There is filter for price between " + getCurrentFilterValues()[0] + " and " + getCurrentFilterValues()[1]);
        } else log.info("There is no filter");
        return isFilterDisplayed;
    }

    public int countNumberOfProductsInCategory() {
        int numberOfProducts = productMiniature.size();
        log.info("Products quantity in category: " + numberOfProducts);
        return numberOfProducts;
    }

    public String getLabelWithNumberOfProductsTextList() {
        String label = totalProductsAmount.getText();
        log.info((totalProductsAmount.getText()) + " in category");
        return label;
    }

    public void setPriceFilter(double minPrice, double maxPrice) {
        try {
            setMaxPrice(maxPrice);
            setMinPrice(minPrice);
        } catch (StaleElementReferenceException e) {
            log.info("msg: " + e.getMessage());
            e.printStackTrace();
        } catch (ElementNotInteractableException f) {
            log.info("msg: " + f.getMessage());
        }
    }

    public String[] getCurrentFilterValues() {
        //waitForLoad();
        String[] nums = new String[2];
        String dataValues = sliderValues.getAttribute("data-slider-values");
        if (dataValues.equals("null")) {
            nums[0] = String.valueOf(getMinValue());
            nums[1] = String.valueOf(getMaxValue());
        } else nums = dataValues.replaceAll("[^0-9.]+", " ").trim().split(" ");
        return nums;
    }

    public List<Double> getProductPriceList() {
        List<Double> allPrices = new ArrayList<>();
        try {
            waitToListVisible(productMiniature);
            allPrices = productMiniature.stream()
                    .map(product -> new ProductMiniaturePage(product).getProductPriceValue())
                    .collect(Collectors.toList());
        } catch (StaleElementReferenceException e) {
            log.info("msg: " + e.getMessage());
        }
        return allPrices;
    }

    public void clearFilter() {
        waitToBeVisible(sliderValues);
        if (sliderValues.getAttribute("data-slider-values").equals("null")) {
            log.info("There is no filter to clear");
        } else {
            moveAndWait(clearFilterButton);
            clickOnElement(clearFilterButton);
            log.info("Filter cleared");
        }
    }

    public String getSalePageCategoryTitle() {
        return getElementText(salePageTitle);
    }

    //still learning stream
    public List<Double> getRegularProductPriceList() {
        List<Double> regularPrices = productMiniature.stream()
                .map(product -> new ProductMiniaturePage(product).getRegularPriceValue())
                .collect(Collectors.toList());
        return regularPrices;
    }

    public List<Integer> getDiscountValue() {
        List<Integer> discountValueList = new ArrayList<>();
        for (WebElement product : productMiniature) {
            discountValueList.add(new ProductMiniaturePage(product).getDiscountValue());
        }
        return discountValueList;
    }

    public boolean isDiscountPriceDisplayed() {
        boolean isDiscountDisplayed = productMiniature.stream()
                .allMatch(product -> new ProductMiniaturePage(product).isPriceDisplayed());
        if (!isDiscountDisplayed) {
            log.info("Price is not displayed");
        }
        return isDiscountDisplayed;
    }


    public boolean isRegularPriceDisplayed() {
        boolean isRegularPriceDisplayed = false;
        for (WebElement product : productMiniature) {
            if (new ProductMiniaturePage(product).isRegularPriceDisplayed()) {
                isRegularPriceDisplayed = true;
            } else {
                log.info("Discount is not displayed");
            }
        }
        return isRegularPriceDisplayed;
    }

    public boolean isLabelWithDiscountDisplayed() {
        boolean isLabelDisplayed = false;
        for (WebElement product : productMiniature) {
            if (new ProductMiniaturePage(product).isDiscountDisplayed()) {
                isLabelDisplayed = true;
            } else {
                log.info("Label is not available");
            }
        }
        return isLabelDisplayed;
    }

    public List<String> getDiscountLabelText() {
        List<String> discountLabelList = new ArrayList<>();
        for (WebElement product : productMiniature) {
            discountLabelList.add(new ProductMiniaturePage(product).getDiscount());
        }
        return discountLabelList;
    }

    public CategoriesPage goToRandomProduct() {
        getRandomListEl(productMiniature).click();
        return this;
    }

    private double getMinValue() {
        return Double.parseDouble(sliderValues.getAttribute("data-slider-min"));
    }

    private double getMaxValue() {
        return Double.parseDouble(sliderValues.getAttribute("data-slider-max"));
    }

    private void setMaxPrice(double maxPrice) {
        moveAndWait(maxSlider);
        for (double i = getMaxValue(); i > maxPrice; i--) {
            clickAndHold(maxSlider);
            waitForLoad();
            maxSlider.sendKeys(Keys.ARROW_LEFT);
        }
        release();

    }

    private void setMinPrice(double minPrice) {
        moveAndWait(minSlider);
        for (double i = getMinValue(); i < minPrice; i++) {
            clickAndHold(minSlider);
            waitForLoad();
            minSlider.sendKeys(Keys.ARROW_RIGHT);
        }
        release();

    }
}