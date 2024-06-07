@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Данные пользователя</title>
</head>
<body>
<div>
    <main>
        <h2>${page.getCourse().getId()} ${page.getCourse().getName()}</h2>
        <div>${page.getCourse().getDescription()}</div>
    </main>
</div>
</body>
</html>
