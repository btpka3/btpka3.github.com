void aaa(String name, Closure c) {
    println "hello : ${name}, $c"
}

println "-----------------";

aaa("111") {
    println "222";
}

/*

def c
c = { List<String> list, List<Boolean> isInList, int i ->

    if (i == list.size()) {
        List<String> resultList = new ArrayList(list.size());
        list.eachWithIndex { String s, int index ->
            resultList[index] = isInList[index] ? s : " "
        }
        println resultList.join(",")
        return;
    }
    isInList[i] = false
    c list, isInList, i + 1
    isInList[i] = true
    c list, isInList, i + 1
}

c(["A", "B", "C"], [false, false, false], 0)
*/

///////////////////////
/*
def factorial
factorial = { int n, def accu = 1G ->
    if (n < 2) return accu

    return  2*factorial.trampoline(n - 1)  + factorial.trampoline(n - 2)
}
factorial = factorial.trampoline()

//assert factorial(1)    == 1
//assert factorial(3)    == 1 * 2 * 3
assert factorial(1000) // == 402387260.. plus another 2560 digits
*/

println "start : " + new Date();

def xx
cc = 0;
xx = { int n, def accu = 1G ->
    if (n <= 2) return accu

    cc++;
    return xx(n - 1) + xx(n - 2)
}
println "result: " + xx(20) + ",  cc = $cc"
println "end   : " + new Date();