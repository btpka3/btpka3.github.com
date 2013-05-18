package me.test.first.spring.rs.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class SortBy implements Serializable {

    public static class Item implements Serializable {

        private static final long serialVersionUID = 1L;
        private static final Pattern p = Pattern.compile("\\s*([\\+-])\\s*(\\S+)\\s*");

        private String attribute;
        private boolean descending = false;

        public Item() {
        };

        public Item(String str) {
            Assert.notNull(str);
            Matcher matcher = p.matcher(str);
            Assert.isTrue(matcher.matches(), "Invalid sort item string");

            this.attribute = matcher.group(2);
            this.descending = "+".equals(matcher.group(1)) ? false : true;
        }

        public Item(String attribute, boolean descending) {
            this.attribute = attribute;
            this.descending = descending;
        }
        public String getAttribute() {
            return attribute;
        }
        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }
        public boolean isDescending() {
            return descending;
        }
        public void setDescending(boolean descending) {
            this.descending = descending;
        }

        @Override
        public String toString() {
            String str = "+";
            if (this.descending) {
                str = "-";
            }
            return str + this.attribute;
        }
    }

    private static final long serialVersionUID = 1L;
    private final List<Item> items = new ArrayList<Item>();

    public SortBy() {
        super();
    }

    public SortBy(String sortValueStr) {
        super();

        Assert.notNull(sortValueStr);
        String[] sortCols = sortValueStr.split(",");
        for (String sortCol : sortCols) {
            items.add(new Item(sortCol));
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (Item item : this.items) {
            buf.append(item.toString());
            buf.append(",");
        }
        if (buf.length() > 1) {
            buf.setLength(buf.length() - 1);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(new SortBy(" +aa, -bb , +  cc  ").toString());
    }
}
