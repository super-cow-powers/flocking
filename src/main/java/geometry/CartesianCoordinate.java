/*
 * This is the co-ordinate class from the labs.
 * I have added some extra utility methods.
 */
package geometry;

/**
 *
 * @author REDACTED
 */
public class CartesianCoordinate {

    private double xPosition;
    private double yPosition;

    public CartesianCoordinate(double x, double y) {
        xPosition = x;
        yPosition = y;
    }
    
    public double getX(){
        return xPosition;
    }
    public double getY(){
        return yPosition;
    }
    
    public double distance_to(CartesianCoordinate B){
        return dist_between(this, B);
    }
    
    public static double dist_between(CartesianCoordinate A, CartesianCoordinate B) {
        return Math.sqrt(Math.abs(Math.pow(A.getX() - B.getX(), 2))
                + Math.abs(Math.pow(A.getY() - B.getY(), 2)));
    }
    
    public static double angle_between(CartesianCoordinate Centre, CartesianCoordinate Point){
        return Math.atan2(Point.getY() - Centre.getY(), Point.getX() - Centre.getX() );
        //return Math.atan2(B.getY(), B.getX()) - Math.atan2(A.getY(), A.getX());
    }
    
    public static CartesianCoordinate mult(CartesianCoordinate input, double factor){
        return new CartesianCoordinate(input.getX()*factor, input.getY()*factor);
    }
}
