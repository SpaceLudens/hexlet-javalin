@import org.example.hexlet.dto.MainPage
@param MainPage page

@template.layout.page(
content = @`
    <body>
    <main>
        <h1>Hello,Hexlet!</h1>
        @if(page.getCurrentUser() != null)
            Welcome, ${page.getCurrentUser()}.
            To log out, delete the JSESSIONID cookie from the browser
        @endif
    </main>
    </body>
`
)