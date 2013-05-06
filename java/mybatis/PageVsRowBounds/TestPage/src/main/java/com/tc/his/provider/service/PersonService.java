package com.tc.his.provider.service;

import java.util.List;

import com.tc.his.api.model.Person;

public interface PersonService {
    List<Person> getList(int offset, int count);
}
