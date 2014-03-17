
package me.test.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Queue;

import javax.annotation.Resource;

import me.test.db.router.DataSourceKey;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "testTransService")
    private TestTransService testTransService;

    private UserRowMapper userRowMapper = new UserRowMapper();

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            User user = new User();
            user.setId(rs.getLong("ID"));
            user.setHospitalId(rs.getLong("HOSPITAL_ID"));
            user.setName(rs.getString("NAME"));
            user.setRemark(rs.getString("REMARK"));
            return user;
        }

    }

    @Override
    @Transactional(readOnly = true)
    @DataSourceKey("#hospitalId")
    public User selectById(Long hospitalId, Long userId) {

        return jdbcTemplate.queryForObject("SELECT ID, HOSPITAL_ID, NAME, REMARK FROM T_USER WHERE ID=?",
                new Object[] { userId }, userRowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> selectAll(Long hospitalId) {

        return jdbcTemplate.query("SELECT ID, HOSPITAL_ID, NAME, REMARK FROM T_USER WHERE HOSPITAL_ID=?",
                new Object[] { hospitalId }, userRowMapper);
    }

    @Override
    @Transactional
    public Long insert(Long hospitalId, String name, String remark) {

        Assert.notNull(hospitalId, "hospitalId must not be null");

        Long maxUserId = jdbcTemplate.queryForObject("SELECT MAX(ID) FROM T_USER WHERE HOSPITAL_ID = ?", Long.class, hospitalId);
        Long newUserId = maxUserId + 1;
        jdbcTemplate.update("INSERT INTO T_USER (ID, HOSPITAL_ID, NAME, REMARK) VALUES (? , ?, ?, ?)",
                new Object[] { newUserId, hospitalId, name, remark });
        return newUserId;
    }

    @Override
    @Transactional
    public void updateById(Long hospitalId, Long userId, String remark) {

        jdbcTemplate.update("UPDATE T_USER SET   REMARK=? WHERE ID=?",
                new Object[] { remark, userId });
    }

    @Override
    @Transactional
    public void deleteById(Long hospitalId, Long userId) {

        jdbcTemplate.update("DELETE FROM T_USER WHERE ID=?", new Object[] { userId });
    }

    // -------------------------------------------------------

    @Override
    public void multiUpdate(Queue<UpdateRecord> leftRecs) {

        UpdateRecord nextRec = leftRecs.poll();
        if (nextRec == null) {
            return;
        }

        // 递归调用

        // 由于Spring AOP不支持同一个类内部的方法1调用被AOP的方法2,
        // 也不支持被AOP的方法的递归自调用,
        // 因此将需要事务控制的方法定义在其他的类中。
        switch (nextRec.getPropagation()) {
        case REQUIRES_NEW:
            testTransService.multiUpdateRequiresNewTrans(nextRec.getHospitalId(), nextRec, leftRecs);
            break;
        default:
            testTransService.multiUpdateRequiredTrans(nextRec.getHospitalId(), nextRec, leftRecs);
        }

    }

    @Override
    public void multiUpdate(Long hospitalId, UpdateRecord curRec, Queue<UpdateRecord> leftRecs) {

        // 更新
        jdbcTemplate.update("UPDATE T_USER SET REMARK=? WHERE ID=?",
                new Object[] { curRec.getRemark(), curRec.getUserId() });

        UpdateRecord nextRec = leftRecs.poll();

        // 退出递归
        if (nextRec == null) {
            return;
        }

        // 递归调用
        switch (nextRec.getPropagation()) {
        case REQUIRES_NEW:
            testTransService.multiUpdateRequiresNewTrans(nextRec.getHospitalId(), nextRec, leftRecs);
            break;
        default:
            testTransService.multiUpdateRequiredTrans(nextRec.getHospitalId(), nextRec, leftRecs);
        }

        // 更新状态
        if (!curRec.isSucceed()) {
            throw new RuntimeException("Failed update at " + curRec.getI() + " : " + curRec);
        }
    }

}
