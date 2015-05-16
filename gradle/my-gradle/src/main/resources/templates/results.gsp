<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>test</title>
</head>

<body>

<div>
    <h2>VALUE</h2>
</div>

<div>
    <h2>LIST</h2>

    <ol>
        <g:each in="${list}">
            <li>${it}</li>
        </g:each>
    </ol>

</div>

<div>
    <h2>MAP</h2>

    <ol>
        <g:each in="${map}">
            <li>${it.key} : ${it.value}</li>
        </g:each>
    </ol>
</div>

</body>
</html>