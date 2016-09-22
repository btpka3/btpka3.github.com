package my.grails3

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UserRoleGroup implements Serializable {

	private static final long serialVersionUID = 1

	User user
	RoleGroup roleGroup

	@Override
	boolean equals(other) {
		if (other instanceof UserRoleGroup) {
			other.userId == user?.id && other.roleGroupId == roleGroup?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (roleGroup) builder.append(roleGroup.id)
		builder.toHashCode()
	}

	static UserRoleGroup get(long userId, long roleGroupId) {
		criteriaFor(userId, roleGroupId).get()
	}

	static boolean exists(long userId, long roleGroupId) {
		criteriaFor(userId, roleGroupId).count()
	}

	private static DetachedCriteria criteriaFor(long userId, long roleGroupId) {
		UserRoleGroup.where {
			user == User.load(userId) &&
			roleGroup == RoleGroup.load(roleGroupId)
		}
	}

	static UserRoleGroup create(User user, RoleGroup roleGroup) {
		def instance = new UserRoleGroup(user: user, roleGroup: roleGroup)
		instance.save()
		instance
	}

	static boolean remove(User u, RoleGroup rg) {
		if (u != null && rg != null) {
			UserRoleGroup.where { user == u && roleGroup == rg }.deleteAll()
		}
	}

	static int removeAll(User u) {
		u == null ? 0 : UserRoleGroup.where { user == u }.deleteAll()
	}

	static int removeAll(RoleGroup rg) {
		rg == null ? 0 : UserRoleGroup.where { roleGroup == rg }.deleteAll()
	}

	static constraints = {
		user validator: { User u, UserRoleGroup ug ->
			if (ug.roleGroup?.id) {
				UserRoleGroup.withNewSession {
					if (UserRoleGroup.exists(u.id, ug.roleGroup.id)) {
						return ['userGroup.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['roleGroup', 'user']
		version false
	}
}
