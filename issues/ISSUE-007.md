# Issue #007

**Title:** Null Fields Returned After Player Creation

**Priority:** Critical

**Severity:** Blocker

**Status:** Open

---

## Description
After executing the player creation API endpoint, the response is returning with several fields set to null, 
specifically `password`, `screenName`, `gender`, `age`, and `role`. 

This indicates that the player creation process is not correctly populating these fields, which may lead to 
issues with user-related systems and overall data integrity.

---

## Steps to Reproduce (STR)
1. Select an "editor" with role 'supervisor'
2. Fully fill the create request DTO with all the available fields
3. `GET /player/create/{editor}`
4.
   ...

---

## Actual Result (AR)
The response from the player creation endpoint includes the following fields with null values:

- **"password"**: `null`
- **"screenName"**: `null`
- **"gender"**: `null`
- **"age"**: `null`
- **"role"**: `null`

---

## Expected Result (ER)
The response from the player creation endpoint should include all relevant fields populated with the correct values based on the input provided during the creation process, specifically:

- **"password"**: `[passed password]`
- **"screenName"**: `[passed screen name]`
- **"gender"**: `[passed gender]`
- **"age"**: `[passed age]`
- **"role"**: `[passed role]`

---

## Attachments

```
The following asserts failed:
	age is incorrect expected [18] but found [null],
	gender is incorrect expected [female] but found [null],
	password is incorrect expected [testPassword_139d8453-c67b-4f19-8f27-4a744a3ad3c8] but found [null],
	role is incorrect expected [user] but found [null],
	screenName is incorrect expected [testScreenName_139d8453-c67b-4f19-8f27-4a744a3ad3c8] but found [null]
	
java.lang.AssertionError: The following asserts failed:
	age is incorrect expected [18] but found [null],
	gender is incorrect expected [female] but found [null],
	password is incorrect expected [testPassword_139d8453-c67b-4f19-8f27-4a744a3ad3c8] but found [null],
	role is incorrect expected [user] but found [null],
	screenName is incorrect expected [testScreenName_139d8453-c67b-4f19-8f27-4a744a3ad3c8] but found [null]
	at org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:46)
	at org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:30)
	at spribe.api.tests.player.create.CreatePlayerTests.createPlayerTest(CreatePlayerTests.java:41)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.testng.internal.invokers.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:141)
	at org.testng.internal.invokers.TestInvoker.invokeMethod(TestInvoker.java:686)
	at org.testng.internal.invokers.TestInvoker.invokeTestMethod(TestInvoker.java:230)
	at org.testng.internal.invokers.MethodRunner.runInSequence(MethodRunner.java:63)
	at org.testng.internal.invokers.TestInvoker$MethodInvocationAgent.invoke(TestInvoker.java:992)
	at org.testng.internal.invokers.TestInvoker.invokeTestMethods(TestInvoker.java:203)
	at org.testng.internal.invokers.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:154)
	at org.testng.internal.invokers.TestMethodWorker.run(TestMethodWorker.java:134)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at org.testng.internal.thread.graph.TestNGFutureTask.run(TestNGFutureTask.java:22)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:829)
```

```
1 expectation failed.
Response body doesn't match expectation.
Expected: The content to match the given JSON schema.
error: instance type (null) does not match any allowed primitive type (allowed: ["integer"])
    level: "error"
    schema: {"loadingURI":"file:/D:/development/spribe/spribe-technical-task/target/test-classes/json.schemas/player/CreatePlayerPositiveSchema.json#","pointer":"/properties/age"}
    instance: {"pointer":"/age"}
    domain: "validation"
    keyword: "type"
    found: "null"
    expected: ["integer"]
error: instance type (null) does not match any allowed primitive type (allowed: ["string"])
    level: "error"
    schema: {"loadingURI":"file:/D:/development/spribe/spribe-technical-task/target/test-classes/json.schemas/player/CreatePlayerPositiveSchema.json#","pointer":"/properties/gender"}
    instance: {"pointer":"/gender"}
    domain: "validation"
    keyword: "type"
    found: "null"
    expected: ["string"]
error: instance type (null) does not match any allowed primitive type (allowed: ["string"])
    level: "error"
    schema: {"loadingURI":"file:/D:/development/spribe/spribe-technical-task/target/test-classes/json.schemas/player/CreatePlayerPositiveSchema.json#","pointer":"/properties/password"}
    instance: {"pointer":"/password"}
    domain: "validation"
    keyword: "type"
    found: "null"
    expected: ["string"]
error: instance type (null) does not match any allowed primitive type (allowed: ["string"])
    level: "error"
    schema: {"loadingURI":"file:/D:/development/spribe/spribe-technical-task/target/test-classes/json.schemas/player/CreatePlayerPositiveSchema.json#","pointer":"/properties/role"}
    instance: {"pointer":"/role"}
    domain: "validation"
    keyword: "type"
    found: "null"
    expected: ["string"]
error: instance type (null) does not match any allowed primitive type (allowed: ["string"])
    level: "error"
    schema: {"loadingURI":"file:/D:/development/spribe/spribe-technical-task/target/test-classes/json.schemas/player/CreatePlayerPositiveSchema.json#","pointer":"/properties/screenName"}
    instance: {"pointer":"/screenName"}
    domain: "validation"
    keyword: "type"
    found: "null"
    expected: ["string"]

  Actual: {"id":1859957619,"login":"testLogin_79800580-e048-4f6e-9590-e937ca109a15","password":null,"screenName":null,"gender":null,"age":null,"role":null}
```
---

## Environment
- **Service URL:** http://3.68.165.45/
- **Service Timeout:** 20000ms
- **Parallel:** methods
- **Thread count:** 3
- **Include groups:** ALL
- **Exclude groups:** (none)