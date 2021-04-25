package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    private final JavascriptExecutor js;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "sign-up-button")
    private WebElement signupbutton;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "back-login")
    private WebElement loginLink;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void signup(String firstName, String lastName, String userName, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);
        //signupbutton.click();
        js.executeScript("arguments[0].click();", signupbutton);
    }

    public void clickLogin() {
        //loginLink.click();
        js.executeScript("arguments[0].click();", loginLink);
    }



}
