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
        Q = new double[maze.length][maze.length][3];
        startX = 8;
        startY = 8;
    }

    /**
     * runs q learning on maze
     * state: current cell
     * actions: proceed to any surrounding cell that is not 1
     * execute action, update Q table
     */
    public void qLearning() {
        // state: current cell
        // actions: proceed to any surrounding cell that is not 1
        // calculate q learning
        int steps = 0;
        Cell current = new Cell(8,8,maze[startX][startY]);
        while(current.getValue() != 9){
            ArrayList<Cell> validCells = getValidCells(current);
            int size = validCells.size();

            Random r1 = new Random();
            int randomNum1 = r1.nextInt(100);
            System.out.println("Random number: " + randomNum1);

            Cell nextCell;
            int r;
            int direction;
            if(randomNum1 < epsilon){ // pick at random from valid cells
                Random r2 = new Random();
                int randomNum2 = r2.nextInt(size);
                nextCell = validCells.get(randomNum2);
                direction = getDirection(current,nextCell);
            }else{ // pick max of Q[x][y]
                direction = maxQdirection(current.getX(), current.getY());
                int[] next  = getCoordinate(current,direction);
                nextCell = getCell(next[0],next[1],validCells);
            }
            r = nextCell.getValue();
            updateQ(current.getX(),current.getY(),direction,r);
            steps++;
        }
        System.out.println("Goal acquired. Number of steps: " + steps);
    }

    int getDirection(Cell from, Cell to){
        int x1 = from.getX();
        int x2 = to.getX();
        int y1 = from.getY();
        int y2 = to.getY();
        // up (0)
        if(y1 > y2){return 0;}
        // right (1)
        if(x2>x1){return 1;}
        // down (2)
        if(y2>y1){
            return 2;
        }
        // left (3)
        if(x2<x1){
            return 3;
        }
        return -1;
    }

    Cell getCell(int x, int y, ArrayList<Cell> cells){
        for(Cell c:cells){
            if((c.getX() == x) && (c.getY() == y)){
                return c;
            }
        }
        return null;
    }

    void updateQ(int x, int y, int direction, int r){
        Q[x][y][direction] = alpha*(Q[x][y][direction] - (r+gamma*maxQval(x,y)));
    }

    int[] getCoordinate(Cell cell,int direction){
        int x = 0;
        int y = 0;
        int[] result = new int[2];
        if(direction == 0){
            x = cell.getX()-1;
            y = cell.getY();
        }
        if(direction == 1){
            x = cell.getX();
            y = cell.getY()+1;
        }
        if(direction == 2){
            x = cell.getX()+1;
            y = cell.getY();
        }
        if(direction == 3){
            x = cell.getX();
            y = cell.getY()+1;
        }
        result[0] = x;
        result[1] = y;
        return result;
    }

    /**
     * returns direction of greatest Q value
     * @param x
     * @param y
     * @return
     */
    private int maxQdirection(int x, int y){
        double max = 0;
        int direction = 0;
        for(int i = 0; i < 3; i++){
            double val = Q[x][y][i];
            if(val < max){
                max = val;
                direction = i;
            }
        }
        return direction;
    }

    private double maxQval(int x, int y){
        double max = 0;
        for(int i = 0; i < 3; i++){
            double val = Q[x][y][i];
            if(val < max){
                max = val;
            }
        }
        return max;
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
        // check right
        if(x+1 < maze.length) {
            if (maze[x + 1][y] != 1) {
                Cell cRight = new Cell(x + 1, y, maze[x + 1][y]);
                result.add(cRight);
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
        return result;
    }
}
