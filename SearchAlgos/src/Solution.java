public class Solution {
    public int totalStatesExplored = 0;
    public Node goalNode = null;

    public Solution( int totalStatesExplored, Node goalNode) {
        this.totalStatesExplored = totalStatesExplored;
        this.goalNode = goalNode;
    }

    public void tracePath() {
        int steps = tracePath(goalNode);
        System.out.println("Total Steps: "+steps);
        System.out.println("Total States Explored: "+totalStatesExplored);
    }

    private int tracePath(Node node) {
        if (node == null) return 0;
        int steps = tracePath(node.parentNode);
        System.out.println(node.state.toString());
        return steps+1;
    }
}
