<#assign hideAuthInfo=true>

<#include "base.ftl">

<#macro title>Register</#macro>

<#macro content>
    <h1 class="page-title">Register</h1>

    <#if password_not_confirmed??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Password was not confirmed
        </p>
    </#if>

    <#if email_not_unique??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Email <b>${email_not_unique}</b> is already registered. Maybe try to <a href="${contextPath}/auth">log in</a>?
        </p>
    </#if>

    <#if login_not_unique??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Login <b>${login_not_unique}</b> is already taken.
        </p>
    </#if>

    <#if unknown_error??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Unknown error. Try again later.
        </p>
    </#if>

    <form action="register" method="post">
<#-- TODO: make it work -->
<#--        <label>-->
<#--            <span>Avatar</span>-->
<#--            <input type="file" name="avatar" />-->
<#--            <span class="btn file-chooser-btn">Choose file</span>-->
<#--        </label>-->
        <label>
            <span>First name</span>
            <input type="text" name="firstName" value="${past_first_name!}" required />
        </label>
        <label>
            <span>Last name</span>
            <input type="text" name="lastName" value="${past_last_name!}" required />
        </label>
        <label>
            <span>E-mail</span>
            <input type="email" name="email" value="${past_email!}" required />
        </label>
        <label>
            <span>Login</span>
            <input type="text" name="login" value="${past_login!}" required />
        </label>
        <label>
            <span>Password</span>
            <input type="password" name="password" required />
        </label>
        <label>
            <span>Confirm password</span>
            <input type="password" name="confirmPassword" required />
        </label>
        <label>
            <span>Remember me</span>
            <input type="checkbox" name="remember">
        </label>
        <input class="btn accent" type="submit" value="Register" />
    </form>
</#macro>
