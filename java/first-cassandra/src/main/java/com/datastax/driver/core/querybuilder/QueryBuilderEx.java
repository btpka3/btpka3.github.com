package com.datastax.driver.core.querybuilder;

public class QueryBuilderEx {
    public static Clause contains(String name, Object value) {
        return new Clause.SimpleClause(name, " contains ", value);
    }
}
