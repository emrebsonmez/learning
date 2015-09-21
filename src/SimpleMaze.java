import java.util.ArrayList;

/**
 * Created by emresonmez on 9/21/15.
 */
public class SimpleMaze {
    private int[][] maze;
    public SimpleMaze() {
        this.maze = new int[10][10];
        generateMaze();
        printMaze();
    }

    /**
     * generates 10 x 10 simple maze
     * @return
     */
    public int[][] generateMaze(){
        for(int i = 0; i < 10; i++){
            maze[0][i] = 1;
            maze[9][i] = 1;
            maze[i][0] = 1;
            maze[i][9] = 1;
        }
        populateWallCols();
        populateArray(5, new int[]{6}, 9);

        return maze;
    }

    public void printMaze(){
        for(int i =0; i < maze.length; i++){
            String row = "";
            for(int j = 0; j < maze.length; j++){
                row += maze[i][j] + " ";
            }
            System.out.println(row);
        }
    }

    private void populateWallCols(){
        int[] cols1 = {2,4};
        int[] cols2 = {4,5,6,7};
        int[] cols3 = {2,3,4,7};
        int[] cols4 = {2,4};
        int[] cols5 = {4,7};
        int[] cols6 = {1,2,4,5,6,7};
        int[] cols7 = {4};
        int[] cols8 = {2,6};
        populateArray(1,cols1,1);
        populateArray(2,cols2,1);
        populateArray(3,cols3,1);
        populateArray(4,cols4,1);
        populateArray(5,cols5,1);
        populateArray(6,cols6,1);
        populateArray(7,cols7,1);
        populateArray(8,cols8,1);
    }

    private void populateArray(int row, int[] cols, int c){
        for(int i = 0 ; i < cols.length; i++){
            maze[row][cols[i]] = c;
        }
    }

    public static void main(String[] args){
        SimpleMaze m = new SimpleMaze();
    }
}