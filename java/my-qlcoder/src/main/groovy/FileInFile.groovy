import org.apache.commons.io.IOUtils

/**
 * http://www.qlcoder.com/task/766e
 */
class FileInFile {
    static void main(String[] args) {
        def filePath = "/home/zll/work/git-repo/github/btpka3/btpka3.github.com/java/my-qlcoder/src/main/resources/rf.data"
        def destDir = "/tmp/FileInFile"
        new File(destDir).mkdirs()


        RandomAccessFile raf = new RandomAccessFile(filePath, "r")
        int i = 0
        try {
            while (true) {
                byte flag = raf.readByte()
                if (flag == 2) {
                    break
                }
                int size = raf.readInt()
                def buf = new byte[size]
                raf.read(buf, 0, size)
                if (flag == 0) {
                    IOUtils.write(buf, new FileOutputStream(new File(destDir, i + "-0")))
                } else {
                    IOUtils.write(buf, new FileOutputStream(new File(destDir, i + "-1")))
                }
                i++
            }
        } finally {
            raf.close();
        }
    }
}