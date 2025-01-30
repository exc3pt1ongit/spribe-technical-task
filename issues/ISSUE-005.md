# Issue #0004

**Title:** Update Player Test Failure: Incorrect Values After Update (separate fields)

**Priority:** Medium

**Severity:** Major

**Status:** Open

---

## Description
The test case has failed due to discrepancies between the expected and actual values after attempting to update a player's information.
Test failure logs indicate that the update functionality is not behaving as intended, leading to incorrect data being saved.

---

## Steps to Reproduce (STR)
1. Select an "editor" with role 'supervisor'
2. Select the player with role 'user' to update and retrieve the "id"
3. Fully fill the update request DTO with all the available fields changed
4. `PATCH /player/update/{editor}/{id}`
5.
   ...

---

## Actual Result (AR)
The following discrepancies were observed after the update operation:

- **Gender:** Found value does not match the expected gender.
- **Role:** Found value does not match the expected role.
- **Screen Name:** Found value does not match the expected screen name.

---

## Expected Result (ER)
The values after the update should match the expected attributes as defined in the test case:

- **Gender:** Should match the expected gender.
- **Role:** Should reflect the expected role.
- **Screen Name:** Should be set to the expected screen name.

---

## Attachments
```
Parameters
arg0: PlayerUpdateRequestDto(age=19, gender=null, login=null, password=null, role=null, screenName=null)
```

```
The following asserts failed:
	gender after update isn't equals expected [null] but found [female],
	role after update isn't equals expected [null] but found [user],
	screenName after update isn't equals expected [null] but found [testScreenName_68588ed6-e7a7-4a9a-adf1-8e8cd2ed0ac4]	

java.lang.AssertionError: The following asserts failed:
	gender after update isn't equals expected [null] but found [female],
	role after update isn't equals expected [null] but found [user],
	screenName after update isn't equals expected [null] but found [testScreenName_68588ed6-e7a7-4a9a-adf1-8e8cd2ed0ac4]
	at org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:46)
	at org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:30)
	at spribe.api.tests.player.update.UpdatePlayerTests.assertPlayerUpdateResponse(UpdatePlayerTests.java:97)
	at spribe.api.tests.player.update.UpdatePlayerTests.updatePlayerWithValidMutateValueTest(UpdatePlayerTests.java:73)
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