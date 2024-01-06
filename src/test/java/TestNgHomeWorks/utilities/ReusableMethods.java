package TestNgHomeWorks.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static TestNgHomeWorks.utilities.Driver.getDriver;
import static org.junit.Assert.assertTrue;


public class ReusableMethods {
    /**
     * Hard wait istenemeyen wait. Butun kodlari durdurur.
     * Gozle testi izleyebilmek icin koyduk. Otomasyon testi kosarken kaldirilmali.
     * @param saniye int cinsinden saniye olarak verilmeli
     */
    public static void wait(int saniye){
        try {
            Thread.sleep(saniye*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * bu metot verilen iki stringin birbirine esit oldugunu dogrular
     * @param str1 verilecek olan 1. string parametresidir
     * @param str2 verilecek olan 2. string parametresidir
     */
    public static void assertString(String str1, String str2){

        assertTrue(str1.equals(str2));
    }

    /**
     * alerti reddeder / cancel yapar
     */
    public static void dismissAlert(){
        getDriver().switchTo().alert().dismiss();
    }

    /**
     * alerti accept yapar
     */
    public static void acceptAlert(){
        getDriver().switchTo().alert().accept();
    }

    /**
     * alerte text gondermek icin kullaniriz
     * @param str alertin icine gonderilecek olan string dir.
     */
    public static void sendKeysToAlert(String str) {

        getDriver().switchTo().alert().sendKeys(str);
    }
    /**
     * WebElement ScreenShot alir
     * @param element screen shot alinacak elementin locate verilir
     */
    public static void webElementScreenShoot(WebElement element){
        String tarih = new SimpleDateFormat("_hh_mm_ss_ddMMyyyy").format(new Date());
        String dosyaYolu= "TestOutput/webElementScreenShoot"+tarih+".png";

        try {
            FileUtils.copyFile(element.getScreenshotAs(OutputType.FILE), new File(dosyaYolu));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * tum sayfanin screenshoot ini alir
     */
    public static void tumSayfaScreenShoot(){

        String tarih = new SimpleDateFormat("_hh_mm_ss_ddMMyyyy").format(new Date());
        String dosyaYolu= "TestOutput/screenshoot"+tarih+".png";
        TakesScreenshot takesScreenshot= (TakesScreenshot) getDriver();

        try {
            FileUtils.copyFile(takesScreenshot.getScreenshotAs(OutputType.FILE), new File(dosyaYolu));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sayfada mause tekerinin bir donusu kadar asagi iner
     */
    public static void pageDown(){
        Actions actions=new Actions(getDriver());
        actions.sendKeys(Keys.PAGE_DOWN).perform();
    }

    /**
     * sayfada mause tekerinin bir donusu kadar asagi iner
     */
    public static void pageUp(){
        Actions actions=new Actions(getDriver());
        actions.sendKeys(Keys.PAGE_UP).perform();
    }

    /**
     * bu metot ile locate verilen element gorunene kadar scroll yapilir
     * @param element scroll yapilacak elementin locate idir.
     */
    public static void scrollToElementByActions(WebElement element){
        Actions actions=new Actions(getDriver());
        actions.scrollToElement(element).perform();
    }

    /**
     * javascript ile bir element üstünde sağ klik yapmaya yarar
     * @param element yerine üstünde sağ klik yapilacak elementin locate verilir
     */
    public static void contextClickByJavascript( WebElement element){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        String script= "var element = arguments[0];" +
                "var evt= new MouseEvent('contextmenu', { bubbles: true, cancelable: true, view:window});"+
                "element.dispatchEvent(evt);"+
                "window.open(element.href, '_blanck');";

        javascriptExecutor.executeScript(script, element );
    }

    /**
     * Bu metot ile bir dosyayi bilgisayardan secerek yukleme yapilir. Metot dosya yukleme
     * isteyen bir pencere acildiginda kullanilabilir
     * @param filePath bilgisayardan yuklenecek dosya yolu
     */
    public static void robotClassDosyaYukleme(String filePath){
        try{
            ReusableMethods.wait(3);
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
            Robot robot = new Robot();
            //pressing ctrl+v
            robot.keyPress(KeyEvent.VK_CONTROL);
            ReusableMethods.wait(3);
            robot.keyPress(KeyEvent.VK_V);
            ReusableMethods.wait(3);
            //releasing ctrl+v
            robot.keyRelease(KeyEvent.VK_CONTROL);
            ReusableMethods.wait(3);
            robot.keyRelease(KeyEvent.VK_V);
            ReusableMethods.wait(3);
            System.out.println("PASSED");
            //pressing enter
            ReusableMethods.wait(3);
            robot.keyPress(KeyEvent.VK_ENTER);
            ReusableMethods.wait(3);
            //releasing enter
            robot.keyRelease(KeyEvent.VK_ENTER);
            ReusableMethods.wait(3);
            System.out.println("ENTER");
        }catch (Exception e){
        }
    }

    /**
     * bu metot ile bir elementin value'suna deger atanir.
     * @param element deger atanacak elementin locate verilmeli
     * @param text elemente gönderilecek value verilmeli
     */
    public static void sendAttributeJS(String text, WebElement element ) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].setAttribute('value','" + text + "')", element);
    }

    /**
     * bu metot ile javascript kullanarak bir elemente sendKey yapılır
     * @param element sendKey yapılacak elementin locate verilmeli
     * @param text elemente gönderilecek değer verilmeli
     */
    public static void sendKeysJS( String text, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].value='" + text + "'", element);

    }

    /**
     * bu metot ile bir webelemente javascript ile click yapılır
     * @param element yerine elementin locate'i verilir
     */
    public static void clickByJavaScript(WebElement element){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }

    /**
     * bu metot ile locate'i verilen elemente kadar sayfa scroll yapılır.
     * @param element yerine üzerine scroll yapılacak webelelement locate'i verilir
     */
    public static void scrollToElementByJavaScript(WebElement element){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * bu metot ile javascript kullanarak sayfanın en altına scroll yapılır
     */
    public static void scrollEndByJavascript(){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    /**
     * bu metot ile javascript kullanarak sayfanın en üstüne scroll yapılır
     */
    public static void scrollTopByJavascript(){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        javascriptExecutor.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
    }
    /**
     * bu metot javascript kullanarak bir elementin value'sunu string olarak döndürür
     * @param id yerine elementin id değeri verilir
     * @param attributeName yerine elementin id'si yazılır
     * @return string return eder
     */
    public static String getValueByJavaScript(String id,String attributeName){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        String string= javascriptExecutor.executeScript("return document.getElementById('"+id+"')."+attributeName).toString();
        return string;
    }

    /**
     * Bu metot Actions class kullanarak bir webelementin ustune gidip bekler
     * @param element yerine webelement'in locate koyulmalidir
     */
    public static void moveToElementWithAction(WebElement element){
        Actions action = new Actions(getDriver());
        action.moveToElement(element).perform();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * bu metot ile görünürlüğü olmayan elementin javascript kullanarak kullanarak görünürlüğünü sağlarız
     * @param element yerine görünürlüğü sağlanacak elementin locate'i verilir
     */
    public static void setElementVisible(WebElement element){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        javascriptExecutor.executeScript("arguments[0].style.opacity='1';", element);
    }

    /**
     * bu metot ile selenium kodlarıyla textini alamadığımz elementin textini javascript ile alabiliriz
     * @param xpath yerin texti alınacak elementin xpath'i string olarak verilir. Örnek: "//div[@class='Toastify__toast-body']"
     * @return olarak elementin texti String olarak döner
     */
    public static String getTextByJavascript(String xpath){
        JavascriptExecutor javascriptExecutor= (JavascriptExecutor) getDriver();
        String text= (String) javascriptExecutor.executeScript("return arguments[0].textContent;", xpath);
        return text;
    }

    public static void executeJavaScript(WebDriver driver, String script, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(script, element);
    }
}
