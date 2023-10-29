<#assign addJquery=true>

<#include "base.ftl">

<#macro title>Bookmarks</#macro>

<#macro content>
    <script>
        $(document).ready(function () {
            $(".remove-bookmark").on("click", function () {
                const link = $(this)
                const adId = link.attr("data-bs-id")
                $.post(
                    "${contextPath}/advertisements",
                    {
                        "action": "bookmark",
                        "adId": adId
                    },
                    function (response) {
                        if ("unauthorized" in response) {
                            window.location.replace("${contextPath}/auth")
                        } else if ("isBookmarked" in response) {
                            if (response.isBookmarked) {
                                console.log("true")
                                link
                                    .addClass("link-danger")
                                    .children(".bi-bookmark-star")
                                        .removeClass("bi-bookmark-star")
                                        .addClass("bi-bookmark-star-fill")
                                    .siblings("span")
                                        .text("Remove from bookmarks")
                            } else {
                                console.log("false")
                                link
                                    .removeClass("link-danger")
                                    .children(".bi-bookmark-star-fill")
                                        .removeClass("bi-bookmark-star-fill")
                                        .addClass("bi-bookmark-star")
                                    .siblings("span")
                                        .text("Add to bookmarks")
                            }
                        }
                    }
                )
            })
        })
    </script>

    <h1 class="page-title">Bookmarks</h1>

    <div class="container">
        <#list advertisements as ad>
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
                            <a href="${contextPath}/advertisements?id=${ad.id}" style="text-decoration: none">
                                <h5 class="mt-2">${ad}</h5>
                            </a>
                            <div>
                                <a class="remove-bookmark icon-link link-danger mt-2 me-2" href="#" data-bs-id="${ad.id}">
                                    <i class="bi bi-bookmark-star-fill"></i>
                                    <span>Remove from bookmarks</span>
                                </a>
                            </div>
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
                                <div class="col">
                                    <strong>Price</strong><br>
                                    ${(ad.price)?string["$0"]}
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
        </#list>
    </div>
</#macro>
