/**
 * Created by emresonmez on 11/9/15.
 */
public class Main {
    public static void main(String[] args) {
        SimpleMaze m = new SimpleMaze();
        int[][] maze = m.generateMaze();
        QLearner q = new QLearner(9, -1, maze);
        try {
            q.qLearning(1000);
        } catch (MazeException e) {
            e.printStackTrace();
        }
    }
}
