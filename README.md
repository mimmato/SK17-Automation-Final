# QA Automation Framework

## Project Overview

This project is an automated testing framework built using **TestNG**, **Selenium WebDriver**, and the **Page Object Model (POM)** pattern. It automates key user interactions within a web application, including **user registration**, **login**, and **home page** functionalities. The framework is designed to be modular, reusable, and scalable, ensuring efficient test maintenance and easy extensibility.

## Key Features

- **BaseTestConfig**: Centralized setup and teardown for WebDriver initialization, screenshot capture on test failures, and environment cleanup.
- **Page Object Model (POM)**: A dedicated page object for each page in the web application (e.g., **HomePage**, **RegistrationPage**, **LoginPage**). Each page object encapsulates the page elements and methods for interacting with them.
- **Test Cases**: Validates critical user flows such as registration, login (via username and email), and home page functionality. Includes assertions for success and failure scenarios.

## Project Structure

```tree
|   .gitignore
|   pom.xml
|   README.md
|   testng.xml
|   
+---.idea
|       .gitignore
|       compiler.xml
|       encodings.xml
|       jarRepositories.xml
|       misc.xml
|       vcs.xml
|       workspace.xml
|       
+---src
|   +---main
|   |   +---java
|   |   |       BasePageObject.java
|   |   |       HeaderObject.java
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
        |   BaseTestConfig.class
        |   HomePageTests.class
        |   LoginPageTests.class
        |   RegistrationPageTests.class
        |
        \---screenshots
  ```


# Table of Contents

