# Issue #003

**Title:** User Role Access Violation: Unauthorized Update of Admin User

**Priority:** High

**Severity:** Critical

**Status:** Open

---

## Description
Currently, users with the role 'user' are able to update the information of users with the role 'admin'. This behavior is a significant security flaw, as it allows lower-privileged users to modify the details of higher-privileged users, potentially compromising the integrity and security of the application. This issue must be addressed immediately to prevent unauthorized access and modifications.

---

## Steps to Reproduce (STR)
1. Select an "editor" with role 'user'
2. Select the player with role 'admin' to update and retrieve the "id"
3. `PATCH /player/update/{editor}/{id}`
4. 
   ...

---

## Actual Result (AR)
The user is able to successfully update the information of the admin user without any error messages or restrictions.

Http Response code: **200 NO CONTENT**.

---

## Expected Result (ER)
The user should receive an error message indicating that they do not have permission to update an admin user. The update action should be blocked.

Http Response code: **403 FORBIDDEN**

---

## Environment
- **Service URL:** http://3.68.165.45/
- **Service Timeout:** 20000ms