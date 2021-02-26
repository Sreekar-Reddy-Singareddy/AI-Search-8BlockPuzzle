import java.util.*;

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
        List<State> explored = new ArrayList<>();
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
                if (tempResult != null) return tempResult;
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

    /**
     * This method takes a problem with initial state as input
     * and runs A* search algorithm on it to find the goal.
     * This method uses the following heuristic:
     * "The total number of blocks that are not in their positions."
     */
    public Solution aStarSearch (Problem problem, String heuristic) {
        AStarNode resultNode = null;
        AStarNode initNode = new AStarNode(problem.initialState, null, null, 0, 0,0);
        Comparator<AStarNode> comparator = new Comparator<AStarNode>() {
            @Override
            public int compare(AStarNode a1, AStarNode a2) {
                return Integer.compare(a1.evaluationCost, a2.evaluationCost);
            }
        };
        PriorityQueue<AStarNode> frontier = new PriorityQueue<AStarNode>(10,comparator);
        List<State> coveredStates = new ArrayList<>();
        frontier.add(initNode);
        while (!frontier.isEmpty()) {
            AStarNode nodeToExpand = frontier.poll();
            if (resultNode != null && nodeToExpand.evaluationCost >= resultNode.evaluationCost) break;

            coveredStates.add(nodeToExpand.state);
            if (problem.isGoalState(nodeToExpand.state)) {
                resultNode = nodeToExpand;
                continue;
            }

            // After checking a node for goal, and before expanding further, check the depth of the node
            // If depth >= 10, we continue to the next node in the frontier.
            if (nodeToExpand.depth == 10) continue;

            for (Action action : problem.validActions(nodeToExpand.state)) {
                AStarNode childNode = createChildNode(problem, nodeToExpand, action);
                if (heuristic.equals("H1")) computeEvaluationCostHeuristic1(childNode);
                else if (heuristic.equals("H2")) computeEvaluationCostHeuristic2(childNode);
                if (!coveredStates.contains(childNode.state)) frontier.add(childNode);
            }
        }
        return new Solution(coveredStates.size(), resultNode);
    }

    /**
     * This method computes the evaluation cost of a node based on
     * the following heuristic:
     * If a block is not in its given position, we count such blocks.
     * - Total number of misplaced blocks is the heuristic cost -> h(n).
     * - The path cost for this node from the initial node -> g(n).
     */
    private void computeEvaluationCostHeuristic1(AStarNode node) {
        String[][] blocksOrientation = node.state.blocksOrientation;
        String[][] goalBlockOrientation = Problem.getGoalState().blocksOrientation;
        int misplacedBlocks = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (!blocksOrientation[i][j].equals(goalBlockOrientation[i][j])) misplacedBlocks++;
            }
        }
        node.evaluationCost = node.pathCost + misplacedBlocks;
    }

    /**
     * This method computes the evaluation cost of a node based on
     * the following heuristic:
     */
    private void computeEvaluationCostHeuristic2(AStarNode node) {
        String[][] blocksOrientation = node.state.blocksOrientation;
        HashMap<String, Integer[]> trueIndicesMap = getTrueIndicesForGoalState();
        int manhattanDistance = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                int iTrue = trueIndicesMap.get(blocksOrientation[i][j])[0];
                int jTrue = trueIndicesMap.get(blocksOrientation[i][j])[1];
                int dist = Math.abs(i - iTrue) + Math.abs(j - jTrue);
                manhattanDistance += dist;
            }
        }
        node.evaluationCost = node.pathCost + manhattanDistance;
    }

    private HashMap<String, Integer[]> getTrueIndicesForGoalState () {
        HashMap<String, Integer[]> trueIndicesMap = new HashMap<>();
        String [][] goalBlockOrientation = Problem.getGoalState().blocksOrientation;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++) {
                trueIndicesMap.put(goalBlockOrientation[i][j], new Integer[] {i,j});
            }
        }
        return trueIndicesMap;
    }

    private AStarNode createChildNode(Problem problem, AStarNode nodeToExpand, Action action) {
        State newState = problem.transformState(nodeToExpand.state, action);
        int stepCost = problem.stepCost(nodeToExpand.state, action);
        return new AStarNode(newState, nodeToExpand, action,
                nodeToExpand.pathCost+stepCost,0, nodeToExpand.depth+1);
    }
}
