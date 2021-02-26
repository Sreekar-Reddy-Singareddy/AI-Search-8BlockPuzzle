import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Test {
    public static void main (String... args) throws Exception{
        System.out.println(args.length);
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader reader = new BufferedReader(fileReader);
        String input = reader.readLine();

        // Test - Problem formulation
        Problem problem = new Problem(input);

        // Test - Legal actions based on state
        List<Action> legalActions = problem.validActions(problem.initialState);

        // Test - Transformation from one state to another
        State s1 = problem.transformState(problem.initialState, legalActions.get(0));
        State s2 = problem.transformState(problem.initialState, legalActions.get(1));
        State s4 = new State(s1.blocksOrientation, s1.blankSpacePosition);

        // Test - Goal check
        System.out.println(s1.equals(s2));
        System.out.println(problem.isGoalState(problem.initialState));

        // Test - Node formation
        Node rootNode = new Node(problem.initialState, null, null, 0);
        Node child = new Node (s1, rootNode, legalActions.get(0), 1);
        Node grandchild = new Node (s2, child, legalActions.get(1), 2);

        // Test - Duplicate states
        List<State> statesArr = new ArrayList<>();
        statesArr.add(s1);
        statesArr.add(s2);
        System.out.println("**"+statesArr.contains(s1));
        System.out.println("**"+statesArr.contains(s4));

        // Test - Priority Queue
        Comparator<AStarNode> comparator = new Comparator<AStarNode>() {
            @Override
            public int compare(AStarNode o1, AStarNode o2) {
                return Integer.compare(o2.evaluationCost, o1.evaluationCost);
            }
        };
        PriorityQueue<AStarNode> queue = new PriorityQueue<AStarNode>(10,comparator);
        AStarNode n1 = new AStarNode(null, null, null, 0, 1,0);
        AStarNode n2 = new AStarNode(null, null, null, 0, 2,0);
        AStarNode n3 = new AStarNode(null, null, null, 0, 3,0);
        queue.add(n1);
        queue.add(n2);
        queue.add(n3);
        System.out.println(queue.peek().evaluationCost);

    }
}
