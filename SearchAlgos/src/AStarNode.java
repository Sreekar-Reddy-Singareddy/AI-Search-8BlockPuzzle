import java.util.Comparator;
import java.util.Objects;

public class AStarNode extends Node implements Comparator {

    public int evaluationCost;
    public int depth;

    public AStarNode (State state, Node parentNode, Action action, Integer pathCost, Integer evaluationCost, Integer depth) {
        super(state,parentNode,action,pathCost);
        this.evaluationCost = evaluationCost;
        this.depth = depth;
    }

    @Override
    public int compare(Object o1, Object o2) {
        AStarNode a1 = (AStarNode) o1;
        AStarNode a2 = (AStarNode) o2;
        return Integer.compare(a1.evaluationCost, a2.evaluationCost);
    }
}
