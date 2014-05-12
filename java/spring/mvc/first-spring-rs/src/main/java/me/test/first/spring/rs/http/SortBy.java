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
        private static final Pattern p = Pattern.compile("\\s*[\\+-]?\\s*(\\S+)\\s*");

        private String attribute;
        private boolean descending = false;

        public Item(String attribute, boolean descending) {
            this.attribute = attribute;
            this.descending = descending;
        }

        public static Item valueOf(String value) {
            Assert.notNull(value);
            Matcher matcher = p.matcher(value);
            Assert.isTrue(matcher.matches(), "Invalid sort item string");
            boolean descending = value.trim().startsWith("-");
            String attribute = matcher.group(1);
            return new Item(attribute, descending);
        }

        public String getAttribute() {
            return attribute;
        }
        public boolean isDescending() {
            return descending;
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

    public SortBy(List<Item> items) {
        super();
        this.items.addAll(items);
    }

    public static SortBy valueOf(String value) {
        Assert.notNull(value);
        String[] sortCols = value.split(",");
        List<Item> items = new ArrayList<Item>();

        for (String sortCol : sortCols) {
            items.add(Item.valueOf(sortCol));
        }
        return new SortBy(items);
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

    public List<Item> getItems() {
        return this.items;
    }

    public static void main(String[] args) {
        System.out.println(SortBy.valueOf(" +aa, -bb , +  cc  ").toString());
    }
}
