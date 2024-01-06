package TestNgHomeWorks.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import TestNgHomeWorks.utilities.Driver;

import java.util.List;

import static TestNgHomeWorks.utilities.Driver.getDriver;

public class tapAz {
    public tapAz() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy (xpath = "(//a[@class='bar-i bar-link'])[1]")
    public WebElement turbo;

    @FindBy (id = "new_ad_link")
    public WebElement yeniElan;

    @FindBy (xpath = "//select[@class='form-control js-category-select']")
    public WebElement kateqoriya;

    @FindBy (xpath = "//select[@class='select required form-control']")
    public WebElement sheherList;

    @FindBy (xpath = "//input[@name='lot[price]']")
    public WebElement qiymet;

    @FindBy (xpath = "//textarea[@name='lot[body]']")
    public WebElement mezmun;

    @FindBy (xpath = "//div[@class='characters-left js-characters-left']")
    public WebElement mezmun1;

    @FindBy (xpath = "//label[@for='pond_input']")
    public WebElement faylSec;

    @FindBy (xpath = "//input[@name='lot[contact_attributes][name]']")
    public WebElement addName;

    @FindBy (xpath = "//input[@name='lot[contact_attributes][email]']")
    public WebElement addEmail;

    @FindBy (xpath = "//button[@type='submit']")
    public WebElement submit;

    @FindBy (xpath = "//span[text()='Вu xana boş qalmamalıdır']")
    public WebElement errorMessage;


}

