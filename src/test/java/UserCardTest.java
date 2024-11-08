import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class UserCardTest {
    static WebDriver driver;

    @BeforeEach
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 1200));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.get("http://5.181.254.246:8080/sd/");
    }

    public void authorization() {
        // Вводим логин и пароль
        driver.findElement(By.id("username")).sendKeys("user28");
        driver.findElement(By.id("password")).sendKeys("EHVy2q#sra");

        // Нажимаем кнопку "Войти"
        driver.findElement(By.id("submit-button")).click();
    }

    public void logout () {
        driver.findElement(By.id("gwt-debug-logout")).click();
    }

    @Test
    public void addUserCardToFavourite() throws InterruptedException {
        // Авторизация
        authorization();

        // Нажимаем кнопку "Добавить в избранное"
        driver.findElement(By.id("gwt-debug-favorite")).click();

        // Подтверждаем добавление
        driver.findElement(By.id("gwt-debug-apply")).click();

        // Пауза 3 сек.
        Thread.sleep(3000);

        // Открываем панель избранного
        Actions actions = new Actions(driver);
        WebElement webElement = driver.findElement(By.xpath("//div[@id='gwt-debug-c5af86c7-6e4d-a611-55f9-d3fc8dcc236c']/div"));
        actions.moveToElement(webElement).perform();
        webElement.click();

        // Проверяем появился элемент в избранном или нет
        WebElement element = driver.findElement(By.linkText("Студент \"Мозгляков Глеб\"/Карточка сотрудника"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили %s", "Студент \"Мозгляков Глеб\"/Карточка сотрудника", textElement);
        Assertions.assertEquals("Студент \"Мозгляков Глеб\"/Карточка сотрудника", textElement, msg);

        // Наводим на элемент в избранном
        actions.moveToElement(element).perform();

        // Нажимаем на кнопку удаления
        driver.findElement(By.xpath("//div[@id='gwt-debug-NTreeItemContent.uuid:employee$7527']/div/span")).click();

        // Подтверждаем удаление
        driver.findElement(By.id("gwt-debug-yes")).click();

        //Выход из системы
        logout();
    }

    @Test
    public void deleteUserCardFromFavourite() throws InterruptedException {
        // Авторизация
        authorization();

        // Нажимаем кнопку "Добавить в избранное"
        driver.findElement(By.id("gwt-debug-favorite")).click();

        // Подтверждаем добавление
        driver.findElement(By.id("gwt-debug-apply")).click();

        // Пауза 3 сек.
        Thread.sleep(3000);

        // Открываем панель избранного
        Actions actions = new Actions(driver);
        WebElement webElement = driver.findElement(By.xpath("//div[@id='gwt-debug-c5af86c7-6e4d-a611-55f9-d3fc8dcc236c']/div"));
        actions.moveToElement(webElement).perform();
        webElement.click();

        // Проверяем появился элемент в избранном или нет
        WebElement element = driver.findElement(By.linkText("Студент \"Мозгляков Глеб\"/Карточка сотрудника"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили %s", "Студент \"Мозгляков Глеб\"/Карточка сотрудника", textElement);
        Assertions.assertEquals("Студент \"Мозгляков Глеб\"/Карточка сотрудника", textElement, msg);

        // Наводим на элемент в избранном
        actions.moveToElement(element).perform();

        // Нажимаем на кнопку удаления
        driver.findElement(By.xpath("//div[@id='gwt-debug-NTreeItemContent.uuid:employee$7527']/div/span")).click();

        // Подтверждаем удаление
        driver.findElement(By.id("gwt-debug-yes")).click();

        // Проверяем удалился ли элемент
        List<WebElement> elements = driver.findElements(By.xpath("(//div[@id='gwt-debug-title']/div)[2]"));
        Assertions.assertTrue(elements.isEmpty());

        //Выход из системы
        logout();
    }

    @AfterEach
    public void close() {
        driver.close();
    }
}
