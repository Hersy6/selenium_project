
import com.gargoylesoftware.htmlunit.BrowserVersion;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class finalCrome {
    WebDriver driver;
    Actions action;
    JavascriptExecutor jse;



    @BeforeMethod
    public void setUp() {


        WebDriverManager.chromedriver().setup();
        driver  = new ChromeDriver();
        action = new Actions(driver);
        jse = (JavascriptExecutor)driver;

    }
    @Test
    public void meth() throws ParseException {
        WebDriverWait wait = new WebDriverWait(driver, 3);

        //Going to page
        driver.get("https://www.google.com/");
        driver.navigate().to("http://tutorialsninja.com/demo/");
        jse.executeScript("window.location = 'http://tutorialsninja.com/demo/'");
        driver.findElement(By.cssSelector("a.dropdown-toggle")).click();

        //trying to register but if account already exists it will log in
        driver.findElement(By.xpath("//*[@id='top-links']//a[text()='Register']")).click();

        driver.findElement(By.id("input-firstname")).sendKeys("Tornike");
        driver.findElement(By.id("input-lastname")).sendKeys("Tchavcthavadze");
        driver.findElement(By.id("input-email")).sendKeys("f.chavchavadze@gmail.com");
        driver.findElement(By.id("input-telephone")).sendKeys("551444699");
        driver.findElement(By.id("input-password")).sendKeys("tornike1234");
        driver.findElement(By.id("input-confirm")).sendKeys("tornike1234");

        driver.findElement(By.name("newsletter")).click();
        driver.findElement(By.name("agree")).click();

        driver.findElement(By.cssSelector("input[value='Continue']")).click();


        if(driver.findElement(By.cssSelector("div.alert.alert-danger.alert-dismissible")).isDisplayed()){
            driver.findElement(By.cssSelector("a.dropdown-toggle")).click();
            driver.findElement(By.xpath("//a[text()='Login']")).click();
            driver.findElement(By.id("input-email")).sendKeys("f.chavchavadze@gmail.com");
            driver.findElement(By.id("input-password")).sendKeys("tornike1234");
            driver.findElement(By.cssSelector("input.btn.btn-primary[value='Login']")).click();

        }else {
            System.out.println("registered");
            driver.findElement(By.xpath("//*[@id='content']/div/div/a[contains(text(),'Continue')]")).click();
        }

        //going in phone section writing review, adding in cart nad going to checkout
        driver.findElement(By.xpath("//*[@id='menu']//a[contains(text(),'Phones & PDAs')]")).click();
        //  Here didn't get it why "img[title='Palm Treo Pro']" of css or same in xpath don.t work
        //  returnes:" element not interactable: [object HTMLImageElement] has no size and location" and copied one does
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[3]/div/div[1]/a/img"))).perform();
        System.out.println(""+driver.findElement(By.xpath("//img[@title='Palm Treo Pro']")).getAttribute("title")
                + "");

        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[3]/div/div[2]/div[1]/h4/a")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul[1]/li[1]/a/img")).click();


        System.out.println("1" + " of " +driver.findElements(By.xpath("//*[@id='content']//li[contains(@class,'thumbnail')]")).size() );

        for(int i=2; i<=driver.findElements(By.xpath("//*[@id='content']//li[contains(@class,'thumbnail')]")).size(); i++){

            driver.findElement(By.xpath("/html/body/div[2]/div/button[2]")).click();
            System.out.println(i + " of " +driver.findElements(By.xpath("//*[@id='content']//li[contains(@class,'thumbnail')]")).size() );

        }

        jse.executeScript("history.go(0)");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']//a[contains(text(),'Write a review')]")));
        driver.findElement(By.xpath("//*[@id='content']//a[contains(text(),'Write a review')]")).click();
        driver.findElement(By.id("input-name")).sendKeys("Tornike Tchavtchavadze");
        driver.findElement(By.id("input-review")).sendKeys("Nice product, my friend bought and i'm gonna buy it too.");
        driver.findElement(By.xpath("//*[@id='form-review']//input[last()]")).click();
        //driver.findElement(By.id("button-review")).click();
        jse.executeScript("document.getElementById('button-review').click();");


        driver.findElement(By.id("button-cart")).click();


        driver.findElement(By.id("cart-total")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[@id='cart']//strong[contains(text(),' Checkout')]")));
        driver.findElement(By.xpath("//*[@id='cart']//strong[contains(text(),' Checkout')]")).click();

        System.out.println( driver.findElement(By.id("cart-total")).getText());

        //here if default address exists program chose it, if not it creates new one
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("input-payment-firstname")));
            driver.findElement(By.id("input-payment-firstname")).sendKeys("Tornike");
            driver.findElement(By.id("input-payment-lastname")).sendKeys("Tchavtchavadze");
            driver.findElement(By.id("input-payment-address-1")).sendKeys("Tbilisi");
            driver.findElement(By.id("input-payment-city")).sendKeys("Tbilisi");
            driver.findElement(By.id("input-payment-postcode")).sendKeys("0067");

            Select drpCountry =new Select(driver.findElement(By.id("input-payment-country")));
            drpCountry.selectByVisibleText("Georgia");
            Select drpRegion = new Select(driver.findElement(By.id("input-payment-zone")));
            drpRegion.selectByVisibleText("Tbilisi");
            driver.findElement(By.id("button-payment-address")).click();

        }catch (TimeoutException e){

            driver.findElement(By.id("button-payment-address")).click();
        }


        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("button-shipping-address")));
        driver.findElement(By.id("button-shipping-address")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("button-shipping-method")));
        driver.findElement(By.id("button-shipping-method")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated( By.name("agree")));
        driver.findElement(By.name("agree")).click();
        driver.findElement(By.id("button-payment-method")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tbody/tr/td[5]")));

        //I took prices from page which where strings, take desirable parts from it and converted in to double
        //Then checked if status was "pending" if date matches and prices where correct
        String shipRate = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[2]/td[2]")).getText();
        int x= shipRate.length();
        String sr= shipRate.substring(1, x);

        String total = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[3]/td[2]")).getText();
        int y= total.length();
        String tot= total.substring(1, y);

        String subTotal = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[1]/td[2]")).getText();
        int z= subTotal.length();
        String subtot= subTotal.substring(1, z);

        double shipRateN= Double.parseDouble(sr);
        double totalN=Double.parseDouble(tot);
        double subTotalN=Double.parseDouble(subtot);


        if(subTotalN+shipRateN==totalN){
            System.out.println("checkout is correct ");
        }else{
            System.out.println("price mismatch");
        }

        String status = "Pending";
        driver.findElement(By.id("button-confirm")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[@id=\"content\"]/p[2]/a[2]")));

        driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/a[2]")).click();

        if(driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr[1]/td[4]")).getText().equals(status)){
            System.out.println("Correct status");
        }else {
            System.out.println("incorrect status " + driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr[1]/td[4]")).getText() );
        }


        String orderDate = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr[1]/td[6]")).getText();
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yy");
        Date date = dateParser.parse(orderDate);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date todaydate = new Date();


        if (dateFormat.format(date).equals(dateFormat.format(todaydate))){
            System.out.println("correct date");
        }else{
            System.out.println("incorrect date");
        }

        driver.quit();
    }

}