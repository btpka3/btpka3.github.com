package me.test.spring.data.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "xxx")
public class User {

    @PrimaryKey
    private Pk pk;

    @Column(value = "name")
    private String name;

    @Column(value = "tags")
    private Set<String> tags;

    @Column(value = "addrs")
    private List<String> addrs;

    @Column(value = "extra")
    private Map<String, String> extra;

    @Column(value = "memo")
    private String memo;

    public Pk getPk() {
        return pk;
    }

    public void setPk(Pk pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<String> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<String> addrs) {
        this.addrs = addrs;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "User [pk=" + pk + ", name=" + name + ", tags=" + tags + ", addrs=" + addrs + ", extra=" + extra
                + ", memo=" + memo + "]";
    }

    @PrimaryKeyClass
    public static class Pk implements Serializable {

        private static final long serialVersionUID = 1L;

        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String id;

        @PrimaryKeyColumn(name = "sid", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
        private String sid;

        public Pk() {
            super();
        }

        public Pk(String id, String sid) {
            super();
            this.id = id;
            this.sid = sid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            result = prime * result + ((sid == null) ? 0 : sid.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pk other = (Pk) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            if (sid == null) {
                if (other.sid != null)
                    return false;
            } else if (!sid.equals(other.sid))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Key [id=" + id + ", sid=" + sid + "]";
        }
    }
}
