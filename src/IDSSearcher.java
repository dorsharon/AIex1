import java.util.*;

public class IDSSearcher implements Searcher {
    @Override
    public SearchResult findPath(Grid grid) {
        List<Cell> path = new ArrayList<>();
        int gridTotalSize = grid.getSize() * grid.getSize();

        setComparator(grid);

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

    public void setComparator(final Grid grid) {
        for (Cell[] row : grid.getAllCells()) {
            for (Cell cell : row) {
                cell.setComparator(new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Cell c1 = (Cell) o1, c2 = (Cell) o2;
                        // First priority - discovery time
                        if (c1.getDiscoveryTime() == c2.getDiscoveryTime()) {
                            // Second priority - direction order
                            return grid.getDirectionBetweenCells(c1, c2).getOrderIndex();
                        } else {
                            return c1.getDiscoveryTime() - c2.getDiscoveryTime();
                        }
                    }
                });
            }
        }
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
