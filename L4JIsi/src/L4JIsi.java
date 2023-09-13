/*
    James Isidro / james_isidro@my.cuesta.edu
    CIS 232 / Scovil
    Lab 2
 */

import java.util.Random;
import java.util.ArrayList;

public class L4JIsi {

    public static String randomString(int letterCount){
        Random rand = new Random();
        String word = "";

        for(int i = 0; i < letterCount ; i++)
            word += (char) (rand.nextInt(92) + 33);

        return word;
    }

    public static <AnyType> void printAll(ArrayList<AnyType> list){
        if (list.get(0) instanceof Shape)
            System.out.println(list.get(0).getClass().getSuperclass().getSimpleName() + " List:");
        else
            System.out.println(list.get(0).getClass().getSimpleName() + " List:");

        for(AnyType item: list)
            System.out.println(item);

        System.out.println();
    }

    public static <AnyType extends Comparable<? super AnyType>> void sort(ArrayList<AnyType> arr){
        for (int i = 0; i < arr.size(); i++){
            int largerItem = i;
            for (int j = i + 1; j < arr.size(); j++){
                if (arr.get(largerItem).compareTo(arr.get(j)) == -1)
                    largerItem = j;
            }
            AnyType temp = arr.get(i);
            arr.set(i, arr.get(largerItem));
            arr.set(largerItem, temp);
        }
    }

    public static void main(String[] args){
        Random rand = new Random();

        ArrayList<Integer> integerList = new ArrayList<>();
        ArrayList<Double> doubleList = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        ArrayList<Shape> shapeList = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            integerList.add(rand.nextInt());
            doubleList.add(rand.nextDouble(100));
            stringList.add(randomString(8));
            if (i < 5)
                shapeList.add(new Circle(rand.nextDouble(100)));
            else
                shapeList.add(new Rectangle(rand.nextDouble(100), rand.nextDouble(100)));
        }

//        printAll(integerList);
//        printAll(doubleList);
        printAll(stringList);
//        printAll(shapeList);

        sort(integerList);
        sort(doubleList);
        sort(stringList);
        sort(shapeList);

//        printAll(integerList);
//        printAll(doubleList);
        printAll(stringList);
//        printAll(shapeList);

    }
}
