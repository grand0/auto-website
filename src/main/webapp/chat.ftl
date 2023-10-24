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
                .text(msg.sentTs)
            return $("<div></div>")
                .addClass("mb-3")
                .append(name).append($("<br>"))
                .append(msgText).append($("<br>"))
                .append(sent)
                .attr("style", "overflow-wrap: break-word");
        }

        $(document).ready(function() {
            let timerId = setInterval(() => {
                $.get(
                    "${contextPath}/chat?ad_id=${adId}&sender_id=${senderId}&format=json",
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
                            "adId": ${adId},
                            "toUserId": ${senderId},
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

    <h1 class="text-center my-3">Chat</h1>

    <div class="container">
        <div class="input-group my-3">
            <input id="msg-input" class="form-control" type="text"
                   maxlength="2000" placeholder="Message...">
            <button href="#" id="send-btn"
                    class="btn btn-primary input-group-text" disabled>Send</button>
        </div>
    </div>

    <div class="container" style="overflow-y: scroll; overflow-x: auto; height: 75vh">
        <div id="messages-list">
            <#list messages as msg>
                <div class="mb-3" style="overflow-wrap: break-word">
                    <strong>${msg.sender}</strong><br>
                    <span>${msg}</span><br>
                    <span class="text-secondary">${msg.sentTs}</span>
                </div>
            </#list>
        </div>
    </div>
</#macro>
