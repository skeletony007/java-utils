import java.util.HashMap;
import java.util.Map;
import java.math.BigInteger;

public class HashMapUtils {

    public static HashMap<Character, String> parseMap(String mapString) {
        String[] tokens = mapString.split(" ");
        HashMap<Character, String> map = new HashMap<>();

        for (int i = 0; i < tokens.length; i += 2) {
            char key = tokens[i].charAt(0);
            String value = tokens[i + 1];
            map.put(key, value);
        }

        return map;
    }

    // public static HashMap<Character, Integer> parseMap(String mapString) {
    //     HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    //     String[] entries = mapString.split(",");
    //     for (String entry : entries) {
    //         String[] parts = entry.split("=");
    //         char key = parts[0].charAt(0);
    //         Integer value = Integer.parseInt(parts[1]);
    //         map.put(key, value);
    //     }
    //     return map;
    // }
    // public static String hashMapToLatexTable(HashMap<Character, Integer> hashMap) {
    //     StringBuilder stringBuilder = new StringBuilder();
    //     stringBuilder.append("\\begin{center}\n\\begin{tabular}{ ");
    //     for (int i = 0; i < hashMap.size(); i++) {
    //         stringBuilder.append("c ");
    //     }
    //     stringBuilder.append("}\n");
    //     for (Character key : hashMap.keySet()) {
    //         stringBuilder.append(key).append(" = ").append(hashMap.get(key)).append(", ");
    //     }
    //     stringBuilder.append("\\\\\n\\end{tabular}\n\\end{center}\n");
    //     return stringBuilder.toString();
    // }
    // public static Character getKeyFromValue(HashMap<Character, Integer> hashMap, int value) {
    //     for (Map.Entry<Character, Integer> entry : hashMap.entrySet()) {
    //         if (entry.getValue().equals(value)) {
    //             return entry.getKey();
    //         }
    //     }
    //     return null;
    // }

    public static String hashMapToLatexTable(HashMap<Character, String> hashMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\begin{center}\n\\begin{tabular}{ ");
        for (int i = 0; i < hashMap.size(); i++) {
            stringBuilder.append("c ");
        }
        stringBuilder.append("}\n");
        for (Character key : hashMap.keySet()) {
            stringBuilder.append(key).append(" = ").append(hashMap.get(key)).append(", ");
        }
        stringBuilder.append("\\\\\n\\end{tabular}\n\\end{center}\n");
        return stringBuilder.toString();
    }

    public static Character getKeyFromValue(HashMap<Character, String> hashMap, String value) {
        for (Map.Entry<Character, String> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

}