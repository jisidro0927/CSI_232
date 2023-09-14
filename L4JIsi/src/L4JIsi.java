/*
    James Isidro / james_isidro@my.cuesta.edu
    CIS 232 / Scovil
    Lab 2
 */

import java.util.Random;
import java.util.ArrayList;

public class L4JIsi {


    public static ArrayList<Integer> randIntegerArr(Random rand){
        return new ArrayList<>() {{
            for(int i = 0; i < 10; i++){
                add(rand.nextInt());
            }
        }};
    }

    public static ArrayList<Double> randDoubleArr(Random rand){
        return new ArrayList<>() {{
            for(int i = 0; i < 10; i++){
                add(rand.nextDouble(100));
            }
        }};
    }

    public static ArrayList<String> randStringArr(Random rand, int letterCount){
        return new ArrayList<>() {{
            for(int i = 0; i < 10; i++){
                add(randomString(rand, letterCount));
            }
        }};
    }


    public static ArrayList<Shape> randShapeArr(Random rand){
        return new ArrayList<>() {{
            for(int i = 0; i < 5; i++){
                add(new Rectangle(rand.nextDouble(100),rand.nextDouble(100)));
                add(new Circle(rand.nextDouble(100)));
            }
        }};
    }

    public static String randomString(Random rand, int letterCount){
        String word = "";

        for(int i = 0; i < letterCount ; i++)
            word += (char) (rand.nextInt(92) + 33);

        return word;
    }

    public static <AnyType> void printAll(ArrayList<AnyType> list){
        for(AnyType item: list)
            System.out.println(item);

        System.out.println();
    }

    public static <AnyType> void printHeader(ArrayList<AnyType> list) {
        if (list.get(0) instanceof Shape)
            System.out.println(list.get(0).getClass().getSuperclass().getSimpleName() + " List:");
        else
            System.out.println(list.get(0).getClass().getSimpleName() + " List:");
    }

    public static <AnyType extends Comparable<? super AnyType>> void sort(ArrayList<AnyType> arr){
        for (int i = 0; i < arr.size(); i++){
            int largerItem = i;
            for (int j = i + 1; j < arr.size(); j++){
                if (arr.get(largerItem).compareTo(arr.get(j)) < 0)
                    largerItem = j;
            }
            AnyType temp = arr.get(i);
            arr.set(i, arr.get(largerItem));
            arr.set(largerItem, temp);
        }
    }

    public static void main(String[] args){
        Random rand = new Random();

        ArrayList<ArrayList> arrList = new ArrayList<>() {{
            add(randIntegerArr(rand));
            add(randDoubleArr(rand));
            add(randStringArr(rand, 8));
            add(randShapeArr(rand));
        }};

        for(ArrayList list: arrList) {
            printHeader(list);
            printAll(list);
        }

        for(ArrayList list: arrList) {
            sort(list);
            System.out.print("Sorted ");
            printHeader(list);
            printAll(list);
        }
    }
}
