import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

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
        State s2 = problem.transformState(problem.initialState, legalActions.get(2));
        State s3 = problem.transformState(problem.initialState, legalActions.get(3));

        // Test - Goal check
        System.out.println(s1.equals(s2));
        System.out.println(problem.isGoalState(problem.initialState));
        System.out.println(problem.isGoalState(s3));
    }
}
