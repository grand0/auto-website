<#assign hideAuthInfo=true>

<#include "base.ftl">

<#macro title>Login</#macro>

<#macro content>
    <h1 class="page-title">Log in</h1>

    <#if unauthorized??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Incorrect login and/or password
        </p>
    </#if>
    <form action="auth" method="post">
        <label>
            <span>Login</span>
            <input type="text" name="login" value="${past_login!}" required />
        </label>
        <label>
            <span>Password</span>
            <input type="password" name="password" required />
        </label>
        <label>
            <span>Remember me</span>
            <input type="checkbox" name="remember">
        </label>
        <input class="btn accent" type="submit" value="Log in" />
    </form>
</#macro>
