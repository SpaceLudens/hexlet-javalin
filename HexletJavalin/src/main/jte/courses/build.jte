@template.layout.page(
content = @`
    <form action="/courses" method="post">
        <div>
            <label>
                Название курса
                <input type="text" name="name" />
            </label>
        </div>
        <div>
            <label>
                Описание курса
                <input type="text" name="description" />
            </label>
        </div>
        <input type="submit" value="Зарегистрировать" />
    </form>
`
)