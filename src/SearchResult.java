import java.util.List;

public class SearchResult {
    private List<Direction> directions;
    private int totalCost;

    public SearchResult(List<Direction> directions, int totalCost) {
        this.directions = directions;
        this.totalCost = totalCost;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public int getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        String str = "";
        if (directions.size() == 0) {
            return "no path";
        } else {
            str += directions.get(0).toString();
            for (int i = 1; i < directions.size(); i++)
                str += "-" + directions.get(i).toString();
            str += " " + totalCost;
        }
        return str;
    }
}
