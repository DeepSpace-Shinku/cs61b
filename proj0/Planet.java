public class Planet
{
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img)
    {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p)
    {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet other)
    {
        double dx = this.xxPos - other.xxPos;
        double dy = this.yyPos - other.yyPos;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    public double calcForceExertedBy(Planet other)
    {
        final double G = 6.67e-11;
        return G * this.mass * other.mass / (this.calcDistance(other) * this.calcDistance(other));
    }

    public double calcForceExertedByX(Planet other)
    {
        return calcForceExertedBy(other) * (other.xxPos - this.xxPos) / this.calcDistance(other);
    }

    public double calcForceExertedByY(Planet other)
    {
        return calcForceExertedBy(other) * (other.yyPos - this.yyPos) / this.calcDistance(other);
    }

    public double calcNetForceExertedByX(Planet[] others)
    {
        double net_force = 0;
        for(int i = 0; i < others.length; i += 1)
        {
            if(this == others[i])
            {
                continue;
            }
            net_force += this.calcForceExertedByX(others[i]);
        }
        return net_force;
    }

    public double calcNetForceExertedByY(Planet[] others)
    {
        double netForce = 0;
        for(int i = 0; i < others.length; i += 1)
        {
            if(this == others[i])
            {
                continue;
            }
            netForce += this.calcForceExertedByY(others[i]);
        }
        return netForce;
    }

    public void update(double dt, double xForce, double yForce)
    {
        double aX = xForce / mass;
        double aY = yForce / mass;

        xxVel = xxVel + dt * aX;
        yyVel = yyVel + dt * aY;
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }

    public void draw()
    {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}