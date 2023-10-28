<#assign addJquery=true>

<#include "base.ftl">

<#macro title>Chat</#macro>

<#macro content>
    <script>
        function toMsgBlock(msg) {
            let name = $("<strong></strong>")
                .text(msg.sender.firstName + " " + msg.sender.lastName)
            let msgText = $("<span></span>")
                .text(msg.message)
            let sent = $("<span></span>")
                .addClass("text-secondary")
                .text(msg.sentDateTime)
            let block = $("<div></div>")
            return block
                .addClass("mb-3")
                .append(name).append($("<br>"))
                .append(msgText).append($("<br>"))
                .append(sent)
                .attr("style", "overflow-wrap: break-word");
        }

        $(document).ready(function() {
            let timerId = setInterval(() => {
                $.get(
                    "${contextPath}/chat?ad_id=${ad.id}&recipient_id=${recipient.id}&format=json",
                    function (response) {
                        if ("unauthorized" in response) {
                            window.location.replace("${contextPath}/auth");
                        }
                        $("#messages-list").empty()
                        $.each(response.messages, function (index, msg) {
                            $("#messages-list").append(toMsgBlock(msg))
                        })
                    }
                )
            }, 3000)
            let msgList = $("#messages-list")
            msgList.scrollTop(msgList[0].scrollHeight)

            $("#send-btn").on("click", function () {
                let msgInput = $("#msg-input")
                if (!msgInput.val()) {
                    msgInput.addClass("is-invalid")
                } else {
                    let msg = msgInput.val()
                    msgInput.val("")
                    $(this).attr("disabled", "true")
                    $.post(
                        "${contextPath}/chat",
                        {
                            "adId": ${ad.id},
                            "recipientId": ${recipient.id},
                            "message": msg
                        },
                        function (response) {
                            if ("unauthorized" in response) {
                                window.location.replace("${contextPath}/auth")
                            }
                            console.log(response)
                            $("#messages-list").prepend(toMsgBlock(response.message))
                        }
                    )
                }
            })

            $("#msg-input").on("input", function () {
                if (!$(this).val()) {
                    $("#send-btn").attr("disabled", "true")
                } else {
                    $("#send-btn").removeAttr("disabled")
                }
            })
        })
    </script>

    <h1 class="text-center my-3">Chat with <#if recipient.id == ad.seller.id>seller<#else>buyer</#if></h1>

    <div class="container mb-3">
        <div class="card">
            <div class="card-header d-flex align-items-center justify-content-between">
                <div>
                    <img src="${ad.imagesUrls[0]}" alt="ad image" width="133" height="75" class="me-3" style="object-fit: cover; border-radius: 0.375em">
                    <span>${ad}</span>
                </div>
                <div>
                    <a href="${contextPath}/advertisements?id=${ad.id}" class="icon-link">
                        <i class="bi bi-card-heading"></i>
                        <span>View ad</span>
                    </a>
                </div>
            </div>
            <div class="card-header d-flex align-items-center justify-content-between">
                <div>
                    <#if (recipient.avatarUrl)??>
                        <img src="${recipient.getRoundCroppedAvatarUrl()}" alt="user avatar" class="me-3" width="50" height="50">
                    <#else>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16" width="50" height="50" class="bi bi-person-circle me-3">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
                            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
                        </svg>
                    </#if>
                    <span>${recipient}</span>
                </div>
                <div>
                    <a class="icon-link" href="${contextPath}/profile?id=${recipient.id}">
                        <i class="bi bi-person-fill"></i>
                        View profile
                    </a>
                </div>
            </div>
            <div class="card-body">
                <div class="input-group mb-3">
                    <input id="msg-input" class="form-control" type="text"
                           maxlength="2000" placeholder="Message...">
                    <button href="#" id="send-btn"
                            class="btn btn-primary input-group-text" disabled>Send</button>
                </div>
                <div style="overflow-y: scroll; overflow-x: auto; height: 50vh">
                    <div id="messages-list">
                        <#list messages as msg>
                            <div class="mb-3" style="overflow-wrap: break-word">
                                <strong>${msg.sender}</strong><br>
                                <span>${msg}</span><br>
                                <span class="text-secondary">${msg.sentDateTime}</span>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>
