import java.util.*;

// print all valid order paths e.g.: delivery after pickup given n

public class Main
{
    public static void main(String[] args) {        
        System.out.println(pickAndDeliveryPermutation(2));
    }
    
    public static List<List<String>> pickAndDeliveryPermutation(int n) {
        List<String> example = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            example.add("P" + i);
            example.add("D" + i);
        }
        
        List<List<String>> result = new ArrayList<>();
        boolean picked[] = new boolean[n+1];
        dfs(0, n, picked, example, result);
        return result;
    }
    
    private static void dfs(int index, int n, boolean[] picked, List<String> curList, List<List<String>> result) {
        if (index == n * 2) {
           result.add(new ArrayList<>(curList));
           return;
        }
        
        for (int i = index; i < n * 2; i++) {
            // Try to swap curList[index] with curList[i] if possible.
            String toSwap = curList.get(i);
            int orderIndex = toSwap.charAt(1) - '0';
            System.out.println("Try to swap " + curList.get(index) + " with " + curList.get(index));
            if (toSwap.startsWith("D") && picked[orderIndex]) {
                System.out.println("Valid");
                swap(curList, index, i);
                dfs(index+1, n, picked, curList, result);
                swap(curList, index, i); // ！！！ Don't forget to swap back.
            } else if (toSwap.startsWith("P")) {
                System.out.println("Valid because it is a pickup");
                // toSwap is a pickup
                int pIndex = toSwap.charAt(1) - '0';
                picked[pIndex] = true;
                swap(curList, index, i);
                dfs(index+1, n, picked, curList, result);
                picked[pIndex] = false;
                swap(curList, index, i); // ！！！ Don't forget to swap back.
            } else {
              System.out.println("Invalid swap");  
            }
        }
    }
    
    private static void swap(List<String> list, int i, int j) {
        if (i == j) {
            return;
        }
        
        String temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
    }
}
