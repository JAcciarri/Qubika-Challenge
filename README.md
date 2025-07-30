# Qubika Automation Challenge

## Table of Contents

- [About the Project](#about-the-project)
- [Solution Overview](#solution-overview)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Reports](#test-reports)
- [Future Enhancements and Improvements](#future-enhancements-and-improvements)

## About the Project

This repository presents the Qubika Sports Club Automation solution, featuring in-depth manual defect analysis and a robust Automation Test Framework designed to execute both UI and API test scenarios against the Qubika web application.

## Solution Overview

- **Manual Defect Reports**: Four detailed PDF documents in the `/defect-reports` directory covering:

  - Security Vulnerability
  - Input Data Validation 
  - UX/UI issues and suggestions

- **Automated Testing Framework**: Java-based leveraging Selenium WebDriver, RestAssured and TestNG:

  - **Page Object Model (POM)** for clear separation of page interactions and test logic
  - **WebDriverFactory** for flexible WebDriver management
  - **DriverManager**: thread-safe ThreadLocal<WebDriver> for parallel tests
  - **Parallel execution** configured in TestNG to allow multiple tests to run simultaneously
  - **Multi-Browser Support** configured for Chrome/Firefox/Edge, with easy extension to BrowserStack
  - **Data-Driven Tests** approach using JSON files for flexible Test Data management
  - **Dynamic locators** combining CSS and XPath for robust element targeting
  - **Common Actions** for reusable robust methods across tests
  - **Logging & Reporting** using TestNG, ExtentReports and custom utilities

## Project Structure

```
Qubika-Challenge/
├── src/main/java/
│ ├── webdriver/    # DriverManager & WebDriverFactory
│ ├── api/          # API Services & Models (RestAssured)
│ ├── config/       # ConfigLoader (env & URLs)
│ ├── pages/        # Page Objects (Login, Dashboard, Categories, Sidebar)
│ └── utils/        # CommonActions, Logger and Reporting
├── src/main/resources/  # config.properties
├── src/test/java/
│ ├── factories/    # Test data factories
│ ├── testdata/     # JsonDataProvider
│ ├── tests/        # BaseTest & E2ETest
│ └── utils/        # Additional test helpers/listeners
├── src/test/resources/
│ └── data/         # JSON files for data-driven tests
├── testng/         # TestNG suite configurations
│   └── testng.xml  # Main TestNG suite file
├── defect-reports/ # Manual defect PDF reports
├── pom.xml         # Maven project configuration and dependencies
└── README.md       # Project overview and instructions
```

## Prerequisites

- **Java** 14+ installed and `JAVA_HOME` configured
- **Maven** 3.7+ installed
- **Chrome/Firefox/Edge browsers** available for local runs

## Installation

Clone the repository and install dependencies:

```bash
git clone https://github.com/JAcciarri/Qubika-Challenge.git
cd Qubika-Challenge
mvn clean install
```

## Configuration


Modify testng.xml to set browser and environment parameters:

```xml
    <parameter name="browser" value="chrome"/>
    <parameter name="executionEnv" value="local|jenkins"/>
```
Edit `src/main/resources/config.properties` for global config (testng.xml overrides these values):

```properties
executionEnv=local|jenkins
timeouts.maxWait=5|10|20
```
## Running Tests

- **Run tests**:

  ```bash
  mvn clean test
  ```

- **Execute default suite**:

  ```bash
  mvn test -DsuiteXmlFile=testng/testng.xml
  ```

## Test Reports

- **Surefire report**: `target/surefire-reports/index.html`
- **Open Extent Report after execution**:

  ```bash
  open target/extent-report.html      # macOS
  xdg-open target/extent-report.html  # Linux
  start target\extent-report.html     # Windows
  ```

## Future Enhancements and Improvements

- **Cross-browser Testing**: Integrate BrowserStack or Selenium Grid for broader coverage. WebDriverFactory can be easily extended to support remote execution, this would allow tests to run on multiple browsers and devices in parallel.
- **API Testing**: Add lots of RestAssured Tests for backend validation, previously adding more Services and Models.
- **CI/CD Integration**: Configure Jenkins Pipelines or GitHub Actions to trigger Automated tests or maybe a Smoke suite on commits/Pull Request.
- **Advanced Reporting**: Currently using ExtentReports, for richer test insights, we could add screenshot captures on failures, advanced and automated reporting, emails to be sent to stakeholders or QA Team.

## License

This project is licensed under the MIT License.

