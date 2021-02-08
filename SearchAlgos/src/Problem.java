import java.util.ArrayList;
import java.util.List;

/**
 * This class captures all the components of a general AI problem.
 * In this case, it is specific to 8-Block-Puzzle.
 */

public class Problem {
    private static int GRID_SIZE = 3;
    private static int[] xDir = new int[]{0,1,0,-1};
    private static int[] yDir = new int[]{-1,0,1,0};
    private static State GOAL_STATE;
    public State initialState;
    public Integer pathCost;
    private List<Action> allActions = new ArrayList<>();

    public Problem (String inputState) {
        String[] strArr = inputState.split(" ");

        String [][] blockOrientation = new String[GRID_SIZE][GRID_SIZE];
        int[] blankSpacePosition = new int[2];
        int k = 0;
        for (int i=0; i<GRID_SIZE; i++) {
            for (int j=0; j<GRID_SIZE; j++) {
                blockOrientation[i][j] = strArr[k++];
                if (blockOrientation[i][j].equals("*")) {
                    blankSpacePosition[0] = i;
                    blankSpacePosition[1] = j;
                }
            }
        }
        initialState = new State(blockOrientation,blankSpacePosition);
        pathCost = 0;
        allActions.add(new Action(Direction.LEFT, new int[]{0,-1}));
        allActions.add(new Action(Direction.DOWN, new int[]{1,0}));
        allActions.add(new Action(Direction.RIGHT, new int[]{0,1}));
        allActions.add(new Action(Direction.UP, new int[]{-1,0}));
        GOAL_STATE = new State(
                new String[][]{{"1","2","3"},{"4","5","6"},{"7","8","*"}},
                new int[]{GRID_SIZE/2,GRID_SIZE/2});
    }

    /**
     * Given a State, this returns a list of valid Action(s) possible.
     * Three possible scenarios:
     * 1. Blank space in corners (0,0) (0,2) (2,0) (2,2) - 2 valid actions
     * 2. Blank space in edges (0,1) (1,0) (1,2) (2,1) - 3 valid actions
     * 3. Blank space in center (1,1) - 4 valid actions
     */
    public List<Action> validActions (State state) {
        List<Action> actions = new ArrayList<>();
        int i = state.blankSpacePosition[0], j = state.blankSpacePosition[1];
        if (inBounds(i+xDir[0], j+yDir[0])) actions.add(allActions.get(0));
        if (inBounds(i+xDir[1], j+yDir[1])) actions.add(allActions.get(1));
        if (inBounds(i+xDir[2], j+yDir[2])) actions.add(allActions.get(2));
        if (inBounds(i+xDir[3], j+yDir[3])) actions.add(allActions.get(3));
        return actions;
    }

    /**
     * Given a State and Action combination, this method computes the resulting
     * state and returns it.
     */
    public State transformState (State state, Action action) {
        int i = state.blankSpacePosition[0], j = state.blankSpacePosition[1];
        int iNew = i + action.coordinates[0], jNew = j + action.coordinates[1];

        String[][] blockOrientationCopy = copyOrientation(state.blocksOrientation);
        State newState = new State(blockOrientationCopy, state.blankSpacePosition.clone());
        newState.blocksOrientation[i][j] = newState.blocksOrientation[iNew][jNew];
        newState.blocksOrientation[iNew][jNew] = "*";

        newState.blankSpacePosition[0] = iNew;
        newState.blankSpacePosition[1] = jNew;
        return newState;
    }

    /**
     * Checks whether the given State is a goal or not
     */
    public boolean isGoalState (State state) {
        return state.equals(GOAL_STATE);
    }

    /**
     * For a given State and Action combination, this method
     * returns the step cost involved in performing that action
     * while in the state.
     */
    public int stepCost (State state, Action action) {
        return -1;
    }

    /**
     * Checks whether this new position is in bounds or not.
     */
    private boolean inBounds(int i, int j) {
        return i>=0 && j>=0 && i<GRID_SIZE && j<GRID_SIZE;
    }

    private String[][] copyOrientation(String[][] blocksOrientation) {
        String[][] arr = new String[3][3];
        for (int i=0; i<3; i++) {
            arr[i] = blocksOrientation[i].clone();
        }
        return arr;
    }
}
