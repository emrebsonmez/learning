import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by emresonmez on 11/16/15.
 */
public class LearnerUtilsTest {
    private int[][] maze;
    LearnerUtils learnerUtils;
//    1 1 1 1 1 1 1 1 1 1
//    1 0 1 0 1 0 0 0 0 1
//    1 0 0 0 1 1 1 1 0 1
//    1 0 1 1 1 0 0 1 0 1
//    1 0 1 0 1 0 0 0 0 1
//    1 0 0 0 1 0 9 1 0 1
//    1 1 1 0 1 1 1 1 0 1
//    1 0 0 0 1 0 0 0 0 1
//    1 0 1 0 0 0 1 0 0 1
//    1 1 1 1 1 1 1 1 1 1

    // 0 1 2 3 for north, east, south, west

    @Before
    public void setUp() throws Exception {
        SimpleMaze m = new SimpleMaze();
        maze = m.generateMaze();
        learnerUtils = new LearnerUtils();
        // fpr reference
        assertEquals(maze[0][0],1);
        assertEquals(maze[1][1],0);
        assertEquals(maze[1][2],1);
        assertEquals(maze[2][1],0);
        assertEquals(maze[8][8],0);
        assertEquals(maze[7][8],0);
        assertEquals(maze[6][8],0);
        assertEquals(maze[5][8],0);
        assertEquals(maze[4][8],0);
        assertEquals(maze[4][7],0);
        assertEquals(maze[4][6],0);
        assertEquals(maze[5][6],9);
    }

    @Test
    public void testNextCell() throws Exception {
        // test north
        int[] a = learnerUtils.nextCell(8,8,0,maze);
        assertEquals(7,a[0]);
        assertEquals(8,a[1]);
        // test west
        a = learnerUtils.nextCell(8,8,3,maze);
        assertEquals(8,a[0]);
        assertEquals(7,a[1]);
        // test south
        a = learnerUtils.nextCell(4,6,2,maze);
        assertEquals(5,a[0]);
        assertEquals(6,a[1]);
        assertEquals(maze[a[0]][a[1]],9);
        // test east
        a = learnerUtils.nextCell(4,6,1,maze);
        assertEquals(4,a[0]);
        assertEquals(7,a[1]);
    }

    @Test
    public void testGetValidCells() throws Exception {
        ArrayList<int[]> validCells = learnerUtils.getValidCells(8,8,maze);
        assertEquals(2,validCells.size());
        boolean foundA = false;
        boolean foundB = false;
        for(int[] cell:validCells){
            if(cell[0] == 7){
                foundA = true;
                assertTrue(cell[0] == 7 && cell[1] == 8);
            }else{
                foundB = true;
                assertTrue(cell[0] == 8 && cell[1] == 7);
            }
        }
        assertTrue(foundA && foundB);

        validCells = learnerUtils.getValidCells(4,6,maze);
        assertEquals(4,validCells.size());

        boolean[] validation = new boolean[4];
        for(int[] cell:validCells){
            int x = cell[0];
            int y = cell[1];

            if(x == 3 && y == 6){
                validation[0] = true;
            }
            if(x == 5 && y == 6){
                validation[1] = true;
            }
            if(x == 4 && y == 7){
                validation[2] = true;
            }
            if(x == 4 && y == 5){
                validation[3] = true;
            }
        }

        for(boolean b:validation){
            assertTrue(b);
        }

    }

    @Test
    public void testGetDirection() throws Exception {
        // 0 1 2 3 for north, east, south, west
        int north = learnerUtils.getDirection(new int[] {4,6},new int[] {3,6});
        assertEquals(0,north);
        int east = learnerUtils.getDirection(new int[] {4,6},new int[] {4,7});
        assertEquals(1,east);
        int south = learnerUtils.getDirection(new int[] {4,6}, new int[] {5,6});
        assertEquals(2,south);
        int west = learnerUtils.getDirection(new int[] {4,6}, new int[] {4,5});
        assertEquals(3,west);
    }

    @Test
    public void testMaxQCellNull() throws Exception {
        double[][][] Q = new double[10][10][4];
        Q[8][8][0] = 10;
        Q[8][8][1] = 0;
        Q[8][8][2] = 2;
        Q[8][8][3] = 2;
        int[] maxQCell = learnerUtils.maxQCell(new int[] {8,8},maze,Q);
        assertEquals(0,maxQCell[0]);
        assertEquals(0,maxQCell[1]);

        Q[8][8][0] = 0;
        Q[8][8][1] = 0;
        Q[8][8][2] = 10;
        Q[8][8][3] = 2;
        maxQCell = learnerUtils.maxQCell(new int[] {8,8},maze,Q);
        assertEquals(0,maxQCell[0]);
        assertEquals(0,maxQCell[1]);

    }

    @Test
    public void testMaxQVal() throws Exception {
        double[][][] Q = new double[10][10][4];
        Q[0][0][0] = 10;
        Q[0][0][1] = 0;
        Q[0][0][2] = 2;
        Q[0][0][3] = 2;
        assertEquals(10,learnerUtils.maxQVal(0,0,Q),10);
    }

    @Test
    public void testGetReward() throws Exception {
        int step = -1;
        int reward = 9;
        assertEquals(step,learnerUtils.getReward(1,1,maze,step,reward));
        assertEquals(step,learnerUtils.getReward(5,5,maze,step,reward));
        assertEquals(step,learnerUtils.getReward(4,5,maze,step,reward));
        assertEquals(reward,learnerUtils.getReward(5,6,maze,step,reward));
    }
}