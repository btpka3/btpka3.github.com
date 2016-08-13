/**
 * Created by zll on 16-5-12.
 */
class B {
    static void main(String[] args) {

        def a = ["a", "b", "c", "d"]
        println a
        a.each { i ->
            if (i == "c") {
                a.remove(i)
                a.add("cc")
            }
        }
        println a
    }
}
