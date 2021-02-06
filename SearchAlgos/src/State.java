import java.util.Arrays;
import java.util.List;

/**
 * Representation of a state. In this case, it a list
 * of strings. List is of constant length 9. Each element
 * represents either a block or an empty space.
 */
public class State {
    public String[][] blocksOrientation;
    public int[] blankSpacePosition;

    public State (String[][] blocksOrientation, int[] blankSpace) {
        this.blocksOrientation = blocksOrientation;
        this.blankSpacePosition = blankSpace;
    }

    @Override
    public boolean equals(Object o) {
        State s = (State) o;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (!this.blocksOrientation[i][j].equals(s.blocksOrientation[i][j])) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                builder.append(blocksOrientation[i][j]+" ");
            }
            builder.append("\b\n");
        }
        return builder.toString();
    }
}
