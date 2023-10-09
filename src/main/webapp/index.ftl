<#include "base.ftl">

<#macro title>Main</#macro>

<#macro content>

    <#if user??>
        <p>Website content will be here.</p>
    <#else>
        <p><b>Log in</b> to get access to all the features of website.</p>
    </#if>

</#macro>
