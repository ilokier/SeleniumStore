package Pages;

import Pages.ProductDetailPages.ProductMiniaturePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    @FindBy(css = ".ui-slider-range")
    private WebElement slider;
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
    @FindBy(css = ".product-price-and-shipping .price")
    private List<WebElement> productsPriceList;
    @FindBy(css = ".js-search-filters-clear-all")
    private WebElement clearFilterButton;

    //sale
    @FindBy(css = "#js-product-list-header")
    private WebElement salePageTitle;


    public CategoriesPage(WebDriver driver) {
        super(driver);
    }


    public String getCategoryName() {
        String name = getElementText(categoryPageName);
        return name;
    }

    public boolean checkIfFilerMenuIsDisplayed() {
        boolean isfilterDisplayed = false;
        if (filterMenu.isDisplayed()) {
            isfilterDisplayed = true;
            log.info("Menu name is: " + getElementText(categoryPageName));
            return true;
        } else log.info("There is no filter");
        return isfilterDisplayed;
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
        log.info("Min value :" + nums[0] + ", max value: " + nums[1]);
        return nums;
    }


    public List<Double> getProductPriceList() {
        List<Double> allPrices = new ArrayList<>();
        for (WebElement product : productMiniature) {
            allPrices.add(new ProductMiniaturePage(product).getProductPriceValue());
        }
        return allPrices;
    }


    public void clearFilter() {
        if (sliderValues.getAttribute("data-slider-values").equals("null")) {
            log.info("There is no filter to clear");
        } else {
            clickOnElement(clearFilterButton);
            log.info("Filter cleared");
        }
    }

    private void setMaxPrice(double maxPrice) {
        scrollToElement(maxSlider);
        waitToBeVisible(maxSlider);

        for (double i = getMaxValue(); i > maxPrice; i--) {
            try {
                waitForElementToBeClickableBy(maxSlider);
                maxSlider.sendKeys(Keys.ARROW_LEFT);
                Thread.sleep(500);

            } catch (StaleElementReferenceException e) {
                e.getMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private double getMinValue() {
        double minValue = Double.parseDouble(sliderValues.getAttribute("data-slider-min"));
        return minValue;
    }

    private double getMaxValue() {
        double maxValue = Double.parseDouble(sliderValues.getAttribute("data-slider-max"));
        log.info("Max value is: " + maxValue);
        return maxValue;
    }

    private void setMinPrice(double minPrice) {
        scrollToElement(minSlider);
        waitToBeVisible(minSlider);
        for (double i = getMinValue(); i < minPrice; i++) {
            try {
                waitForElementToBeClickableBy(minSlider);
                minSlider.sendKeys(Keys.ARROW_RIGHT);
                Thread.sleep(500);

            } catch (StaleElementReferenceException e) {
                e.getMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
        List<Integer> dicsountValueList = new ArrayList<>();
        for (WebElement product : productMiniature) {
            dicsountValueList.add(new ProductMiniaturePage(product).getDiscountValue());
        }
        return dicsountValueList;
    }

    public boolean isDiscountPriceDisplayed() {
        boolean isDiscountDisplayed = false;
        for (WebElement product : productMiniature) {
            if (new ProductMiniaturePage(product).isPriceDisplayed()) {
                isDiscountDisplayed = true;
            } else {
                log.info("Discount is not awaliable");
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
                log.info("Discount is not awaliable");
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
                log.info("Label is not awaliable");
            }
        }
        return isLabelDisplayed;
    }

    public List<String> getDiscountLabelText() {
        List<String> dicsountLabelList = new ArrayList<>();
        for (WebElement product : productMiniature) {
            dicsountLabelList.add(new ProductMiniaturePage(product).getDisount());
        }
        return dicsountLabelList;
    }

    public CategoriesPage goToRandomProduct() {
        getRandomListEl(productMiniature).click();
        return this;
    }

}