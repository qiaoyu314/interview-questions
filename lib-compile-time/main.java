// 原题：https://www.1point3acres.com/bbs/thread-846216-1-1.html
// 有一群代码库需要编译，提供编译所需时间和dependency。
// 还要保证没有dependency的代码库并行编译。
// 比如这个例子中B和C就可以一起编译。
// 并行编译的代码顺序无所谓， 也就是下面的例子里面同一行的字母顺序无限制。

import java.util.*;

public class Main
{
    public static void main(String[] args) {
        System.out.println("Welcome to Online IDE!! Happy Coding :)");
        
        Map<String, Integer> compileTime = new HashMap<>();
        compileTime.put("A", 1);
        compileTime.put("B", 3);
        compileTime.put("C", 2);
        compileTime.put("D", 2);
        compileTime.put("E", 4);
        
        Map<String, List<String>> deps = new HashMap<>();
        deps.put("B", Arrays.asList("A"));
        deps.put("C", Arrays.asList("A"));
        deps.put("D", Arrays.asList("B", "C"));
        deps.put("E", Arrays.asList("A", "C"));
        deps.put("D", Arrays.asList("B", "C"));
        
        List<String> result = printCompileTime(compileTime, deps);
        for (String s : result) {
            System.out.println(s);
        }
    }
    
    // Example result: 
    // {"1, A", "2,C,B", "1,B,E", "2,D,E", "1,E"}
    private static List<String> printCompileTime(Map<String, Integer> compileTime, Map<String, List<String>> deps) {
        List<String> result = new ArrayList<>();
        // Build:
        // 1) a map from a lib to its dependents
        // 2) a map from a lib to the number of remaining deps
        // 3) Find all libs.
        Map<String, List<String>> libToDependents = new HashMap<>();
        Map<String, Integer> libToRemainingDeps = new HashMap<>();
        Set<String> allLibs = new HashSet<>();
        
        for (String lib : deps.keySet()) {
            allLibs.add(lib);
            // Build the map from a lib to the # of its dependecies.
            libToRemainingDeps.put(lib, deps.get(lib).size());
            for (String dep : deps.get(lib)) {
                allLibs.add(dep);
                
                // Build the map from a lib to its dependents.
                List<String> dependents = libToDependents.getOrDefault(dep, new ArrayList<>());
                dependents.add(lib);
                libToDependents.put(dep, dependents);
            }
        }
        System.out.println("libToDependents: " + libToDependents);
        
        // Find the libs w/o deps.
        Queue<LibAndTime> queue = new PriorityQueue<>();
        for (String lib : allLibs) {
            if (!deps.containsKey(lib)) {
                System.out.println(lib + " doesn't have any deps");
                queue.offer(new LibAndTime(lib, compileTime.get(lib)));
            }
        }
        
        if (queue.isEmpty()) {
            throw new IllegalStateException("No valid answer for this problem!");
        }
        
        Queue<LibAndTime> nextQueue = new PriorityQueue<>();
        while (!queue.isEmpty()) {
            StringBuilder curLine = new StringBuilder();
            int minTime = queue.peek().remainingTime;
            System.out.println("minTime: " + minTime);
            curLine.append(minTime);
            while (!queue.isEmpty()) {
                LibAndTime libToRun = queue.poll();
                // Add it to the result.
                curLine.append("," + libToRun.name);
                if (libToRun.remainingTime == minTime) {
                    System.out.println(libToRun.name + " can finish");
                    // This lib can finish in this round. Update the libToRemainingDeps to find next candidates.
                    for (String dependent : libToDependents.getOrDefault(libToRun.name, new ArrayList<>())) {
                        System.out.println("Reducing the incomng edges for " + dependent);
                        int remainingDeps = libToRemainingDeps.get(dependent) - 1;
                        libToRemainingDeps.put(dependent, remainingDeps);
                        if (remainingDeps == 0) {
                            System.out.println(dependent + " can run now");
                            nextQueue.offer(new LibAndTime(dependent, compileTime.get(dependent)));
                        }
                    }
                } else { // Still need more time to compile. Add to the nextQueue.
                    libToRun.remainingTime -= minTime;
                    nextQueue.offer(libToRun);
                }
            }
            result.add(curLine.toString());
            // cur Queue is process. Need to process the nextQueue.
            queue = nextQueue;
            nextQueue = new PriorityQueue<>();
        }
        return result;
    }   
    
    public static class LibAndTime implements Comparable<LibAndTime> {
      public String name;
      public int remainingTime;

      public LibAndTime(String name, int remainingTime) {
          this.name = name;
          this.remainingTime = remainingTime;
      }

      @Override
      public int compareTo(LibAndTime other) {
          return this.remainingTime - other.remainingTime;
      }
  }
}
