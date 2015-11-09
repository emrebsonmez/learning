import java.util.ArrayList;
import java.util.Random;

/**
 * Created by emresonmez on 11/9/15.
 */
public class SarsaLambda {
    private int reward;
    private int step;
    private int[][] maze;
    private double[][][] Q; // 0 1 2 3 for north, east, south, west
    private double[][][] E;
    private int startX;
    private int startY;
    private double alpha = 0.1;
    private double gamma = 0.95;
    private double epsilon = 15;
    private LearnerUtils learnerUtils;


    public SarsaLambda(int reward, int step, int[][] maze) {
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
    public void sarsaLambdaLearning(int runs) throws MazeException {
        ArrayList<int[]> log = new ArrayList<>();
        Random r1 = new Random();
        Random r2 = new Random();
        for(int i = 0; i < runs; i++) {
            E = new double[maze.length][maze.length][4];
            int steps = 0;
            // initialize s
            int[] current = new int[2];
            current[0] = 8;
            current[1] = 8;
            int[] next = learnerUtils.maxQCell(current,maze,Q);
            // initialize action
            int direction = learnerUtils.getDirection(current,next);
            int x = current[0];
            int y = current[1];
            int randomNum;
            double delta;
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
                int nextReward = learnerUtils.getReward(next[0],next[1],maze,-1,9);
                delta = nextReward + gamma*Q[next[0]][next[1]][nextDirection]-Q[x][y][direction];
                E[x][y][direction] = 1;
                updateQSarsaLambda(x,y,direction,delta);
                E[x][y][direction] = gamma*E[x][y][direction]; // what is lambda here?
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
     * @param delta
     */
    private void updateQSarsaLambda(int x, int y, int direction, double delta) {
        Q[x][y][direction] = Q[x][y][direction] + alpha*delta*E[x][y][direction];
    }

}
