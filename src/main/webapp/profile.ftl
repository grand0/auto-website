<#include "base.ftl">

<#macro title>Profile</#macro>

<#macro content>

    <h2>
        <i class="fa-solid fa-user"></i>
        ${user}
    </h2>
    <p>
        <i class="fa-solid fa-envelope"></i>
        ${user.email}
    </p>
    <br>
    <a href="${contextPath}/auth?action=logout" class="btn">Log out</a>

</#macro>
