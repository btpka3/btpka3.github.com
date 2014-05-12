
package me.test.service;

import java.util.Queue;

/**
 * 该类仅仅以测试事务为目的。
 */
public interface TestTransService {

    void multiUpdateRequiredTrans(Long hospitalId, UpdateRecord curRec, Queue<UpdateRecord> leftRecs);

    void multiUpdateRequiresNewTrans(Long hospitalId, UpdateRecord curRec, Queue<UpdateRecord> leftRecs);
}