1. **[BaseTestConfig.java](#basetestconfigjava)**
    - Overview
    - Methods
        - `setBeforeSuite()`
        - `setBeforeMethod()`
        - `tearDownTest(ITestResult testResult)`
        - `delScreenshots()`
        - `takeScreenshotOnFailure(ITestResult testResult)`
        - `getDriver()`
        - `quitDriver()`
        - `delScreenshotDIR(String SCREENSHOTS_DIR)`

2. **[BasePageObject.java](#basepageobjectjava)**
    - Overview
    - Fields
        - Web Elements
        - Constants
        - WebDriver Instance
    - Methods
        - `BasePageObject(WebDriver webDriver)`
        - `isCurrentURLCorrect(String uri)`
        - `openURL(String uri)`
        - `clickLoginLink()`
        - `navigateToRegistrationPage()`
        - `navigateToLoginPage()`
        - Text Retrieval Methods
        - Element Visibility & Interaction

3. **[HomePageObject.java](#homepageobjectjava)**
    - Overview
    - Fields
        - Web Elements
    - Constructor
    - Methods
        - `checkPostsLoaded()`
        - `scrollToLoad(int maxScrolls)`

4. **[HomePageTests.java](#homepageTestsjava)**
    - Overview
    - Test Methods
        - `verifyLandingPage()`

5. **[RegistrationPageObject.java](#registrationpageobjectjava)**
    - Overview
    - Key Functionalities
        - Field Interactions
        - Validation Helpers
        - Random Data Generation
        - Handling Errors & Toasters
        - Form Submission

6. **[RegistrationPageTests.java](#registrationpagetestsjava)**
    - Overview
    - Test Cases
        - `testInvalidUsernameTooLong()`
        - `testValidUsernameTooShortBug()`
        - `testRegWithSuccessMessagesEmailBug()`
        - `testRegSuccess()`

7. **[LoginPageObject.java](#loginpageobjectjava)**
    - Overview
    - Elements
    - Methods
        - `getToastMessage()`
        - `isRegisterLinkVisible()`
        - `validateLoginElements()`
        - `isSignInEnabled()`
        - `validateElementsPostLogin()`
        - `clearEnterUsernameValidate(String username)`
        - `clearEnterEmailValidate(String email)`
        - `clearAndEnterPassword()`
        - `checkRememberMe()`
        - `clickSignIn()`
        - `fillUserInfoCheck()`
        - `fillEmailCheck()`
        - `clickRegister()`

8. **[LoginPageTests.java](#loginpagetestsjava)**
    - Overview
    - Test Cases
        - `testLoginUserSuccessExitRemember()`
        - `testLoginEmailSuccessExitRemember()`

9. **[Project Structure](#project-structure)**
    - Overview of Project Directories and Files
        - `.gitignore`
        - `pom.xml`
        - `README.md`
        - `testng.xml`
        - `.idea` Folder
        - `src/main/java` Folder
        - `src/test/java` Folder
        - `src/test/resources/screenshots` Folder
        - `target` Folder


# QA Automation Tests

## BaseTestConfig.java

The `BaseTestConfig` class provides the foundational setup and teardown logic for all test cases. It ensures the WebDriver is properly initialized and handles screenshot capture on test failures.

### Methods:

- **`setBeforeSuite()`**
    - Runs once before all test methods.
    - Sets up the Chrome WebDriver using WebDriverManager.
    - Deletes old screenshots to maintain a clean test environment.

- **`setBeforeMethod()`**
    - Runs before each test method.
    - Initializes a new Chrome WebDriver instance.
    - Maximizes the browser window.
    - Sets page load timeout to **20 seconds** (originally **60**).
    - Sets implicit wait timeout to **5 seconds** (originally **45**).

- **`tearDownTest(ITestResult testResult)`**
    - Runs after each test method.
    - Captures a screenshot if the test fails.
    - Quits the WebDriver and ensures it is null.

- **`delScreenshots()`**
    - Deletes old screenshots before the test suite starts.

- **`takeScreenshotOnFailure(ITestResult testResult)`**
    - Takes a screenshot if a test fails.
    - Saves the screenshot in `src/test/resources/screenshots/` with a timestamp and test name.

- **`getDriver()`**
    - Returns the current WebDriver instance.

- **`quitDriver()`**
    - Quits the WebDriver instance and verifies it is null after quitting.

- **`delScreenshotDIR(String SCREENSHOTS_DIR)`**
    - Deletes all files in the screenshot directory.
    - Verifies that the directory is valid before attempting deletion.
    - Prints success or failure messages.

---

This class ensures a stable and reliable test execution environment by handling setup, cleanup, and failure logging.

## BasePageObject.java

The `BasePageObject` class serves as the base for all page objects, defining common elements and utility methods for navigation and interaction.

### Fields:
- **Web Elements:**
    - `profileLink` - Profile navigation link.
    - `loginLink` - Login page link.
    - `homeLink` - Home page link.
    - `newPostLink` - New post creation link.
    - `logoutIcon` - Logout button.
    - `searchBar` - Search input field.

- **Constants:**
    - `CURRENT_URL` - Base URL of the application.

- **WebDriver Instance:**
    - `webDriver` - Holds the current browser session.

### Methods:

- **`BasePageObject(WebDriver webDriver)`**
    - Constructor that initializes the WebDriver and WebElements using PageFactory.

- **`isCurrentURLCorrect(String uri): boolean`**
    - Verifies if the current page URL matches the expected URL.

- **`openURL(String uri)`**
    - Navigates to the given relative URI by appending it to `CURRENT_URL`.

- **`clickLoginLink()`**
    - Clicks on the login link after ensuring it is clickable.

- **`navigateToRegistrationPage()`**
    - Navigates to the registration page step by step:
        1. Opens the landing page (`/posts/all`).
        2. Clicks on the login link (`/users/login`).
        3. Clicks on the register button (`/users/register`).
        4. Verifies each navigation step.

- **`navigateToLoginPage()`**
    - Navigates to the login page from the landing page and verifies the URL.

- **Text Retrieval Methods:**
    - `getProfileLinkText()`
    - `getHomeLinkText()`
    - `getNewPostLinkText()`
    - `getSearchBarText()` - Retrieves the placeholder text of the search bar.

- **Element Visibility & Interaction:**
    - `getExitIconText()` - Checks if the logout icon is displayed.
    - `clickExit()` - Clicks on the logout icon.

---

This class provides essential methods for page navigation, URL validation, and basic element interactions.

## HomePageObject.java

The `HomePageObject` class extends `BasePageObject` and represents the Home Page. It provides methods to interact with the post feed, including checking for loaded posts and triggering lazy loading by scrolling.

### Fields:
- **Web Elements:**
    - `postThumbnails` - A list of all post thumbnails displayed on the page.
    - `bodyScroll` - Represents the body element, used for triggering scrolling.

### Constructor:
- **`HomePageObject(WebDriver webDriver)`**
    - Calls the parent `BasePageObject` constructor.
    - Initializes the elements using `PageFactory`.

### Methods:

- **`checkPostsLoaded(): int`**
    - Waits until all post thumbnails are visible.
    - Returns the number of posts loaded.

- **`scrollToLoad(int maxScrolls)`**
    - Scrolls the page to the bottom multiple times (up to `maxScrolls`) to load more posts.
    - Uses `ExpectedConditions.numberOfElementsToBeMoreThan()` to detect when new posts are loaded.
    - Stops scrolling if no additional posts appear.

---

This class enables post visibility verification and dynamic content loading through scrolling.

## HomePageTests.java

The `HomePageTests` class extends `BaseTestConfig` and contains test cases for verifying the home page functionality, including post visibility and lazy loading behavior.

### Test Methods:

- **`verifyLandingPage()`**
    - Opens the landing page (`/posts/all`).
    - Asserts that the correct URL is loaded.
    - Checks the number of posts initially visible (expected: 3).
    - Scrolls down three times to trigger lazy loading.
    - Asserts that the expected number of posts (12) is loaded after scrolling.

### Dependencies:
- **TestNG** for assertions.
- **Selenium WebDriver** for browser automation.
- **Page Object Model (POM)** for structured page interactions.

---

This test ensures that the home page loads posts correctly and dynamically loads more posts when scrolled.

## RegistrationPageObject.java

The `RegistrationPageObject` class models the registration page and provides methods to interact with its elements.

### Key Functionalities:
- **Field Interactions**: Methods to enter, clear, and validate form fields (e.g., username, email, birthdate, password, public info).
- **Validation Helpers**:
    - Checks placeholder text correctness.
    - Determines if a field is valid (`has-success`) or invalid (`has-danger`).
- **Random Data Generation**:
    - Generates random usernames (`genRandomUser(int desiredLength)`) and emails (`genRandomEmail(int desiredLength)`).
- **Handling Errors & Toasters**:
    - Detects the "Username taken" message and clears the field (`handleUsernameTaken()`).
    - Retrieves generic toast messages (`getToastMessage()`).
- **Form Submission**:
    - Clicks the "Sign in" button (`clickSignInButton()`).

### Dependencies:
- **Selenium WebDriver** for browser automation.
- **TestNG** for assertions.
- **Page Factory** for initializing elements.
- **WebDriverWait** for handling dynamic elements.

---

This class ensures a structured and reusable approach to interacting with the registration form.

## RegistrationPageTests.java

The `RegistrationPageTests` class contains automated test cases for verifying the registration functionality.

### Test Cases:

1. **testInvalidUsernameTooLong()**
    - Generates a username longer than 20 characters.
    - Asserts that the input is marked as invalid (`has-danger`).

2. **testValidUsernameTooShortBug()**
    - Generates a very short (2-character) username.
    - Verifies if it is accepted or incorrectly marked as invalid.
    - Checks the validation message for unexpected behavior.

3. **testRegWithSuccessMessagesEmailBug()**
    - Attempts registration with an invalid (21-character) email.
    - Ensures that success indicators align with the outcome.
    - Detects if the system incorrectly allows registration failure while displaying success messages.

4. **testRegSuccess()**
    - Registers with valid credentials.
    - Clicks the sign-in button and verifies the outcome.
    - Checks if the final URL is correct (`/posts/all`).
    - Ensures that success messages and the registration process align correctly.

### Dependencies:
- **Selenium WebDriver** for test execution.
- **TestNG** for structuring test cases and assertions.
- **Page Object Model (POM)** for maintaining separation of concerns.

---

This class is essential for detecting registration-related bugs and ensuring that the registration process works as expected.

## LoginPageObject.java

The `LoginPageObject` class represents the Login Page and encapsulates its elements and interactions using the Page Object Model (POM).

### Elements:
- **Username/Email Field (`defaultLoginFormUsername`)**: Input for entering a username or email.
- **Password Field (`defaultLoginFormPassword`)**: Input for entering a password.
- **Sign In Button (`sign-in-button`)**: Button to submit login credentials.
- **Sign In Title (`h4 mb-4`)**: Header text for the login form.
- **Register Link (`form//a`)**: Link to the registration page.
- **Remember Me Checkbox (`formcontrolname='rememberMe'`)**: Checkbox for remembering user credentials.
- **Toast Message (`role='alertdialog'`)**: Notification messages displayed after login attempts.

### Methods:

1. **getToastMessage()**
    - Waits for and retrieves the toast message text after login.

2. **isRegisterLinkVisible()**
    - Checks if the registration link is visible and returns its text.

3. **validateLoginElements()**
    - Asserts that login form elements are correctly displayed and contain the expected text.

4. **isSignInEnabled()**
    - Returns `true` if the Sign In button is enabled.

5. **validateElementsPostLogin()**
    - Verifies the presence and correctness of elements after a successful login (Profile, Home, New Post, Exit Icon, Search Bar).

6. **clearEnterUsernameValidate(String username)**
    - Clears the username field, enters the provided username, and asserts the input.

7. **clearEnterEmailValidate(String email)**
    - Clears the email field, enters the provided email, and asserts the input.

8. **clearAndEnterPassword()**
    - Clears and enters a predefined password, then verifies the input.

9. **checkRememberMe()**
    - Clicks the "Remember Me" checkbox and asserts that it is selected.

10. **clickSignIn()**
    - Clicks the Sign In button after ensuring the toast message is not visible.

11. **fillUserInfoCheck()**
    - Fills the login form using a valid username and password, then verifies the Sign In button is enabled.

12. **fillEmailCheck()**
    - Fills the login form using a valid email and password, then verifies the Sign In button is enabled.

13. **clickRegister()**
    - Clicks the "Register" link to navigate to the registration page.

### Dependencies:
- **Selenium WebDriver** for UI automation.
- **TestNG** for assertions.
- **Page Factory** for element initialization.
- **Explicit Waits** for handling dynamic elements.

---

This class provides a structured way to interact with and validate the login functionality of the application.

## LoginPageTests.java

The `LoginPageTests` class contains automated test cases for validating the login functionality.

### Description:
- Uses TestNG for structured test execution.
- Tests login with both username and email.
- Verifies correct navigation after login and logout.
- Ensures the "Remember Me" functionality works as expected.

### Test Cases:

#### `testLoginUserSuccessExitRemember()`
**Steps:**
1. Navigate to the login page.
2. Validate login page elements.
3. Enter a valid **username** and password.
4. Click "Sign In" and verify the **success message**.
5. Validate the **redirect URL** (`/posts/all`).
6. Verify that post-login elements are displayed.
7. Click "Exit" and validate the **logout success message**.
8. Confirm redirection to the **login page** (`/users/login`).
9. Click "Sign In" again to ensure "Remember Me" is working.
10. Validate the **login success message**.

#### `testLoginEmailSuccessExitRemember()`
**Steps:**
1. Navigate to the login page.
2. Validate login page elements.
3. Enter a valid **email** and password.
4. Click "Sign In" and verify the **success message**.
5. Validate the **redirect URL** (`/posts/all`).
6. Verify that post-login elements are displayed.
7. Click "Exit" and validate the **logout success message**.
8. Confirm redirection to the **login page** (`/users/login`).
9. Click "Sign In" again to ensure "Remember Me" is working.
10. Validate the **login success message**.

### Dependencies:
- **Selenium WebDriver**: For browser interaction.
- **TestNG**: For test execution and assertions.
- **Page Object Model (POM)**: For structured test implementation.

---

This test class ensures that both **username and email-based login work correctly** while verifying navigation and "Remember Me" functionality.

Project structure:

```tree
│   .gitignore
│   pom.xml
│   README.md
│   testng.xml
│   
├───.idea
│       .gitignore
│       compiler.xml
│       encodings.xml
│       jarRepositories.xml
│       misc.xml
│       vcs.xml
│       workspace.xml
│       
├───src
│   ├───main
│   │   ├───java
│   │   │       BasePageObject.java
│   │   │       HeaderObject.java
│   │   │       HomePageObject.java
│   │   │       LoginPageObject.java
│   │   │       RegistrationPageObject.java
│   │   │
│   │   └───resources
│   └───test
│       ├───java
│       │       BaseTestConfig.java
│       │       HomePageTests.java
│       │       LoginPageTests.java
│       │       RegistrationPageTests.java
│       │
│       └───resources
│           └───screenshots
└───target
    ├───classes
    │       BasePageObject.class
    │       HeaderObject.class
    │       HomePageObject.class
    │       LoginPageObject.class
    │       RegistrationPageObject.class
    │
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    └───test-classes
        │   BaseTestConfig.class
        │   HomePageTests.class
        │   LoginPageTests.class
        │   RegistrationPageTests.class
        │
        └───screenshots
```