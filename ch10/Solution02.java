/**
 *  @algorithms:
 *  1). We only need to rearrange all the anagrams together. We do not need
 *      to give a strict sorting.
 *  2). We can sort the characters of a string to a particular order, such as
 *      ascending order. Then all the anagrams will have the same sorting
 *      result.
 *  3). We then put all the strings that map to the same key into the same
 *      list.
 *  4). Finally, we put all the strings in the same list back to the adjacent
 *      positions.
 */


import java.util.*;


public class Solution02 {

    // Sort the chars in the string.
    private static String sort_chars(String s) {
        char [] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    /**
     *  @brief: Sort the strings to collect all the anagrams to the adjacent
     *      positions.
     *  @param: [in/out] strings: The initial and result string array.
     *  @return: N/A
     */
    private static void sort(String[] strings) {
        HashMap<String, ArrayList<String> > mapList = new HashMap<>();

        for (String str : strings) {
            String key = sort_chars(str);
            ArrayList<String> str_list = mapList.get(key);
            if (str_list == null) {
                str_list = new ArrayList<String>();
                mapList.put(key, str_list);
            }
            str_list.add(str);
        }

        int index = 0;
        for (String key : mapList.keySet()) {
            ArrayList<String> str_list = mapList.get(key);
            for (String str : str_list) {
                strings[index] = str;
                index++;
            }
        }
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            String [] strings = {
                "abc", "bca", "xyz", "yzx", "xzy", "cba"
            };
            sort(strings);
            for (int i = 0; i < strings.length; ++i) {
                System.out.println(strings[i]);
            }
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
