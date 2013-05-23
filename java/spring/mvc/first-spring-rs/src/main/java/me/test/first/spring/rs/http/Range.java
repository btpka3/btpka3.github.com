package me.test.first.spring.rs.http;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 *
 * <ul>
 * <li>{@code Range: items=0-9} : 前10条记录</li>
 * <li>{@code Range: items=0-} : 全部记录</li>
 * <li>{@code Range: items=10-} : 从第11条记录开始到末尾</li>
 * </ul>
 *
 *
 * @author zhangliangliang
 *
 */
// for http request header
public class Range implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern p = Pattern.compile("\\s*items\\s*=\\s*(\\d*)\\s*-\\s*(\\d*)?\\s*",
            Pattern.CASE_INSENSITIVE);

    // required (inclusive)
    private int start = 0;

    // optional (inclusive) : -1 means to end
    private int end = -1;

    public Range() {
        super();
    }

    public Range(int start, int end) {
        this.start = start;
        this.end = end;

        Assert.isTrue(this.start >= 0, "Range start value must be equal or bigger than 0.");
        Assert.isTrue(this.end < 0 || this.end >= this.start,
                "Range end value must be minus, or equal or bigger than start value.");
    }

    public Range(int start) {
        this(start, -1);
    }

    public static Range valueOf(String value) {

        Assert.notNull(value);
        Matcher matcher = p.matcher(value);
        Assert.isTrue(matcher.matches(), "Invalid http Range header");
        String startStr = matcher.group(1);
        String endStr = matcher.group(2);

        int start = Integer.valueOf(startStr);

        int end = -1;
        if (StringUtils.isNotEmpty(endStr)) {
            end = Integer.valueOf(endStr);
        }
        return new Range(start, end);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        if (this.end > -0) {
            return String.format("items=%d-%d", this.start, this.end);
        }
        return String.format("items=%d-", this.start);
    }

    public static void main(String[] args) {
        System.out.println(Range.valueOf(" items  =  4  -  9   ").toString());
        System.out.println(Range.valueOf(" items  =  4  -     ").toString());
    }
}
