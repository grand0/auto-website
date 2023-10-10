<#include "base.ftl">

<#macro title>Login</#macro>

<#macro content>
    <h1 class="text-center my-3">Log in</h1>

    <#if unauthorized??>
        <p class="alert alert-danger m-3">
            <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
            Incorrect login and/or password
        </p>
    </#if>
    <form action="auth" method="post" class="container">
        <div class="mb-3">
            <label for="login-input" class="form-label">Login</label>
            <input type="text" class="form-control" id="login-input" name="login" value="${past_login!}" required>
        </div>

        <div class="mb-3">
            <label for="password-input" class="form-label">Password</label>
            <input type="password" class="form-control" id="password-input" name="password" required>
        </div>

        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" id="remember-check" value="">
            <label class="form-check-label" for="remember-check">Remember me</label>
        </div>

        <button type="submit" class="btn btn-primary">Log in</button>
    </form>
</#macro>
