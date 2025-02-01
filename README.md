# Spribe - technical task

**Attention!** This README will not include basic stuff for setup, contributing, and the like.

---

## Table of Contents

- [Overview](#overview)
- [Test run and Environment setup](#test-run-and-environment-setup)
  - [Environment setup](#environment-setup)
  - [Run tests with custom properties](#run-tests-with-custom-properties)
  - [Allure and Environment](#allure-and-environment)

---

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

---

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

### Run tests with custom properties

You can override the default values by providing custom properties when running your tests. 

To do this, you can use the `-D` flag with Maven. 
Here’s an example command to run tests with custom properties:

```bash
mvn test -Denv_service_url=https://api.custom.com -Denv_service_timeout=35000
```

### Allure and Environment

The environment settings will also be displayed in the Allure Report in the Overview -> Environment section. The values will be automatically packed into `environment.properties`.

![Allure and Environment Screenshot](https://i.imgur.com/IwjmFV3.png)