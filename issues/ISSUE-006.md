# Issue #006

**Title:** Missing Field in Get All Endpoint Response

**Priority:** High

**Severity:** Major

**Status:** Open

---

## Description
The API endpoint for retrieving all player records is returning responses that are missing critical field, specifically the 'role' attributes.
This issue affects the integrity of the data being provided to clients and may lead to confusion or errors in applications relying on this information.

---

## Steps to Reproduce (STR)
1. `GET /player/get/all`
2.
   ...

---

## Actual Result (AR)
The response from the "Get All" endpoint is missing the 'role' field for some or all player records, leading to incomplete data being returned.

---

## Expected Result (ER)
The response from the "Get All" endpoint should include all relevant fields for each player record, specifically the 'role' attribute, ensuring that the data is complete and accurate.

---

## Attachments

```
java.lang.AssertionError: 1 expectation failed.
Response body doesn't match expectation.
Expected: The content to match the given JSON schema.
error: object has missing required properties (["role"])
    level: "error"
    schema: {"loadingURI":"file:/D:/development/spribe/spribe-technical-task/target/test-classes/json.schemas/player/GetAllPlayersPositiveSchema.json#","pointer":"/properties/players/items"}
    instance: {"pointer":"/players/0"}
    domain: "validation"
    keyword: "required"
    required: ["age","gender","id","role","screenName"]
    missing: ["role"]
```
---

## Environment
- **Service URL:** http://3.68.165.45/
- **Service Timeout:** 20000ms