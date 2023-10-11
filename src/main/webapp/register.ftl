<#include "base.ftl">

<#macro title>Register</#macro>

<#macro content>
    <h1 class="text-center my-3">Register</h1>

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

    <#if first_name_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            First name is invalid.
        </p>
    </#if>

    <#if last_name_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Last name is invalid.
        </p>
    </#if>

    <#if email_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Email is invalid.
        </p>
    </#if>

    <#if login_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Login is invalid.
        </p>
    </#if>

    <#if password_invalid??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Password is invalid.
        </p>
    </#if>

    <#if password_not_confirmed??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Password was not confirmed.
        </p>
    </#if>

    <#if email_not_unique??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Email <b>${email_not_unique}</b> is already registered. Maybe try to <a href="${contextPath}/auth">log in</a>?
        </p>
    </#if>

    <#if login_not_unique??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Login <b>${login_not_unique}</b> is already taken.
        </p>
    </#if>

    <#if unknown_error??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Unknown error. Try again later.
        </p>
    </#if>

    <form action="register" method="post" enctype="multipart/form-data" class="container">
        <div class="mb-3">
            <label for="avatar-input" class="form-label">Avatar</label>
            <input class="form-control" type="file" id="avatar-input" name="avatar" accept=".jpg, .jpeg, .png" aria-describedby="avatarHelp">
            <div id="avatarHelp" class="form-text">
                We recommend setting your real photo as an avatar. Max 5 MB. Supported formats: .jpg, .jpeg, .png.
            </div>
        </div>
        <div class="mb-3">
            <label for="first-name-input" class="form-label">First name</label>
            <input type="text" class="form-control" id="first-name-input" name="firstName" value="${past_first_name!}" required />
        </div>
        <div class="mb-3">
            <label for="last-name-input" class="form-label">Last name</label>
            <input type="text" class="form-control" id="last-name-input" name="lastName" value="${past_last_name!}" required />
        </div>
        <div class="mb-3">
            <label for="email-input" class="form-label">E-mail</label>
            <input type="email" class="form-control" id="email-input" name="email" value="${past_email!}" required />
        </div>
        <div class="mb-3">
            <label for="login-input" class="form-label">Login</label>
            <input type="text" class="form-control" id="login-input" name="login" value="${past_login!}" aria-describedby="loginHelp" required />
            <div id="loginHelp" class="form-text">
                Must be unique. Length from 5 to 60 symbols. You can use English letters, digits, underscores, dots and hyphens.
            </div>
        </div>
        <div class="mb-3">
            <label for="password-input" class="form-label">Password</label>
            <input type="password" class="form-control" id="password-input" name="password" aria-describedby="passwordHelp" required />
            <div id="passwordHelp" class="form-text">
                Length from 6 to 64 symbols. You can use English letters, digits and special symbols.
            </div>
        </div>
        <div class="mb-3">
            <label for="confirm-password-input" class="form-label">Confirm password</label>
            <input type="password" class="form-control" id="confirm-password-input" name="confirmPassword" aria-describedby="confirmPasswordHelp" required />
            <div id="confirmPasswordHelp" class="form-text">
                Retype your password here.
            </div>
        </div>
        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" id="remember-check" value="">
            <label class="form-check-label" for="remember-check">Remember me</label>
        </div>
        <button type="submit" class="btn btn-primary mb-3">Register</button>
    </form>
</#macro>
