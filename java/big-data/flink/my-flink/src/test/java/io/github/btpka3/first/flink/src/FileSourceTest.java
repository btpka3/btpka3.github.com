package io.github.btpka3.first.flink.src;

import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.StreamFormat;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import java.nio.file.Paths;

/**
 * @author dangqian.zll
 * @date 2024/7/3
 */
public class FileSourceTest {
    public void x(){
        StreamFormat<String> streamFormat=new TextLineInputFormat();
        FileSource.forRecordStreamFormat(streamFormat, new Path("/tmp/data.txt"));
    }
}
