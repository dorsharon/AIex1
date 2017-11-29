import java.util.ArrayList;
import java.util.List;

public class Grid {
    private char[][] cells;
    private int size;

    public Grid(int size) {
        this.cells = new char[size][size];
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public char getCell(int i, int j) {
        return cells[i][j];
    }

    public char[][] getAllCells() {
        return cells;
    }

    public void setCell(int i, int j, char type) {
        cells[i][j] = type;
    }

    public Cell generateCell(int i, int j) {
        return new Cell(i, j, getCell(i, j));
    }

    public List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        checkNeighboursInDirection(neighbours, cell, Direction.RIGHT);
        checkNeighboursInDirection(neighbours, cell, Direction.RIGHTDOWN);
        checkNeighboursInDirection(neighbours, cell, Direction.DOWN);
        checkNeighboursInDirection(neighbours, cell, Direction.LEFTDOWN);
        checkNeighboursInDirection(neighbours, cell, Direction.LEFT);
        checkNeighboursInDirection(neighbours, cell, Direction.LEFTUP);
        checkNeighboursInDirection(neighbours, cell, Direction.UP);
        checkNeighboursInDirection(neighbours, cell, Direction.RIGHTUP);

        return neighbours;
    }

    public Cell getNeighbourInDirection(Cell cell, Direction dir) {
        int row = cell.getRow(), col = cell.getCol();

        switch (dir) {
            case RIGHT:
                if (col < size - 1)
                    return generateCell(row, col + 1);
                return null;
            case RIGHTDOWN:
                if (col < size - 1 && row < size - 1)
                    return generateCell(row + 1, col + 1);
                return null;
            case DOWN:
                if (row < size - 1)
                    return generateCell(row + 1, col);
                return null;
            case LEFTDOWN:
                if (col > 0 && row < size - 1)
                    return generateCell(row + 1, col - 1);
                return null;
            case LEFT:
                if (col > 0)
                    return generateCell(row, col - 1);
                return null;
            case LEFTUP:
                if (col > 0 && row > 0)
                    return generateCell(row - 1, col - 1);
                return null;
            case UP:
                if (row > 0)
                    return generateCell(row - 1, col);
                return null;
            case RIGHTUP:
                if (row > 0 && col < size - 1)
                    return generateCell(row - 1, col + 1);
                return null;
            default:
                return null;
        }
    }

    private void checkNeighboursInDirection(List<Cell> neighbours, Cell cell, Direction dir) {
        Cell dirNeighbour = getNeighbourInDirection(cell, dir);

        if (dirNeighbour != null) {
            switch (dir) {
                // Check the straight directions by simply seeing if they're water
                case RIGHT:
                case LEFT:
                case UP:
                case DOWN:
                    if (dirNeighbour.getCellType() != CellType.WATER) {
                        neighbours.add(dirNeighbour);
                    }
                    break;

                // Diagonal direction require checking the appropriate direct directions for water
                case RIGHTUP:
                    if (getNeighbourInDirection(cell, Direction.RIGHT).getCellType() != CellType.WATER &&
                            getNeighbourInDirection(cell, Direction.UP).getCellType() != CellType.WATER) {
                        neighbours.add(dirNeighbour);
                    }
                    break;
                case RIGHTDOWN:
                    if (getNeighbourInDirection(cell, Direction.RIGHT).getCellType() != CellType.WATER &&
                            getNeighbourInDirection(cell, Direction.DOWN).getCellType() != CellType.WATER) {
                        neighbours.add(dirNeighbour);
                    }
                    break;
                case LEFTUP:
                    if (getNeighbourInDirection(cell, Direction.LEFT).getCellType() != CellType.WATER &&
                            getNeighbourInDirection(cell, Direction.UP).getCellType() != CellType.WATER) {
                        neighbours.add(dirNeighbour);
                    }
                    break;
                case LEFTDOWN:
                    if (getNeighbourInDirection(cell, Direction.LEFT).getCellType() != CellType.WATER &&
                            getNeighbourInDirection(cell, Direction.DOWN).getCellType() != CellType.WATER) {
                        neighbours.add(dirNeighbour);
                    }
                    break;
            }
        }
    }

    public Direction getDirectionBetweenCells(Cell cell1, Cell cell2) {
        int rowDiff = cell1.getRow() - cell2.getRow();
        int colDiff = cell1.getCol() - cell2.getCol();

        if (rowDiff == 0 && colDiff == -1)
            return Direction.RIGHT;
        else if (rowDiff == 0 && colDiff == 1)
            return Direction.LEFT;
        else if (rowDiff == -1 && colDiff == 0)
            return Direction.DOWN;
        else if (rowDiff == 1 && colDiff == 0)
            return Direction.UP;
        else if (rowDiff == 1 && colDiff == -1)
            return Direction.RIGHTUP;
        else if (rowDiff == -1 && colDiff == -1)
            return Direction.RIGHTDOWN;
        else if (rowDiff == 1 && colDiff == 1)
            return Direction.LEFTUP;
        else if (rowDiff == -1 && colDiff == 1)
            return Direction.LEFTDOWN;
        else
            return null;
    }
}
