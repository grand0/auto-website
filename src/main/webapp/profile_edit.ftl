<#include "base.ftl">

<#macro title>Edit profile</#macro>

<#macro content>
    <h1 class="page-title">Edit profile</h1>

    <#if email_not_unique??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Email <b>${email_not_unique}</b> is already registered.
        </p>
    </#if>

    <#if old_password_wrong??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            You entered your old password wrong.
        </p>
    </#if>

    <#if password_not_confirmed??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Password was not confirmed.
        </p>
    </#if>

    <#if unknown_error??>
        <p class="error">
            <i class="fa-solid fa-triangle-exclamation"></i>
            Unknown error. Try again later.
        </p>
    </#if>

    <form action="profile_edit" method="post">
        <label>
            <span>E-mail</span>
            <input type="email" name="email" value="${email!}" required />
        </label>
        <p>Leave next fields blank if you don't want to change the password:</p>
        <label>
            <span>Old password</span>
            <input type="password" name="oldPassword" />
        </label>
        <label>
            <span>New password</span>
            <input type="password" name="newPassword" />
        </label>
        <label>
            <span>Confirm password</span>
            <input type="password" name="confirmPassword" />
        </label>
        <input class="btn accent" type="submit" value="Save changes" />
        <a href="${contextPath}/profile" class="btn">Cancel</a>
    </form>
</#macro>
