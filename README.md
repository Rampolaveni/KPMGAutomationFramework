
# 📘 KPMG Automation Framework

## 📌 Overview

This project is a web automation testing framework (**Java + Selenium + TestNG automation framework**) built using a layered architecture.

The framework supports:

* Browser selection via JVM arguments
* Environment-based configuration
* Thread-safe WebDriver handling
* Page Object Model using PageFactory
* Listener-driven test lifecycle
* Scenario orchestration layer
* DAO interaction layer
* Centralized utilities
* Reporting-ready architecture

---

---

# 🏗️ Framework Architecture

```
Test Class
   ↓
Scenario Layer
   ↓
DAO Layer
   ↓
Object Repository (Locators)
   ↓
Selenium WebDriver
```

---

---

# 📂 Package Structure

```
com.kpmg.webAutomation
│
├── automationTests
│   └── functional          → TestNG test classes
│
├── scenarios               → Business flows
│
├── dao                     → Page interaction logic
│
├── objectRepository        → @FindBy locators
│
├── utils                   → Common reusable actions
│
├── controllers
│   ├── DriverManager       → ThreadLocal WebDriver
│   ├── DriverClass         → Browser creation
│   └── SetUpTest           → Test lifecycle hooks
│
├── common
│   └── Listeners           → TestNG listeners
│
└── resources
    └── webConfig           → Environment configs
```

---

---

# 🚀 Execution Flow

### ✅ 1. TestNG starts test

Test class extends `SetUpTest`.

---

### ✅ 2. Listener creates browser

Inside `Listeners.beforeInvocation()`:

* Reads browser from JVM
* Creates WebDriver
* Stores in `ThreadLocal`
* Opens application URL

```java
WebDriver driver = DriverClass.createInstance(browserName);
DriverManager.setDriver(driver);
driver.get(SetUpTest.strUrlVal);
```

---

---

### ✅ 3. Scenario Layer orchestrates flow

Example:

```java
public void verifyUserDataFromHomePage() throws Exception {
    getHomePageDAO().enterUserData();
}
```

Scenarios contain **only business flows**.

---

---

### ✅ 4. DAO performs actions

DAO initializes:

* WebDriver
* PageFactory locators
* Utilities

```java
this.driverInstance = DriverManager.getDriver();
this.homeLocatorsPage =
        PageFactory.initElements(driverInstance, HomePageLocators.class);
```

---

---

### ✅ 5. Locators defined separately

```java
@FindBy(xpath = "//input[@id='name']")
public WebElement btnName;
```

---

---

### ✅ 6. Utilities wrap Selenium

```java
public boolean type(WebElement element, String message, String value)
```

All waits and interactions live here.

---

---

### ✅ 7. Tear down

After method:

```java
DriverManager.getDriver().quit();
```

---

---

# ⚙️ Running Tests

---

## ▶ From IntelliJ

Add VM options:

```
-DTestEnv=qa
-Dbrowser=chrome
```

Run TestNG test.

---

---

## ▶ From Maven

```
mvn clean test -DTestEnv=qa -Dbrowser=chrome
```

---

---

# 🌍 Environment Configuration

Stored in:

```
src/main/resources/webConfig/environment.properties
```

Example:

```
qa=https://testautomationpractice.blogspot.com/
prod=https://prod.example.com
```

---

---

# 🧠 Key Design Concepts

---

## 🔐 ThreadLocal Driver

Each test thread has its own WebDriver:

```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

---

---

## 🧩 Listener Driven Startup

Driver creation happens in Listener — **not in DAO/Test**.

This avoids:

❌ null drivers
❌ race conditions
❌ parallel failures

---

---

## ⚠️ Common Pitfall (What You Hit)

### ❌ Creating DAO too early

If DAO is constructed **before** Listener runs:

```
DriverManager.getDriver() → null
```

👉 leads to:

```
searchContext is null
```

---

### ✅ Correct Pattern

Create DAO **inside Scenario method**, not constructor.

```java
private HomePageDAO getHomePageDAO() {

    if (homePageDAO == null) {
        homePageDAO = new HomePageDAO();
    }

    return homePageDAO;
}
```

---

---

# 🛠️ Best Practices

✔ Never initialize DAO in Scenario constructor
✔ Always lazy-load DAO
✔ Keep Scenarios thin
✔ DAO handles page only
✔ Utilities wrap Selenium
✔ DriverManager is single source of driver
✔ No static WebDriver fields elsewhere

---

---

# 🔮 Future Enhancements

* ExtentReports integration
* Log4j implimentation
* Retry analyzer
* Screenshot capture on failure
* Docker/Grid support
* CI/CD pipelines
* Parallel execution
* TestRail Integration
* API + UI hybrid testing
