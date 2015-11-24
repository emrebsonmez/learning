import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @TODO run each 20 times, average for each episode
 * @TODO experimentNumber_episode_steps
 * matplotlib
 *
 * Created by emresonmez on 11/9/15.
 */
public class Main {
    private FileUtils fileUtils;

    public Main(){
        fileUtils = new FileUtils();
    }

    /**
     * runs q, sarsa, sarsa lambda learning and writes to files
     * averages "overall runs" number of individual runs
     * @param args
     * @throws MazeException
     */
    public static void main(String[] args) throws MazeException {
        int individualRuns = Integer.parseInt(args[0]);
        int overallRuns = Integer.parseInt(args[1]);
        SimpleMaze m = new SimpleMaze();
        int[][] maze = m.generateMaze();
        Main main = new Main();
        main.runQ(maze,individualRuns,overallRuns,"../plotting/q.txt");
        main.runSarsa(maze,individualRuns,overallRuns,"../plotting/sarsa.txt");
        main.runSarsaLambda(maze,individualRuns,overallRuns,"../plotting/sarsaLambda.txt");
    }

    private void runQ(int[][] maze, int individualRuns, int overallRuns, String filename) throws MazeException {
        QLearner q = new QLearner(9, -1, maze);
        ArrayList<ArrayList<Integer>> qLogs = new ArrayList<>();
        for(int i = 0; i < overallRuns; i++){
            qLogs.add(q.qLearning(individualRuns));
        }
        fileUtils.averageAndWrite(filename, qLogs);
    }

    private void runSarsa(int[][] maze, int individualRuns, int overallRuns, String filename) throws MazeException {
        Sarsa sarsa = new Sarsa(9,-1,maze);
        ArrayList<ArrayList<Integer>> sarsaLogs = new ArrayList<>();
        for(int i = 0; i < overallRuns; i++){
            sarsaLogs.add(sarsa.sarsaLearning(individualRuns));
        }
        fileUtils.averageAndWrite(filename,sarsaLogs);
    }

    private void runSarsaLambda(int[][] maze, int individualRuns, int overallRuns, String filename) throws MazeException {
        SarsaLambda sarsaLambda = new SarsaLambda(9,-1,maze);
        ArrayList<ArrayList<Integer>> sarsaLambdaLogs = new ArrayList<>();
        for(int i = 0; i < overallRuns; i++){
            sarsaLambdaLogs.add(sarsaLambda.sarsaLambdaLearning(individualRuns));
        }
        fileUtils.averageAndWrite(filename,sarsaLambdaLogs);
    }




}