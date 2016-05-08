import groovy.json.JsonSlurper

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by zll on 3/14/16.
 */
class Main {

    static foundPassed = false
    // 24: 000001014040120120201022
    static void main(String[] args) {

        def jsonStr = """{"level":20,"modu":"2","map":["111011","001110","001000","110110","110100"],"pieces":["X.X.,XXX.,..XX,..X.","XX.,.X.,XXX,X..","X...,XXXX,.X..,.X..","....XX,XXXXX.,...X..,...X..","X..,X..,XXX","...X.,XXXXX,.X...,.X...","XXX,X..","XXX.,..XX,...X,..XX,..X.",".X,XX","XX,.X,.X"]}"""

        def jsonObj = new JsonSlurper().parseText(jsonStr)
        int modu = Integer.valueOf(jsonObj.modu)

        println "aaa = " + jsonObj


        println "------------------------------------------ map"
        def map = genMap(jsonObj.map)
        println printMap(map)

        def pieces = genPieces(jsonObj.pieces)
        def piecesPos = [:]
        long total = 1
        for (int i = 0; i < pieces.size(); i++) {
            println "------------------------------------------ piece : $i"
            println printMap(pieces.get(i))

            def availPos = calcAvailPos(map, pieces.get(i))
            piecesPos.put(pieces, availPos)
            println "---------------- available pos: "
            println availPos
            total *= availPos.size()
        }
        println "========================================== total : $total"
//
//
//        for (int i = 0; i < pieces.size(); i++) {
//            println "------------------------------------------ $i"
//            putOn(map, pieces.get(0), modu, 0, 0)
//            println printMap(map)
//            println "-------reverse"
//            putOff(map, pieces.get(0), modu, 0, 0)
//            println printMap(map)
//
//        }

        // 多线程
//        ExecutorService pool = Executors.newFixedThreadPool(8)
//
//        def availPos0 = calcAvailPos(map, pieces.get(0))
//        println "=============================== start @@ " + new Date()
//        def final count = new AtomicInteger()
//        availPos0.size().times { i ->
//            pool.submit {
//                def map1 = genMap(jsonObj.map)
//                def result = [] as LinkedList
//                if (calc(result, map1, modu, pieces, 0, i, i + 1)) {
//                    println "------------------------------ result $i: true : " + toResultStr(result) + " ,  i =  $i"
//                    println "=============================== end @ " + new Date()
//                    pool.shutdownNow()
//                }
//            }
//        }

        // 单线程
        def result = [] as LinkedList
        println "----------------- start at " + new Date()
        def passed = calc(result, map, modu, pieces, 0)
        println "------------------------------ result : ${toResultStr(result)}, loopCount : $cc, "
        println "----------------- end  at " + new Date()

        // REC1 : loopCount =  52095470, time :  65s,  801468/s
        // REC2 : loopCount = 327918037, time : 389s,  842976/s
//
//        Map result = [:]
//        while (true) {
//
//            map = genMap(jsonObj.map)
//
//            for (int p = 0; p < pieces.size(); p++) {
//                List piece = pieces.get(p)
//                for (int i = 0; i < mapRows - piece.get(0).size(); i++) {
//
//                }
//            }
//
//
//            if (isPassed(map)) {
//                break
//            }
//
//        }

    }

//    static def c = 0L
//
//    static List calc(String mapStr, List pieces) {
//        def result = [] as LinkedList
//
//        List map = genMap(mapStr)
//        def availPos = calcAvailPos(map, pieces.get(0))
//        for (List pos : availPos) {
//            c++
//            println "=====$c"
//            result.push(pos)
//            if (pieces.size() > 1) {
//                if (calc(result, map, pieces, 1, pos[0], pos[1])) {
//                    println "------------------------loop $c ,then found result : $result"
//                    return result
//                }
//            } else {
//                if (isPassed(map)) {
//                    return result
//                }
//            }
//        }
//        return []
//    }

//    static class T implements Runnable {
//        List result,
//
//        int i
//
//        @Override
//        void run() {
//            println "==== $i"
//            if (calc(result, map, modu, pieces, 0, i, i + 1)) {
//                println "------------------------------ result $i: true"
//                println toResultStr(result)
//            } else {
//                println "------------------------------ result $i: false"
//            }
//        }
//    }

