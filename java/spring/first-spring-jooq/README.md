


```bash
docker create                                       \
    --name my-mysql                                 \
    --restart unless-stopped                        \
    -it                                             \
    -e MYSQL_ROOT_PASSWORD=123456                   \
    -v /Users/zll/data0/store/soft/mysql:/var/lib/mysql                  \
    -p 3306:3306                                    \
    mysql:5.7.21

docker start my-mysql


# 通过docker 本地启动 mysql 数据库，如何创建，咱时省略。
docker start my-mysql

```