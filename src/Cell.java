import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cell {
    public CellType cellType;
    public int cost;
    public Coordinates coordinates;
    public int discoveryTime;
    public Direction directionFromFather;

    public Cell(int row, int col, char type) {
        switch (type) {
            case 'S':
                cellType = CellType.START;
                cost = Integer.MAX_VALUE;
                break;
            case 'G':
                cellType = CellType.FINISH;
                cost = 0;
                break;
            case 'R':
                cellType = CellType.PAVED;
                cost = 1;
                break;
            case 'D':
                cellType = CellType.UNPAVED;
                cost = 3;
                break;
            case 'H':
                cellType = CellType.HILL;
                cost = 10;
                break;
            case 'W':
                cellType = CellType.WATER;
                cost = 0;
                break;
            default:
                break;
        }

        this.coordinates = new Coordinates(row, col);
        this.discoveryTime = -1;
    }

    public int getRow() {
        return coordinates.getRow();
    }

    public int getCol() {
        return coordinates.getCol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return coordinates != null ? coordinates.equals(cell.coordinates) : cell.coordinates == null;
    }

    @Override
    public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
