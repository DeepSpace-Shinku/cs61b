public class TestPlanet
{
    public static void main(String[] args)
    {
        Planet planetA = new Planet(1, 2, 0, 0, 2e5, "sun");
        Planet planetB = new Planet(7, 10, 0, 0, 5e6, "earth");
        System.out.println(planetA.calcForceExertedBy(planetB));
    }
}