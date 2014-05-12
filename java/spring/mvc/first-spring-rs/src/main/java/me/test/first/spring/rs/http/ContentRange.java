package me.test.first.spring.rs.http;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

// start from 0
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

    public ContentRange(int start, int end, int total) {
        this.start = start;
        this.end = end;
        this.total = total;

        Assert.isTrue(this.start >= 0, "Content-Range start value must equal or bigger than 0");
        Assert.isTrue(this.end >= this.start, "Content-Range end value must equal or bigger than start value");
        Assert.isTrue(this.end < this.total, "Content-Range end value must less than total value");
    }

    public static ContentRange valueOf(String value) {

        Assert.notNull(value);
        Matcher matcher = p.matcher(value);
        Assert.isTrue(matcher.matches(), "Invalid http Content-Range header");
        int start = Integer.valueOf(matcher.group(1));
        int end = Integer.valueOf(matcher.group(2));
        int total = Integer.valueOf(matcher.group(3));

        return new ContentRange(start, end, total);
    }

    @Override
    public String toString() {
        return String.format("items %d-%d/%d", this.start, this.end, this.total);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTotal() {
        return total;
    }

    public static void main(String[] args) {
        ContentRange c = ContentRange.valueOf("    items   4  -  9 / 50  ");
        System.out.println(c.toString());
    }
}
