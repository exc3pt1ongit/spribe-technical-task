# Spribe - technical task

**Attention!** This README will not include basic stuff for setup, contributing, and the like.

## Table of Contents

- [Overview](#overview)
- [Issue tracking](#issue-tracking)
  - [How does it work?](#how-does-it-work)
- [Test run and Environment setup](#test-run-and-environment-setup)
  - [Environment setup](#environment-setup)
  - [Run tests with custom properties](#run-tests-with-custom-properties)
  - [How can I run smock tests quickly?](#how-can-i-run-smock-tests-quickly)
  - [Allure and Environment](#allure-and-environment)
- [Test Framework Structure](#test-framework-structure)
  - [Project structure](#project-structure)
  - [Data Layer and Fetching](#data-layer-and-fetching)

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

### How can I run smock tests quickly?

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

### Data Layer and Fetching

I have prepared the logic to facilitate a smooth transition from using **Enums** to a real data source, whether it be **SQL** or **NoSQL**.

This logic allows for more dynamic data management and improved scalability of the API test framework.

---

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