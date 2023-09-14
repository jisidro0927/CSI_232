package cis232.lab4;

public class Rectangle extends Shape
{
    public Rectangle( double len, double wid )
    {
        length = len; width = wid;
    }
     
    public double area( )
    {
        return length * width;
    }
    
    public double perimeter( )
    {
        return 2 * ( length + width );
    }
    
    public String toString( )
    {
        return "cis232.lab4.Rectangle: " + length + " " + width;
    }
    
    public double getLength( )
    {
        return length;
    }
    
    public double getWidth( )
    {
        return width;
    }

    private double length;
    private double width;
}