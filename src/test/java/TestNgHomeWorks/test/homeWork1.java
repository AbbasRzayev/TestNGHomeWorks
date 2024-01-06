package TestNgHomeWorks.test;

import TestNgHomeWorks.pages.tapAz;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import org.testng.annotations.Test;
import TestNgHomeWorks.pages.tapAz;
import TestNgHomeWorks.utilities.ConfigReader;
import TestNgHomeWorks.utilities.ReusableMethods;
import org.testng.asserts.SoftAssert;
import TestNgHomeWorks.utilities.ExcelUtils;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import static org.junit.Assert.*;
import static TestNgHomeWorks.utilities.Driver.closeDriver;
import static TestNgHomeWorks.utilities.Driver.getDriver;

public class homeWork1 {
    tapAz page = new tapAz();
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void checkSiteUrl() {
        /*
        1.	Kullanici https://tap.az/ sitesine gider
        2.	Kullanici https://tap.az/ sitesine gittiğini url ile dogrular
        */
        getDriver().get(ConfigReader.getProperty("tapAz"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String expectedTapUrl = "https://tap.az/";
        String actualTapUrl = getDriver().getCurrentUrl();
        softAssert.assertEquals(expectedTapUrl, actualTapUrl, "Url ayni");
        closeDriver();
    }

    @Test  (dependsOnMethods = "checkSiteUrl")
    public void checkSiteTittle() throws InterruptedException {
        /*
        3.	Kullanıcı Turbo.az linkine sağ klik yapıp yeni sekmede açar
        4.	Kullanıcı yeni sekmeye geçer
        5.	Kullanıcı https://tap.az/ sayfasına geri döner
        6.	Kullanici “yeni elan” butonuna basar
        7.	Kullanici yeni elan linkine gectigini title ile dogrular
         */
        getDriver().get(ConfigReader.getProperty("tapAz"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String firstPage = getDriver().getWindowHandle();
        ReusableMethods.executeJavaScript(getDriver(), "arguments[0].dispatchEvent(new MouseEvent('contextmenu'));", page.turbo);
        ReusableMethods.executeJavaScript(getDriver(), "window.open(arguments[0].href, '_blank');", page.turbo);
        Set<String> windowHandles = getDriver().getWindowHandles();
        for (String windowHandle : windowHandles) {
            getDriver().switchTo().window(windowHandle);
        }
        getDriver().switchTo().window(firstPage);
        page.yeniElan.click();
        String expectedTapTittle = "Yeni elan — Tap.Az";
        String actualTapTittle = getDriver().getTitle();
        softAssert.assertEquals(expectedTapTittle, actualTapTittle, "Url tittle");
        closeDriver();
    }

    @Test  (dependsOnMethods = "checkSiteTittle")
    public void checkLettersCount() throws InterruptedException {
        /*
        6.	Kullanici “yeni elan” butonuna basar
        7.	Kullanici yeni elan linkine gectigini title ile dogrular
        8.	Kullanici kategoriye secer
        9.	Kullanici şehar secer
        10.	Kullanici qiymet kutusuna deger girer
        11.	Kullanici mezmun kutusuna 100 harflik text girer
            kullanici mezmun kutusuna 100 harf girdigini dogurlar
         */
        getDriver().get(ConfigReader.getProperty("tapAz"));
        page.yeniElan.click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Select select = new Select(page.kateqoriya);
        select.selectByVisibleText("Avtomobil oturacaqları");
        Select selectCity = new Select(page.sheherList);
        selectCity.selectByVisibleText("Bərdə");
        page.sheherList.click();
        String cityName = "Bərdə";
        WebElement citySelect = getDriver().findElement(By.xpath("//*[contains(text(), '" + cityName + "')]"));
        citySelect.click();
        page.sheherList.click();
        // ReusableMethods.sendKeysJS("500",page.qiymet);
        page.qiymet.sendKeys("500");
        page.mezmun.sendKeys("$&EB7j8zb0,*G=Cx9nED8,qnRMQc75VZ,53+5FvkT%V,*BAS%Qq@qb,h4WqGY4N=r,zR+2+s1hoU,CcM9O8ANNn,oN1Ax1g4s$,R");
        String expectedMezmunSybol = "2 900";
        WebElement actualMezmunSybol = getDriver().findElement(By.xpath("//*[contains(text(), '" + expectedMezmunSybol + "')]"));
        softAssert.assertEquals(expectedMezmunSybol, actualMezmunSybol, "Simvol sayi 100");
    }

    @Test  (dependsOnMethods = "checkLettersCount")
    public void checkNotValidDatas() {
        /*
        12.	Kullanici “şekil elave et” butonuna basar
        13.	Kullanici resim ekler
        14.	Kullanici adınız kutusuna ad girer
        15.	Kullanici e-mail kutusuna geçersiz email girer (geçersiz emailleri excel’den çek)
        16.	Kullanici devam et butonuna basar
        17.	Kullanici “Вu xana boş qalmamalıdır” uyarisinin çıkana kadar 15 ve 16. İşlemi denemeye devam eder.
         */

        getDriver().get(ConfigReader.getProperty("tapAz"));
        page.yeniElan.click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Select select = new Select(page.kateqoriya);
        select.selectByVisibleText("Avtomobil oturacaqları");
        Select selectCity = new Select(page.sheherList);
        selectCity.selectByVisibleText("Bərdə");
        page.sheherList.click();
        String cityName = "Bərdə";
        WebElement citySelect = getDriver().findElement(By.xpath("//*[contains(text(), '" + cityName + "')]"));
        citySelect.click();
        page.sheherList.click();
        // ReusableMethods.sendKeysJS("500",page.qiymet);
        page.qiymet.sendKeys("500");
        page.mezmun.sendKeys("$&EB7j8zb0,*G=Cx9nED8,qnRMQc75VZ,53+5FvkT%V,*BAS%Qq@qb,h4WqGY4N=r,zR+2+s1hoU,CcM9O8ANNn,oN1Ax1g4s$,R");
        String faylPath = "C:\\Users\\Hp\\Desktop\\FileTest\\test.png";
//        try{
//            page.faylSec.click();
//        }catch (Exception e)
//        {
//          page.faylSec.sendKeys(faylPath);
//        }
        page.faylSec.click();
        ReusableMethods.robotClassDosyaYukleme(faylPath);
        ReusableMethods.scrollToElementByJavaScript(page.addName);
        page.addName.sendKeys("TestAbbas");
        String path = "src/test/java/TestNgHomeWorks/DataBase/fakeMailData.xlsx";
        String sheetName = "email";
        ExcelUtils excelUtils = new ExcelUtils(path, sheetName);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        excelUtils.rowCount();
        String error = "Вu xana boş qalmamalıdır";
        By errorXPath = By.xpath("//*[contains(text(), '" + error + "')]");
        excelUtils.rowCount();
        for (int i = 1; i <= excelUtils.rowCount(); i++) {
            String email = excelUtils.getCellData(i, 0);
            page.addEmail.sendKeys(email);
            page.submit.click();
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(errorXPath));
                break;
            } catch (org.openqa.selenium.TimeoutException e) {

            } finally {
                page.addEmail.clear();
            }
        }
    }
}