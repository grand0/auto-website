<#include "base.ftl">

<#macro title>Buy ${advertisement}</#macro>

<#macro content>
    <div class="container">
        <div class="d-flex justify-content-between mt-3">
            <h1>${advertisement.car}</h1>
            <h1>${(advertisement.price)?string["$0"]}</h1>
        </div>

        <a class="icon-link mb-3" href="#">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-star" viewBox="0 0 16 16">
                <path d="M7.84 4.1a.178.178 0 0 1 .32 0l.634 1.285a.178.178 0 0 0 .134.098l1.42.206c.145.021.204.2.098.303L9.42 6.993a.178.178 0 0 0-.051.158l.242 1.414a.178.178 0 0 1-.258.187l-1.27-.668a.178.178 0 0 0-.165 0l-1.27.668a.178.178 0 0 1-.257-.187l.242-1.414a.178.178 0 0 0-.05-.158l-1.03-1.001a.178.178 0 0 1 .098-.303l1.42-.206a.178.178 0 0 0 .134-.098L7.84 4.1z"/>
                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
            </svg>
            <span>Add to favorites</span>
        </a>

        <div class="row">
            <div id="carousel${advertisement.id}" class="carousel slide col-md-6" style="height: max(22.5vw, 250px)">
                <div class="carousel-inner" style="border-radius: 0.375rem">
                    <#list advertisement.imagesUrls as imageUrl>
                        <div class="carousel-item <#if imageUrl?index == 0>active</#if>">
                            <img src="${imageUrl}"
                                 class="d-block w-100" style="object-fit: cover; height: max(22.5vw, 250px)">
                        </div>
                    </#list>
                </div>
                <button class="carousel-control-prev"
                        type="button"
                        data-bs-target="#carousel${advertisement.id}"
                        data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon"
                                          aria-hidden="true"></span>
                </button>
                <button class="carousel-control-next"
                        type="button"
                        data-bs-target="#carousel${advertisement.id}"
                        data-bs-slide="next">
                                    <span class="carousel-control-next-icon"
                                          aria-hidden="true"></span>
                </button>
            </div>

            <div class="col-3">
                <div class="mb-3">
                    <strong>Body</strong><br>
                    ${advertisement.car.body}
                </div>
                <div class="mb-3">
                    <strong>Transmission</strong><br>
                    ${advertisement.car.transmission}
                </div>
                <div class="mb-3">
                    <strong>Engine</strong><br>
                    ${advertisement.car.engine}
                </div>
                <div class="mb-3">
                    <strong>Drive</strong><br>
                    ${advertisement.car.drive}
                </div>
                <#if advertisement.car.engine.id != 4> <!-- if not electro engine -->
                    <div class="mb-3">
                        <strong>Engine volume</strong><br>
                        ${advertisement.car.engineVolume?string["0.0"]} L
                    </div>
                </#if>
                <div class="mb-3">
                    <strong>Horsepower</strong><br>
                    ${advertisement.car.horsepower} hp
                </div>
                <div class="mb-3">
                    <strong>Wheel</strong><br>
                    <#if advertisement.car.leftWheel>Left<#else>Right</#if>
                </div>
            </div>

            <div class="col-3">
                <div class="mb-3">
                    <strong>Mileage</strong><br>
                    ${advertisement.mileage} km
                </div>
                <div class="mb-3">
                    <strong>Condition</strong><br>
                    ${advertisement.condition}
                </div>
                <div class="mb-3">
                    <strong>Owners</strong><br>
                    ${advertisement.owners}
                </div>
                <div class="mb-3">
                    <strong>Exchange</strong><br>
                    <#if advertisement.exchangeAllowed>
                        Allowed
                    <#else>
                        Disallowed
                    </#if>
                </div>
                <div class="mb-3">
                    <strong>Color</strong><br>
                    ${advertisement.carColor}
                </div>
                <div class="mb-3 text-secondary">
                    <strong>Posted</strong><br>
                    ${advertisement.publicationTs}
                </div>
                <div class="mb-3 text-secondary">
                    <strong>Views</strong><br>
                    ${advertisement.viewCount}
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <h2>Description</h2>
                <p>${advertisement.description}</p>
            </div>
            <div class="col-md-6">
                <h2>Seller</h2>
                <div>
                    <img src="${advertisement.seller.getRoundCroppedAvatarUrl()}" class="me-1" width="50" height="50">
                    ${advertisement.seller}
                </div>
                <a class="btn btn-primary icon-link my-3" href="${contextPath}/chat?ad_id=${advertisement.id}&sender_id=${advertisement.seller.id}">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-dots me-1" viewBox="0 0 16 16">
                        <path d="M5 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm4 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                        <path d="m2.165 15.803.02-.004c1.83-.363 2.948-.842 3.468-1.105A9.06 9.06 0 0 0 8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6a10.437 10.437 0 0 1-.524 2.318l-.003.011a10.722 10.722 0 0 1-.244.637c-.079.186.074.394.273.362a21.673 21.673 0 0 0 .693-.125zm.8-3.108a1 1 0 0 0-.287-.801C1.618 10.83 1 9.468 1 8c0-3.192 3.004-6 7-6s7 2.808 7 6c0 3.193-3.004 6-7 6a8.06 8.06 0 0 1-2.088-.272 1 1 0 0 0-.711.074c-.387.196-1.24.57-2.634.893a10.97 10.97 0 0 0 .398-2z"/>
                    </svg>
                    <span>Send a message</span>
                </a>
            </div>
        </div>
    </div>
</#macro>
