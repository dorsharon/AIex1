import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSearcher implements Searcher {
    @Override
    public SearchResult findPath(Grid grid) {
        setComparator(grid);
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>();
        Map<Cell, Integer> g = new HashMap<>();
        Map<Cell, Integer> h = new HashMap<>();

        priorityQueue.add(grid.getCell(0, 0));

        while (!priorityQueue.isEmpty()) {
            Cell cell = priorityQueue.poll();

        }
    }

    @Override
    public void setComparator(Grid grid) {

    }

    public int getG(Grid grid, Cell cell) {

    }


}
