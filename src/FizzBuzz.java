import java.util.*;

public class FizzBuzz {
    public static void main(String[] args) {
        Hashtable<Integer, String> nums = new Hashtable<>();

        nums.put(3, "Fizz");
        nums.put(5, "Buzz");

        Set<Integer> numKeys = nums.keySet();

        List<Integer> numbersList = new ArrayList<>(numKeys);

        Collections.sort(numbersList);

        numKeys = new LinkedHashSet<>(numbersList);

        for (int i = 1; i <= 100; i++) {
            StringBuilder output = new StringBuilder();

            for (Integer key: numKeys) {
                if (i % key == 0) {
                    output.append(nums.get(key));
                }
            }

            if (output.toString().equals("")) {
                output.append(i);
            }

            System.out.println(output);
        }
    }
}
