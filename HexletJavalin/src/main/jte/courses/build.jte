@import org.example.hexlet.dto.courses.BuildCoursePage
@import org.example.hexlet.util.NamedRoutes
@param BuildCoursePage page

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

    <form action="${NamedRoutes.coursesPath()}" method="post">
        <div>
            <label>
                Course name
                <input type="text" name="name" value="${page.getName()}"/>
            </label>
        </div>
        <div>
            <label>
                Course Description
                <input type="text" name="description" value="${page.getDescription()}"/>
            </label>
        </div>
        <input type="submit" value="Create" />
    </form>
`
)