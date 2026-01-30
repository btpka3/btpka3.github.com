package me.test.jdk.java.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author dangqian.zll
 * @date 2023/3/28
 */
public class EmptyPreparedStatement implements PreparedStatement {

    private SQLException NOT_IMPLEMENTED = new SQLException("not implemented.");
    private SQLException TODO = new SQLException("TODO.");

    private String sql;
    private String[] arr;


    /**
     * 限制：注释里不要包含 "?"
     *
     * @param sql
     */
    public EmptyPreparedStatement(String sql) {
        this.sql = sql;

        this.arr = sql.split("\\?");

    }


    public void getSqlWithParameters() {

    }


    // -------------------------

    @Override

    public void setNull(int parameterIndex, int sqlType) throws SQLException {

    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {

    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {

    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {

    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {

    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {

    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {

    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {

    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {

    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {

    }


    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {

    }


    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {

    }


    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {

    }


    // ----------------------


    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw NOT_IMPLEMENTED;
    }


    @Override
    public void clearParameters() throws SQLException {
        throw NOT_IMPLEMENTED;

    }


    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int executeUpdate() throws SQLException {
        throw NOT_IMPLEMENTED;
    }


    @Override
    public boolean execute() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void addBatch() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void close() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void cancel() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void clearBatch() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw NOT_IMPLEMENTED;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw NOT_IMPLEMENTED;
    }
}
