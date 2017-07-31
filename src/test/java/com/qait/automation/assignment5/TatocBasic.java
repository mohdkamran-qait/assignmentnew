package com.qait.automation.assignment5;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class TatocBasic {

/**
*
* Test to TatocBasic
**/




	@Test
	public void tatocBasicTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/Selenium_Drivers/chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get(" http://10.0.1.86/tatoc");
		driver.findElement(By.linkText("Basic Course")).click();
		driver.findElement(By.xpath("//div[@class='greenbox']")).click();

		// Integer size = driver.findElements(By.tagName("iframe")).size();
		// System.out.println(size);

		driver.switchTo().frame("main");
		// driver.findElement(By.xpath("//div[@id='answer'][text()='Box
		// 1']")).click();
		String b1 = driver.findElement(By.xpath("//div[@id='answer'][text()='Box 1']")).getAttribute("class");
		System.out.println(b1 + ".........");
		String b2;
		boolean flag = true;
		while (flag) {
			Thread.sleep(1000);
			driver.switchTo().frame("child");
			b2 = driver.findElement(By.xpath("//div[text()='Box 2']")).getAttribute("class");
			System.out.println(b2);
			Thread.sleep(1000);
			if (b2.equals(b1)) {
				flag = false;
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");
				driver.findElement(By.xpath("//a [text()= 'Proceed']")).click();

			} else {
				driver.switchTo().defaultContent();
				driver.switchTo().frame("main");

				driver.findElement(By.xpath("//a  [text()= 'Repaint Box 2']")).click();
			}

		}

		driver.switchTo().defaultContent();

		WebElement source = driver.findElement(By.xpath("//div[@id='dragbox']"));
		WebElement target = driver.findElement(By.xpath("//div[@id='dropbox']"));
		(new Actions(driver)).dragAndDrop(source, target).perform();
		driver.findElement(By.xpath("//a [text()= 'Proceed']")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//a [text()= 'Launch Popup Window']")).click();
		Thread.sleep(1000);

		String parentHandle = driver.getWindowHandle();
		System.out.println(parentHandle + "..................");
		for (String winHandle : driver.getWindowHandles()) {
			System.out.println(winHandle);
			driver.switchTo().window(winHandle);
		}

		driver.findElement(By.xpath("//input [@id='name']")).sendKeys("Player");
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[@id='submit']")).click();

		driver.switchTo().window(parentHandle);
		driver.findElement(By.xpath("//a [@href='#'][text()='Proceed']")).click();
		driver.findElement(By.xpath("//a [text()= 'Generate Token']")).click();
		String[] tokengenerated = driver.findElement(By.xpath("//span[@id='token']")).getText().split(": ");
		System.out.println(tokengenerated[1]);
		Cookie cookie = new Cookie("Token", tokengenerated[1]);
		driver.manage().addCookie(cookie);
		driver.findElement(By.xpath("//a[text ()='Proceed']")).click();

		driver.quit();
	}
}
