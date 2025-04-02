# 📌 Project Overview
This is a practical software test automation project that demonstrates a real-world test automation framework.

## 🛠 Framework Used  
<div style="display: flex; gap: 40px; align-items: center; justify-content: center;">
    <div style="text-align: center;">
        <strong>Selenium WebDriver</strong><br>
        <a href="https://www.selenium.dev" target="_blank" rel="noreferrer">
            <img src="https://raw.githubusercontent.com/detain/svg-logos/780f25886640cef088af994181646db2f6b1a3f8/svg/selenium-logo.svg" alt="Selenium Logo" style="width: 60px; height: 60px;" />
        </a>
    </div>
    <div style="text-align: center;">
        <strong>TestNG</strong><br>
        <a href="https://testng.org/welcome.html" target="_blank" rel="noreferrer">
            <img src="https://kms-solutions.asia/wp-content/uploads/TestNG-1024x683.webp" alt="TestNG Logo" style="width: 60px; height: 60px;" />
        </a>
    </div>
</div>

## 🌐 Demo Website Used
[Demo Blaze](https://www.demoblaze.com/)

<img title="Demo Blaze" src="https://demoblaze.com/favicon.ico">

## 🧩 Project Design:
* Page Object Model (POM) design pattern.
* **Fluent design approach** (method chaining) for better test cases readability.
* **Data-Driven Approach:** Implement a data-driven approach using properties and JSON files to handle test data in different scenarios.
* **Listeners:** Apply TestNG listeners to capture and handle events during test execution.
* **Allure Reports:** Use Allure for generating detailed and user-friendly test reports.
* **Logging:** Use Log4j and SLF4J for comprehensive logging, aiding in debugging and analysis.
* **TestNG XML:** Create an XML file to run and configure test cases, allowing for test suite setup and parameterization.

## 🚀 Extras:
* **API Injection and Cookie Handling:** Inject APIs and manage cookies to speed up test execution by avoiding repeated steps and simulating real user scenarios more efficiently.
* **Dynamic Drivers:** Implement dynamic drivers to enable parallel execution, optimizing test runtime and improving scalability.
