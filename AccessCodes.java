/*
* Given an array of elements return the number of tuples that satisfy the
* following condition:
*
* Given 3 elements in the array, a, b ,c
* a is divisible by b and b is divisible by c
* */
package GoogleFooBar;

public class AccessCodes {
    public static int solution(int[] l) {

        int[] arr = new int[l.length];

        int count = 0;

        for(int i = 0; i < l.length; i++) {

            for(int j = i + 1; j < l.length; j++) {
                if(l[j] % l[i] == 0) {
                    arr[j]++;
                    count += arr[i];
                }
            }
        }

        return count;

    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{6,5,4,3,2,1}));
    }
}
