# Issue #008

**Title:** Null Field Values in Get Endpoint Response

**Priority:** High

**Severity:** Major

**Status:** Open

---

## Description
The API endpoint for retrieving player record by id is returning responses that are `null` critical field, specifically the `age`, `gender`, `password`, `role`, `screenName` attributes.

This issue affects the integrity of the data being provided to clients and may lead to confusion or errors in applications relying on this information.

---

## Steps to Reproduce (STR)
1. Fill the `playerId` property in request DTO with valid (existing) value
2. `POST /player/get`
3. 
   ...

---

## Actual Result (AR)
The response from the "Get" endpoint is null the `age`, `gender`, `password`, `role`, `screenName` fields for player record, leading to incomplete data being returned.

---

## Expected Result (ER)
The response from the "Get" endpoint should include all relevant fields player record, ensuring that the data is complete and accurate.

---

## Attachments

```
The following asserts failed:
	age isn't equals expected [null] but found [18],
	gender isn't equals expected [null] but found [female],
	password isn't equals expected [null] but found [testPassword_78eb23d9-39ed-419f-a3e6-f0d3b44fa1ab],
	role isn't equals expected [null] but found [admin],
	screenName isn't equals expected [null] but found [testScreenName_78eb23d9-39ed-419f-a3e6-f0d3b44fa1ab]
java.lang.AssertionError: The following asserts failed:
	age isn't equals expected [null] but found [18],
	gender isn't equals expected [null] but found [female],
	password isn't equals expected [null] but found [testPassword_78eb23d9-39ed-419f-a3e6-f0d3b44fa1ab],
	role isn't equals expected [null] but found [admin],
	screenName isn't equals expected [null] but found [testScreenName_78eb23d9-39ed-419f-a3e6-f0d3b44fa1ab]
	at org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:46)
	at org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:30)
	at spribe.api.tests.player.get.GetPlayerTests.lambda$getPlayerByPlayerIdTest$0(GetPlayerTests.java:58)
	at io.qameta.allure.Allure.lambda$step$0(Allure.java:113)
	at io.qameta.allure.Allure.lambda$step$1(Allure.java:127)
	at io.qameta.allure.Allure.step(Allure.java:181)
	at io.qameta.allure.Allure.step(Allure.java:125)
	at io.qameta.allure.Allure.step(Allure.java:112)
	at spribe.api.tests.player.get.GetPlayerTests.getPlayerByPlayerIdTest(GetPlayerTests.java:49)
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
---

## Environment
- **Service URL:** http://3.68.165.45/
- **Service Timeout:** 20000ms
- **Parallel:** methods
- **Thread count:** 3
- **Include groups:** ALL
- **Exclude groups:** (none)