package me.test.first.chanpay.api;

import org.springframework.util.*;

import java.io.*;
import java.util.*;


/**
 * @see org.springframework.http.HttpHeaders
 */
public abstract class AbstractNamedMultiValueMap
        implements MultiValueMap<String, String>, Serializable {

    private static final long serialVersionUID = 1L;


    private final Map<String, List<String>> headers;


    /**
     * Constructs a new, empty instance of the {@code HttpHeaders} object.
     */
    public AbstractNamedMultiValueMap() {
        this(new LinkedHashMap<String, List<String>>());
    }

    /**
     * Private constructor that can create read-only {@code HttpHeader} instances.
     */
    private AbstractNamedMultiValueMap(Map<String, List<String>> headers) {
        Assert.notNull(headers, "'headers' must not be null");

        this.headers = headers;

    }

    /**
     * Retrieve a combined result from the field values of multi-valued headers.
     * @param headerName the header name
     * @return the combined result
     * @since 4.3
     */
    protected String getFieldValues(String headerName) {
        List<String> headerValues = get(headerName);
        return (headerValues != null ? toCommaDelimitedString(headerValues) : null);
    }

    /**
     * Turn the given list of header values into a comma-delimited result.
     * @param headerValues the list of header values
     * @return a combined result with comma delimitation
     */
    protected String toCommaDelimitedString(List<String> headerValues) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<String> it = headerValues.iterator(); it.hasNext(); ) {
            String val = it.next();
            builder.append(val);
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }


    // MultiValueMap implementation

    /**
     * Return the first header value for the given header name, if any.
     *
     * @param headerName the header name
     * @return the first header value, or {@code null} if none
     */
    @Override
    public String getFirst(String headerName) {
        List<String> headerValues = this.headers.get(headerName);
        return (headerValues != null ? headerValues.get(0) : null);
    }

    /**
     * Add the given, single header value under the given name.
     *
     * @param headerName  the header name
     * @param headerValue the header value
     * @throws UnsupportedOperationException if adding headers is not supported
     * @see #put(String, List)
     * @see #set(String, String)
     */
    @Override
    public void add(String headerName, String headerValue) {
        List<String> headerValues = this.headers.get(headerName);
        if (headerValues == null) {
            headerValues = new LinkedList<String>();
            this.headers.put(headerName, headerValues);
        }
        headerValues.add(headerValue);
    }

    /**
     * Set the given, single header value under the given name.
     *
     * @param headerName  the header name
     * @param headerValue the header value
     * @throws UnsupportedOperationException if adding headers is not supported
     * @see #put(String, List)
     * @see #add(String, String)
     */
    @Override
    public void set(String headerName, String headerValue) {
        List<String> headerValues = new LinkedList<String>();
        headerValues.add(headerValue);
        this.headers.put(headerName, headerValues);
    }

    @Override
    public void setAll(Map<String, String> values) {
        for (Entry<String, String> entry : values.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<String, String> toSingleValueMap() {
        LinkedHashMap<String, String> singleValueMap = new LinkedHashMap<String, String>(this.headers.size());
        for (Entry<String, List<String>> entry : this.headers.entrySet()) {
            singleValueMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return singleValueMap;
    }


    // Map implementation

    @Override
    public int size() {
        return this.headers.size();
    }

    @Override
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.headers.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return this.headers.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        return this.headers.put(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        return this.headers.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> map) {
        this.headers.putAll(map);
    }

    @Override
    public void clear() {
        this.headers.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.headers.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return this.headers.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return this.headers.entrySet();
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractNamedMultiValueMap)) {
            return false;
        }
        AbstractNamedMultiValueMap otherHeaders = (AbstractNamedMultiValueMap) other;
        return this.headers.equals(otherHeaders.headers);
    }

    @Override
    public int hashCode() {
        return this.headers.hashCode();
    }

    @Override
    public String toString() {
        return this.headers.toString();
    }
}
