<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@title/></title>
</head>
<body>
<header>
    <h1><a href="/" style="color: black">cars website.</a></h1>
    <#if !hideAuthInfo??>
        <p>
            <#if username??>
                welcome, <b>${username}</b>. <a href="/auth?action=logout">logout</a>
            <#else>
                welcome, <b>stranger</b>. <a href="/auth">login</a>
            </#if>
        </p>
    </#if>
</header>

<div class="content">
    <@content/>
</div>
</body>
</html>
