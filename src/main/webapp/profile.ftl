<#include "base.ftl">

<#macro title>Profile</#macro>

<#macro content>
    <h1 class="text-center my-3">Profile</h1>

    <#if profile_edited??>
        <p class="success">
            <i class="fa-solid fa-check"></i>
            Profile edited successfully.
        </p>
    </#if>

    <div class="container">
        <h2 class="text-center mb-3">
            <i class="bi bi-person-fill" aria-hidden="true"></i>
            ${user}
        </h2>
        <p class="text-center mb-3">
            <i class="bi bi-envelope-fill" aria-hidden="true"></i>
            ${user.email}
        </p>

        <div class="row justify-content-center">
            <a href="${contextPath}/profile_edit" class="btn btn-outline-primary col-auto me-3">Edit profile</a>
            <a href="${contextPath}/auth?action=logout" class="btn btn-outline-danger col-auto">Log out</a>
        </div>
    </div>

</#macro>
