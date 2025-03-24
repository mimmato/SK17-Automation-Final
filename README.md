# QA Automation Framework

## Project Overview

This project is an automated testing framework built using **TestNG**, **Selenium WebDriver**, and the **Page Object Model (POM)** pattern. It automates key user interactions within a web application, including **user registration**, **login**, and **home page** functionalities. The framework is designed to be modular, reusable, and scalable, ensuring efficient test maintenance and easy extensibility.

## Project requirements 

- Develop a **Java Maven** test automation project using **TestNG** and **Selenium WebDriver**.
- Follow the **Page Object Model (POM)** design pattern with **PageFactory** for project structure.
- Automate at least **5 different test scenarios** against the following website:

  **[Skillo Training Website](http://training.skillo-bg.com:4300/posts/all)**

- Include a **testng.xml** file for test execution.
- Ensure that all tests can be executed using **testng.xml** in the **Chrome browser**.
- Capture a **screenshot** on test failure and save it to a designated directory.
- Host the project in a **public GitHub repository**.
- Provide a **README.md** file that explains the project and describes the implemented test cases.


## How to Clone the Project Using IntelliJ

If you're using IntelliJ, follow these steps to clone the project:

1. Go to **File** > **New** > **Project From Version Control**.
2. Copy the following URL to your clipboard:
```   
https://github.com/mimmato/SK17-Automation-Final.git
```
3. Paste the URL into the **URL** field.
4. Specify the directory where you want to save your project.
5. Click **Clone**.

The project will now be cloned to your specified directory, and you can begin testing.

## How to Run tests

The project is configured to execute a total of 9 tests within 3 class files in parallel and run in Chrome. 

To execute it, you can either right-click on the testng.xml file located in the project root directory and click **Run** or
use the **"Run / Debug Configuration"** option of IntelliJ (make sure the correct path to testng.xml is selected).

# Test Case Scenarios

## Home Page - Interaction with Elements
- ✅ Verify posts load correctly via scrolling.
- ❌ Click on a post and check interaction with elements.

## Registration Page
- ❌ Test registration with an **invalid username** that exceeds the length limit.
- ⚠️ Test registration with a **valid username** that triggers the **min length** (**bug**).
- ⚠️ Test registration with an **email that exceeds the length limit** (**bug**).
- ✅ Test successful registration.

## Login Page
- ✅ Test **successful login with a username**, exit, and verify "Remember Me" functionality.
- ✅ Test **successful login with an email**, exit, and verify "Remember Me" functionality.
- ❌ Test login with an **incorrect username**.

### Legend
✅ - positive path scenarios (Expected success or valid input)

❌ - negative path scenarios (Expected failure or invalid input)

⚠️ - bugs

### Expected Test Outcomes
- **Total tests:** 9
- **Expected to pass:** 7 ✅
- **Expected to fail:** 2 ⚠️

## Project contents

- **src/test/java**:
    - **BaseTestConfig**: Centralized setup and teardown for WebDriver initialization, screenshot capture on test failures, and environment cleanup.
    - **HomePageTests**: Executes 2 test scenarios (verifyLoadingOfPostsOnHome() and interactWithPostOnHome())
    - **RegistrationPageTests**: Executes 4 test scenarios (testInvalidUsernameTooLong(), testValidUsernameTooShortBug(), testRegWithSuccessMessagesEmailBug(), testRegSuccess())
    - **LoginPageTests**: Executes 3 test scenarios (testLoginUserSuccessExitRemember(), testLoginEmailSuccessExitRemember(), testLoginWrongUser(), insertWrongUserAndPass())

- **src/main/java**:
    - **BasePageObject**: Holds methods used for navigation through the app and serves as extension for the other objects (inheritance).
  
    - **HomePageObject**: Holds methods for interactions on the Home Page.
  
    - **RegistrationPageObject**: Holds methods for validations of text fields, generation of user registrations etc.
  
    - **LoginPageObject**: Holds methods for interactions with web elements on the Login Page.


- **src/test/resources/screenshots**: directory set up in the **BaseTestConfig** file. Its contents are deleted @BeforeSuite, so users can inspect evidence following the TestSuite execution run. Contents are also\
excluded in the .gitignore file.

[Screenshots directory](https://imgur.com/a/vQxRrrz)


- **testng.xml**: Specifies the configuration for running the tests in parallel. It defines the test suite, test parameters, and the classes to be executed. 
In this case, it executes 3 Test Classes in parallel.


- **pom.xml**: Specifies the Maven configuration for your project, including project metadata, dependencies, and build settings. 

## 📌 Test Case Documentation

| #  | Test Name | Description | Expected Result                                                            |
|----|----------------------------------|---------------------------------------------------|----------------------------------------------------------------------------|
| 1  | `verifyLoadingOfPostsOnHome` | Check if posts load correctly on the homepage, including lazy loading. | The correct number of posts should load initially and after scrolling.     |
| 2  | `interactWithPostOnHome` | Click on a post, check post details, navigate back, and add a comment. | Post modal should display correctly, and interactions should work.         |
| 3  | `testLoginUserSuccessExitRemember` | Log in with a valid username, log out, and log back in using the remembered credentials. | Login, logout, and remember me should work as expected.                    |
| 4  | `testLoginEmailSuccessExitRemember` | Log in with an email, log out, and log back in. | Login, logout, and remember me should work as expected.                     |
| 5  | `testLoginWrongUser` | Attempt to log in with a random incorrect username and password. | A "Wrong username or password!" error message should appear.               |
| 6  | `testInvalidUsernameTooLong` | Attempt to register with a username exceeding the max character limit. | The username should be rejected, and an error should be displayed.         |
| 7  | `testValidUsernameTooShortBug` | Attempt to register with a very short username to check for unexpected acceptance. | Test should fail if the username is incorrectly accepted.                  |
| 8  | `testRegWithSuccessMessagesEmailBug` | Attempt to register with valid inputs but check if failure occurs despite success messages. | If failure occurs with success messages displayed, the test should fail.   |
| 9  | `testRegSuccess` | Complete a valid registration process. | The user should be successfully registered and redirected to `/posts/all`. |

## Project tree

```tree
|   .gitignore
|   pom.xml
|   README.md
|   testng.xml
|   
+---.idea
|   |   .gitignore
|   |   compiler.xml
|   |   encodings.xml
|   |   jarRepositories.xml
|   |   misc.xml
|   |   vcs.xml
|   |   workspace.xml
|   |   
|   \---libraries
|           testng.xml
|           
+---lib
|       jcommander-1.83.jar
|       jquery-3.7.1.jar
|       slf4j-api-2.0.16.jar
|       testng-7.11.0.jar
|
+---src
|   +---main
|   |   +---java
|   |   |       BasePageObject.java
|   |   |       HomePageObject.java
|   |   |       LoginPageObject.java
|   |   |       RegistrationPageObject.java
|   |   |
|   |   \---resources
|   \---test
|       +---java
|       |       BaseTestConfig.java
|       |       HomePageTests.java
|       |       LoginPageTests.java
|       |       RegistrationPageTests.java
|       |
|       \---resources
|           \---screenshots
|                   testValidUsernameTooShortBug_20250324_163637.jpg
|
\---target
    +---classes
    |       BasePageObject.class
    |       HeaderObject.class
    |       HomePageObject.class
    |       LoginPageObject.class
    |       RegistrationPageObject.class
    |
    +---generated-sources
    |   \---annotations
    +---generated-test-sources
    |   \---test-annotations
    \---test-classes
            BaseTestConfig.class
            HomePageTests.class
            LoginPageTests.class
            RegistrationPageTests.class
```