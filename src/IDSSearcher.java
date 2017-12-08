import java.util.*;

public class IDSSearcher implements Searcher {
    private List<Coordinates> openList;

    @Override
    public SearchResult findPath(Grid grid) {
        List<Cell> path = new ArrayList<>();
        int gridTotalSize = grid.getSize() * grid.getSize();

        initialize(grid);
        Cell start = grid.generateCell(0, 0);

        // Find the path (reversed)
        for (int depth = 0; depth < gridTotalSize; depth++) {
            openList.add(start.coordinates);
            if (DFS(grid, start, path, depth)) {
                break;
            }
        }

        // Add the starting point to the path
        path.add(start);
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

    @Override
    public void initialize(Grid grid) {
        openList = new ArrayList<>();
    }

    public Boolean DFS(Grid grid, Cell cell, List<Cell> path, int depth) {
        // If you've reached the FINISH cell
        if (cell.cellType == CellType.FINISH) {
            return true;
        }

        // If you've reached the final allowed depth
        if (depth <= 0) {
            openList.remove(cell.coordinates);
            return false;
        }

        // Recursively go over the neighbours
        List<Cell> neighbours = grid.getNeighbours(cell);
        for (Cell neighbour : neighbours) {
            // Duplicate pruning
            if (!openList.contains(neighbour.coordinates)) {
                openList.add(neighbour.coordinates);
                if (DFS(grid, neighbour, path, depth - 1)) {
                    path.add(neighbour);
                    return true;
                }
            }
        }

        openList.remove(cell.getCoordinates());
        return false;
    }
}
