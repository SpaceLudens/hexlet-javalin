@import org.example.hexlet.dto.users.BuildUserPage
@import org.example.hexlet.util.NamedRoutes
@param BuildUserPage page

@template.layout.page(
    page = page,
content = @`
    @if(page.getErrors() != null)
        <ul>
            @for(var validator : page.getErrors().values())
                @for(var error : validator)
                    <li>${error.getMessage()}</li>
                    @endfor
                @endfor
        </ul>
        @endif

    <form action="${NamedRoutes.usersPath()}" method="post">
        <div>
            <label>
                Имя
                <input type="text" name="name" value="${page.getName()}" />
            </label>
        </div>
        <div>
            <label>
                Email
                <input type="email" required name="email" value="${page.getEmail()}" />
            </label>
        </div>
        <div>
            <label>
                Пароль
                <input type="password" required name="password" />
            </label>
        </div>
        <div>
            <label>
                Подтверждение пароля
                <input type="password" required name="passwordConfirmation" />
            </label>
        </div>
        <input type="submit" value="Зарегистрировать" />
    </form>
`
)