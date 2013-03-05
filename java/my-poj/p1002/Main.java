import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int count = cin.nextInt();
        boolean noDuplicates = true;
        Map<String, Integer> resultMap = new TreeMap<String, Integer>();
        for (int i = 0; i < count; i++) {
            String phoneNum = cin.next();
            String result = normalize(phoneNum);
            int c = 1;
            if (resultMap.containsKey(result)) {
                c = resultMap.get(result) + 1;
                noDuplicates = false;
            }
            resultMap.put(result, c);
        }

        for (Entry<String, Integer> entry : resultMap.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
        if (noDuplicates) {
            System.out.println("No duplicates.");
        }
    }

    public static String normalize(String phoneNum) {
        String trimedStr = phoneNum.replaceAll("-", "");
        StringBuilder builder = new StringBuilder();
        for (char c : trimedStr.toCharArray()) {
            char numChar = toNumChar(c);
            builder.append(numChar);
        }
        builder.insert(3, '-');
        return builder.toString();
    }

    public static char toNumChar(char c) {

        if ('0' <= c && c <= '9') {
            return c;
        }
        switch (c) {
        case 'A':
            ;
        case 'B':
            ;
        case 'C':
            return '2';
        case 'D':
            ;
        case 'E':
            ;
        case 'F':
            return '3';
        case 'G':
            ;
        case 'H':
            ;
        case 'I':
            return '4';
        case 'J':
            ;
        case 'K':
            ;
        case 'L':
            return '5';
        case 'M':
            ;
        case 'N':
            ;
        case 'O':
            return '6';
        case 'P':
            ;
        case 'R':
            ;
        case 'S':
            return '7';
        case 'T':
            ;
        case 'U':
            ;
        case 'V':
            return '8';
        default:
            return '9';
        }
    }
}
