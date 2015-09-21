import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by emresonmez on 9/21/15.
 */
public class QLearner {
    int reward;
    int step;
    int[][] maze;
    HashMap<Q,Integer> qTable = new HashMap<>();

    public QLearner(int reward, int step, int[][] maze) {
        this.reward = reward;
        this.step = step;
        this.maze = maze;
    }

    public void qLearning() {
        // state: current cell
        // actions: proceed to any surrounding cell that is not 1
        // calculate q learning

    }

    /**
     * reward for going to a certain cell
     * @param cell
     * @return
     */
    private int getReward(Cell cell){
        if(cell.getValue() == 0){
            return step;
        }
        if(cell.getValue() == 9){
            return reward;
        }
        return 0;
    }

    /**
     * returns valid cells as arraylist for any location on grid
     * @param currentCell
     * @return
     */
    public ArrayList<Cell> getValidCells(Cell currentCell){
        ArrayList<Cell> result = new ArrayList<>();
        int x = currentCell.getX();
        int y = currentCell.getY();
        // check up
        if(y+1 < maze.length){
            if(maze[x][y+1] != 1){
                Cell cUp = new Cell(x,y+1,maze[x][y+1]);
                result.add(cUp);
            }
        }
        // check down
        if(y-1 >= 0){
            if(maze[x][y-1] != 1){
                Cell cDown = new Cell(x,y-1,maze[x][y+1]);
                result.add(cDown);
            }
        }
        // check left
        if(x-1 >= 0){
            if(maze[x-1][y] != 1){
                Cell cLeft = new Cell(x-1,y,maze[x-1][y]);
                result.add(cLeft);
            }
        }
        // check right
        if(x+1 < maze.length){
            if(maze[x+1][y] != 1){
                Cell cRight = new Cell(x+1,y,maze[x+1][y]);
                result.add(cRight);
            }
        }
        return result;
    }
}
