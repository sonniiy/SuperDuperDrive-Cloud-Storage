package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    @FindBy(id = "logOutButton")
    private WebElement logOutButton;

    // Web Elements for Note Creation Tests

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "noteDescriptionText")
    private WebElement noteDescriptionText;

    @FindBy(id = "noteTitelText")
    private WebElement noteTitelText;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 1);
    }

    public void logOut() {
        js.executeScript("arguments[0].click();", logOutButton);

    }

    // Note Test
    public void createNote(String titel, String description) {
        js.executeScript("arguments[0].click();", noteTab);
        js.executeScript("arguments[0].click();", addNoteButton);
        insertNodeTitel(titel);
        insertNotdeDescription(description);
        js.executeScript("arguments[0].click();", noteSubmitButton);
    }

    public void insertNodeTitel(String titel) {
        wait.until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.sendKeys(titel);
    }

    public void insertNotdeDescription(String description) {
        wait.until(ExpectedConditions.visibilityOf(noteDescription));
        noteDescription.sendKeys(description);
    }

    public String getNoteTitel() {
        wait.until(ExpectedConditions.visibilityOf(noteTitelText));
        return noteTitelText.getText();
    }

    public String getNoteDescription() {
        wait.until(ExpectedConditions.visibilityOf(noteDescriptionText));
        return noteDescriptionText.getText();
    }



}
