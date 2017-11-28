import java.util.*;

public class IDSSearcher implements Searcher {
    @Override
    public SearchResult findPath(Grid grid) {
        List<Cell> path = new ArrayList<>();
        int gridTotalSize = grid.getSize() * grid.getSize();

        initialize(grid);

        // Find the path (reversed)
        for (int depth = 0; depth < gridTotalSize; depth++) {
            if (DFS(grid, grid.getCell(0, 0), path, depth)) {
                break;
            }
        }

        // Add the starting point to the path
        path.add(grid.getCell(0, 0));
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

    @Override
    public void initialize(Grid grid) {
        for (Cell[] row : grid.getAllCells()) {
            for (Cell cell : row) {
                setComparator(cell);
            }
        }
    }

    public void setComparator(Cell cell) {
        cell.setComparator(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Cell c1 = (Cell) o1, c2 = (Cell) o2;
                // First priority - discovery time
                if (c1.getDiscoveryTime() == c2.getDiscoveryTime()) {
                    // Second priority - direction order
                    return Integer.compare(c1.getDirectionFromFather().getOrderIndex(),
                            c2.getDirectionFromFather().getOrderIndex());
                } else {
                    return Integer.compare(c1.getDiscoveryTime(), c2.getDiscoveryTime());
                }
            }
        });
    }

    public Boolean DFS(Grid grid, Cell cell, List<Cell> path, int depth) {
        if (cell.getCellType() == CellType.FINISH) {
            return true;
        }

        if (depth <= 0) {
            return false;
        }

        List<Cell> neighbours = grid.getNeighbours(cell);
        for (Cell neighbour : neighbours) {
            if (DFS(grid, neighbour, path, depth - 1)) {
                path.add(neighbour);
                return true;
            }
        }

        return false;
    }
}
