import java.util.ArrayList;
import java.util.Random;

/**
 * Implements sarsa learning
 *
 * Hits 9 steps consistently at 847
 *
 * foreach episode do
 *    x <- initial state
 *    a <- greedy(x)
 *    while x is not terminal do
 *        r,x' <- Step(a)
 *       a' <- Greedy(x')
 *       Q(x,a) <- Q(x,a) + alpha(r + gamma*Q(x',a')-Q(x,a))
 *       x <- x'
 *       a <- a'
 *   end
 * end
 *
 * Created by emresonmez on 11/9/15.
 */
public class Sarsa {
    private int goalReward;
    private int stepPenalty;
    private int[][] maze;
    private double[][][] Q; // 0 1 2 3 for north, east, south, west
    private int startX;
    private int startY;
    private double alpha = 0.1;
    private double gamma = 0.95;
    private double epsilon = 15;
    private LearnerUtils learnerUtils;


    public Sarsa(int reward, int step, int[][] maze) {
        this.goalReward = reward;
        this.stepPenalty = step;
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

        for(int i = 0; i < runs; i++) {
            int steps = 0;
            // initial state
            int[] current = new int[2];
            current[0] = 8;
            current[1] = 8;
            // initial a
            int[] next = learnerUtils.greedy(current, maze, Q, epsilon);
            int direction = learnerUtils.getDirection(current,next);

            int nextDirection;

            while(maze[current[0]][current[1]] != 9){
                // get reward
                int reward =learnerUtils.getReward(next[0],next[1],maze,stepPenalty,goalReward);
                // get x',a'
                int[] nextPrime = new int[2];
                nextPrime = learnerUtils.greedy(next,maze,Q,epsilon);

                // update Q
                log.add(next);
                nextDirection = learnerUtils.getDirection(next,nextPrime);
                updateQsarsa(current[0],current[1],direction,next[0],next[1],nextDirection,reward,maze[current[0]][current[1]]==goalReward);

                // x <- x'
                current = next;
                next = nextPrime;

                // a <- a'
                direction = nextDirection;
                steps++;
            }
            if(i == 850){
                epsilon = 0;
            }
            assert (log.size() == steps);
            System.out.println("Run " + i + ", Steps: " + steps);
        }
    }

    /**
     * updates Q sarsa (don't include gammaQ if end of episode)
     * @param x
     * @param y
     * @param direction
     * @param r
     */
    private void updateQsarsa(int x, int y, int direction, int nextX, int nextY, int nextDirection, int r, boolean end) {
        if(end){
            Q[x][y][direction] += alpha * (r - Q[x][y][direction]);
        }else{
            Q[x][y][direction] += alpha * (r + gamma * Q[nextX][nextY][nextDirection] - Q[x][y][direction]);
        }

    }

}
