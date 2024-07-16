@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
content = @`
        <h3>User description</h3>
        <div>ID: ${page.getUser().getId()}</div>
        <div>Fist name: ${page.getUser().getName()}</div>
        <div>Last name: ${page.getUser().getEmail()}</div>
        <div>Email: ${page.getUser().getEmail()}</div>
`
)