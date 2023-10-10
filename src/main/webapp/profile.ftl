<#include "base.ftl">

<#macro title>Profile</#macro>

<#macro content>
    <h1 class="page-title">Profile</h1>

    <#if profile_edited??>
        <p class="success">
            <i class="fa-solid fa-check"></i>
            Profile edited successfully.
        </p>
    </#if>

    <h2>
        <i class="fa-solid fa-user"></i>
        ${user}
    </h2>
    <p>
        <i class="fa-solid fa-envelope"></i>
        ${user.email}
    </p>
    <br>
    <a href="${contextPath}/profile_edit" class="btn">Edit profile</a>
    <a href="${contextPath}/auth?action=logout" class="btn">Log out</a>

</#macro>
