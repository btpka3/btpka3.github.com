package me.test.jdk.java.util;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java.util.Scanner 有一些不方便的地方。参考其实现追加了以下需要的方法：
 * {@link #skipBuffer() skipBuffer()}、
 * {@link #findAtBeginning(Pattern) findAtBeginning(Pattern)}、
 * {@link #findWithinHorizon(Pattern) findWithinHorizon(Pattern)}、
 * {@link #hasMore() hasMore()}、
 */
public class MyScanner implements Closeable {

    public static final Pattern LINE_PATTERN = Pattern.compile("\r\n|[\n\r\u2028\u2029\u0085]");

    // Boolean indicating if a match result is available
    private boolean sourceClosed = false;
    private CharBuffer buf = null;
    private Readable source;
    // A holder of the last IOException encountered
    private IOException lastException;

    // Boolean indicating more input is required
    private boolean needInput = false;

    // Internal matcher used for finding delimiters
    private Matcher matcher;
    private boolean closed = false;
    // The index into the buffer currently held by the Scanner
    private int position = 0;

    public MyScanner(Readable source) {
        this(source, 1024);
    }

    public MyScanner(Readable source, int bufSize) {
        this.source = source;
        buf = CharBuffer.allocate(bufSize);
        buf.limit(0);
        matcher = LINE_PATTERN.matcher(buf);
        matcher.useTransparentBounds(true);
        matcher.useAnchoringBounds(false);
    }

    private void ensureOpen() {
        if (closed) {
            throw new IllegalStateException("Scanner closed");
        }
    }

    // 判断在buffer中是否包含指定的模式。
    public String findWithinHorizon(Pattern pattern, int horizon) {
        ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }
        if (horizon < 0) {
            throw new IllegalArgumentException("horizon < 0");
        }
        while (true) {
            String token = findPatternInBuffer(pattern, horizon);
            if (token != null) {
                return token;
            }
            if (needInput) {
                readInput();
            } else {
                break; // up to end of input
            }
        }
        return null;
    }

    private String findPatternInBuffer(Pattern pattern, int horizon) {
        matcher.usePattern(pattern);
        int bufferLimit = buf.limit();
        int horizonLimit = -1;
        int searchLimit = bufferLimit;
        if (horizon > 0) {
            horizonLimit = position + horizon;
            if (horizonLimit < bufferLimit) {
                searchLimit = horizonLimit;
            }
        }
        matcher.region(position, searchLimit);
        if (matcher.find()) {
            if (matcher.hitEnd() && (!sourceClosed)) {
                // The match may be longer if didn't hit horizon or real end
                if (searchLimit != horizonLimit) {
                    // Hit an artificial end; try to extend the match
                    needInput = true;
                    return null;
                }
                // The match could go away depending on what is next
                if ((searchLimit == horizonLimit) && matcher.requireEnd()) {
                    // Rare case: we hit the end of input and it happens
                    // that it is at the horizon and the end of input is
                    // required for the match.
                    needInput = true;
                    return null;
                }
            }
            // Did not hit end, or hit real end, or hit horizon
            position = matcher.end();
            return matcher.group();
        }

        if (sourceClosed) {
            return null;
        }

        // If there is no specified horizon, or if we have not searched
        // to the specified horizon yet, get more input
        if ((horizon == 0) || (searchLimit != horizonLimit)) {
            needInput = true;
        }
        return null;
    }

    private boolean hasMoreInBuffer() {
        // System.out.println("=== buf.position/limit/capacity = "
        // + buf.position() + "/"
        // + buf.limit() + "/"
        // + buf.capacity());
        // matcher.region(position, buf.limit());

        // If we are sitting at the end, no more tokens in buffer
        if (position == buf.limit()) {
            return false;
        }
        return true;
    }

    public void close() throws IOException {
        if (closed)
            return;
        if (source instanceof Closeable) {
            try {
                ((Closeable) source).close();
            } catch (IOException ioe) {
                lastException = ioe;
            }
        }
        sourceClosed = true;
        source = null;
        closed = true;
    }

    public boolean isClosed() {
        return sourceClosed || closed;
    }

    // ----------------------------------------------------------------------------------------

    private String getCompleteTokenInBuffer(Pattern pattern) {

        // Attempt to match against the desired pattern
        matcher.usePattern(pattern);
        matcher.region(position, buf.limit());
        if (matcher.lookingAt()) {

            if (position > 0 && matcher.hitEnd() && (!sourceClosed)) {
                needInput = true;
                return null;
            }
            position = matcher.end();
            return matcher.group();
        }

        if (position > 0 && !sourceClosed) {
            needInput = true;
            return null;
        }

        return null;

    }

    // // 最后一次匹配结果在buffer中的开始位置。
    // public int begin() {
    // return matcher.start();
    // }

    // // 最后一次匹配结果在buffer中的结束位置。
    // public int end() {
    // return matcher.end();
    // }

    // // 跳到最后一次匹配结果在buffer中的结束位置。
    // public void skipLastMatch() {
    // buf.position(end());
    // }

    /**
     * 跳过buffer中的剩余的字符串，并跳过的字符串返回。
     */
    public String skipBuffer() {
        String remain = buf.toString();
        position = buf.limit();
        buf.position(buf.limit());
        return remain;
    }

    // public void position(int newPosition) {
    // buf.position(newPosition);
    // }

    private void readInput() {
        if (buf.limit() == buf.capacity()) {
            makeSpace();
        }

        // Prepare to receive data
        int p = buf.position();
        buf.position(buf.limit());
        buf.limit(buf.capacity());

        int n = 0;
        try {
            n = source.read(buf);
        } catch (IOException ioe) {
            lastException = ioe;
            n = -1;
        }

        if (n == -1) {
            sourceClosed = true;
            needInput = false;
        }

        if (n > 0) {
            needInput = false;
        }

        // Restore current position and limit for reading
        buf.limit(buf.position());
        buf.position(p);
    }

    // After this method is called there will either be an exception
    // or else there will be space in the buffer
    private void makeSpace() {
        buf.position(position);
        // Gain space by compacting buffer
        if (buf.position() > 0) {
            buf.compact();
            buf.flip();
            position = 0;
            return;
        }

        System.out.println("================================double space : " + buf.capacity());
        // Gain space by growing buffer
        int newSize = buf.capacity() * 2;
        CharBuffer newBuf = CharBuffer.allocate(newSize);
        newBuf.put(buf);
        newBuf.flip();
        buf = newBuf;
        position = 0;
        matcher.reset(buf);
    }

    // 判断当前位置buffer中是否以指定模式开始。
    public String findAtBeginning(Pattern pattern) {

        ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }

        // Search for the pattern
        while (true) {
            String token = getCompleteTokenInBuffer(pattern);
            if (token != null) {
                return token;
            }
            if (needInput) {
                readInput();
            } else {
                // throwFor();
                return null;
            }
        }
    }

    // 判断在buffer中是否包含指定的模式。
    public String findWithinHorizon(Pattern pattern) {
        return findWithinHorizon(pattern, buf.capacity());
    }

    public boolean hasMore() {
        ensureOpen();
        while (!sourceClosed) {
            if (hasMoreInBuffer()) {
                return true;
            }
            readInput();
        }
        return hasMoreInBuffer();
    }

    // TEST 1 : count line
    public static void main1(String[] args) throws IOException {
        StringBuilder b = new StringBuilder();
        for (int i = 'a'; i <= 'z'; i++) {
            for (int j = 0; j < 100; j++) {
                b.append((char) i);
            }
            b.append("\n");
        }

        System.out.println(b);
        int line = 0;
        // X x = new X(new CharArrayReader(b.toString().toCharArray()), 64);
        MyScanner x = new MyScanner(new InputStreamReader(fromLargeFile()), 2048);
        try {
            while (x.hasMore()) {
                if (x.findWithinHorizon(LINE_PATTERN) != null) {
                    line++;
                    System.out.println(line);
                } else {
                    x.skipBuffer();
                }
            }
        } finally {
            x.close();
            System.out.println("line count : " + line);
        }
    }

    // TEST 2 : count line && search target line
    public static void main2(String[] args) throws IOException {
        StringBuilder b = new StringBuilder();
        for (int i = 'a'; i <= 'z'; i++) {
            for (int j = 0; j < 100; j++) {
                b.append((char) i);
            }
            b.append("\n");
        }

        System.out.println(b);
        int line = 0;
        // X x = new X(new CharArrayReader(b.toString().toCharArray()), 100);
        // Pattern prefix = Pattern.compile("o+");
        MyScanner x = new MyScanner(new InputStreamReader(fromLargeFile()), 2048);
        Pattern prefix = Pattern.compile("INSERT INTO `trade` VALUES ");
        int atLine = 0;
        try {
            while (x.hasMore()) {
                String s = null;
                if ((s = x.findAtBeginning(prefix)) != null) {
                    System.out.println("s = " + s);
                    atLine = line + 1;
                }
                if (x.findWithinHorizon(LINE_PATTERN) != null) {
                    line++;
                    System.out.println(line);
                } else {
                    x.skipBuffer();
                }
            }
        } finally {
            x.close();
            System.out.println("atLine/line : " + atLine + "/" + line);
        }
    }

    public static void main(String[] args) throws IOException {
        Pattern prefix = Pattern.compile("INSERT INTO `trade` VALUES ");

        Pattern colPattern = Pattern.compile("(NULL|'(\\\\'|[^'])*'|[\\+-]?\\d+(\\.?\\d*)?),?");

        MyScanner x = new MyScanner(new InputStreamReader(fromLargeFile()), 2048);
        int line = 0;
        int atLine = 0;
        int count = 0;
        long latestTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            while (x.hasMore()) {
                String p = null;
                // 1. 查找行前缀
                if ((p = x.findAtBeginning(prefix)) != null) {
                    atLine = line + 1;
                    while ((p = x.findAtBeginning(Pattern.compile("\\("))) != null) {

                        count++;
                        // 1.1 查找记录
                        Map<String, String> rec = new LinkedHashMap<String, String>();
                        for (String colName : cols) {
                            String colValue = x.findAtBeginning(colPattern);
                            rec.put(colName, colValue);
                        }
                        x.findAtBeginning(Pattern.compile("\\)[,;]"));
                        System.out.println("REC : " + count + ". " + rec);

                        String dateCreated = rec.get("date_created");
                        if (dateCreated != null) {
                            if (dateCreated.endsWith(",")) {
                                dateCreated = dateCreated.substring(0, dateCreated.length() - 1);
                            }
                            dateCreated = dateCreated.replace("'", "");
                            try {
                                long time = sdf.parse(dateCreated).getTime();
                                if (latestTime < time) {
                                    latestTime = time;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
                if ((p = x.findWithinHorizon(LINE_PATTERN)) != null) {
                    line++;
                    System.out.println(line);
                } else {
                    p = x.skipBuffer();
                    // System.out.println("REMAIN:" + p);
                    if (x.isClosed()) {
                        line++;
                    }
                }
            }

        } finally {
            x.close();
            end = System.currentTimeMillis();
            System.out.println("time = " + ((end - start) / 1000) + " second");
            System.out.println("atLine/line : " + atLine + "/" + line);
            System.out.println("count = " + count);
            System.out.println("latestTime = " + sdf.format(new Date(latestTime)));
            System.out.println("buf.capacity = " + x.buf.capacity());
        }
    }

    static InputStream fromLargeFile() throws FileNotFoundException {
        final String dumpFile = "/home/zll/tmp/mysql_dump_140928.sql";
        return new BufferedInputStream(new FileInputStream(dumpFile));
    }

    public final static String[] cols = new String[]{
            "id",
            "version",
            "adjust_price",
            "area",
            "buyer_id",
            "buyer_memo",
            "cart_id",
            "channel",
            "city",
            "date_created",
            "expect_send_date",
            "last_updated",
            "pay_time",
            "postage",
            "province",
            "receive_date",
            "receiver",
            "seller_memo",
            "send_date",
            "status",
            "telphone",
            "total_price",
            "urge_count",
            "weight",
            "zip_code",
            "coupon_id",
            "pindan_id",
            "gift_id",
            "invoice_content",
            "invoice_name",
            "logistics_id",
            "logistics_type",
            "postage_fee",
            "street",
            "man_song_detail_id",
            "man_song_sended",
            "is_virtual",
            "adjust_custom_price",
            "pay_price",
            "pay_type",
            "integral",
            "iphone_code",
            "grade",
            "is_free_postage",
            "parent_id",
            "source",
            "full_re_activity",
            "actual_price",
            "box_memo",
            "neibu_channel",
            "integral_change",
            "is_delete",
            "used_balance"
    };

}
