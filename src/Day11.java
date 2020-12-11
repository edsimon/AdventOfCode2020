import Helpers.Reader;

public class Day11 {
    public static void main(String[] args) {
        System.out.println("Task A: " + run(true));
        System.out.println("Task B: " + run(false));
    }

    private static int run(boolean isPart1) {
        char[][] inputGrid = Reader.getCharMatrix("day11.txt");
        World currentLayout = new World(inputGrid);
        World nextState= new World(getNextState(currentLayout.getGrid(), isPart1));

        while (!currentLayout.equals(nextState)) {
            currentLayout = nextState;
            nextState = new World(getNextState(currentLayout.getGrid(), isPart1));
        }
        return nextState.getNumOfTakenSeats();
    }

    private static char[][] getNextState(char[][] state, boolean isPart1) {
        char[][] nextState = new char[state.length][state[0].length];
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0},{-1,1},{1,1},{1,-1},{-1,-1}};
        int nTaken;
        for(int i=0;i<state.length;i++) {
            for(int j=0;j<state[0].length;j++) {
                nTaken = 0;
                for(int[] dir:dirs) {
                    if(isPart1) {
                        if(isTakenA(i,j,dir,state.length,state[0].length,state)) nTaken++;
                    }
                    else if(isTakeB(i,j,dir,state.length,state[0].length,state)) nTaken++;
                }
                if(state[i][j] == 'L' && nTaken==0) nextState[i][j] = '#';
                else if(isPart1 && state[i][j] == '#' && nTaken>=4) nextState[i][j] = 'L';
                else if(!isPart1 && state[i][j] == '#' && nTaken>=5) nextState[i][j] = 'L';
                else nextState[i][j] = state[i][j];
            }
        }return nextState;
    }

    private static boolean isTakenA(int row, int col, int[] dir, int rows, int cols, char[][] currentState) {
        return (!isOutOfRange(row+dir[0], col+dir[1], rows, cols) && currentState[row+dir[0]][col+dir[1]] == '#');
    }

    private static boolean isTakeB(int row, int col, int[] dir, int rows, int cols, char[][] currentState) {
        if(isOutOfRange(row+dir[0], col+dir[1], rows, cols) || currentState[row+dir[0]][col+dir[1]] == 'L') return false;
        if(currentState[row+dir[0]][col+dir[1]] == '#') return true;
        return isTakeB(row+dir[0], col+dir[1], dir, rows, cols, currentState);
    }

    private static boolean isOutOfRange(int x, int y, int rows, int cols) {
        return x < 0 || x >= rows || y < 0 || y >= cols;
    }

    public static class World {
        final private char[][] grid;
        private int occupiedSeatCount;

        public char[][] getGrid() {
            return grid;
        }

        public World(char[][] grid) {
            this.grid = grid;
        }

        public int getNumOfTakenSeats() {
            for (char[] chars : grid) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (chars[j] == '#') occupiedSeatCount++;
                }
            }
            return occupiedSeatCount;
        }

        public boolean equals(World inputGrid) {
            for(int i=0;i<grid.length;i++) {
                for(int j=0;j<grid[0].length;j++) {
                    if(grid[i][j] != inputGrid.getGrid()[i][j]) return false;
                }
            }return true;
        }
    }
}