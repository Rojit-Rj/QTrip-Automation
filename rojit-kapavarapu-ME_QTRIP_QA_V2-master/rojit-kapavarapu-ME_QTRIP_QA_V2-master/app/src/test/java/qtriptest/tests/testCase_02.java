package qtriptest.tests;

import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;

public class testCase_02 
{
   static RemoteWebDriver driver;
   public static String lastGeneratedUserName;

   @BeforeSuite(
      alwaysRun = true
   )
   public static void createDriver() throws MalformedURLException {
      DriverSingleton sbc1 = DriverSingleton.getInstanceOfSingletonBrowserClass();
      driver = sbc1.getDriver();
   }




   

   @Test(
      description = "Verify that Search and filters work fine",
      priority = 2,
      dataProvider = "data-provider",
      dataProviderClass = DP.class,
      groups = {"Search and Filter flow"}
   )
   public static void TestCase02(String CityName, String Category_Filter, String DurationFilter, String ExpectedFilteredResults, String ExpectedUnFilteredResults) throws InterruptedException {
      HomePage home = new HomePage(driver);
      home.navigateToHome();
      Boolean status = home.searchElement("qwe");
      home.searchElement(CityName);
      status = home.searchResultlist(CityName);
      Assert.assertTrue(status, "Item not found");
      AdventureDetailsPage details = new AdventureDetailsPage(driver);
      status = details.resultBeforeFilters(ExpectedUnFilteredResults);
      Assert.assertTrue(status, "Quantity didn't match");
      AdventurePage adventurePage = new AdventurePage(driver);
      status = adventurePage.filterByDuration(DurationFilter);
      Assert.assertTrue(status, "Filter not applied");
      status = adventurePage.selectCategory(Category_Filter);
      Assert.assertTrue(status, "Category not applied");
      status = details.resultAfterFilters(ExpectedFilteredResults);
      Assert.assertTrue(status, "Quantity didn't match");
      adventurePage.clearDurationFilter();
      adventurePage.clearCategoryFilter();
      status = details.resultBeforeFilters(ExpectedUnFilteredResults);
      Assert.assertTrue(status, "Quantity didn't match");
      home.performLogOut();
   }
}
