package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import utils.CommonActions;
import utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPage extends BasePage {

    public CategoriesPage() {
        super();
    }

    private static final Logger logger = LoggerUtil.getLogger(CategoriesPage.class);

    // ─── Page Elements
    @FindBy(xpath = "//*[text()=' Adicionar']//../button")
    public WebElement addCategoryButton;

    @FindBy(xpath = "//h3[text()='Tipos de categorías']")
    public WebElement header;

    @FindBy(xpath = "//*[text()='Adicionar tipo de categoría']")
    public WebElement modalHeader;

    @FindBy(css = "input[formcontrolname='name']")
    public WebElement categoryNameInput;
    @FindBy(css = "label[for='customCheckMain']")
    public WebElement subcategoryCheckbox;

    @FindBy(css = "button[type='submit']")
    public WebElement saveCategoryButton;

    @FindBy(xpath = "//ng-select[@formcontrolname='categoryId']")
    public WebElement parentCategoryDropdown;
    @FindBy(xpath = "//ng-select[@formcontrolname='categoryId']//input")
    public WebElement parentCategoryInput;

    @FindBy(xpath = "//div[contains(@class, 'toast-success')]")
    public WebElement successToastMessage;

    @FindBy(xpath = "(//a[@class='page-link'])[last()-1]")
    public WebElement lastPageNavigationLink;

    // ─── Static dynamic locators
    private static final By ROWS_LOCATOR             = By.xpath("//table//tr[@class='ng-star-inserted']");
    private static final By NAME_CELL_LOCATOR        = By.cssSelector("td:nth-child(1)");
    private static final By PARENT_CELL_LOCATOR      = By.cssSelector("td:nth-child(2)");
    private static final By LAST_PAGE_NAVIGATION_ITEM = By.xpath("(//li[contains(@class,'page-item')])[last()-1]");


    // ─── Public, business‐level methods

    /** Adds a new *root* category and waits for success toast. */
    public void addCategory(String categoryName) {
        openModal();
        CommonActions.typeText(categoryNameInput, categoryName);
        CommonActions.clickElement(saveCategoryButton, "Save Category Button");
        CommonActions.waitForElementToDisappear(modalHeader);
    }

    /** Adds a new *sub* category under the given parent and waits for success toast. */
    public void addSubCategory(String categoryName, String parentCategoryName) {
        openModal();
        CommonActions.typeText(categoryNameInput, categoryName);
        selectSubcategoryDropdown(parentCategoryName);
        CommonActions.waitForElementEnabled(saveCategoryButton);
        CommonActions.clickElement(saveCategoryButton, "Save Category Button");
        CommonActions.waitForElementToDisappear(modalHeader);
    }

    public void selectSubcategoryDropdown(String parentCategoryName) {
        CommonActions.clickElement(subcategoryCheckbox, "Subcategory Checkbox");
        CommonActions.waitForElementDisplayed(parentCategoryDropdown);
        CommonActions.typeText(parentCategoryInput, parentCategoryName);
        CommonActions.selectDropdownOption(parentCategoryDropdown, parentCategoryName);
    }

    public void navigateToLastPage() {
        CommonActions.clickElement(lastPageNavigationLink, "Last Page Navigation Link");
        CommonActions.waitForElementContainAttribute(
                LAST_PAGE_NAVIGATION_ITEM, "class", "active");
    }

    public boolean isCategoryPresent(String categoryName) {
        CommonActions.waitForElementDisplayed(header);
        List<String> names = new ArrayList<>();
        // Dinamically find row corresponding to the category name
        List<WebElement> rows = CommonActions.findElements(ROWS_LOCATOR);
        for (WebElement row : rows) {
            WebElement nameCell = row.findElement(NAME_CELL_LOCATOR);
            names.add(CommonActions.getText(nameCell));
        }
        return names.contains(categoryName);
    }

    public String getParentBasedOnCategory(String categoryName) {
        CommonActions.waitForElementDisplayed(header);
        List<WebElement> rows = CommonActions.findElements(ROWS_LOCATOR);
        for (WebElement row : rows) {
            WebElement nameCell = row.findElement(NAME_CELL_LOCATOR);
            String nameText = CommonActions.getText(nameCell);
            if (nameText.equals(categoryName)) {
                WebElement parentCell = row.findElement(PARENT_CELL_LOCATOR);
                return CommonActions.getText(parentCell);
            }
        }
        return null; // Category not found
    }

    public boolean isSubcategoryUnderParent(String subCategoryName, String parentCategoryName) {
        CommonActions.waitForElementDisplayed(header);
        if(!isCategoryPresent(subCategoryName)) {
            return false; // If subcategory is not present, no need to check parent
        }
        String parentText = getParentBasedOnCategory(subCategoryName);
        if (parentText == null) {
            return false; // If parent is not found, return false
        }
        return parentText.equals(parentCategoryName);
    }

    private void openModal() {
        CommonActions.waitForElementToDisappear(successToastMessage);
        CommonActions.clickElement(addCategoryButton, "Add Category Button");
        CommonActions.waitForElementDisplayed(modalHeader);
    }

}
