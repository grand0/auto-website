<#include "base.ftl">

<#macro title>Edit profile</#macro>

<#macro content>
    <h1 class="text-center my-3">Edit profile</h1>

    <#if avatar_too_big??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Avatar file exceeds size threshold.
        </p>
    </#if>

    <#if avatar_unsupported_format??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Format of avatar file is unsupported.
        </p>
    </#if>

    <#if email_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Email is invalid.
        </p>
    </#if>

    <#if password_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            New password is invalid.
        </p>
    </#if>

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

    <form action="profile_edit" method="post" enctype="multipart/form-data" class="container">
        <div class="mb-3">
            <label for="avatar-input" class="form-label">Avatar</label>
            <input class="form-control" type="file" id="avatar-input" name="avatar" accept=".jpg, .jpeg, .png" aria-describedby="avatarHelp">
            <div id="avatarHelp" class="form-text">
                We recommend setting your real photo as an avatar. Max 5 MB. Supported formats: .jpg, .jpeg, .png.
            </div>
        </div>
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
            <input type="password" class="form-control" id="new-password-input" name="newPassword" aria-describedby="passwordHelp">
            <div id="passwordHelp" class="form-text">
                Length from 6 to 64 symbols. You can use English letters, digits and special symbols.
            </div>
        </div>
        <div class="mb-3">
            <label for="confirm-password-input" class="form-label">Confirm password</label>
            <input type="password" class="form-control" id="confirm-password-input" name="confirmPassword" aria-describedby="confirmPasswordHelp">
            <div id="confirmPasswordHelp" class="form-text">
                Retype your new password.
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Save changes</button>
        <a class="btn btn-outline-secondary" href="${contextPath}/profile">Cancel</a>
    </form>
</#macro>
