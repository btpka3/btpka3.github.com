package com.tc.his.provider.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tc.his.api.model.Person;
import com.tc.his.api.model.PersonCriteria;
import com.tc.his.provider.dao.PersonDao;
import com.tc.his.provider.service.PersonService;

@Component("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao dao;

    public List<Person> getList(int offset, int count) {
        PersonCriteria c = new PersonCriteria();
        return dao.selectByCriteriaWithRowbounds(c, new RowBounds(offset, count));
    }

}
