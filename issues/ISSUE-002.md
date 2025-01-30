# Issue #002

**Title:** User Role Access Violation: Unauthorized Update of Another User

**Priority:** High

**Severity:** Major

**Status:** Open

---

## Description
Currently, users with the role 'user' are able to update the information of other users who also have the role 'user'. This behavior contradicts the intended access control policies, which should prevent users from modifying the details of other users with the same role. This issue raises concerns regarding data integrity and user privacy.

---

## Steps to Reproduce (STR)
1. Select an "editor" with role 'user'
2. Select the player with role 'user' to update and retrieve the "id"
3. `PATCH /player/update/{editor}/{id}`
4. 
   ...

---

## Actual Result (AR)
The user is able to successfully update the information of another user with the same role 'user' without any error messages or restrictions.

Http Response code: **200 NO CONTENT**.

---

## Expected Result (ER)
The user should receive an error message indicating that they do not have permission to update another user with the same role 'user'. The update action should be blocked.

Http Response code: **403 FORBIDDEN**

---

## Environment
- **Service URL:** http://3.68.165.45/
- **Service Timeout:** 20000ms