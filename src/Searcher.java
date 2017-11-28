public interface Searcher {
    public SearchResult findPath(Grid grid);

    public void setComparator(Cell cell);

    public void initialize(Grid grid);
}
