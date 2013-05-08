package me.test.filter;

import java.util.ArrayList;
import java.util.List;

public class MatchItem {

    private String url;
    private final List<String> allowedValues = new ArrayList<String>();
    private final List<String> ommitedValues = new ArrayList<String>();

    public boolean isAllowed(String value) {

        for (String ommitedValue : ommitedValues) {
            if (value.matches(ommitedValue)) {
                return true;
            }
        }

        for (String allowedValue : allowedValues) {
            if (value.matches(allowedValue)) {
                return true;
            }
        }

        return false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(List<String> allowedValues) {
        this.allowedValues.clear();
        this.allowedValues.addAll(allowedValues);
    }

    public List<String> getOmmitedValues() {
        return ommitedValues;
    }

    public void setOmmitedValues(List<String> ommitedValues) {
        this.ommitedValues.clear();
        this.ommitedValues.addAll(ommitedValues);
    }

}
