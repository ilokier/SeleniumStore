package Models;

public enum Browsers {
    CHROME("chrome"),
    EDGE("edge"),
    FIREFOX("firefox"),
    IE("ie");
    private String browser;

    Browsers(String browser) {
        this.browser = browser;
    }
}
