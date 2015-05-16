<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title><g:if env="development">Ice Runtime Exception</g:if><g:else>Error</g:else></title>
</head>

<body>

<div>
    <h2>ERROR~~~~~~~~~~~${1+2}</h2>
    <div>${request.getAttribute('javax.servlet.error.exception')?.class}</div>
    <div>${request.getAttribute('javax.servlet.error.exception')?.message}</div>
    %{--<g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}" />--}%
</div>

</body>
</html>