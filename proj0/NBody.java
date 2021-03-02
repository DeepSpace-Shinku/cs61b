public class NBody
{
    public static void main(String[] args)
    {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double universe_radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-universe_radius, universe_radius);

        for(double time = 0; time <= T; time += dt) {
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int i = 0; i < planets.length; i += 1) {
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i += 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i += 1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universe_radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }

    public static double readRadius(String file_directory)
    {
        In in = new In(file_directory);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String file_directory)
    {
        In in = new In(file_directory);
        int number_of_planets =  in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[number_of_planets];
        for(int i = 0; i < number_of_planets; i += 1)
        {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return planets;
    }
}