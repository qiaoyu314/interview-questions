// https://leetcode.com/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/


// You are given a 0-indexed binary string s which represents a sequence of train cars. s[i] = '0' denotes that the ith car does not contain illegal goods and s[i] = '1' denotes that the ith car does contain illegal goods.

// As the train conductor, you would like to get rid of all the cars containing illegal goods. You can do any of the following three operations any number of times:

// Remove a train car from the left end (i.e., remove s[0]) which takes 1 unit of time.
// Remove a train car from the right end (i.e., remove s[s.length - 1]) which takes 1 unit of time.
// Remove a train car from anywhere in the sequence which takes 2 units of time.
// Return the minimum time to remove all the cars containing illegal goods.

// Note that an empty sequence of cars is considered to have no cars containing illegal goods.

class Solution {
    public int minimumTime(String s) {
        char[] c = s.toCharArray();
        
        // dpL[i] is the min cost of removing [1st, ith] "1" from the left side. 
        // We have two choise:
        // 1) Removing all cars on the left. 
        // 2) Removing it from the middle. 
        List<Integer> dpL = new ArrayList<>();
        dpL.add(0);
        for (int i = 0; i< c.length; i++) {
            if (c[i] == '1') {
                dpL.add(Math.min(i+1, dpL.get(dpL.size() - 1) + 2));
            }
        }
        
        // dpR[i] is the min cost of removing [1st, ith] "1" from the right side. 
        List<Integer> dpR = new ArrayList<>();
        dpR.add(0);
        for (int i = c.length - 1; i > -1; i--) {
            if (c[i] == '1') {
                dpR.add(Math.min(c.length - i, dpR.get(dpR.size() - 1) + 2));
            }
        }
        
        int result = Integer.MAX_VALUE;
        int l = 0;
        int r = dpR.size() - 1;
        while (l < dpL.size()) {
            result = Math.min(result, dpL.get(l++) + dpR.get(r--));
        }
        
        return result;
    }
}
