<#include "base.ftl">

<#macro title>Edit profile</#macro>

<#macro content>
    <h1 class="text-center my-3">Edit profile</h1>

    <#if email_not_unique??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Email <b>${email_not_unique}</b> is already registered.
        </p>
    </#if>

    <#if old_password_wrong??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            You entered your old password wrong.
        </p>
    </#if>

    <#if password_not_confirmed??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Password was not confirmed.
        </p>
    </#if>

    <#if unknown_error??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Unknown error. Try again later.
        </p>
    </#if>

    <form action="profile_edit" method="post" class="container">
        <div class="mb-5">
            <label for="email-input" class="form-label">E-mail</label>
            <input type="email" class="form-control" id="email-input" name="email" value="${email!}" required>
        </div>
        <div class="mb-3 alert alert-info">
            <i class="bi bi-lightbulb-fill"></i>
            Leave next fields blank if you don't want to change the password.
        </div>

        <div class="mb-3">
            <label for="old-password-input" class="form-label">Old password</label>
            <input type="password" class="form-control" id="old-password-input" name="oldPassword">
        </div>
        <div class="mb-3">
            <label for="new-password-input" class="form-label">New password</label>
            <input type="password" class="form-control" id="new-password-input" name="newPassword">
        </div>
        <div class="mb-3">
            <label for="confirm-password-input" class="form-label">Confirm password</label>
            <input type="password" class="form-control" id="confirm-password-input" name="confirmPassword">
        </div>
        <button type="submit" class="btn btn-primary">Save changes</button>
        <a class="btn btn-outline-secondary" href="${contextPath}/profile">Cancel</a>
    </form>
</#macro>
