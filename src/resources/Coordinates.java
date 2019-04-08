package resources;

public class Coordinates {

    private double x;
    private double y;
    private double z;

    public Coordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getRelative() {
        String xLoc = (x == 0.0) ? "" : x + "";
        String yLoc = (y == 0.0) ? "" : y + "";
        String zLoc = (z == 0.0) ? "" : z + "";
        return "~" + xLoc + " ~" + yLoc + " ~" + zLoc;
    }

    public String getInverseRelative() {
        String xLoc = (x == 0.0) ? "" : (-1*x) + "";
        String yLoc = (y == 0.0) ? "" : (-1*y) + "";
        String zLoc = (z == 0.0) ? "" : (-1*z) + "";
        return "~" + xLoc + " ~" + yLoc + " ~" + zLoc;
    }

    public Coordinates multiply(double scalar) {
        return new Coordinates(x*scalar, y*scalar, z*scalar);
    }

    public Coordinates transform(double dx, double dy, double dz) {
        return new Coordinates(x + dx, y + dy, z + dz);
    }
}
