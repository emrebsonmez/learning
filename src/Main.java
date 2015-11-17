/**
 * Created by emresonmez on 11/9/15.
 */
public class Main {
    public static void main(String[] args) {
        SimpleMaze m = new SimpleMaze();
        int[][] maze = m.generateMaze();
        Main main = new Main();
        main.runQ(maze);
//        main.runSarsa(maze);
//        main.runSarsaLambda(maze);
    }

    private void runQ(int[][] maze){
        QLearner q = new QLearner(9, -1, maze);
        try {
            q.qLearning(1000);
        } catch (MazeException e) {
            e.printStackTrace();
        }
    }

    private void runSarsa(int[][] maze){
        Sarsa sarsa = new Sarsa(9,-1,maze);
        try {
            sarsa.sarsaLearning(1000);
        } catch (MazeException e) {
            e.printStackTrace();
        }
    }

    private void runSarsaLambda(int[][] maze){
        SarsaLambda sarsaLambda = new SarsaLambda(9,-1,maze);
        try {
            sarsaLambda.sarsaLambdaLearning(1000);
        } catch (MazeException e) {
            e.printStackTrace();
        }
    }
}
