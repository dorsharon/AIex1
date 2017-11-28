import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("input3.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            Searcher searcher = null;

            // Define the search algorithm
            String s = br.readLine();
            if (s.equals("IDS")) {
                searcher = new IDSSearcher();
            } else if (s.equals("A*")) {
                searcher = new AStarSearcher();
            }

            // Define the grid size
            int gridSize = Integer.parseInt(br.readLine());
            Grid grid = new Grid(gridSize);

            // Define the grid cells
            for (int i = 0; i < gridSize; i++) {
                s = br.readLine();
                for (int j = 0; j < gridSize; j++) {
                    grid.setCell(i, j, s.charAt(j));
                }
            }

            SearchResult searchResult = searcher.findPath(grid);

            // Write output file
            file = new File("outputtest.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(searchResult.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
