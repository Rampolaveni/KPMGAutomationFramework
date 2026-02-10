package com.kpmg.webAutomation.scenarios;

import com.kpmg.webAutomation.controllers.SetUpTest;
import com.kpmg.webAutomation.dao.HomePageDAO;

public class HomePageScenarios extends SetUpTest {

    private HomePageDAO homePageDAO;

    public HomePageDAO getHomePageDAO() {
        return this.homePageDAO = new HomePageDAO();
    }

    public void verifyUserDataFromHomePage() throws Exception {
            homePageDAO = getHomePageDAO();
            homePageDAO.enterUserData();
    }
}
