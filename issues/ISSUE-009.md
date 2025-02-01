# Issue #009

**Title:** Null Field Value is not OK getting player by ID

**Priority:** High

**Severity:** Major

**Status:** Open

---

## Description
The API endpoint for retrieving a player record by ID accepts `null` as a valid value for the search and returns status code `200 OK`.

This issue affects the integrity of the data being provided to clients and may lead to confusion or errors in applications relying on this information.

---

## Steps to Reproduce (STR)
1. Fill the `playerId` property in request DTO with `null` value
2. `POST /player/get`
3. 
   ...

---

## Actual Result (AR)
The response from the "Get" endpoint is returning the `200 OK`.

---

## Expected Result (ER)
The response from the "Get" endpoint should return `404 Not Found` or `400 Bad Request` (based on requirements).

---

## Attachments

```
1 expectation failed.
Expected status code <400> but was <200>.
java.lang.AssertionError: 1 expectation failed.
Expected status code <400> but was <200>.

	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
	at org.codehaus.groovy.reflection.CachedConstructor.invoke(CachedConstructor.java:73)
	at org.codehaus.groovy.reflection.CachedConstructor.doConstructorInvoke(CachedConstructor.java:60)
	at org.codehaus.groovy.runtime.callsite.ConstructorSite$ConstructorSiteNoUnwrap.callConstructor(ConstructorSite.java:86)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:277)
	at io.restassured.internal.ResponseSpecificationImpl$HamcrestAssertionClosure.validate(ResponseSpecificationImpl.groovy:512)
	at io.restassured.internal.ResponseSpecificationImpl$HamcrestAssertionClosure$validate$1.call(Unknown Source)
	at io.restassured.internal.ResponseSpecificationImpl.validateResponseIfRequired(ResponseSpecificationImpl.groovy:696)
	at io.restassured.internal.ResponseSpecificationImpl.this$2$validateResponseIfRequired(ResponseSpecificationImpl.groovy)
	at jdk.internal.reflect.GeneratedMethodAccessor239.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.codehaus.groovy.runtime.callsite.PlainObjectMetaMethodSite.doInvoke(PlainObjectMetaMethodSite.java:43)
	at org.codehaus.groovy.runtime.callsite.PogoMetaMethodSite$PogoCachedMethodSiteNoUnwrapNoCoerce.invoke(PogoMetaMethodSite.java:198)
	at org.codehaus.groovy.runtime.callsite.PogoMetaMethodSite.callCurrent(PogoMetaMethodSite.java:62)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:185)
	at io.restassured.internal.ResponseSpecificationImpl.statusCode(ResponseSpecificationImpl.groovy:135)
	at io.restassured.specification.ResponseSpecification$statusCode$0.callCurrent(Unknown Source)
	at io.restassured.internal.ResponseSpecificationImpl.statusCode(ResponseSpecificationImpl.groovy:143)
	at io.restassured.internal.ValidatableResponseOptionsImpl.statusCode(ValidatableResponseOptionsImpl.java:89)
	at spribe.api.tests.AbstractBaseTest.lambda$assertValidStatusCodeAndContentType$1(AbstractBaseTest.java:59)
	at io.qameta.allure.Allure.lambda$step$0(Allure.java:113)
	at io.qameta.allure.Allure.lambda$step$1(Allure.java:127)
	at io.qameta.allure.Allure.step(Allure.java:181)
	at io.qameta.allure.Allure.step(Allure.java:125)
	at io.qameta.allure.Allure.step(Allure.java:112)
	at spribe.api.tests.AbstractBaseTest.assertValidStatusCodeAndContentType(AbstractBaseTest.java:56)
	at spribe.api.tests.AbstractBaseTest.assertValidStatusCodeAndContentType(AbstractBaseTest.java:67)
	at spribe.api.tests.player.get.GetPlayerTests.getPlayerByNotValidPlayerIdTest(GetPlayerTests.java:76)
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