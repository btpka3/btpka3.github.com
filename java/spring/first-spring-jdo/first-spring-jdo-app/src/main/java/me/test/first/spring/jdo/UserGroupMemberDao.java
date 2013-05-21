package me.test.first.spring.jdo;

import java.util.List;

import me.test.first.spring.jdo.entity.UserGroupMember;

public interface UserGroupMemberDao {

    List<UserGroupMember> selectByUser(long uid);

    List<UserGroupMember> selectByUserGroup(long gid);

    void insert(UserGroupMember userGroupMember);

    void delete(long uid, long gid);

}
