package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonActions;

public class CategoriesPage extends BasePage {

    public CategoriesPage() {
        super();
    }

    @FindBy(css = "button[class='btn btn-primary']")
    public WebElement addCategoryButton;

    @FindBy(xpath = "//h3[text()='Tipos de categorías']")
    public WebElement header;

    public void clickAddCategory(){
        CommonActions.clickElement(addCategoryButton, "Add Category Button");
    }
    public String getHeaderText() {
        return CommonActions.getText(header);
    }





}
