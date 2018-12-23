## 说明

该目录下的文件来自于 flowable 官网的说明。用于演示 Flowable 所有产品。


## 快速开始

1.  启动

    ```bash
    mkdir -p /tmp/flowable/config
    cd /tmp/flowable
    wget -O config/modeler-task-idm-admin-postgres.yml https://github.com/flowable/flowable-engine/blob/master/docker/config/modeler-task-idm-admin-postgres.yml?raw=true
    wget -O modeler-task-idm-admin-postgres.sh https://github.com/flowable/flowable-engine/blob/master/docker/modeler-task-idm-admin-postgres.sh?raw=true
    chmod u+x modeler-task-idm-admin-postgres.sh
    ./modeler-task-idm-admin-postgres.sh start
    ```
1. 浏览器访问:
    
    - 用户名/密码: admin/test
    - [flowable-idm](http://localhost:8080/flowable-idm/)
    - [flowable-modeler](http://localhost:8888/flowable-modeler/)
    - [flowable-admin](http://localhost:9988/flowable-admin/)
    - [flowable-task](http://localhost:9999/flowable-task/)
