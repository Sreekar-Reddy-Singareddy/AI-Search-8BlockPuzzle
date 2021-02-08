import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main (String ... args) throws IOException {
        System.out.println(args.length);
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader reader = new BufferedReader(fileReader);
        String input = reader.readLine();

        // Problem instantiation
        Problem problem = new Problem(input);

        // DFS Search
        Search aiSearch = new Search();
        Node result = aiSearch.dfsSearch(problem, 10);
        result.tracePath();
    }
}
