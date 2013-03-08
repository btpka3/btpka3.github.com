该工程主要目的是通过Spring MVC和Spring Security 完成：
1. 所有JSP文件不能直接访问，且源文件可以不放到/WEB-INF目录下 (index.jsp是不能放到该目录下的，因为Welcome页面不支持虚拟路径)
2. 所有的JSP文件都通过 .htm 后缀访问。


---------------------------
建议：JSP文件都映射成 *.htm, 而静态的HTMl文件则全部以 *.html 为后缀。