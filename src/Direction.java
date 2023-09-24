public enum Direction {
    UP("UP"),
    DOWN("DOWN");

    private final String str;
     Direction(String str)
    {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
