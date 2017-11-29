import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cell {
    private CellType cellType;
    private int cost;
    private Coordinates coordinates;
    private int discoveryTime;
    private Direction directionFromFather;

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

    public CellType getCellType() {
        return cellType;
    }

    public int getCost() {
        return cost;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getRow() {
        return coordinates.getRow();
    }

    public int getCol() {
        return coordinates.getCol();
    }

    public int getDiscoveryTime() {
        return discoveryTime;
    }

    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public Direction getDirectionFromFather() {
        return directionFromFather;
    }

    public void setDirectionFromFather(Direction directionFromFather) {
        this.directionFromFather = directionFromFather;
    }
}
