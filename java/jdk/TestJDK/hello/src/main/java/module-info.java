/**
 *
 * @author dangqian.zll
 * @date 2026/3/6
 */
module hello {
    requires javafx.controls;
    requires org.apache.commons.lang3;
    requires javafx.fxml;

    opens me.test.jdk.javafx to
            javafx.fxml,
            javafx.graphics;

    exports me.test.jdk.javafx;
}
