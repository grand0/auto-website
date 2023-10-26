<#assign addJquery=true>

<#include "base.ftl">

<#macro title>Advertisements</#macro>

<#macro content>
    <script>
        function isNumeric(str) {
            return !isNaN(str)
        }

        $(document).ready(function () {
            $("#filter-collapse-btn").on("click", function () {
                if ($(this).hasClass("collapsed")) { // collapse was collapsed
                    $(this)
                        .text("Show filters")
                        .removeClass("btn-primary")
                        .addClass("btn-outline-primary")
                } else {
                    $(this)
                        .text("Hide filters")
                        .removeClass("btn-outline-primary")
                        .addClass("btn-primary")
                }
            })
            $("#clear-filter-btn").on("click", function () {
                $("input:not([type=checkbox])").val(null)
                $("#exchange-select").val("-1")
                $("input[type=checkbox]").prop("checked", "")
            })

            $("#filter-btn").on("click", function () {
                const priceFromInput = $("#price-from-input")
                const priceToInput = $("#price-to-input")
                const mileageFromInput = $("#mileage-from-input")
                const mileageToInput = $("#mileage-to-input")
                const ownersFromInput = $("#owners-from-input")
                const ownersToInput = $("#owners-to-input")
                const exchangeSelect = $("#exchange-select")

                let formValid = true;
                if (priceFromInput.val().trim() && !isNumeric(priceFromInput.val().trim())) {
                    priceFromInput.addClass("is-invalid")
                    formValid = false;
                }
                if (priceToInput.val().trim() && !isNumeric(priceToInput.val().trim())) {
                    priceToInput.addClass("is-invalid")
                    formValid = false;
                }
                if (mileageFromInput.val().trim() && !isNumeric(mileageFromInput.val().trim())) {
                    mileageFromInput.addClass("is-invalid")
                    formValid = false;
                }
                if (mileageToInput.val().trim() && !isNumeric(mileageToInput.val().trim())) {
                    mileageToInput.addClass("is-invalid")
                    formValid = false;
                }
                if (ownersFromInput.val().trim() && !isNumeric(ownersFromInput.val().trim())) {
                    ownersFromInput.addClass("is-invalid")
                    formValid = false;
                }
                if (ownersToInput.val().trim() && !isNumeric(ownersToInput.val().trim())) {
                    ownersToInput.addClass("is-invalid")
                    formValid = false;
                }
                if (exchangeSelect.val() === null) {
                    exchangeSelect.addClass("is-invalid")
                    formValid = false;
                }

                if (formValid) {
                    let urlParams = "?"
                    if (priceFromInput.val().trim()) {
                        urlParams += "priceFrom=" + priceFromInput.val().trim() + "&"
                    }
                    if (priceToInput.val().trim()) {
                        urlParams += "priceTo=" + priceToInput.val().trim() + "&"
                    }
                    if (mileageFromInput.val().trim()) {
                        urlParams += "mileageFrom=" + mileageFromInput.val().trim() + "&"
                    }
                    if (mileageToInput.val().trim()) {
                        urlParams += "mileageTo=" + mileageToInput.val().trim() + "&"
                    }
                    if (ownersFromInput.val().trim()) {
                        urlParams += "ownersFrom=" + ownersFromInput.val().trim() + "&"
                    }
                    if (ownersToInput.val().trim()) {
                        urlParams += "ownersTo=" + ownersToInput.val().trim() + "&"
                    }
                    if (exchangeSelect.val() === "1") {
                        urlParams += "exchangeAllowed=true&"
                    }
                    urlParams += "conditions="
                    let addComma = false
                    $.each($("input[type=checkbox]"), function (index, checkbox) {
                        if (checkbox.checked) {
                            if (addComma) {
                                urlParams += "," + checkbox.value
                            } else {
                                urlParams += checkbox.value
                                addComma = true
                            }
                        }
                    })
                    window.location.replace("${contextPath}/advertisements" + urlParams)
                }
            })
        })
    </script>

    <h1 class="text-center my-3">Advertisements</h1>

    <div class="container">
        <button id="filter-collapse-btn" class="btn btn-outline-primary mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#filter-collapse">
            Show filters
        </button>

        <div class="collapse" id="filter-collapse">
            <div class="card card-body mb-3">
                <div class="row mb-3">
                    <div class="col-md-3">
                        <label for="price-from-input" class="form-label">Price from</label>
                        <input type="number" class="form-control" id="price-from-input" value="${filter.priceFrom!?c}">
                    </div>
                    <div class="col-md-3">
                        <label for="price-to-input" class="form-label">Price to</label>
                        <input type="number" class="form-control" id="price-to-input" value="${filter.priceTo!?c}">
                    </div>
                    <div class="col-md-3">
                        <label for="mileage-from-input" class="form-label">Mileage from</label>
                        <input type="number" class="form-control" id="mileage-from-input" value="${filter.mileageFrom!?c}">
                    </div>
                    <div class="col-md-3">
                        <label for="mileage-to-input" class="form-label">Mileage to</label>
                        <input type="number" class="form-control" id="mileage-to-input" value="${filter.mileageTo!?c}">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-3">
                        <label for="owners-from-input" class="form-label">Owners from</label>
                        <input type="number" class="form-control" id="owners-from-input" value="${filter.ownersFrom!?c}">
                    </div>
                    <div class="col-md-3">
                        <label for="owners-to-input" class="form-label">Owners to</label>
                        <input type="number" class="form-control" id="owners-to-input" value="${filter.ownersTo!?c}">
                    </div>
                    <div class="col-md-3">
                        <label for="exchange-select" class="form-label">Exchange</label>
                        <select id="exchange-select" class="form-select">
                            <option value="-1">
                                Doesn't matter
                            </option>
                            <option value="1" <#if filter.exchangeAllowed?? && filter.exchangeAllowed>selected</#if>>
                                Allowed
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row mb-5">
                    <label for="conditions-select" class="form-label d-block">Conditions</label>
                    <div class="btn-group" role="group">
                        <#list conditions as condition>
                            <input type="checkbox" class="btn-check" id="condition-check-${condition.id}" autocomplete="off" value="${condition.id}" <#if filter.conditions?? && filter.conditions?seq_contains(condition)>checked</#if>>
                            <label class="btn btn-outline-primary" for="condition-check-${condition.id}">${condition}</label>
                        </#list>
                    </div>
                </div>
                <div>
                    <button type="button" id="filter-btn" class="btn btn-outline-primary me-3">
                        Filter
                    </button>
                    <button type="button" id="clear-filter-btn" class="btn btn-outline-danger">
                        Clear filter
                    </button>
                </div>
            </div>
        </div>

        <div id="advertisements-list">
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
            <#else>
                <p class="text-center">
                    There are no advertisements matching the filter.
                </p>
            </#list>
        </div>
    </div>
</#macro>
