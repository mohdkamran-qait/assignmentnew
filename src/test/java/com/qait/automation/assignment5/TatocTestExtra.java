package com.qait.automation.assignment5;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class TatocTestExtra {
	WebDriver driver;

	@Test
	public void tatocTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/Selenium_Drivers/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(" http://10.0.1.86/tatoc");
		PageObjects object = PageFactory.initElements(driver, PageObjects.class);

		object.BasicTatocLink.click();
		object.SelectGreenBox.click();
		driver.switchTo().frame("main");

		String b1 = object.SelectBox1.getAttribute("class");
		System.out.println(b1 + ".........");
		String b2;
		boolean flag = true;
		while (flag) {
			// Thread.sleep(1000);
			driver.switchTo().frame("child");
			b2 = object.SelectBox2.getAttribute("class");
			System.out.println(b2);
			// Thread.sleep(1000);
			if (b2.equals(b1)) {
				flag = false;
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");
				object.ProceedLink.click();

			} else {
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");

				object.RepaintBoxLink.click();
			}

		}

		driver.switchTo().defaultContent();

		WebElement source = object.DragBox;
		WebElement target = object.DropBox;
		(new Actions(driver)).dragAndDrop(source, target).perform();
		object.ProceedLink.click();
		Thread.sleep(1000);

		object.LaunchPopupWindow.click();
		Thread.sleep(1000);

		String parentHandle = driver.getWindowHandle();
		System.out.println(parentHandle + "..................");
		for (String winHandle : driver.getWindowHandles()) {
			System.out.println(winHandle);
			driver.switchTo().window(winHandle);
		}

		object.SendName.sendKeys("Player");
		Thread.sleep(500);
		object.SubmitButton.click();

		driver.switchTo().window(parentHandle);
		object.ProceedLink.click();
		object.GenerateToken.click();
		String[] tokengenerated = object.GetToken.getText().split(": ");
		System.out.println(tokengenerated[1]);
		Cookie cookie = new Cookie("Token", tokengenerated[1]);
		driver.manage().addCookie(cookie);
		object.ProceedLink.click();

		driver.quit();

	}
}
