a:
  b:
    - "aaa"
    [# th:each="item : ${items}"  hh:outer-space-trim-mode="--"  th:b="++" ]
    - [(${item})]
    [/]
    - "bbb"