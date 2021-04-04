package GoogleFooBar.ExpandingNebula;

import java.util.ArrayList;

public class Solution {
    private static int totalPaths = 0;
    private static boolean[][] originalState;

    public static int solution(boolean[][] g) {
        originalState = g;

        Boolean[][] prevState = new Boolean[g.length + 1][g[0].length + 1];

        findAllPreviousStates(prevState, 0, 0);

        return 0;
    }

    private static void findAllPreviousStates(Boolean[][] currArr, int row, int column) {

        if (row == originalState.length) {
            totalPaths++;
        } else {
            ArrayList<Integer[]> nullPositions = getAllNullPositions(currArr, row, column);
            generateAllStateCombinations(currArr, 0, nullPositions, row, column);
        }
    }

    private static void generateAllStateCombinations(Boolean[][] arr, int index, ArrayList<Integer[]> nullPositions, int row, int column) {
        if (index == nullPositions.size()) {
            boolean isValidState = checkState(arr, row, column);

            if (isValidState) {
                int[] nextPos = getNextPosition(row, column);
                findAllPreviousStates(arr, nextPos[0], nextPos[1]);
            }

        } else {

            Boolean[][] arrCopyOne = deepCopy(arr);
            arrCopyOne[nullPositions.get(index)[0]][nullPositions.get(index)[1]] = true;
            generateAllStateCombinations(arrCopyOne, index + 1, nullPositions, row, column);

            Boolean[][] arrCopyTwo = deepCopy(arr);
            arrCopyTwo[nullPositions.get(index)[0]][nullPositions.get(index)[1]] = false;
            generateAllStateCombinations(arrCopyTwo, index + 1, nullPositions, row, column);
        }
    }

    private static boolean checkState(Boolean[][] arr, int row, int column) {
        boolean isValidState = true;

        int totalTrueSum = calculateTrueSum(arr, row, column);

        boolean currentState = originalState[row][column];

        if (currentState) {
            if (totalTrueSum != 1) isValidState = false;
        } else {
            if (totalTrueSum == 1) isValidState = false;

            if (isValidState) {

                if (column < originalState[0].length - 1 && row < originalState.length - 1) {
                    boolean nextColumnState = originalState[row][column + 1];
                    if (nextColumnState) {
                        if (arr[row][column + 1] && arr[row + 1][column + 1]) {
                            isValidState = false;
                        }
                    }

                    boolean nextRowState = originalState[row + 1][column];
                    if (nextRowState) {
                        if (arr[row + 1][column] && arr[row + 1][column + 1]) {
                            isValidState = false;
                        }
                    }
                }
            }
        }

        return isValidState;
    }

    private static ArrayList<Integer[]> getAllNullPositions(Boolean[][] arr, int row, int column) {

        ArrayList<Integer[]> list = new ArrayList<>();

        if (arr[row][column] == null) list.add(new Integer[]{row, column});
        if (arr[row][column + 1] == null) list.add(new Integer[]{row, column + 1});
        if (arr[row + 1][column] == null) list.add(new Integer[]{row + 1, column});
        if (arr[row + 1][column + 1] == null) list.add(new Integer[]{row + 1, column + 1});

        return list;
    }

    private static int[] getNextPosition(int row, int column) {
        if (column == originalState[0].length - 1) {
            row = row + 1;
            column = 0;
        } else {
            column++;
        }

        return new int[]{row, column};
    }

    private static int calculateTrueSum(Boolean[][] arr, int row, int column) {
        int sum = 0;

        if (arr[row][column] != null && arr[row][column].equals(true)) sum++;
        if (arr[row][column + 1] != null && arr[row][column + 1].equals(true)) sum++;
        if (arr[row + 1][column] != null && arr[row + 1][column].equals(true)) sum++;
        if (arr[row + 1][column + 1] != null && arr[row + 1][column + 1].equals(true)) sum++;

        return sum;
    }

    private static Boolean[][] deepCopy(Boolean[][] arr) {
        Boolean[][] arrCopy = new Boolean[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != null) {
                    arrCopy[i][j] = arr[i][j];
                }
            }
        }
        return arrCopy;
    }

    public static void main(String[] args) {
        boolean arr[][] = {{true, false, true, false, false, true, true, true}, {true, false, true, false, false, false, true, false}, {true, true, true, false, false, false, true, false}, {true, false, true, false, false, false, true, false}, {true, false, true, false, false, true, true, true}};
        solution(arr);
        System.out.println(totalPaths);
    }
}
