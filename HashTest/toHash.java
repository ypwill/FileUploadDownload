package HashTest;

/**
 * @author WYP
 * @date 2020-10-07 19:31
 */
public class toHash {
    public static void main(String[] args) {
        String str = "abccddsdw";

        long hash = Hash(str);

//        System.out.println(hash);
        System.err.println(hash);

    }

    public static int Hash(String str) {
        int hash= 0;
        char val[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            hash = 10 * hash + val[i];
 //           System.out.println(hash);
//            System.out.println(val[i]);
        }
        return hash;
    }

}
