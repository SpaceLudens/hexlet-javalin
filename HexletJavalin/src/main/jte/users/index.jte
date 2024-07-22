@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    page = page,
content = @`
    <form action="${NamedRoutes.usersPath()}" method="get">
        <input type="search" name="term" value="${page.getTerm()}">
        <input type="submit" value="Search">
    </form>
    <div style="row row-cols-md-3 mb-3">
        <h2>Users list</h2>
        <table class="table table-striped">
    @if(page.getUsers().isEmpty())
        <p>Пока не добавлено ни одного пользователя</p>
    @else
        <tr >
                <th >ID</th>
                <th>Name</th>
                <th>Email</th>
            </tr>
            @for(var user : page.getUsers())
            <tr>
                <td>${user.getId()}</td>
                <td>${user.getName()}</td>
                <td>${user.getEmail()}</td>
            </tr>
            @endfor
    @endif
        </table>
        <li><a href="/users/build">Add new user</a></li>
    </div>
`
)