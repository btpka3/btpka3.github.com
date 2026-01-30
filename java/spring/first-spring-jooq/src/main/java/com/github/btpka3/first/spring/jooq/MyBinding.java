package com.github.btpka3.first.spring.jooq;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/14
 */
public class MyBinding implements org.jooq.Binding<Geometry, String> {

//    public MyBinding(Class g, String s){
//
//    }

    @Override
    public Converter<Geometry, String> converter() {
        return new Converter() {

            @Override
            public Object from(Object databaseObject) {
                return null;
            }

            @Override
            public Object to(Object userObject) {
                return null;
            }

            @Override
            public Class<Geometry> fromType() {
                return Geometry.class;
            }

            @Override
            public Class<String> toType() {
                return String.class;
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<String> ctx) throws SQLException {
        if (ctx.render().paramType() == ParamType.INLINED)
            ctx.render().visit(DSL.inline(ctx.convert(converter()).value())).sql("::json");
        else
            ctx.render().sql(ctx.variable()).sql("::json");
    }

    @Override
    public void register(BindingRegisterContext<String> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<String> ctx) throws SQLException {
        Geometry g = ctx.convert(converter()).value();
        ctx.statement().setString(ctx.index(), g == null ? null : null);
    }

    @Override
    public void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(null);
    }

    @Override
    public void get(BindingGetStatementContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(null);
    }

    @Override
    public void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
