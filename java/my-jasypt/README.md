

# 客户端加密？

1. 浏览器传输时使用密文： 

    ```
    encPwd = encryptSHA512withRSA(pubKey, clearPwd)
    ```
2. 服务器端先解密：
 
    ```
    clearPwd = decryptSHA512withRSA(priKey, encPwd)
    ```
3. 数据库内用单向hash方法进行存储：

    ```
    pwdHash = bcrypt(clearPwd, salt, logrounds=7)
    ```
