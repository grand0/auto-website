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

    <#if (user.avatarUrl)??>
        <img src="${user.getRoundCroppedAvatarUrl()}" alt="user avatar" class="d-block mx-auto mb-2" width="150" height="150">
    <#else>
        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16" width="150" height="150" class="bi bi-person-circle mb-2 d-block mx-auto">
            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
        </svg>
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

        <div class="row justify-content-center mb-3">
            <a href="${contextPath}/profile_edit" class="btn btn-outline-primary col-auto me-3">Edit profile</a>
            <a href="${contextPath}/auth?action=logout" class="btn btn-outline-danger col-auto">Log out</a>
        </div>
    </div>

</#macro>
