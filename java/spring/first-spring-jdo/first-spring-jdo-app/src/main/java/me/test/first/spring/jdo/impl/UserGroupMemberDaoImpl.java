package me.test.first.spring.jdo.impl;

import java.util.Arrays;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import me.test.first.spring.jdo.UserGroupMemberDao;
import me.test.first.spring.jdo.entity.UserGroup;
import me.test.first.spring.jdo.entity.UserGroupMember;

import org.springframework.transaction.annotation.Transactional;

public class UserGroupMemberDaoImpl implements UserGroupMemberDao {

    private PersistenceManagerFactory pmFactory;

    public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
        this.pmFactory = pmf;
    }

    @SuppressWarnings("unchecked")
    public List<UserGroupMember> selectByUser(long uid) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            Query query = pm
                    .newQuery(UserGroupMember.class, "uid = query_uid ");
            query.declareParameters("Long query_uid");
            List<UserGroupMember> memberList = (List<UserGroupMember>) query
                    .execute(uid);
            return memberList;

        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<UserGroupMember> selectByUserGroup(long gid) {
        PersistenceManager pm = this.pmFactory.getPersistenceManager();
        try {
            Query query = pm
                    .newQuery(UserGroupMember.class, "gid = query_gid ");
            query.declareParameters("Long query_gid");
            List<UserGroupMember> memberList = (List<UserGroupMember>) query
                    .execute(gid);
            return memberList;
        } finally {
            pm.close();
        }
    }

    public void insert(UserGroupMember userGroupMember) {
        // TODO Auto-generated method stub

    }

    public void delete(long uid, long gid) {
        // TODO Auto-generated method stub

    }

}
