<#include "base.ftl">

<#macro title>Error</#macro>

<#macro content>
    <div class="container text-center">
        <h1 class="status-code">${statusCode}</h1>
        <h2 class="mb-3">
            <#if statusCode == 404>
                Page not found
            <#else>
                There was an error
            </#if>
        </h2>
        <a id="error-home-btn" class="btn btn-primary" href="${contextPath}/">
            Take me outta here
        </a>
        <div class="accordion mb-3" id="info-accordion">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#info-collapse">
                        Show more info
                    </button>
                </h2>
                <div id="info-collapse" class="accordion-collapse collapse text-start" data-bs-parent="#info-accordion">
                    <div class="accordion-body">
                        <strong>URI requested: </strong>${requestUri}
                        <br>
                        <#if exception??>
                            <strong>Exception: </strong>${exception}
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>
