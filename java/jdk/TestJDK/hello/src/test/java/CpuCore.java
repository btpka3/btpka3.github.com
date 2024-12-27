// -XX:+UseContainerSupport
// ognl '@java.lang.Runtime@getRuntime().availableProcessors()'
public class CpuCore {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
