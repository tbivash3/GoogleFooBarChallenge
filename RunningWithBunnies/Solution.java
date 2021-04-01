package GoogleFooBar.RunningWithBunnies;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Solution {

    public static ArrayList<ArrayList<Integer>> allPaths = new ArrayList<>();

    public static int[] solution(int[][] times, int times_limit) {
        boolean containsNegativeCycle = findAllPairsShortestPath(times);
        int[] bunniesRescued;

        if (!containsNegativeCycle) {
            int[] nodesVisited = new int[times.length];
            ArrayList<Integer> paths = new ArrayList<>();
            dfs(0, times, nodesVisited, paths, -times_limit);
            ArrayList<Integer> bestPath = findBestPath(allPaths, 0, times.length - 1);

            bunniesRescued = getBunniesFromBestPath(bestPath, 0, times.length - 1);
        } else {
            bunniesRescued = getAllBunnies(times.length - 2);
        }

        return bunniesRescued;
    }

    private static boolean findAllPairsShortestPath(int[][] arr) {
        for (int vertex = 0; vertex < arr.length; vertex++) {
            for (int row = 0; row < arr.length; row++) {
                for (int column = 0; column < arr.length; column++) {
                    if (arr[row][column] > (arr[row][vertex] + arr[vertex][column])) {
                        arr[row][column] = arr[row][vertex] + arr[vertex][column];
                    }

                }

            }

        }

        return checkContainsNegativeCycle(arr);
    }

    public static void dfs(int currentVertex, int[][] arr, int[] nodesVisited, ArrayList<Integer> paths, int currentPathTotalCost) {
        paths.add(currentVertex);

        int currentPathCost = 0;

        if (paths.size() > 1) {

            int prevPath = paths.get(paths.size() - 2);
            currentPathCost = arr[prevPath][currentVertex];
        }

        currentPathTotalCost += currentPathCost;

        if (paths.get(paths.size() - 1) == nodesVisited.length - 1 && currentPathTotalCost <= 0) {
            allPaths.add(paths);
        }

        for (int i = 0; i < nodesVisited.length; i++) {

            if (i != currentVertex) {

                if (nodesVisited[currentVertex] == 0) {

                    int[] nodesVisitedCopy = makeEdgesVisitedDeepCopy(nodesVisited);
                    ArrayList<Integer> pathsCopy = makePathsDeepCopy(paths);
                    nodesVisitedCopy[currentVertex] = 1;
                    dfs(i, arr, nodesVisitedCopy, pathsCopy, currentPathTotalCost);

                }
            }
        }
    }

    private static boolean checkContainsNegativeCycle(int[][] arr) {
        boolean containsNegativeCycle = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][i] != 0) {
                containsNegativeCycle = true;
                break;
            }
        }
        return containsNegativeCycle;
    }

    private static int[] getAllBunnies(int size) {
        int[] allBunniesArray = new int[size];
        for (int i = 0; i < size; i++) {
            allBunniesArray[i] = i;
        }
        return allBunniesArray;
    }

    private static int[] getBunniesFromBestPath(ArrayList<Integer> bestPath, int startVertex, int endVertex) {
        HashSet<Integer> bunnies = new HashSet<>();

        for (int j = 0; j < bestPath.size(); j++) {

            int currentVertex = bestPath.get(j);

            if (currentVertex != startVertex && currentVertex != endVertex) {
                bunnies.add(bestPath.get(j));
            }
        }
        int[] bunniesRescued = convertToArray(bunnies);

        Arrays.sort(bunniesRescued);
        return bunniesRescued;
    }

    private static int[] convertToArray(HashSet<Integer> bunnies) {
        int[] arr = new int[bunnies.size()];

        int index = 0;
        for (int x : bunnies)
            arr[index++] = x - 1;

        return arr;
    }

    private static ArrayList<Integer> findBestPath(ArrayList<ArrayList<Integer>> allPaths, int startVertex, int endVertex) {
        ArrayList<Integer> bestPath = new ArrayList<>();
        int maxBunnies = 0;
        HashSet<Integer> bunnies = new HashSet<>();

        for (int i = 0; i < allPaths.size(); i++) {

            for (int j = 0; j < allPaths.get(i).size(); j++) {

                int currentVertex = allPaths.get(i).get(j);

                if (currentVertex != startVertex && currentVertex != endVertex) {
                    bunnies.add(allPaths.get(i).get(j));
                }
            }

            if (bunnies.size() > maxBunnies) {
                bestPath = allPaths.get(i);
                maxBunnies = bunnies.size();
            }
            bunnies.clear();
        }
        return bestPath;
    }

    private static int[] makeEdgesVisitedDeepCopy(int[] nodesVisited) {

        int[] nodesVisitedCopy = new int[nodesVisited.length];

        for (int i = 0; i < nodesVisited.length; i++) {

                nodesVisitedCopy[i] = nodesVisited[i];

        }

        return nodesVisitedCopy;
    }

    private static ArrayList<Integer> makePathsDeepCopy(ArrayList<Integer> a) {
        ArrayList<Integer> deepCopy = new ArrayList<Integer>();
        a.forEach(ele -> deepCopy.add(Integer.valueOf(ele)));
        return deepCopy;
    }

    public static void main(String[] args) {
        int[][] arr = {
                {0, 1,1,1,1},
                {1,0,1,1,1},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 0}
        };

        int[] result = solution(arr, 3);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
