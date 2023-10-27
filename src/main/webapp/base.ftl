<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@title/></title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Rubik&display=swap" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    <style>
        * {
            font-family: Rubik, sans-serif;
        }

        .icon-link {
            align-items: baseline;
        }
    </style>

    <#if addJquery??><script src="https://code.jquery.com/jquery-3.7.1.min.js"></script></#if>
</head>
<body>

<nav class="navbar sticky-top navbar-expand-md bg-body-tertiary">
    <div class="container">
        <a class="navbar-brand" href="${contextPath}/">
            <img src="${contextPath}/assets/img/buycar_logo.png" height="50" alt="Logo"/>
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
                    <a class="nav-link" href="${contextPath}/advertisements">Advertisements</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${contextPath}/advertisements/my">My advertisements</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${contextPath}/advertisements/new">New advertisement</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${contextPath}/garage">Garage</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${contextPath}/chats">Chats</a>
                </li>
            </ul>
            <div class="d-flex align-items-center" style="--bs-link-color-rgb: var(--bs-body-color-rgb);">
                <#if user??>
                        <a href="${contextPath}/bookmarks" class="me-3">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-bookmark-star" viewBox="0 0 16 16">
                                <path d="M7.84 4.1a.178.178 0 0 1 .32 0l.634 1.285a.178.178 0 0 0 .134.098l1.42.206c.145.021.204.2.098.303L9.42 6.993a.178.178 0 0 0-.051.158l.242 1.414a.178.178 0 0 1-.258.187l-1.27-.668a.178.178 0 0 0-.165 0l-1.27.668a.178.178 0 0 1-.257-.187l.242-1.414a.178.178 0 0 0-.05-.158l-1.03-1.001a.178.178 0 0 1 .098-.303l1.42-.206a.178.178 0 0 0 .134-.098L7.84 4.1z"/>
                                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                            </svg>
                        </a>
                        <a class="icon-link" href="${contextPath}/profile">
                            <i class="bi bi-person-fill" aria-hidden="true"></i>
                            <span>${user}</span>
                        </a>
                <#else>
                        <a href="${contextPath}/register" class="btn btn-outline-primary me-2">Register</a>
                        <a href="${contextPath}/auth" class="btn btn-outline-secondary">Log in</a>
                </#if>
                <a href="#" id="theme-switcher" style="--bs-link-color-rgb: var(--bs-body-color-rgb); font-size: 24px; margin-left: 16px">
                    <i class="bi bi-moon"></i>
                </a>
            </div>
        </div>
    </div>
</nav>

<script>
    function getThemeCookieValue() {
        return document.cookie
            .split("; ")
            .find((row) => row.startsWith("theme="))
            ?.split("=")[1];
    }
    if (getThemeCookieValue() === "dark") {
        document.getElementsByTagName("html")[0].setAttribute("data-bs-theme", "dark")
        document.getElementById("theme-switcher").firstElementChild.classList.add("bi-sun")
        document.getElementById("theme-switcher").firstElementChild.classList.remove("bi-moon")
    }

    document.getElementById("theme-switcher").addEventListener("click", function () {
        if (getThemeCookieValue() === "dark") {
            document.cookie = "theme=light; path=/; max-age=31536000"
            document.getElementsByTagName("html")[0].setAttribute("data-bs-theme", "light")
            document.getElementById("theme-switcher").firstElementChild.classList.remove("bi-sun")
            document.getElementById("theme-switcher").firstElementChild.classList.add("bi-moon")
        } else {
            document.cookie = "theme=dark; path=/; max-age=31536000"
            document.getElementsByTagName("html")[0].setAttribute("data-bs-theme", "dark")
            document.getElementById("theme-switcher").firstElementChild.classList.add("bi-sun")
            document.getElementById("theme-switcher").firstElementChild.classList.remove("bi-moon")
        }
    })
</script>

<div class="content">
    <@content/>
</div>

</body>
</html>
