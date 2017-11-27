import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cell implements Comparable {
    private CellType cellType;
    private int cost;
    private int row;
    private int col;
    private Comparator comparator;
    private int discoveryTime;

    public Cell(int row, int col, char type) {
        switch (type) {
            case 'S':
                cellType = CellType.START;
                cost = 0;
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

        this.row = row;
        this.col = col;
        this.discoveryTime = -1;
    }

    public CellType getCellType() {
        return cellType;
    }

    public int getCost() {
        return cost;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public int getDiscoveryTime() {
        return discoveryTime;
    }

    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    @Override
    public int compareTo(Object o) {
        return this.comparator.compare(this, o);
    }
}
