package GoogleFooBar;

import java.util.LinkedList;
import java.util.Queue;

public class MazeSolverWithCanBreakOneWall {
    public static int solution(int[][] map) {

        int[][][] visited = new int[map.length][map[0].length][2];

        Node source = new Node(0, 0, true, 1);

        Queue<Node> queue = new LinkedList<>();
        queue.add(source);
        int dist = 1;
        Node current = null;
        while (!queue.isEmpty()) {
            current = queue.remove();

            if (visited[current.row][current.column][current.canBreakWall == true ? 0 : 1] == 1) continue;

            if (current.row == map.length - 1 && current.column == map[0].length - 1) {
                dist = current.dist;
                break;
            }

            visited[current.row][current.column][current.canBreakWall == true ? 0 : 1] = 1;
            addNeighborsToQueue(queue, current, map);
        }
        return dist;
    }

    public static void addNeighborsToQueue(Queue<Node> queue, Node current, int[][] map) {

        //Right
        if (current.column != map[0].length - 1) {
            int row = current.row;
            int column = current.column + 1;
            int dist = current.dist;

            if (map[row][column] == 0) {
                queue.add(new Node(row, column, current.canBreakWall, dist + 1));
            } else {
                if (current.canBreakWall) {
                    queue.add(new Node(row, column, false, dist + 1));
                }
            }
        }

        //Left
        if (current.column != 0) {
            int row = current.row;
            int column = current.column - 1;
            int dist = current.dist;

            if (map[row][column] == 0) {
                queue.add(new Node(row, column, current.canBreakWall, dist + 1));
            } else {
                if (current.canBreakWall) {
                    queue.add(new Node(row, column, false, dist + 1));
                }
            }
        }

        //Down
        if (current.row != map.length - 1) {
            int row = current.row + 1;
            int column = current.column;
            int dist = current.dist;

            if (map[row][column] == 0) {
                queue.add(new Node(row, column, current.canBreakWall, dist + 1));
            } else {
                if (current.canBreakWall) {
                    queue.add(new Node(row, column, false, dist + 1));
                }
            }
        }

        //Up
        if (current.row != 0) {
            int row = current.row - 1;
            int column = current.column;
            int dist = current.dist;

            if (map[row][column] == 0) {
                queue.add(new Node(row, column, current.canBreakWall, dist + 1));
            } else {
                if (current.canBreakWall) {
                    queue.add(new Node(row, column, false, dist + 1));
                }
            }
        }
    }

    static class Node {
        int row;
        int column;
        boolean canBreakWall;
        int dist;

        Node(int row, int column, boolean canBreakWall, int dist) {
            this.row = row;
            this.column = column;
            this.canBreakWall = canBreakWall;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        int[][] arr =
                {
                        {0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 1, 1},
                        {0, 1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0}};

        System.out.println(solution(arr));
    }
}
