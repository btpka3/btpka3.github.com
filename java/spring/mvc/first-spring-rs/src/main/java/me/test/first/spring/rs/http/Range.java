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

    // required (inclusive) : must not be null or minus
    private Integer start;

    // optional (inclusive) : null means to end
    private Integer end;

    public Range() {
        super();
    }

    public Range(Integer start, Integer end) {
        Assert.notNull(start);
        Assert.isTrue(start >= 0, "Range start value must be equal or bigger than 0.");
        if (end != null) {
            Assert.isTrue(end >= start,
                    "Range end value must be null, or equal or bigger than start value.");
        }
        this.start = start;
        this.end = end;

    }

    public Range(Integer start) {
        this(start, null);
    }

    public static Range valueOf(String value) {

        Assert.notNull(value);
        Matcher matcher = p.matcher(value);
        Assert.isTrue(matcher.matches(), "Invalid http Range header");
        String startStr = matcher.group(1);
        String endStr = matcher.group(2);

        Integer start = Integer.valueOf(startStr);

        Integer end = null;
        if (StringUtils.isNotEmpty(endStr)) {
            end = Integer.valueOf(endStr);
        }
        return new Range(start, end);
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    @Override
    public String toString() {
        if (this.end == null) {
            return String.format("items=%d-", this.start);
        }
        return String.format("items=%d-%d", this.start, this.end);
    }

    public static void main(String[] args) {
        System.out.println(Range.valueOf(" items  =  4  -  9   ").toString());
        System.out.println(Range.valueOf(" items  =  4  -     ").toString());
    }
}
