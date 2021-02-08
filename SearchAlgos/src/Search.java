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
    public Node dfsSearch (Problem problem, Integer depthLimit) {
        Node initNode = new Node(problem.initialState, null, null, 0);
        return dfsRecursion(initNode, problem, depthLimit);
    }

    private Node dfsRecursion(Node node, Problem problem, Integer pendingDepth) {
        if (problem.isGoalState(node.state)) return node;
        else if (pendingDepth == 0) return null;
        else {
            Node result = null;
            for (Action action : problem.validActions(node.state)) {
                State newState = problem.transformState(node.state, action);
                int stepCost = problem.stepCost(node.state, action);
                Node childNode = new Node(newState, node, action, stepCost+ node.pathCost);
                Node tempResult = dfsRecursion(childNode, problem, pendingDepth-1);
                if (result == null && tempResult != null) result = tempResult;
            }
            return result;
        }
    }
}
