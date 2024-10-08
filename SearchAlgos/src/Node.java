import javafx.scene.Parent;

/**
 * This class represents a Node in search tree.
 * Different from State. Node contains all information
 * about the path taken to reach a particular state
 * from the initial state. We can return this alone
 * and trace all the steps taken from the beginning.
 */
public class Node {
    public State state;
    public Node parentNode;
    public Action action;
    public Integer pathCost;

    public Node(State state, Node parentNode, Action action, Integer pathCost) {
        this.state = state;
        this.parentNode = parentNode;
        this.action = action;
        this.pathCost = pathCost;
    }
}

