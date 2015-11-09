import java.util.ArrayList;
import java.util.Random;

/**
 * Created by emresonmez on 11/9/15.
 */
public class Sarsa {
    private int reward;
    private int step;
    private int[][] maze;
    private double[][][] Q; // 0 1 2 3 for north, east, south, west
    private int startX;
    private int startY;
    private double alpha = 0.1;
    private double gamma = 0.95;
    private double epsilon = 15;
    private LearnerUtils learnerUtils;


    public Sarsa(int reward, int step, int[][] maze) {
        this.reward = reward;
        this.step = step;
        this.maze = maze;
        Q = new double[maze.length][maze.length][4];
        startX = 8;
        startY = 8;
        learnerUtils = new LearnerUtils();
    }

    /**
     * runs Q learning n times
     * @param runs
     * @throws MazeException
     */
    public void sarsaLearning(int runs) throws MazeException {
        ArrayList<int[]> log = new ArrayList<>();
        // for sarsa learning — change order of requesting action
        // state: current cell
        // actions: proceed to any surrounding cell that is not 1
        // calculate q learning
        Random r1 = new Random();
        Random r2 = new Random();
        for(int i = 0; i < runs; i++) {
            int steps = 0;
            int[] current = new int[2];
            current[0] = 8;
            current[1] = 8;
            int[] next = learnerUtils.maxQCell(current,maze,Q);
            int direction = learnerUtils.getDirection(current,next);
            int x = current[0];
            int y = current[1];
            int randomNum;
            while (maze[x][y] != 9) {
                randomNum = learnerUtils.randomInt(100, r1);
                int nextDirection;
                if (randomNum < epsilon) { // pick at random from valid cells
                    ArrayList<int[]> validCells = learnerUtils.getValidCells(x, y, maze);
                    int size = validCells.size();
                    int randomNum2 = learnerUtils.randomInt(size, r2);
                    next = validCells.get(randomNum2);
                    nextDirection = learnerUtils.getDirection(current, next);
                } else { // pick max of Q[x][y]
                    next = learnerUtils.maxQCell(current,maze,Q);
                    nextDirection = learnerUtils.getDirection(current, next);
                }
                log.add(next);
                int nextReward = learnerUtils.getReward(next[0], next[1],maze,-1,9);
                updateQsarsa(x,y,direction,next[0],next[1],nextDirection,reward);
                steps++;
                current = next;
                x = current[0];
                y = current[1];
                direction = nextDirection;
            }
            if(i == 850){
                epsilon = 0;
            }
            assert (log.size() == steps);
            System.out.println("Run " + i + ", Steps: " + steps);
        }
    }

    /**
     * updates Q sarsa
     * @param x
     * @param y
     * @param direction
     * @param r
     */
    private void updateQsarsa(int x, int y, int direction, int nextX, int nextY, int nextDirection, int r) {
        Q[x][y][direction] += alpha * (r + gamma * Q[nextX][nextY][nextDirection] - Q[x][y][direction]);
    }

}
