<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@title/></title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    <#if addJquery??><script src="https://code.jquery.com/jquery-3.7.1.min.js"></script></#if>
</head>
<body>

<header>

    <nav class="navbar navbar-expand-md bg-body-tertiary">
        <div class="container">
            <a class="navbar-brand" href="${contextPath}/">
                <img src="assets/img/buycar_logo.png" height="50" alt="Logo"/>
            </a>
            <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapsingNavbar"
                    aria-controls="collapsingNavbar"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsingNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Cars</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Reviews</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">News</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Forum</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Help</a>
                    </li>
                </ul>
                <#if user??>
                    <a class="icon-link" style="--bs-link-color-rgb: var(--bs-body-color-rgb);" href="${contextPath}/profile">
                        <i class="bi bi-person-fill" aria-hidden="true"></i>
                        <span>${user}</span>
                    </a>
                <#else>
                    <form class="d-flex">
                        <a href="${contextPath}/register" class="btn btn-outline-primary me-2">Register</a>
                        <a href="${contextPath}/auth" class="btn btn-outline-secondary">Log in</a>
                    </form>
                </#if>
            </div>
        </div>
    </nav>
</header>

<div class="content">
    <@content/>
</div>

</body>
</html>
