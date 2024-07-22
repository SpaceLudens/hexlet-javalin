@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
    page = page,
content = @`
    <form action="${NamedRoutes.coursesPath()}" method="get">
        <input type="search" name="term" value="${page.getTerm()}" required="">
        <input type="submit" value="Искать">
    </form>
    <div style="row row-cols-md-3 mb-3">
        <h2>List of courses</h2>
        <table class="table table-striped">
            @if(page.getCourses().isEmpty())
                <p>No courses have been added yet</p>
            @else
                <tr >
                    <th >ID</th>
                    <th>Name</th>
                    <th>Description</th>
                </tr>
                @for(var course : page.getCourses())
                <tr>
                    <td>${course.getId()}</td>
                    <td>
                        <a href="${NamedRoutes.coursesPath(course.getId())}">${course.getName()}</a>
                    </td>
                    <td>${course.getDescription()}</td>
                </tr>
            @endfor
            @endif
        </table>
        <li><a href="/courses/build">Add new course</a></li>
    </div>
`
)