package com.vvs.roomReservations.selenium;

import com.vvs.roomReservations.data.repository.RoomRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {
    @LocalServerPort
    private int localPort;
    private String serverUrl;

    private WebDriver webDriver;
    @BeforeAll
    public static void init(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void initServerUrl(){
        this.serverUrl="http://localhost:" + localPort;
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }
    @AfterEach
    public void tearDown(){
        if(webDriver != null){
            webDriver.quit();
        }
    }
    @Test
    public void goToShowReservationsPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);
        By btn_getReservations = By.id("btn_get_reservations");
        wait.until(elementToBeClickable(btn_getReservations));
        webDriver.findElement(btn_getReservations).click();
        Thread.sleep(1000);
        String newURL=serverUrl+"/reservations";
        assertEquals(newURL,webDriver.getCurrentUrl());
    }

    @Test
    public void goToAddReservationsPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);
        By btn_getReservations = By.id("btn_add_reservations");
        wait.until(elementToBeClickable(btn_getReservations));
        webDriver.findElement(btn_getReservations).click();
        Thread.sleep(1000);
        String newURL=serverUrl+"/addReservation";
        assertEquals(newURL,webDriver.getCurrentUrl());
    }

    @Test
    public void goToShowReservationsPageAndBackAgainToMainPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);
        By btn_getReservations = By.id("btn_get_reservations");
        wait.until(elementToBeClickable(btn_getReservations));
        webDriver.findElement(btn_getReservations).click();
        Thread.sleep(1000);
        By go_back = By.xpath("//a[@href='/']");
        wait.until(elementToBeClickable(go_back));
        webDriver.findElement(go_back).click();
        Thread.sleep(1000);
        assertEquals(serverUrl,webDriver.getCurrentUrl());
    }

    @Test
    public void goToAddReservationsPageAndBackAgainToMainPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);
        By btn_getReservations = By.id("btn_add_reservations");
        wait.until(elementToBeClickable(btn_getReservations));
        webDriver.findElement(btn_getReservations).click();
        Thread.sleep(2000);
        By go_back = By.xpath("//a[@href='/']");
        wait.until(elementToBeClickable(go_back));
        webDriver.findElement(go_back).click();
        Thread.sleep(2000);
        assertEquals(serverUrl,webDriver.getCurrentUrl());
    }

    @Test
    public void showReservationsByDate() throws InterruptedException {
        String mon="January";
        String day= "1";
        String person="Young, Judith";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);
        By btn_getReservations = By.id("btn_get_reservations");
        wait.until(elementToBeClickable(btn_getReservations));
        webDriver.findElement(btn_getReservations).click();
        Thread.sleep(3000);
        By datePicker = By.id("datepicker");
        wait.until(elementToBeClickable(datePicker));
        webDriver.findElement(datePicker).click();
        Thread.sleep(3000);
        WebElement month = webDriver.findElement(By.xpath("//div[@class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/div/div/span[1]"));
        System.out.println(month.getText());

        while(true){
            Thread.sleep(1000);
            if(month.getText().equals(mon)){
                break;
            }
            webDriver.findElement(By.xpath("//div[@class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/div/a[1]")).click();
            month = webDriver.findElement(By.xpath("//div[@class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/div/div/span[1]"));
        }

        WebElement dayS =  webDriver.findElement(By.xpath("//div[@class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']/table/tbody/tr[1]/td[4]"));
        WebElement personS = null;
        if(dayS.getText().equals(day)){
            dayS.click();
            Thread.sleep(1000);
            personS =  webDriver.findElement(By.xpath("//body/div[@class='container']/table/tbody/tr[9]/td[3]"));
        }
        Thread.sleep(1000);
        assertEquals(person,personS.getText());
    }

    @Test
    public void testWhenAdReservation() throws InterruptedException {
        serverUrl=serverUrl+"/addReservation";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);
        Thread.sleep(2000);
        String roomID="1";
        String guestID="1";
        By rroomID=By.id("roomID");
        wait.until(presenceOfElementLocated(rroomID));

    }
}
