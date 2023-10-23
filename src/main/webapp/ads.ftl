<#include "base.ftl">

<#macro title>Advertisements</#macro>

<#macro content>
    <h1 class="text-center my-3">Advertisements</h1>

    <div class="container">
        <#list advertisements as ad>
            <a href="${contextPath}/advertisements?id=${ad.id}"
               style="text-decoration: none">
                <div class="card mb-2">
                    <div class="row g-0">
                        <div class="col-md-3">
                            <div id="carousel${ad.id}" class="carousel slide">
                                <div class="carousel-inner" style="border-radius: 0.375rem">
                                    <#list ad.imagesUrls as imageUrl>
                                        <div class="carousel-item <#if imageUrl?index == 0>active</#if>">
                                            <img src="${imageUrl}"
                                                 class="d-block w-100" style="object-fit: cover; height: 200px">
                                        </div>
                                    </#list>
                                </div>
                                <button class="carousel-control-prev"
                                        type="button"
                                        data-bs-target="#carousel${ad.id}"
                                        data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon"
                                          aria-hidden="true"></span>
                                </button>
                                <button class="carousel-control-next"
                                        type="button"
                                        data-bs-target="#carousel${ad.id}"
                                        data-bs-slide="next">
                                    <span class="carousel-control-next-icon"
                                          aria-hidden="true"></span>
                                </button>
                            </div>
                        </div>
                        <div class="col-md-9">
                            <div class="card-header d-flex justify-content-between">
                                <h5 class="mt-2">${ad}</h5>
                                <h5 class="mt-2">${(ad.price)?string["$0"]}</h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <strong>Mileage</strong><br>
                                        ${ad.mileage} km
                                    </div>
                                    <div class="col">
                                        <strong>Condition</strong><br>
                                        ${ad.condition}
                                    </div>
                                    <div class="col">
                                        <strong>Owners</strong><br>
                                        ${ad.owners}
                                    </div>
                                    <div class="col">
                                        <strong>Exchange</strong><br>
                                        <#if ad.exchangeAllowed>
                                            Allowed
                                        <#else>
                                            Disallowed
                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-between mx-3 mb-3 text-secondary">
                                <span>${ad.publicationTs}</span>
                                <span>
                                    <i class="bi bi-eye"></i>
                                    ${ad.viewCount}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </#list>
    </div>
</#macro>
