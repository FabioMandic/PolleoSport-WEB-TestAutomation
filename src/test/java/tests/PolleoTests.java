package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class PolleoTests extends BaseTest {

    @Test
    public void test1_homePageTitle() {
        HomePage home = new HomePage(driver).open();
        Assert.assertTrue(home.title().toLowerCase().contains("polleo"), "Title ne sadrži 'polleo'.");
    }

    @Test
    public void test2_searchProtein_hasResults() {
        HomePage home = new HomePage(driver).open();
        SearchPage search = home.openSearch("protein");
        Assert.assertTrue(search.hasResults(), "Search nema rezultate za 'protein'.");
    }

    @Test
    public void test3_searchCreatine_urlContainsSearch() {
        HomePage home = new HomePage(driver).open();
        SearchPage search = home.openSearch("kreatin");
        Assert.assertTrue(search.currentUrl().contains("route=product/search"), "URL nije search route.");
    }

    @Test
    public void test4_openCategoryProteini() {
        HomePage home = new HomePage(driver).open();
        CategoryPage cat = home.openProteiniCategory();
        Assert.assertTrue(cat.currentUrl().contains("proteini"), "Nije otvorena kategorija Proteini.");
        Assert.assertTrue(cat.hasProducts(), "Kategorija Proteini nema proizvode (ili selector nije pogođen).");
    }

    @Test
    public void test5_openCategoryVitamini() {
        HomePage home = new HomePage(driver).open();
        CategoryPage cat = home.openVitaminiCategory();
        Assert.assertTrue(cat.currentUrl().contains("vitamini"), "Nije otvorena kategorija Vitamini i zdravlje.");
        Assert.assertTrue(cat.hasProducts(), "Kategorija Vitamini nema proizvode (ili selector nije pogođen).");
    }

    @Test
    public void test6_openPoslovnicePage() {
        HomePage home = new HomePage(driver).open().acceptCookiesIfPresent();
        home.openTopLinkByText("Poslovnice");
        Assert.assertTrue(home.urlContains("poslovnice") || home.pageContainsText("Poslovnice"),
                "Poslovnice nije otvoreno");
    }

    @Test
    public void test7_openPovratiPage() {
        HomePage home = new HomePage(driver).open().acceptCookiesIfPresent();
        home.openTopLinkByText("Povrati");
        Assert.assertTrue(home.urlContains("povrat") || home.pageContainsText("Povrat"),
                "Povrati nije otvoreno");
    }


    @Test
    public void test8_addProductToCart_fromSearch() {
        HomePage home = new HomePage(driver).open();

        home.openSearch("protein");


        ProductPage product = home.openFirstProductFromListing();
        Assert.assertTrue(product.isOpened(), "Product page nije otvoren.");


        int before = product.cartBadgeCount();
        product.addToCart();

        // 4) provjeri da se badge povećao ili bar > 0
        int after = product.cartBadgeCount();

        Assert.assertTrue(after > before || after > 0,
                "Košarica se nije povećala nakon add-to-cart. before=" + before + ", after=" + after);
    }
}
