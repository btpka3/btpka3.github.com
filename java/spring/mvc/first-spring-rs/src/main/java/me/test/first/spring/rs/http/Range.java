package me.test.first.spring.rs.http;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
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
//for http request header
public class Range implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern p = Pattern.compile("\\s*items\\s*=\\s*(\\d*)\\s*-\\s*(\\d*)?\\s*",
            Pattern.CASE_INSENSITIVE);

    // required
    private int start = 0;

    // optional : -1 means to end
    private int end = -1;

    public Range() {
        super();
    }

    public Range(String headStr) {
        super();

        Assert.notNull(headStr);
        Matcher matcher = p.matcher(headStr);
        Assert.isTrue(matcher.matches(), "Invalid http Range header");
        String startStr = matcher.group(1);
        String endStr = matcher.group(2);

        this.start = Integer.valueOf(startStr);
        if (StringUtils.isNotEmpty(endStr)) {
            this.end = Integer.valueOf(endStr);
        }
    }
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        if (this.end > -0) {
            return String.format("items=%d-%d", this.start, this.end);
        }
        return String.format("items=%d-", this.start);
    }

    public static void main(String[] args) {
        System.out.println(new Range(" items  =  4  -  9   ").toString());
        System.out.println(new Range(" items  =  4  -     ").toString());
    }
}
