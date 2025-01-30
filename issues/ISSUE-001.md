# Issue #001

**Title:** User Role Access Violation: Deletion of Another User

**Priority:** High

**Severity:** Major

**Status:** Open

---

## Description
Currently, users with the role 'user' are able to delete other users who also have the role 'user'. This behavior violates the intended access control policies, which should restrict users from performing deletion actions on other users of the same role. This issue poses a significant risk to user data integrity and security.

---

## Steps to Reproduce (STR)
1. Select an "editor" with role 'user'
2. `DELETE /player/delete/{editor}`
3. 
   ...

---

## Actual Result (AR)
The user is able to successfully delete another user with the same role 'user' without any error messages or restrictions.

Http Response code: **204 NO CONTENT**.

---

## Expected Result (ER)
The user should receive an error message indicating that they do not have permission to delete another user with the same role 'user'. The deletion action should be blocked.

Http Response code: **403 FORBIDDEN**

---

## Environment
- **Service URL:** http://3.68.165.45/
- **Service Timeout:** 20000ms