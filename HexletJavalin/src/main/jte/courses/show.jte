@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
content = @`
    <div>ID: ${page.getCourse().getName()}</div>
    <div>Course name: ${page.getCourse().getName()}</div>
`
)
