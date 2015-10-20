import java.util.ArrayList;
import java.util.Random;

/**
 * Created by emresonmez on 9/21/15.
 */
public class QLearner {
    int reward;
    int step;
    int[][] maze;
    double[][][] Q; // 0 1 2 3 for north, east, south, west
    int startX;
    int startY;
    double alpha = 0.1;
    double gamma = 0.95;
    double epsilon = 50;

    public QLearner(int reward, int step, int[][] maze) {
        this.reward = reward;
        this.step = step;
        this.maze = maze;
        Q = new double[maze.length][maze.length][4];
        System.out.println(maze[5][6]);
        startX = 8;
        startY = 8;
    }

    public static void main(String[] args) {
        SimpleMaze m = new SimpleMaze();
        int[][] maze = m.generateMaze();
        QLearner q = new QLearner(9, -1, maze);
        try {
            q.qLearning();
        } catch (MazeException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs q learning on maze
     * state: current cell
     * actions: proceed to any surrounding cell that is not 1
     * execute action, update Q table
     */
    public void qLearning() throws MazeException {
        // state: current cell
        // actions: proceed to any surrounding cell that is not 1
        // calculate q learning
        Random r1 = new Random();
        Random r2 = new Random();

        int steps = 0;
        int[] current = new int[2];
        current[0] = 8;
        current[1] = 8;
        int x = current[0]; // goes down
        int y = current[1]; // goes across

        while (maze[x][y] != 9) {
            int randomNum = randomInt(100, r1);
            int direction;
            int[] next;
            if (randomNum < epsilon) { // pick at random from valid cells
                System.out.println("randomNum < epsilon (choosing randomly)");
                ArrayList<int[]> validCells = getValidCells(x, y);
                int size = validCells.size();
                int randomNum2 = randomInt(size, r2);
                next = validCells.get(randomNum2);
                direction = getDirection(current, next);
            } else { // pick max of Q[x][y]
                System.out.println("randomNum > epsilon (choosing max)");
                next = maxQCell(current);
                direction = getDirection(current,next);

            }
            System.out.println("HELLOOO " + next[0] + " " + next[1]);
            int reward = getReward(next[0],next[1]);
            System.out.println("next x: " + next[0] + " next y: " + next[1] + " next value " + reward);
            updateQ(x, y, direction, reward);
            steps++;

            current = next;
            x = current[0];
            y = current[1];
        }
    }

    int randomInt(int limit, Random r){
        return r.nextInt(limit);
    }

    ArrayList<int[]> getValidCells(int x, int y) {
        ArrayList<int[]> cells = new ArrayList<>();
        System.out.println(maze.length);
        if(x+1 < maze.length){
            int[] ret = new int[2];
            ret[0] = x+1;
            ret[1] = y;
            if(maze[ret[0]][ret[1]] != 1){
                cells.add(ret);
            }
        }

        if(x-1 >= 0){
            int[] ret = new int[2];
            ret[0] = x-1;
            ret[1] = y;
            if(maze[ret[0]][ret[1]] != 1){
                cells.add(ret);
            }
        }

        if(y+1 < maze.length){
            int[] ret = new int[2];
            ret[0] = x;
            ret[1] = y+1;
            if(maze[ret[0]][ret[1]] != 1){
                cells.add(ret);
            }
        }

        if(y-1 >= 0){
            int[] ret = new int[2];
            ret[0] = x;
            ret[1] = y-1;
            if(maze[ret[0]][ret[1]] != 1){
                cells.add(ret);
            }
        }
        for(int[] k:cells){
            System.out.println(k[0] + " " + k[1]);
        }
        return cells;
    }

    /**
     * returns direction
     * @param from
     * @param to
     * @return
     */
    int getDirection(int[] from, int[] to) throws MazeException {
        if (from[0] > to[0]) { // up
            return 0;
        }
        if (from[1] < to[1]) { // right
            return 1;
        }
        if (from[0] < to[0]) { // down
            return 2;
        }
        if (from[1] > to[1]) { // left
            return 3;
        }
        throw new MazeException("Invalid direction.");
    }

    /**
     * gets next cell from direction
     * @param x
     * @param y
     * @param direction
     * @return
     */
    int[] nextCell(int x, int y, int direction) throws MazeException {
        int[] ret = new int[2];
        switch (direction) {
            case 0:
                ret[0] = x-1;
                ret[1] = y;
            case 1:
                ret[0] = x;
                ret[1] = y+1;
            case 2:
                ret[0] = x+1;
                ret[1] = y;
            case 3:
                ret[0] = x;
                ret[1] = y-1;
        }
        if(maze[ret[0]][ret[1]] == 1){
            throw new MazeException("next cell error for (" + x + ", " + y + ", " + direction + ")");
        }
        return ret;
    }

    /**
     * updates Q matrix
     * @param x
     * @param y
     * @param direction
     * @param r
     */
    void updateQ(int x, int y, int direction, int r) {
        Q[x][y][direction] += alpha * (r + gamma * maxQVal(x, y) - Q[x][y][direction]);
    }

    private int[] maxQCell(int[] from) throws MazeException {
        int[] ret = new int[2];
        double max = -1;
        ArrayList<int[]> valid = getValidCells(from[0],from[1]);
        for(int[] k:valid){
            System.out.println(from[0] + " " + from[1] + " " + getDirection(from,k));
            if(Q[from[0]][from[1]][getDirection(from,k)] >= max){
                max = Q[from[0]][from[1]][getDirection(from,k)];
                ret = k;
            }
        }
        return ret;
    }

    private double maxQVal(int x, int y) {
        double max = 0;
        for (int i = 0; i < 3; i++) {
            double val = Q[x][y][i];
            if ((val > max) && val !=0){
                max = val;
            }
        }
        return max;
    }


    /**
     * reward for going to a certain cell
     * @param x
     * @param y
     * @return
     */
    private int getReward(int x, int y) throws MazeException {
        if(maze[x][y] == 0){
            return step;
        }
        if(maze[x][y] == 9){
            System.out.println("x: " + x + "y: " + y);
            return reward;
        }
        throw new MazeException("Invalid cell to obtain reward from (" + x + ", " + y + ")");
    }

}
