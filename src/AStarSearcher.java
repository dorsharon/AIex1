import java.util.*;

public class AStarSearcher implements Searcher {
    private Map<Cell, Double> g;
    private Map<Cell, Double> h;

    @Override
    public SearchResult findPath(Grid grid) {
        g = new HashMap<>();
        h = new HashMap<>();

        initialize(grid);

        List<Cell> path = aStar(grid);

        Collections.reverse(path);
        int totalCost = 0;

        // Convert list of cells to a list of directions
        List<Direction> directions = new ArrayList<>();
        if (!path.isEmpty()) {
            for (int i = 0; i < path.size() - 1; i++) {
                directions.add(grid.getDirectionBetweenCells(path.get(i), path.get(i + 1)));
                totalCost += path.get(i + 1).getCost();
            }
        }

        for (Direction d : directions)
            System.out.println(d);

        return new SearchResult(directions, totalCost);
    }

    public void initialize(Grid grid) {
        for (Cell[] row : grid.getAllCells()) {
            for (Cell cell : row) {
                setComparator(cell);
                g.put(cell, Double.MAX_VALUE);
                h.put(cell, Double.MAX_VALUE);
            }
        }
    }

    @Override
    public void setComparator(Cell cell) {
        cell.setComparator(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Cell c1 = (Cell) o1, c2 = (Cell) o2;
                // First priority - f(x) = g(x)+h(x)
                if (f(c1) == f(c2)) {
                    // Second priority - discovery time
                    if (c1.getDiscoveryTime() == c2.getDiscoveryTime()) {
                        // Third priority - direction order
                        return Integer.compare(c1.getDirectionFromFather().getOrderIndex(),
                                c2.getDirectionFromFather().getOrderIndex());
                    } else {
                        return Integer.compare(c1.getDiscoveryTime(), c2.getDiscoveryTime());
                    }
                } else {
                    return Double.compare(f(c1), f(c2));
                }
            }
        });
    }

    public List<Cell> aStar(Grid grid) {
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>();

        // This map will always save the father of each cell's best path to it
        Map<Cell, Cell> prevInBestPath = new HashMap<>();

        // Initialize the starting cell
        Cell start = grid.getCell(0, 0);
        start.setDiscoveryTime(0);

        priorityQueue.add(start);
        g.put(start, (double) 0);
        h.put(start, calcHeuristic(grid, start));
        prevInBestPath.put(start, null);

        while (!priorityQueue.isEmpty()) {
            Cell currentCell = priorityQueue.poll();

            if (currentCell.getCellType() == CellType.FINISH) {
                return getBestPath(prevInBestPath, currentCell);
            }

            List<Cell> neighbours = grid.getNeighbours(currentCell);
            for (Cell neighbour : neighbours) {
                // If this neighbour is discovered for the first time
                if (neighbour.getDiscoveryTime() == -1)
                    neighbour.setDiscoveryTime(currentCell.getDiscoveryTime() + 1);

                neighbour.setDirectionFromFather(grid.getDirectionBetweenCells(currentCell, neighbour));

                priorityQueue.add(neighbour);

                double tentativeG = g.get(currentCell) + neighbour.getCost();
                if (tentativeG < g.get(neighbour)) {
                    g.put(neighbour, tentativeG);
                    h.put(neighbour, calcHeuristic(grid, neighbour));
                    prevInBestPath.put(neighbour, currentCell);
                }
            }
        }

        return null;
    }

    public List<Cell> getBestPath(Map<Cell, Cell> prevInBestPath, Cell finish) {
        List<Cell> path = new ArrayList<>();
        path.add(finish);

        Cell current = finish;
        while (prevInBestPath.get(current) != null) {
            path.add(prevInBestPath.get(current));
            current = prevInBestPath.get(current);
        }

        return path;
    }

    public double f(Cell cell) {
        return g.get(cell) + h.get(cell);
    }

    public double calcHeuristic(Grid grid, Cell cell) {
        Cell finish = grid.getCell(grid.getSize() - 1, grid.getSize() - 1);
        return Math.hypot(cell.getRow() - finish.getRow(), cell.getCol() - finish.getCol());
    }


}
