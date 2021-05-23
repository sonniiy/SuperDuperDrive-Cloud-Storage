package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final JavascriptExecutor js;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitbutton;

    @FindBy(id = "signup-link")
    private WebElement signuplink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void login(String username, String password) {
        //js.executeScript("arguments[0].value'" + username + "';" + inputUsername);
        inputUsername.sendKeys(username);

        inputPassword.sendKeys(password);
        //submitbutton.submit();
        js.executeScript("arguments[0].click();", submitbutton);
    }



}
