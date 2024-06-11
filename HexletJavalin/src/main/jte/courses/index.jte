@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
content = @`
    <form action="/courses" method="get">
        <input type="search" name="term" value="${page.getTerm()}">
        <input type="submit" value="Искать">
    </form>
    <div style="row row-cols-md-3 mb-3">
        <h2>Список Курсов</h2>
        <table class="table table-striped">
            @if(page.getCourses().isEmpty())
                <p>Пока не добавлено ни одного курса</p>
            @else
                <tr >
                    <th >ID</th>
                    <th>Название</th>
                    <th>Описание</th>
                </tr>
                @for(var course : page.getCourses())
                <tr>
                    <td>${course.getId()}</td>
                    <td>${course.getName()}</td>
                    <td>${course.getDescription()}</td>
                </tr>
            @endfor
            @endif
        </table>
        <li><a href="/courses/build">Добавить новый курс</a></li>
    </div>
`
)