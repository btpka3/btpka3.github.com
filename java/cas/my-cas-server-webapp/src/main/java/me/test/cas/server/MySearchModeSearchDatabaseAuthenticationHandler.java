package me.test.cas.server;

import javax.validation.constraints.NotNull;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @see org.springframework.security.authentication.encoding.BasePasswordEncoder
 * @see org.jasig.cas.adaptors.jdbc.SearchModeSearchDatabaseAuthenticationHandler
 * 
 * @author ZLL
 * 
 */
public class MySearchModeSearchDatabaseAuthenticationHandler extends
		AbstractJdbcUsernamePasswordAuthenticationHandler implements
		InitializingBean {

	private static final String SQL_PREFIX = "Select count('x') from ";

	@NotNull
	private String fieldUser;

	@NotNull
	private String fieldPassword;

	@NotNull
	private String tableUsers;

	private String sql;

	@Override
	protected boolean authenticateUsernamePasswordInternal(
			UsernamePasswordCredentials credentials)
			throws AuthenticationException {
		final String transformedUsername = getPrincipalNameTransformer()
				.transform(credentials.getUsername());
		final String encyptedPassword = getPasswordEncoder().encode(
				mergePasswordAndSalt(credentials.getPassword(),
						credentials.getUsername(), true));
System.out.println("==============="+transformedUsername+":"+encyptedPassword);
		final int count = getJdbcTemplate().queryForInt(this.sql,
				transformedUsername, encyptedPassword);

		return count > 0;
	}

	protected String mergePasswordAndSalt(String password, Object salt,
			boolean strict) {
		if (password == null) {
			password = "";
		}

		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1)
					|| (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException(
						"Cannot use { or } in salt.toString()");
			}
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	public void afterPropertiesSet() throws Exception {
		this.sql = SQL_PREFIX + this.tableUsers + " Where " + this.fieldUser
				+ " = ? And " + this.fieldPassword + " = ?";
	}

	/**
	 * @param fieldPassword
	 *            The fieldPassword to set.
	 */
	public final void setFieldPassword(final String fieldPassword) {
		this.fieldPassword = fieldPassword;
	}

	/**
	 * @param fieldUser
	 *            The fieldUser to set.
	 */
	public final void setFieldUser(final String fieldUser) {
		this.fieldUser = fieldUser;
	}

	/**
	 * @param tableUsers
	 *            The tableUsers to set.
	 */
	public final void setTableUsers(final String tableUsers) {
		this.tableUsers = tableUsers;
	}
}
