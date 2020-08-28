package br.pb.pauloabsouto.tasks.e2e;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver accessApplication() throws MalformedURLException {
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.4:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.1.4:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void addTask() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test 01");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void addTaskWithoutDescription() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void addTaskWithPastDate() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test 01");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void deleteTask() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Remove test");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

            driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
            message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success", message);
        }finally {
            driver.quit();
        }
    }

}
