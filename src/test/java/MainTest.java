import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class MainTest {

    WebDriver driver = null;

    @Before
    public void init(){
        System.setProperty("webdriver.gecko.driver", "drvs/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("https://papajohns.ru/moscow");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    @Test
    public void main() {


        WebElement pizzaButton = driver.findElement(By.xpath("//*[contains(@class, 'HomePage__menu')]//*[@alt='pizza']"));
        pizzaButton.click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("pizza")));

        List<WebElement> pizzas = driver.findElements(By.xpath("//*[@id='pizza']//*[contains(@class,'ProductCard')]"));

        WebElement targetProduct = null;
        for(WebElement product : pizzas) {
            WebElement productText = product.findElement(By.xpath(".//h3"));
            if(productText.getText().equalsIgnoreCase("Сердце Пепперони")) {
                targetProduct = product;
                break;
            }
        }

        Assert.assertTrue(targetProduct != null);
            System.out.println("founded");
            WebElement toCartBtn = targetProduct.findElement(By.xpath(".//*[text()='В корзину']"));
            toCartBtn.click();

            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Оформить']")));

            WebElement confirmBtm = driver.findElement(By.xpath("//*[text()='Оформить']"));
            confirmBtm.click();

            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Ваши контакты']")));
            new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe("https://papajohns.ru/moscow/order"));

            Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://papajohns.ru/moscow/order"));

                System.out.println("continue order");

                WebElement username = driver.findElement(By.id("username"));
                username.sendKeys("Pavel");

                WebElement email = driver.findElement(By.id("email"));
                email.sendKeys("batovpa@intech.rshb.ru");

                WebElement phone = driver.findElement(By.name("phone"));
                phone.sendKeys("9011234567");

                WebElement address = driver.findElement(By.id("address_in_line"));
                address.sendKeys("Москва, улица Новый Арбат");



                new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfNestedElementLocatedBy(address, By.xpath("./../following-sibling::ul/li")));

                List<WebElement> addresses = address.findElements(By.xpath("./../following-sibling::ul/li"));
//                for(WebElement concreteAddr : addresses) {
//                    if(!concreteAddr.getText().equalsIgnoreCase("Москва, улица Новый Арбат")) {
//                        concreteAddr.click();
//                    }
//                }
                addresses.get(3).click();

                WebElement flat = driver.findElement(By.id("flat"));
                flat.sendKeys("987");


                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Наличными']")));
                WebElement cache = driver.findElement(By.xpath("//button[text()='Наличными']"));
                Assert.assertTrue(cache.isEnabled());
                    System.out.println("chache is enabled");
                WebElement change = driver.findElement(By.id("change"));
                change.sendKeys("9987");

                WebElement iframe = driver.findElement(By.xpath("//*[@title='reCAPTCHA']"));
                driver.switchTo().frame(iframe);
                WebElement captcha = driver.findElement(By.xpath("//*[@class='recaptcha-checkbox-border']"));
                captcha.click();

    }
    @After
    public void close(){
        driver.quit();
    }
}
