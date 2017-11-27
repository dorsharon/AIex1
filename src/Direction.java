public enum Direction {
    RIGHT("R", 0), LEFT("L", 1), UP("U", 2), DOWN("D", 3), RIGHTUP("RU", 4), RIGHTDOWN("RD", 5), LEFTUP("LU", 6), LEFTDOWN("LD", 7);

    private final String value;
    private final int orderIndex;

    private Direction(String value, int orderIndex) {
        this.value = value;
        this.orderIndex = orderIndex;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @Override
    public String toString() {
        return value;
    }
}
