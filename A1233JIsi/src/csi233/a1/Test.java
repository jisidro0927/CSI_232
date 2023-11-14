    package csi233.a1;

    import java.util.List;
    import java.util.Random;

    public class Test {
        public static void main(String[] args) {
            A1233JIsiAVL<Integer> tree = new A1233JIsiAVL<>();
            Random rand = new Random();

            for(int i = 0; i < 100; i++)
            {
                    tree.insert(rand.nextInt(12));
            }
            tree.printBalTree(true);
            tree.insert(null);
            System.out.println("Count: " + tree.findMode().count() +
                    " Mode: " + tree.findMode().mode());
            System.out.println(tree.findMin());
            System.out.println(tree.findMax());

            for(Integer i: tree.findAll(5))
                System.out.print(i + ", ");
            System.out.println();
            tree.removeAll(5);

            tree.printBalTree(false);

            System.out.println("Count: " + tree.findMode().count() +
                    " Mode: " + tree.findMode().mode());

            System.out.println("Finish");

//
//            // Test insertion and tree structure
//            System.out.println("Testing insertion:");
//            tree.insert(20);
//            tree.insert(25);
//            tree.insert(15);
//            tree.insert(10);
//            tree.insert(30);
//            tree.insert(5);
//            tree.insert(35);
//            tree.insert(1);
//            tree.insert(10);
//            tree.insert(10);
//            tree.insert(25);
//            tree.insert(25);
//            tree.insert(25);
//
//            // Check balance and order by printing the tree
//            tree.printTree();
//            tree.printBalTree(true);
//            tree.writeBalTree(false);
//            System.out.println(tree.findMode().count() + " " + tree.findMode().mode());
//
//            // Test finding minimum and maximum
//            assert tree.findMin() == 1 : "Min should be 1";
//            assert tree.findMax() == 35 : "Max should be 35";
//            System.out.println("Min and Max found correctly.");
//
//            // Test lazy deletion
//            tree.remove(25); // Remove leaf
//            tree.remove(15); // Remove node with one child
//            tree.remove(20); // Remove node with two children
//            System.out.println("After lazy deletions:");
//            tree.printTree();
//            tree.printBalTree(false);
//
//            // Test contains method
//            assert !tree.contains(25) : "Tree should not contain 25";
//            assert tree.contains(30) : "Tree should contain 30";
//            System.out.println("Contains check passed.");
//
//            // Test finding all instances
//            tree.insert(20); // Insert duplicate
//            List<Integer> all20s = tree.findAll(20);
//            List<Integer> all25s = tree.findAll(25);
//            assert all20s.size() == 1 : "There should be one '20' in the tree.";
//            System.out.println("FindAll check passed for value 20.");
//            System.out.println(tree.findMode().count() + " " + tree.findMode().mode());
//
//            // Test removing all instances
//            tree.removeAll(20);
//            assert !tree.(20) : "Tree should not contain 20 after removeAll";
//            System.out.println("RemoveAll check passed for value 20.");
//            tree.writeBalTree(true);
//
//            // Test balance after removals
//            System.out.println("Checking balance:");
//            tree.checkBalance();
//
//            // Ensure tree is empty after making it empty
//            tree.makeEmpty();
//            assert tree.isEmpty() : "Tree should be empty after makeEmpty";
//            System.out.println("MakeEmpty check passed.");
//
//            System.out.println(tree.findMode().count() + " " + tree.findMode().mode());
        }
    }
