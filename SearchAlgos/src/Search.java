import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the search algorithms
 * that are implemented for the 8 Block Puzzle
 * problem.
 * 1. Depth First Search
 * 2. Iterative Deepening Search
 * 3. A* Heuristic Search (i)
 * 4. A* Heuristic Search (ii)
 */

public class Search {

    /**
     * This method takes a problem instance and tries
     * to find a solution to it using depth first search
     * algorithm. It is implemented by taking the pseudo
     * code from the text book as reference. Search happens
     * only till a maximum limit as given by depthLimit.
     */
    public Solution dfsSearch (Problem problem, Integer depthLimit) {
        Node initNode = new Node(problem.initialState, null, null, 0);
        List<State> explored = new ArrayList<State>();
        explored.add(initNode.state);
        Node result = dfsRecursion(initNode, problem, depthLimit, explored);
        return new Solution(explored.size(), result);
    }

    private Node dfsRecursion(Node node, Problem problem, Integer pendingDepth, List<State> explored) {
        if (problem.isGoalState(node.state)) return node;
        else if (pendingDepth == 0) return null;
        else {
            Node result = null;
            for (Action action : problem.validActions(node.state)) {
                State newState = problem.transformState(node.state, action);
                int stepCost = problem.stepCost(node.state, action);

                if (explored.contains(newState)) continue;
                explored.add(newState);

                Node childNode = new Node(newState, node, action, stepCost+ node.pathCost);
                Node tempResult = dfsRecursion(childNode, problem, pendingDepth-1, explored);
                if (result == null && tempResult != null) result = tempResult;
            }
            return result;
        }
    }

    /**
     * This method performs Iterative Deepening Search on the
     * given problem and returns a solution if found. Limit
     * of depth is 10 in this case. If solution is not found
     * at or before 10 depth, the method return an empty solution.
     * @param problem
     * @return
     */
    public Solution idsSearch (Problem problem) {
        Solution result = null;
        int depthLimit = 0;
        while (depthLimit <= 10) {
            result = dfsSearch(problem, depthLimit);
            if (result.goalNode != null) return result;
            depthLimit++;
        }
        return result;
    }
}
