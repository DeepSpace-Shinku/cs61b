public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y) {

        x = java.lang.Character.toLowerCase(x);
        y = java.lang.Character.toLowerCase(y);
        return Math.abs(x - y) == 1 || Math.abs(Math.abs(x - y) - 32) == 1;
    }
}
