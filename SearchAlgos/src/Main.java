import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main (String ... args) throws IOException {
        if (args.length != 2) {
            System.out.println("Please provide valid format: 'Main <algorithm> <input_file_path>'");
            return;
        }

        String algorithm = args[0];
        String fileName = args[1];

        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader);

        String input = null;
        while ((input = reader.readLine()) != null) {
            if (input.length() != 9+8) continue;
            startProblem(input, algorithm);
        }
    }

    public static void startProblem(String input, String algorithm) {
        // Problem instantiation
        Problem problem = new Problem(input);
        Search aiSearch = new Search();

        switch (algorithm) {
            case "dfs":
                Solution dfsSolution = aiSearch.dfsSearch(problem, 10);
                printSolution(dfsSolution);
                System.out.println("======= DFS END ======");
                break;
            case "ids":
                Solution idsSolution = aiSearch.idsSearch(problem);
                printSolution(idsSolution);
                System.out.println("======= IDS END ======");
                break;
            case "astar1":
                Solution aStarSolution = aiSearch.aStarSearch(problem, "H1");
                printSolution(aStarSolution);
                System.out.println("======= A*1 END ======");
                break;
            case "astar2":
                Solution aStarSolution2 = aiSearch.aStarSearch(problem, "H2");
                printSolution(aStarSolution2);
                System.out.println("======= A*2 END ======");
                break;
            default:
                System.out.println("Please provide valid algorithm ('dfs', 'ids', 'astar1', 'astar2') to run. ");
        }
    }

    private static void printSolution(Solution solution) {
        if (solution.goalNode != null) solution.tracePath();
        else {
            System.out.println("No solution found before depth 10.");
        }
    }
}