    static toResultStr(List result) {
        StringBuilder buf = new StringBuilder();
        for (List pos : result) {
            buf.append(Integer.valueOf(pos[0]))
            buf.append(Integer.valueOf(pos[1]))
        }
        return buf.toString()
    }

    static def cc = 0L

    static boolean calc(List result, List map, int modu, List pieces, int p, int startPos = 0, int endPos = Integer.MAX_VALUE) {
        def piece = pieces.get(p)
        def availPos = calcAvailPos(map, piece)
        endPos = Math.min(availPos.size(), endPos)

        for (int i = startPos; i < Math.min(availPos.size(), endPos); i++) {
            cc++
            if (foundPassed) {
                return false;
            }
//            println "############################## p=$p, startPos=$startPos, endPos = ${endPos}"
            List pos = availPos.get(i)
            result.push(pos)
            putOn(map, piece, modu, pos[0], pos[1])
            boolean passed = false;
            if (p == (pieces.size() - 1)) {
                passed = isPassed(map)
            } else {
                passed = calc(result, map, modu, pieces, p + 1)
            }
            putOff(map, piece, modu, pos[0], pos[1])

            if (passed) {
                foundPassed = true
                return passed
            } else {
                result.pop()
            }
        }
    }

    static void printResut() {

    }

    static List<Integer> calcAvailPos(List map, List piece) {
        List result = []
        int mapRows = map.size()
        int mapCols = map.get(0).size()
        int picRows = piece.size()
        int picCols = piece.get(0).size()

        for (int i = 0; i <= mapRows - picRows; i++)
            for (int j = 0; j <= mapCols - picCols; j++) {
                result.add([i, j])
            }
        return result
    }

    //
    @Deprecated
    static void checkModo(List map, final modu) {
        for (def i = 0; i < map.size(); i++) {
            List row = map.get(i)
            for (def j = 0; j < row.size(); j++) {
                if (row.get(j) == modu) {
                    row.set(j, 0)
                }
            }
        }
    }

    static void putOn(List map, List piece, int modu, final int i, final int j) {

        List mapRow = map.get(0)


        for (int ii = 0; ii < piece.size(); ii++) {
            List pieceRow = piece.get(ii);
            for (int jj = 0; jj < pieceRow.size(); jj++) {
                mapRow = map.get(i + ii)
                def oldValue = mapRow.get(j + jj)
                def newValue = oldValue + pieceRow.get(jj)
                if (newValue == modu) {
                    newValue = 0
                }
                mapRow.set(j + jj, newValue)
            }
        }
    }


    static void putOff(List map, List piece, final int modu, final int i, final int j) {

        List mapRow = map.get(0)
        for (int ii = 0; ii < piece.size(); ii++) {
            List pieceRow = piece.get(ii);
            for (int jj = 0; jj < pieceRow.size(); jj++) {
                mapRow = map.get(i + ii)
                def oldValue = mapRow.get(j + jj)
                def newValue = oldValue - pieceRow.get(jj)
                if (newValue < 0) {
                    newValue += modu
                }
                mapRow.set(j + jj, newValue)
            }
        }
    }

    static StringBuilder buf = new StringBuilder()

    static String printMap(List map) {
        buf.setLength(0)

        for (def i = 0; i < map.size(); i++) {
            List row = map.get(i)
            for (def j = 0; j < row.size(); j++) {
                buf.append(Integer.toString(row.get(j)))
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    static boolean isPassed(List map) {
        for (def i = 0; i < map.size(); i++) {
            List row = map.get(i)
            for (def j = 0; j < row.size(); j++) {
                if (row.get(j) != 0) return false
            }
        }
        return true
    }

    static List<List<Integer>> genMap(arr) {

        def resultList = []
        for (String row : arr) {
            def rowList = []
            for (char c : row.chars) {
                rowList.add(Integer.valueOf(c.toString()))
            }
            resultList.add(rowList)
        }
        return resultList
    }

    static List<List<List<Integer>>> genPieces(arr) {

        def pieceList = []
        for (String row : arr) {
            def piece = []
            def pieceRow = []
            piece.add(pieceRow)
            for (char c : row.chars) {
                switch (c) {
                    case 'X':
                        pieceRow.add(1);
                        break;
                    case ',':
                        pieceRow = []
                        piece.add(pieceRow)
                        break;
                    case '.':
                        pieceRow.add(0);
                        break;
                    default:
                        break;
                }
            }
            pieceList.add(piece)
        }
        return pieceList
    }
}