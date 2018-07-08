package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@Getter
//@Setter
//@NoArgsConstructor
//@RequiredArgsConstructor

// toString 会有递归死循环风险
@ToString(exclude = {"country", "addressList"})
//@EqualsAndHashCode

//@Value
@Data
@Entity
@Table(name = "city")
public class City {

    public static final String C_CITY_ID = "city_id";
    public static final String C_CITY = "city";
    public static final String C_COUNTRY_ID = "country_id";
    public static final String C_LAST_UPDATE = "last_update";

    public static final String A_CITY_ID = "cityId";
    public static final String A_CITY = "city";
    public static final String A_COUNTRY_ID = "countryId";
    public static final String A_COUNTRY = "country";
    public static final String A_LAST_UPDATE = "lastUpdate";
    public static final String A_ADDRESS_LIST = "addressList";

    @Id
    @Column(name = C_CITY_ID)
    private Integer cityId;

    @Column(name = C_CITY)
    private String city;

    // 作为简单值查询
    @Column(name = C_COUNTRY_ID)
    private Integer countryId;

    // 作为关联表对象查询, 因为同一个数据库字段映射到 两个不同的 JavaBean 字段，所以其中一个不能用来更新。
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = C_COUNTRY_ID, insertable = false, updatable = false)
    private Country country;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;

    // mappedBy 是指在 Address 实体类中有个 `city` 字段来指明关联关系, 注意：不是数据库列名
    @OneToMany(mappedBy = Address.A_CITY)
    private List<Address> addressList;

}
