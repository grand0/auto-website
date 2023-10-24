<#include "base.ftl">

<#macro title>Chats</#macro>

<#macro content>
    <h1 class="text-center my-3">Chats</h1>

    <div class="container">
        <#list chats as user>
            <a href="${contextPath}/chat?ad_id=${adId}&sender_id=${user.id}" class="mb-3" style="text-decoration: none">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <img src="${user.getRoundCroppedAvatarUrl()}" alt="avatar" width="50" height="50" class="me-3">
                                <span>${user}</span>
                            </div>
                            <div>
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
                                </svg>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </#list>
    </div>
</#macro>
