<#include "base.ftl">

<#macro title>Main</#macro>

<#macro content>

    <#if username??>
        <p>Website content will be here.</p>
    <#else>
        <p><b>Login</b> to get access to all the features of website.</p>
    </#if>

</#macro>
