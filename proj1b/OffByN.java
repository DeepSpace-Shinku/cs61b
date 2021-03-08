public class OffByN implements CharacterComparator{
    private int n;

    public OffByN(int paraN)
    {
        n = paraN;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == n;
    }
}