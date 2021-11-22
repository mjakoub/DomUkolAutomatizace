package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    // Test1
    public void uspesnePrihlaseniRodice() throws InterruptedException {
        otevriStranku("https://cz-test-jedna.herokuapp.com/");

        prihlasovaciStranka();

        prihlaseniRodice("ssssss@seznam.cz", "Kobra11");

        WebElement vydarenePrihlaseni = prohlizec.findElement(By.xpath("//a[contains(@title,'Sss')]"));  //je treba zmenit podle jmena uzivatele
        Assertions.assertNotNull(vydarenePrihlaseni);
    }

    @Test
    // Test2
    public void uspesnePrihlaseniDoKurzu1() throws InterruptedException {
        otevriStranku("https://cz-test-jedna.herokuapp.com/");

        seznamyKurzu(1);
        Thread.sleep(1_000);

        vytvorPrihlasku();
        Thread.sleep(1_000);

        prihlaseniRodice("ssssss@seznam.cz","Kobra11");
        Thread.sleep(1_000);

        vyplnPrihlasku("Harantik","Testovaci","13.10.2013");
        Thread.sleep(1_000);

        seznamPrihlasek();
        Thread.sleep(1_000);

        WebElement prihlaska = prohlizec.findElement(By.xpath("//*[contains(text(),'Základy')]"));

        Assertions.assertNotNull(prihlaska);
    }
      @Test
      //Test 3
      public void uspesnePrihlaseniDoKurzuPoPrihlaseni() throws InterruptedException {
          otevriStranku("https://cz-test-jedna.herokuapp.com/");
          prihlasovaciStranka();
          prihlaseniRodice("ssssss@seznam.cz","Kobra11");

          WebElement novaPrihlaska = prohlizec.findElement(By.xpath("//a[contains(@class,'btn-info')]"));
          novaPrihlaska.click();

          seznamyKurzu(2);
          vytvorPrihlasku();
          vyplnPrihlasku("Harantik","Testovaci","13.10.2013");
          seznamPrihlasek();

          WebElement prihlaska = prohlizec.findElement(By.xpath("//*[contains(text(),'HTML')]"));
          Assertions.assertNotNull(prihlaska);
      }

      @Test
      //Test 4 : Odhlasit se po uspesnem prihlaseni

      public void odhlaseniSe() throws InterruptedException {
          otevriStranku("https://cz-test-jedna.herokuapp.com/");
          prihlasovaciStranka();
          prihlaseniRodice("ssssss@seznam.cz","Kobra11");
          odhlasitSe();

          WebElement prihlasit = prohlizec.findElement(By.xpath("//i[contains(@class,'fa-user')]"));
          Assertions.assertNotNull(prihlasit);
      }



    public void odhlasitSe() {
        WebElement profil = prohlizec.findElement(By.xpath("//a[contains(@title,'Sss')]"));
        profil.click();
        WebElement odhlaseni = prohlizec.findElement(By.xpath("//a[@id='logout-link']"));
        odhlaseni.click();
    }
    public void prihlaseniRodice(String email, String password) throws InterruptedException {
        zadejEmail(email);
        zadejHeslo(password);
        prihlasSe();
        Thread.sleep(1_000);
    }
    public void otevriStranku(String adresa) throws InterruptedException {
        prohlizec.navigate().to(adresa);
        Thread.sleep(1_000);         //pomaly internet
    }
    public void prihlasovaciStranka() throws InterruptedException {
        WebElement prihlasit = prohlizec.findElement(By.xpath("//i[contains(@class,'fa-user')]"));
        prihlasit.click();
        Thread.sleep(1_000);
    }
    public void zadejEmail(String email) {
        WebElement mail = prohlizec.findElement(By.xpath("//input[@id='email']"));
        mail.sendKeys(email);
    }
    public void zadejHeslo(String password) {
        WebElement heslo = prohlizec.findElement(By.xpath("//input[@id='password']"));
        heslo.sendKeys(password);
    }
    public void prihlasSe() {
        WebElement prihlasitSe = prohlizec.findElement(By.xpath("//button[contains(@class,'btn-primary')]"));
        prihlasitSe.click();
    }

    public void seznamyKurzu(int vyberJednaAzTri) {
        WebElement viceInformaci1 = prohlizec.findElement(By.xpath("(//a[contains(@class,'btn-primary')])[1]"));
        WebElement viceInformaci2 = prohlizec.findElement(By.xpath("(//a[contains(@class,'btn-primary')])[2]"));
        WebElement viceInformaci3 = prohlizec.findElement(By.xpath("(//a[contains(@class,'btn-primary')])[3]"));
        if (vyberJednaAzTri == 1) {
            viceInformaci1.click();
        }
        else if (vyberJednaAzTri == 2) {
            viceInformaci2.click();
        }
        else if (vyberJednaAzTri == 3) {
            viceInformaci3.click();
        }
    }

    public void vytvorPrihlasku() {
        WebElement vytvoritPrihlasku = prohlizec.findElement(By.xpath("//a[contains(@class,'btn-primary')]"));
        vytvoritPrihlasku.click();
    }

    public void vyplnPrihlasku(String jmenoDitete, String prijmeniDitete, String datumNarozeniDitete) throws InterruptedException {
        WebElement termin = prohlizec.findElement(By.xpath("//div[@class='filter-option-inner-inner']"));
        termin.click();
        Thread.sleep(500);
        WebElement vybranyTermin = prohlizec.findElement(By.xpath("//span[@class='text']"));
        vybranyTermin.click();
        WebElement krestniJmeno = prohlizec.findElement(By.xpath("//input[@id='forename']"));
        krestniJmeno.sendKeys(jmenoDitete);
        WebElement prijmeni = prohlizec.findElement(By.xpath("//input[@id='surname']"));
        prijmeni.sendKeys(prijmeniDitete);
        WebElement datumNarozeni = prohlizec.findElement(By.xpath("//input[@id='birthday']"));
        datumNarozeni.sendKeys(datumNarozeniDitete);
        WebElement hotove = prohlizec.findElement(By.xpath("//label[@for='payment_cash']"));  //vybrala jsem jen jeden zpusob, jinak by sly urcite vsechny
        hotove.click();
        WebElement podminky = prohlizec.findElement(By.xpath("//label[@for='terms_conditions']"));
        podminky.click();
        WebElement vytvorPrihlasku = prohlizec.findElement(By.xpath("//input[contains(@class,'mt-3')]"));
        vytvorPrihlasku.click();
    }

    public void seznamPrihlasek() {
        WebElement prihlasky = prohlizec.findElement(By.xpath("//a[@href='https://cz-test-jedna.herokuapp.com/zaci']"));
        prihlasky.click();
    }

    @AfterEach
    public void tearDown() {
        prohlizec.quit();
    }
}
