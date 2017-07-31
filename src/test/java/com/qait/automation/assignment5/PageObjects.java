package com.qait.automation.assignment5;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObjects {

	WebDriver driver;

	@FindBy(linkText = "Basic Course")
	public WebElement BasicTatocLink;

	@FindBy(xpath = "//div[@class='greenbox']")
	public WebElement SelectGreenBox;

	@FindBy(xpath = "//div[@id='answer'][text()='Box 1']")
	public WebElement SelectBox1;

	@FindBy(xpath = "//div[text()='Box 2']")
	public WebElement SelectBox2;

	@FindBy(xpath = "//a[text()= 'Proceed']")
	public WebElement ProceedLink;

	@FindBy(xpath = "//a[text()= 'Repaint Box 2']")
	public WebElement RepaintBoxLink;

	@FindBy(xpath = "//div[@id='dragbox']")
	public WebElement DragBox;

	@FindBy(xpath = "//div[@id='dropbox']")
	public WebElement DropBox;

	@FindBy(xpath = "//a[text()= 'Launch Popup Window']")
	public WebElement LaunchPopupWindow;

	@FindBy(xpath = "//input [@id='name']")
	public WebElement SendName;

	@FindBy(xpath = "//input[@id='submit']")
	public WebElement SubmitButton;

	@FindBy(xpath = "//a [text()= 'Generate Token']")
	public WebElement GenerateToken;

	@FindBy(xpath = "//span[@id='token']")
	public WebElement GetToken;

	public WebElement getBasicTatocLink() {
		return BasicTatocLink;
	}

	public WebElement getSelectGreenBox() {
		return SelectGreenBox;
	}

	public WebElement getSelectBox1() {
		return SelectBox1;
	}

	public WebElement getSelectBox2() {
		return SelectBox2;
	}

	public WebElement getProceedLink() {
		return ProceedLink;
	}

	public WebElement getRepaintBoxLink() {
		return RepaintBoxLink;
	}

	public WebElement getDragBox() {
		return DragBox;
	}

	public WebElement getDropBox() {
		return DropBox;
	}

	public WebElement getLaunchPopupWindow() {
		return LaunchPopupWindow;
	}

	public WebElement getSendName() {
		return SendName;
	}

	public WebElement getSubmitButton() {
		return SubmitButton;
	}

	public WebElement getGenerateToken() {
		return GenerateToken;
	}

	public WebElement getGetToken() {
		return GetToken;
	}

	public void MainPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

}