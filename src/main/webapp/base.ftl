<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@title/></title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">

    <link href="css/styles.css" rel="stylesheet">

    <link href="assets/fontawesome/css/fontawesome.min.css" rel="stylesheet">
    <link href="assets/fontawesome/css/solid.min.css" rel="stylesheet">
</head>
<body>

<header>
    <a href="${contextPath}/"><img src="assets/img/buycar_logo.png" class="logo" alt="logo"/></a>
    <#if user??>
        <a class="profile-link" href="${contextPath}/profile">
            <i class="fa-solid fa-user"></i>
            <span>${user}</span>
        </a>
    <#else>
        <div class="auth-btns">
            <a href="${contextPath}/auth" class="btn login-btn">Log in</a>
            or
            <a href="${contextPath}/register" class="btn accent register-btn">Register</a>
        </div>
    </#if>
</header>

<div class="content">
    <@content/>
</div>

</body>
</html>
