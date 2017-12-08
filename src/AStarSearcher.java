import java.util.*;

public class AStarSearcher implements Searcher {
    private Map<Coordinates, Double> g;
    private Map<Coordinates, Double> h;

    @Override
    public SearchResult findPath(Grid grid) {
        initialize(grid);

        List<Cell> path = aStar(grid);

        Collections.reverse(path);
        int totalCost = 0;

        // Convert list of cells to a list of directions
        List<Direction> directions = new ArrayList<>();
        if (!path.isEmpty()) {
            for (int i = 0; i < path.size() - 1; i++) {
                directions.add(grid.getDirectionBetweenCells(path.get(i), path.get(i + 1)));
                totalCost += path.get(i + 1).cost;
            }
        }

        return new SearchResult(directions, totalCost);
    }

    public void initialize(Grid grid) {
        g = new HashMap<>();
        h = new HashMap<>();
        int gridSize = grid.getSize();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                g.put(new Coordinates(i, j), Double.MAX_VALUE);
                h.put(new Coordinates(i, j), Double.MAX_VALUE);
            }
        }
    }

    public Comparator<Cell> initComparator() {
        return new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                // First priority - f(x) = g(x)+h(x)
                if (f(c1) == f(c2)) {
                    // Second priority - discovery time
                    if (c1.discoveryTime == c2.discoveryTime) {
                        // Third priority - direction order
                        return c1.directionFromFather.getOrderIndex() < c2.directionFromFather.getOrderIndex() ? -1 : 1;
                    } else {
                        return c1.discoveryTime < c2.discoveryTime ? -1 : 1;
                    }
                } else {
                    return f(c1) < f(c2) ? -1 : 1;
                }
            }
        };
    }

    public List<Cell> aStar(Grid grid) {
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>(1, initComparator());

        // This map will always save the father of each cell's best path to it
        Map<Cell, Cell> prevInBestPath = new HashMap<>();

        // Initialize the starting cell
        Cell start = grid.generateCell(0, 0);
        start.discoveryTime = 0;

        priorityQueue.add(start);
        g.put(start.coordinates, (double) 0);
        h.put(start.coordinates, calcHeuristic(grid, start.coordinates));
        prevInBestPath.put(start, null);

        while (!priorityQueue.isEmpty()) {
            Cell currentCell = priorityQueue.poll();

            if (currentCell.cellType == CellType.FINISH) {
                return getBestPath(prevInBestPath, currentCell);
            }

            List<Cell> neighbours = grid.getNeighbours(currentCell);
            for (Cell neighbour : neighbours) {
                // Duplicate pruning
                if (!priorityQueue.contains(neighbour)) {
                    // If this neighbour is discovered for the first time
                    if (neighbour.discoveryTime == -1)
                        neighbour.discoveryTime = currentCell.discoveryTime + 1;

                    neighbour.directionFromFather = grid.getDirectionBetweenCells(currentCell, neighbour);

                    // See if you've found a better path to the current neighbour
                    double tentativeG = g.get(currentCell.coordinates) + neighbour.cost;
                    if (tentativeG < g.get(neighbour.coordinates)) {
                        g.put(neighbour.coordinates, tentativeG);
                        h.put(neighbour.coordinates, calcHeuristic(grid, neighbour.coordinates));
                        prevInBestPath.put(neighbour, currentCell);
                    }

                    priorityQueue.add(neighbour);
                }
            }
        }

        return null;
    }


    public List<Cell> getBestPath(Map<Cell, Cell> prevInBestPath, Cell finish) {
        List<Cell> path = new ArrayList<>();
        path.add(finish);

        // Keep adding the father in each node's best path to it until you reach
        // the START cell, who's father is NULL.
        Cell current = finish;
        while (prevInBestPath.get(current) != null) {
            path.add(prevInBestPath.get(current));
            current = prevInBestPath.get(current);
        }

        return path;
    }

    public double f(Cell cell) {
        return g.get(cell.coordinates) + h.get(cell.coordinates);
    }

    public double calcHeuristic(Grid grid, Coordinates cell) {
        // Calculate chess distance
        Coordinates finish = new Coordinates(grid.getSize() - 1, grid.getSize() - 1);
        return Math.max(cell.getRow() - finish.getRow(), cell.getCol() - finish.getCol());
    }


}
