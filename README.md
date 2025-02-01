# Spribe - technical task

**Attention!** This README will not include basic stuff for setup, contributing, and the like.

## Table of Contents

- [Overview](#overview)
- [Issue tracking](#issue-tracking)
  - [How does it work?](#how-does-it-work)
- [Test run and Environment setup](#test-run-and-environment-setup)
  - [Environment setup](#environment-setup)
  - [Run tests with custom properties](#run-tests-with-custom-properties)
  - [How can I run SMOKE tests quickly?](#how-can-i-run-smoke-tests-quickly)
  - [Allure and Environment](#allure-and-environment)
- [Test Framework Structure](#test-framework-structure)
  - [Project structure](#project-structure)
  - [Data Layer and Fetching](#data-layer-and-fetching)
    - [GrantedPlayerFetcher](#grantedplayerfetcher)
    - [GrantedPlayerDataSource](#grantedplayerdatasource)
  - [Listeners and Interceptors](#listeners-and-interceptors)
    - [ResponsiveExecutionListener](#responsiveexecutionlistener)
    - [ResponsiveSuiteListener](#responsivesuitelistener)
    - [ResponsiveMethodInterceptor](#responsivemethodinterceptor)

## Overview

The API Test Framework built with: 
- **Language:** Java 11
- **Test execution:** TestNG
- **API Testing:** RestAssured *(with JSON validator)*
- **Build:** Maven *(with Surefire plugin)*
- **Logging:** Log4j2 *(with custom setup)*
- **Test Reporting:** Allure *(with dynamic setup)*
- **Addition utils:** Lombok, Jackson Databind

This test framework is created to cover a test case from [Spribe](https://spribe.co/).

## Issue tracking

In addition to the testing framework, I have created an `issues` folder where all issues related to the `Player Controller` are documented. 
Each issue is linked with the `Allure Report`, allowing for easy tracking and management of bugs and feature requests.

### How does it work?

- Navigate to the `issues` folder in the project directory.
- Each issue is documented in a separate markdown file, detailing the problem, steps to reproduce, and any relevant screenshots or logs.
- When running tests, any related issues will be automatically linked in the **Allure Report**, providing a comprehensive view of the test results alongside the issues.

## Test run and Environment setup

### Environment setup

The environment is set up automatically when you start up tests, which is provided by custom ***Execution-*** and ***Test-*** listeners from TestNG. 
Setup can also be prioritized by passing properties during test runtime.

| **Environment Key** | **Default value**  | **Description**                               |
|---------------------|--------------------|-----------------------------------------------|
| env_service_url     | http://3.68.165.45 | Service URL (which we are testing)            |
| env_service_timeout | 20000              | HTTP timeout for Service                      |
| env_parallel        | methods            | Parallel config for TestNG run in multithread |
| env_thread_count    | 3                  | Thread count config for multithread run       |
| env_included_groups | ALL                | Included groups in test run                   |
| env_excluded_groups |                    | Excluded groups from test run                 |

---

### Run tests with custom properties

You can override the default values by providing custom properties when running your tests. 

To do this, you can use the `-D` flag with Maven. 
Here’s an example command to run tests with custom properties:

```bash
mvn clean test -Denv_service_url=https://api.custom.com -Denv_service_timeout=35000
```

---

### How can I run SMOKE tests quickly?

- **Step 1: Identify Smoke Test Cases**
  - Ensure that you have identified the smoke test cases in your test suite. 
  - These are typically the most critical tests that cover the core functionalities of your API.
- **Step 2: Mark tests with SMOKE group**
  - Make sure your smoke tests are annotated with a specific group
  - `@Test(groups = {ALL, ..., SMOKE})`
- **Step 3: Run Smoke Tests with Maven**
  - To execute only the smoke tests, use the following Maven command.

```bash
mvn clean test -Denv_included_groups=SMOKE
```

**Attention!** Tests will only be run if they are included in the startup and NOT excluded from it.

---

### Allure and Environment

The environment settings will also be displayed in the Allure Report in the Overview -> Environment section. The values will be automatically packed into `environment.properties`.

![Allure and Environment Screenshot](https://i.imgur.com/IwjmFV3.png)

## Test Framework Structure

### Project Structure
```
spribe-technical-task
├───issues
└───src
    ├───main
    │   └───java
    │       └───spribe
    │           ├───api
    │           │   └───player
    │           │       ├───dto
    │           │       │   ├───create
    │           │       │   ├───delete
    │           │       │   ├───get
    │           │       │   │   └───all
    │           │       │   └───update
    │           │       └───requests
    │           ├───config
    │           ├───data
    │           │   ├───entity
    │           │   ├───fetch
    │           │   └───models
    │           ├───helpers
    │           ├───listeners
    │           └───utils
    │               └───exceptions
    └───test
        ├───java
        │   └───spribe
        │       └───api
        │           └───tests
        │               └───player
        │                   ├───create
        │                   ├───delete
        │                   ├───get
        │                   │   └───all
        │                   └───update
        └───resources
            └───json.schemas
                └───player
```

---

### Data Layer and Fetching

I have prepared the logic to facilitate a smooth transition from using **Enums** to a real data source, whether it be **SQL** or **NoSQL**.

This logic allows for more dynamic data management and improved scalability of the API test framework.

---

#### GrantedPlayerFetcher

  - This interface is functional and defines a method to get the list of players.

    - `fetch()`:
      - A method that returns a list of `GrantedPlayer` objects. 
      - This method can be implemented by different classes that receive data from different sources (for example, from a database, API, or dummy data).

---

#### GrantedPlayerDataSource

  - This interface defines methods for getting data about specific players.

    - `getPlayerByLogin(String login)`:
      - A method that takes a player's login and returns a `GrantedPlayer` object corresponding to that login. 
      - This allows you to quickly get information about a specific player.
      
    - `getPlayerByRole(PlayerRole role)`:
      - A method that takes a player role and returns the `GrantedPlayer` object corresponding to that role.
      - This is useful for getting players with specific access rights.
      
    - `getAllPlayers()`:
      - A method that returns a list of all players. 
      - This allows you to get a complete list of players for further analysis or testing.

---

### Listeners and Interceptors

Test framework implements several listeners and interceptors that help with managing test execution, 
check the availability of services, and configure execution parameters. Let's take a look at how these components work.

---

#### ResponsiveExecutionListener

   - This listener implements the `IExecutionListener` interface and performs actions at the beginning and end of test execution.

     - `onExecutionStart()`:
       - Calls the `AllureHelper.configAllureEnvironment()` method to configure the Allure reporting environment. 
       - Performs a service availability check via the `serviceAvailabilityCheck()` method to ensure that the API is available before testing begins.

     - `onExecutionFinish()`:
       - Executes post-conditions, removing all players that were involved during the test. 
       - This is done through a `ResponsiveDataContainer` that contains a list of player IDs to be deleted.

     - `serviceAvailabilityCheck()`:
       - Checks the availability of the service by sending a request to the specified URL. 
       - If the service is unavailable, the listener repeats the request up to three times with the timeout specified in the configuration. 
       - If the service is still unavailable, a `ServiceNotReachableException` is thrown.

---

#### ResponsiveSuiteListener

   - This listener implements the `ISuiteListener` interface and is responsible for configuring the execution parameters of the test suite.
   
     - `onStart(ISuite suite)`:
        - Calls the `configureTestNGExecutionProperties(suite)` method to configure test execution parameters such as parallel mode, 
       number of threads, and included and excluded test groups.
       
     - `onFinish(ISuite suite)`:
       - Logs information about the completion of a test suite execution. 
  
     - `configureTestNGExecutionProperties(ISuite suite)`:
       - Configures the test suite properties, including name, listeners, parallel mode, number of threads, and included and excluded groups. 
       - Logging information about the settings helps in diagnostics.

---

#### ResponsiveMethodInterceptor

  - This interceptor implements the IMethodInterceptor interface and is responsible for filtering test methods based on groups.

    - `intercept(List<IMethodInstance> methods, ITestContext context)`:
      - Checks which test methods are included or excluded based on the `ENV_INCLUDED_GROUPS` and `ENV_EXCLUDED_GROUPS` configuration parameters.
      - Returns a list of methods that meet the inclusion and exclusion criteria.