behind HTTPS reverse proxy, how to fix httpServletRequest.getScheme() == 'https' and isSecure() == true ?

FIXME : Spring Security behind HTTPS reverse proxy


sudo vi /etc/nginx/sites-enabled/default

```conf
upstream his {
    server                       10.1.18.37:9999  weight=1 max_fails=1 fail_timeout=60s;
}
server {
    listen 443;
    server_name a.test.me;

    root html;
    index index.html index.htm;

    ssl on; 
    ssl_certificate /etc/nginx/key/whhit.pem.cer;
    ssl_certificate_key /etc/nginx/key/whhit.pem.clear.key;  

    ssl_session_timeout 5m; 

    ssl_protocols SSLv3 TLSv1;
    ssl_ciphers ALL:!ADH:!EXPORT56:RC4+RSA:+HIGH:+MEDIUM:+LOW:+SSLv3:+EXP;
    ssl_prefer_server_ciphers on; 

    location / { 
        #try_files $uri $uri/ =404;
        proxy_next_upstream http_500 http_502 http_503 http_504 timeout error invalid_header;
        proxy_pass              http://his;
        proxy_set_header        Host            $host;   # ???  $http_host;
        proxy_set_header        X-Real-IP       $remote_addr;
        proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;                                                                                                                                        
        proxy_set_header        X-Forwarded-Proto $scheme;
        add_header              Front-End-Https   on; 
        proxy_redirect          off;
       #proxy_redirect            http://his    https://his;
    }   
}

```

修改hosts

```sh
sudo vi /etc/hosts
10.1.18.37 a.test.me
```



运行

```sh
mvn -Dmaven.test.skip=true -Djetty.port=9999 jetty:run
```

