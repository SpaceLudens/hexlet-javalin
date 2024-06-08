@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
content = @`
    <div style="row row-cols-md-3 mb-3">
        <table class="table table-striped">
            <tr >
                <th >ID</th>
                <th>Name</th>
                <th>Description</th>
            </tr>
            @for(var course : page.getCourses())
                <tr>
                    <td>${course.getId()}</td>
                    <td>${course.getName()}</td>
                    <td>${course.getDescription()}</td>
                </tr>
            @endfor
        </table>
    </div>
`
)