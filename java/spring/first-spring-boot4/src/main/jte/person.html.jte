@import com.github.btpka3.first.spring.boot4.Persson
@import org.apache.commons.lang3.StringUtils

@param Persson page
<head>
    <title>hello</title>
</head>
<body>
  <p>Welcome to ${StringUtils.isNotBlank(page.getName()) ? page.getName() : "guest"}!</p>
</body>