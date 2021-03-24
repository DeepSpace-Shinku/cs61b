public class test {
    private static String stringGenerator(String functionName, int parameter)
    {
        return functionName + '(' + String.valueOf(parameter) + ')';
    }

    public void main(String[] args)
    {
        System.out.println(stringGenerator("f", 1));
    }
}
