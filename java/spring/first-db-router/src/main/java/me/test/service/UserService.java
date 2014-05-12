
package me.test.service;

import java.util.List;
import java.util.Queue;

public interface UserService {


    User selectById(Long hospitalId, Long userId);

    List<User> selectAll(Long hospitalId);

    /**
     * 新增一个用户，并返回用户ID。
     */
    Long insert(Long hospitalId, String name, String remark);

    void updateById(Long hospitalId, Long userId, String remark);

    void deleteById(Long hospitalId, Long userId);

    // ---------------------------------------------------

    void multiUpdate(   Queue<UpdateRecord> leftRecs) ;
      void multiUpdate(Long hospitalId, UpdateRecord curRec, Queue<UpdateRecord> leftRecs) ;
//
//    void reset();
//
//    // ---------------------------------------------------
//
//    void crossDbUpdateJoinTransAllSucceed();
//
//    void crossDbUpdateJoinTransInnerFailed();
//
//    void crossDbUpdateJoinTransOuterFailed();
//
//    // ---------------------------------------------------
//    void crossDbUpdateNewTransAllSucceed();
//
//    void crossDbUpdateNewTransInnerFailed();
//
//    void crossDbUpdateNewTransOuterFailed();

}
