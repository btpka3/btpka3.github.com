package me.test.first.spring.rs.http;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

// for http response header
public class ContentRange implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern p = Pattern.compile(
            "\\s*items\\s*(\\d*)\\s*-\\s*(\\d*)\\s*/\\s*(\\d*)\\s*",
            Pattern.CASE_INSENSITIVE);

    private int start = 0;
    private int end = 0;
    private int total = 0;

    public ContentRange() {
        super();
    }

    public ContentRange(String headStr) {
        super();

        Assert.notNull(headStr);
        Matcher matcher = p.matcher(headStr);
        Assert.isTrue(matcher.matches(), "Invalid http Content-Range header");
        this.start = Integer.valueOf(matcher.group(1));
        this.end = Integer.valueOf(matcher.group(2));
        this.total = Integer.valueOf(matcher.group(3));
    }

    public ContentRange(int start, int end, int total) {
        this.start = start;
        this.end = end;
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("items %d-%d/%d", this.start, this.end, this.total);
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static void main(String[] args) {
        ContentRange c = new ContentRange("    items   4  -  9 / 50  ");
        System.out.println(c.toString());
    }
}
