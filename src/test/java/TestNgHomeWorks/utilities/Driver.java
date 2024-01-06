package TestNgHomeWorks.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class Driver {
    /*
        Driver class'ındaki temel mantık extends yöntemiyle değil yani ReusableMethods class'ına extent etmek yerine
    Driver class'ından static methodlar kullanarak driver oluştururuz. Static olduğu için class ismi ile
    her yerden methoda ulaşabileceğiz.
     */
    /*
    Singleton Pattern: Tekli kullanım kalıbı.
        Bir class'tan obje oluşturulmasının önüne geçilmesi için kullanılan ifade
        Bir class'tan obje oluşturmanın önüne geçmek için default constructor'ın kullanımını engellemek için
    private access modifire kullanarak bir constructor oluştururuz
     */
    private Driver(){
/*
Singleton Pattern: Bir class'tan obje olusturulmasinin engellenmesidir.
Bunun icin class default constructor private yapildi.
 */
    }
    static WebDriver driver;

    public static WebDriver getDriver() {

        /*
            Driver'i her çağırdığında yeni bir pencere açılmasının önüne geçmek için
        if bloğu içinde Eğer driver'a değer atanmamışsa(driver doluysa) değer ata, Eğer değer atanmışsa Driver'i aynı
        sayfada RETURN et. Bunun sadece yapmamız gereken if(driver==null) kullanmak
         */
        if (driver == null) { //driver'a deger atanmamissa asagidaki degeri ata
            switch (ConfigReader.getProperty("browser")) {
                case "chrome":
                //    System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
                    //asagideki 3 satirdaki kodlar testleri Headless (Jenkins gibi) kosmak istedigimiz yerlerde aktive edilebilir

                      ChromeOptions chromeOptions = new ChromeOptions();
                      chromeOptions.addArguments("--headless"); // Başsız modu etkinleştir
                      chromeOptions.addArguments("--disable-gpu"); // GPU kullanımını devre dışı bırak
                    //  driver = new ChromeDriver(chromeOptions); //bu satir yorumdan kalkarsa, alt satir yoruma alinirsa headless calisir.

                    //asagidaki kodlar file download yaparken default deger olan download klasoru yerine bir yol vermemize yarar
//                    ChromeOptions options = new ChromeOptions();
//                    String filePath ="C:\\Hakan Arsiv";
//                    Map<String, Object> prefs = new HashMap<>();
//                    prefs.put("download.default_directory", filePath);
//                    options.setExperimentalOption("prefs", prefs);
                    //  driver = new ChromeDriver(options);  //bu satirdaki yorum slashlarini bir alt satira indirip calistirmaliyiz.
                    driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
                    break;
                case "edge":

                    driver = new EdgeDriver(new EdgeOptions().addArguments("--remote-allow-origins=*"));
                    break;
                case "chrome-headless":

                    driver = new ChromeDriver();
                    //bu secenekte chrome acilmadan test kosulur
                    break;
                default:

                    driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        }

        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {//Driver'a değer atanmışşsa
            driver.close();
            driver = null;
        }
    }

    public static void quitDriver() {
        if (driver != null) {//Driver'a değer atanmışşsa
            driver.quit();
            driver = null;
        }
    }
}

