package Pages;

import Pages.ProductDetailPages.ProductMiniaturePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        boolean isFilterDisplayed = false;
        if (filterMenu.isDisplayed()) {
            isFilterDisplayed = true;
            log.info("There is filter for price between " + getCurrentFilterValues()[0] + " and " + getCurrentFilterValues()[1]);
            return true;
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
        setMaxPrice(maxPrice);
        setMinPrice(minPrice);
    }

    public String[] getCurrentFilterValues() {
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
            for (WebElement product : productMiniature) {
                allPrices.add(new ProductMiniaturePage(product).getProductPriceValue());
            }
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
            scrollToElement(clearFilterButton);
            clickOnElement(clearFilterButton);
            log.info("Filter cleared");
        }
    }

    public String getSalePageCategoryTitle() {
        return getElementText(salePageTitle);
    }

    public List<Double> getRegularProductPriceList() {
        List<Double> regularPrices = new ArrayList<>();
        for (WebElement product : productMiniature) {
            regularPrices.add(new ProductMiniaturePage(product).getRegularPriceValue());
        }
        return regularPrices;
    }

    public List<Integer> getDisountValue() {
        List<Integer> discountValueList = new ArrayList<>();
        for (WebElement product : productMiniature) {
            discountValueList.add(new ProductMiniaturePage(product).getDiscountValue());
        }
        return discountValueList;
    }

    public boolean isDiscountPriceDisplayed() {
        boolean isDiscountDisplayed = false;
        for (WebElement product : productMiniature) {
            if (new ProductMiniaturePage(product).isPriceDisplayed()) {
                isDiscountDisplayed = true;
            } else {
                log.info("Discount is not available");
            }
        }
        return isDiscountDisplayed;
    }

    public boolean isRegularPriceDisplayed() {
        boolean isRegularPriceDisplayed = false;
        for (WebElement product : productMiniature) {
            if (new ProductMiniaturePage(product).isRegularPriceDisplayed()) {
                isRegularPriceDisplayed = true;
            } else {
                log.info("Discount is not available");
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
        try {
            scrollToElement(maxSlider);
            for (double i = getMaxValue(); i > maxPrice; i--) {
                clickAndHold(maxSlider);
                waitForElementToBeClickable(maxSlider);
                maxSlider.sendKeys(Keys.ARROW_LEFT);
            }
            release();
        } catch (StaleElementReferenceException e) {
            log.info("msg: " + e.getMessage());
        } catch (ElementNotInteractableException f) {
            log.info("msg: " + f.getMessage());
        }
    }

    private void setMinPrice(double minPrice) {
        try {
            scrollToElement(minSlider);
            for (double i = getMinValue(); i < minPrice; i++) {
                clickAndHold(minSlider);
                waitForElementToBeClickable(minSlider);
                minSlider.sendKeys(Keys.ARROW_RIGHT);
            }
            release();
        } catch (StaleElementReferenceException e) {
            log.info("msg: " + e.getMessage());
            e.printStackTrace();
        } catch (ElementNotInteractableException f) {
            log.info("msg: " + f.getMessage());
        }
    }
}