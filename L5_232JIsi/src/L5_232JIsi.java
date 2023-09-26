/*
    James Isidro / james_isidro@my.cuesta.edu
    CIS 232 / Scovil
    Lab 5
 */

import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import weiss.util.Comparator;

public class L5_232JIsi
{
    static class Ascend implements Comparator<Point>
    {
        public int compare(Point a, Point b)
        {
            if(Math.abs(a.getX() - b.getX()) <= 0.0001)
              return (int) (a.getY() - b.getY());
            return (int) (a.getX() - b.getX());
        }
    }
    
    static class Descend implements Comparator<Point>
    {
        public int compare(Point a, Point b)
        {
            if(Math.abs(b.getX() - a.getX()) <= 0.0001)
                return (int) (b.getY() - a.getY());
            return (int) (b.getX() - a.getX());
        }
    }
    
    public static <AnyType> void sort(AnyType [] array,
                                            Comparator<? super AnyType> cmp)
    {
        for(int i = 0; i < array.length; i++)
        {
            int key = i;
            for(int j = i + 1; j < array.length; j++)
            {
                if(cmp.compare(array[key], array[j]) > 0)
                {
                    key = j;
                }
            }
            AnyType temp = array[i];
            array[i] = array[key];
            array[key] = temp;
        }
    }

    public static <AnyType> void printAll(AnyType[] array, int lineSize)
    {
        for(int i = 0; i < array.length; i++)
        {
            System.out.print((i + 1) + "." + array[i] + " ");
            if ((i + 1) % lineSize == 0)
                System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();
        Point[] points;
        int size;
        int lineSize;

        System.out.print("Enter Number of Random Points: ");
        size = scnr.nextInt();
        points = new Point[size];
        for(int i = 0; i < size; i++)
        {
            points[i] = new Point(rand.nextInt(201) - 100,
                    rand.nextInt(201) - 100);
        }

        System.out.print("Enter Number of Points per Line: ");

        lineSize = scnr.nextInt();
        System.out.println();
        System.out.println("Unsorted:");
        printAll(points, lineSize);

        sort(points, new Ascend());
        System.out.println("Ascending Sort:");
        printAll(points, lineSize);

        sort(points, new Descend());
        System.out.println("Descending Sort:");
        printAll(points, lineSize);
    }
}