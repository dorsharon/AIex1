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

        // Add the starting point to the path
        path.add(grid.getCell(0, 0));
        Collections.reverse(path);
        int totalCost = 0;

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
                // First priority - cost
                if (c1.getCost() == c2.getCost()) {
                    // Second priority - discovery time
                    if (c1.getDiscoveryTime() == c2.getDiscoveryTime()) {
                        // Third priority - direction order
                        return Integer.compare(c1.getDirectionFromFather().getOrderIndex(),
                                c2.getDirectionFromFather().getOrderIndex());
                    } else {
                        return Integer.compare(c1.getDiscoveryTime(), c2.getDiscoveryTime());
                    }
                } else {
                    return Integer.compare(c1.getCost(), c2.getCost());
                }
            }
        });
    }

    public List<Cell> aStar(Grid grid) {
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>();
        List<Cell> path = new ArrayList<>();

        Cell start = grid.getCell(0, 0);
        start.setDiscoveryTime(0);

        priorityQueue.add(start);
        g.put(start, (double) 0);
        h.put(start, calcHeuristic(grid, start));

        while (!priorityQueue.isEmpty()) {
            Cell currentCell = priorityQueue.poll();

            if (currentCell.getCellType() == CellType.FINISH) {
                return path;
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

                }
            }
        }

        return null;
    }

    public double f(Cell cell) {
        return g.get(cell) + h.get(cell);
    }

    public double calcHeuristic(Grid grid, Cell cell) {
        Cell finish = grid.getCell(grid.getSize() - 1, grid.getSize() - 1);
        return Math.hypot(cell.getRow() - finish.getRow(), cell.getCol() - finish.getCol());
    }


}
