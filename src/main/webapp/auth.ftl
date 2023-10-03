<#assign hideAuthInfo=true>

<#include "base.ftl">

<#macro title>Login</#macro>

<#macro content>
    <#if unauthorized??>
        <p style="color: red">Incorrect login and/or password</p>
    </#if>
    <form action="auth" method="post">
        <label>
            Login
            <input type="text" name="login" required />
        </label>
        <br>
        <label>
            Password
            <input type="password" name="password" required />
        </label>
        <br>
        <input type="submit" value="Login" />
    </form>
</#macro>
