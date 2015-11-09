import java.util.ArrayList;
import java.util.Random;

/**
 * Created by emresonmez on 9/21/15.
 */
public class QLearner {
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


    public QLearner(int reward, int step, int[][] maze) {
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
    public void qLearning(int runs) throws MazeException {
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
            int x = current[0]; // goes down
            int y = current[1]; // goes across
            log.add(current);
            while (maze[x][y] != 9) {
                int randomNum = learnerUtils.randomInt(100, r1);
                int direction;
                int[] next;
                if (randomNum < epsilon) { // pick at random from valid cells
                    ArrayList<int[]> validCells = learnerUtils.getValidCells(x, y, maze);
                    int size = validCells.size();
                    int randomNum2 = learnerUtils.randomInt(size, r2);
                    next = validCells.get(randomNum2);
                    direction = learnerUtils.getDirection(current, next);
                } else { // pick max of Q[x][y]
                    next = learnerUtils.maxQCell(current, maze, Q);
                    direction = learnerUtils.getDirection(current, next);

                }
                log.add(next);
                int reward = learnerUtils.getReward(next[0], next[1],maze,-1,9);
                updateQ(x, y,next[0],next[1],direction, reward);
                steps++;
                current = next;
                x = current[0];
                y = current[1];
            }
            if(i == 850){
                epsilon = 0;
            }
            assert (log.size() == steps);
            System.out.println("Run " + i + ", Steps: " + steps);
        }
    }

    /**
     * updates Q matrix
     * @param x
     * @param y
     * @param direction
     * @param r
     */
    void updateQ(int x, int y, int nextX, int nextY, int direction, int r) {
        Q[x][y][direction] += alpha * (r + gamma * learnerUtils.maxQVal(nextX,nextY,Q) - Q[x][y][direction]);
    }

}
