
说明：
该工程仅仅用以学习研究之用，不可用于商业目的。商业场景请考虑与 [快递100](http://www.kuaidi100.com/) 等商家合作。

# 查询申通快递单号

```sh
echo 968500117030 > /tmp/sto.txt
mvn exec:java -Dexec.mainClass="me.test.sto.Sto" -Dexec.args="/tmp/sto.txt" 
```