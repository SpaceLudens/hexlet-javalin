@import gg.jte.Content
@import org.example.hexlet.dto.BasePage

@param Content content
@param BasePage page = null

<!doctype html>
<html lang="ru">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
              crossorigin="anonymous">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Main page</title>
    </head>

    <body>
    <div class="container">
        <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
            <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
            </a>

            <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/" class="nav-link px-2 link-secondary">Home page</a></li>
                <li><a href="/users" class="nav-link px-2 link-dark">Users</a></li>
                <li><a href="/courses" class="nav-link px-2 link-dark">Courses</a></li>
                <li><a href="/sessions/build" class="nav-link px-2 link-dark">Login</a></li>
            </ul>
        </header>
    </div>

    <div class="mx-auto p-4 py-md-5">
        @if(page != null && page.getFlash() != null)
            @if(page.getFlash().equals("New user has been created"))
                <div class="alert alert-success" role="alert">
                    <p>${page.getFlash()}</p>
                </div>
            @endif
            @if(page.getFlash().equals("Failed to create a new user"))
                <div class="alert alert-warning" role="alert">
                    <p>${page.getFlash()}</p>
                </div>
            @endif
        @endif
        ${content}
    </div>
    </body>
</html>
