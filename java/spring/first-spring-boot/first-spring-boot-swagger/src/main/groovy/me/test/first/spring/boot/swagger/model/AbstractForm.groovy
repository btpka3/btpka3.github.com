package me.test.first.spring.boot.swagger.model

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * 参考:
 *
 * @See org.springframework.http.HttpHeaders
 */
abstract class AbstractForm implements MultiValueMap<String, String>, Serializable {


    protected final Map<String, List<String>> map = new LinkedMultiValueMap<>();


    // MultiValueMap implementation

    /**
     * Return the first header value for the given header name, if any.
     * @param headerName the header name
     * @return the first header value, or {@code null} if none
     */
    @Override
    public String getFirst(String headerName) {
        List<String> headerValues = this.map.get(headerName);
        return (headerValues != null ? headerValues.get(0) : null);
    }

    /**
     * Add the given, single header value under the given name.
     * @param headerName the header name
     * @param headerValue the header value
     * @throws UnsupportedOperationException if adding headers is not supported
     * @see #put(String, List)
     * @see #set(String, String)
     */
    @Override
    public void add(String headerName, String headerValue) {
        List<String> headerValues = this.map.get(headerName);
        if (headerValues == null) {
            headerValues = new LinkedList<String>();
            this.map.put(headerName, headerValues);
        }
        headerValues.add(headerValue);
    }

    /**
     * Set the given, single header value under the given name.
     * @param headerName the header name
     * @param headerValue the header value
     * @throws UnsupportedOperationException if adding headers is not supported
     * @see #put(String, List)
     * @see #add(String, String)
     */
    @Override
    public void set(String headerName, String headerValue) {
        List<String> headerValues = new LinkedList<String>();
        headerValues.add(headerValue);
        this.map.put(headerName, headerValues);
    }

    @Override
    public void setAll(Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<String, String> toSingleValueMap() {
        LinkedHashMap<String, String> singleValueMap = new LinkedHashMap<String, String>(this.map.size());
        for (Map.Entry<String, List<String>> entry : this.map.entrySet()) {
            singleValueMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return singleValueMap;
    }

    // Map implementation
    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return this.map.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        return this.map.put(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> map) {
        this.map.putAll(map);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return this.map.values();
    }

    @Override
    public Set<Map.Entry<String, List<String>>> entrySet() {
        return this.map.entrySet();
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractForm)) {
            return false;
        }
        AbstractForm otherForm = (AbstractForm) other;
        return this.map.equals(otherForm.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

}
