import java.util.ArrayList;
import java.util.Random;

/**
 * Implements Sarsa(Lambda) learning.
 * intialize s, a
 * foreach do:
 *  take action a, observe r, s'
 *  choose a' from s' using Q
 *  delta <- r + gamma*Q(s',a') - Q(s,a)
 *  e(s,a) <- 1
 *  for all s,a:
 *      Q(s,a) <- Q(s,a) + alpha*delta*e(s,a)
 *      e(s,a) <- gamma*lambda*e(s,a)
 *  s <- s'
 *  a <- a'
 *
 * Created by emresonmez on 11/9/15.
 */
public class SarsaLambda {
    private int goalReward;
    private int stepPenalty;
    private int[][] maze;
    private double[][][] Q; // 0 1 2 3 for north, east, south, west
    private double[][][] E;
    private int startX;
    private int startY;
    private double alpha = 0.1;
    private double gamma = 0.95;
    private double lambda = 0.9;
    private double epsilon = 15;
    private LearnerUtils learnerUtils;


    public SarsaLambda(int reward, int step, int[][] maze) {
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
    public ArrayList<Integer> sarsaLambdaLearning(int runs) throws MazeException {
        ArrayList<Integer> log = new ArrayList<>();

        for(int i = 0; i < runs; i++){
            E = new double[maze.length][maze.length][4];
            int steps = 0;
            // initial state
            int[] current = new int[2];
            current[0] = startX;
            current[1] = startY;

            // initial a
            int[] next = learnerUtils.greedy(current, maze, Q, epsilon);
            int direction = learnerUtils.getDirection(current,next);

            int nextDirection;

            while(maze[current[0]][current[1]] != 9) {
                // get reward
                int reward = learnerUtils.getReward(next[0], next[1], maze, stepPenalty, goalReward);

                // get x',a'
                int[] nextPrime = new int[2];
                nextPrime = learnerUtils.greedy(next, maze, Q, epsilon);

                nextDirection = learnerUtils.getDirection(next, nextPrime);

                // get delta
                double delta = getDelta(current,direction,next,nextDirection,reward);

                // set e(s,a) to 1
                E[current[0]][current[1]][direction] = 1;
                // update each s,a
                updateQSarsaLambda(delta);

                // x <- x'
                current = next;
                next = nextPrime;

                // a <- a'
                direction = nextDirection;
                steps++;
            }
            log.add(steps);
        }
        return log;
    }

    /**
     * calculates delta value
     * @param current
     * @param direction
     * @param next
     * @param nextDirection
     * @param reward
     * @return
     */
    private double getDelta(int[] current, int direction, int[] next, int nextDirection, double reward){
        double delta = reward - Q[current[0]][current[1]][direction];
        if (maze[next[0]][next[1]] != goalReward) { // if not end of episode
            delta += gamma * Q[next[0]][next[1]][nextDirection];
        }
        return delta;
    }

    /**
     * updates E and Q matrices
     * @param delta
     */
    private void updateQSarsaLambda(double delta) {
        for (int j = 0; j < E.length; j++) {
            for (int k = 0; k < E.length; k++) {
                for (int a = 0; a < 4; a++) {
                    Q[j][k][a] += alpha*delta*E[j][k][a];
                    E[j][k][a] = gamma*lambda*E[j][k][a];
                }
            }
        }
    }

}
