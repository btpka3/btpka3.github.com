package com.tc.his.provider.dao;

import com.tc.his.api.model.Person;
import com.tc.his.api.model.PersonCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonDao extends MyBatisRepository {
    int countByCriteria(PersonCriteria example);

    int deleteByCriteria(PersonCriteria example);

    int deleteById(Long id);

    int insert(Person record);

    int insertSelective(Person record);

    List<Person> selectByCriteria(PersonCriteria example);

    Person selectById(Long id);

    int updateByCriteriaSelective(@Param("record") Person record, @Param("example") PersonCriteria example);

    int updateByCriteria(@Param("record") Person record, @Param("example") PersonCriteria example);

    int updateByIdSelective(Person record);

    int updateById(Person record);
}