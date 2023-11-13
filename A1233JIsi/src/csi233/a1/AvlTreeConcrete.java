package csi233.a1;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AvlTreeConcrete<AnyType extends Comparable<? super AnyType>>
{
    private AnyType   currentMode;
    private int       modeCount;
    private AnyType   lastElementSeen;
    private int       lastCount;

    /**
     * Construct the tree.
     */
    public AvlTreeConcrete( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Lazy delete from the tree. Nothing is done if x is not found.
     * @param x the item to lazy delete.
     */
    private void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Lazy delete all nodes from the tree. Nothing is done if x is not found.
     * @param key elements that satisfy the key to lazy delete.
     */
    public void removeAll(AnyType key)
    {
        root = removeAll(root, key);
    }

    /**
     * Internal method to lazy delete from a subtree.
     * @param x the item to lazy delete.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else {
            if (t.isActive && t.element.equals(x))
            {
                t.isActive = false;
            }
            AvlNode<AnyType> current = t.next;
            AvlNode<AnyType> previous = t;
            while (current != null) {
                if (current.isActive && current.element.equals(x)) {
                    current.isActive = false;
                    break;
                }
                previous = current;
                current = current.next;
            }
        }
        return balance( t );
    }

    /**
     * Internal method to lazy delete all items similar
     * to the key's class from a subtree.
     * @param key the elements that satisfy the key to lazy delete.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> removeAll(AvlNode<AnyType> t, AnyType key) {
        if (t == null) return null;

        int compareResult = key.compareTo(t.element);

        if (compareResult < 0) {
            t.left = removeAll(t.left, key);
        } else if (compareResult > 0) {
            t.right = removeAll(t.right, key);
        } else {
            t.isActive = false;
            AvlNode<AnyType> current = t.next;
            while (current != null) {
                current.isActive = false;
                current = current.next;
            }
        }
        return t;
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) ) {
            throw new UnderflowException();
        }
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException();
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    private boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Finds all active nodes in the tree that satisfy the class of the key.
     * @param key the items to search for.
     * @return Collection of items that match the key's class.
     */
    public List<AnyType> findAll(AnyType key) {
        List<AnyType> resultList = new ArrayList<>();
        findAll( root, key, resultList);
        return resultList;
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Tree is currently empty." );
        else
            printTree( root );
    }

    /**
     * Print active nodes in sorted order.
     */
    public void printBalTree(boolean ascending) {
        if (isEmpty())
            System.out.println("The tree is currently empty.");
        else
        {
            if (ascending) {
                printBalTreeAscending(root);
            } else {
                printBalTreeDescending(root);
            }
        }

    }

    /**
     * Writes a txt file with the same data that printBalTree() outputs.
     */
    public void writeBalTree(boolean ascending)
    {
        try (PrintWriter writer = new PrintWriter("A1233JIsiAVLout.txt"))
        {
            if (isEmpty())
                writer.println("The tree is currently empty.");
            else {
                if (ascending) {
                    writeBalTreeAscending(root, writer);
                } else {
                    writeBalTreeDescending(root, writer);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error opening the output file.");
        }
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<AnyType> balance( AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> t )
    {
        if( t == null )
            return -1;

        if( t != null )
        {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t )
    {
        if( t == null )
            return new AvlNode<>( x, null, null, null);

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
        {
            if (!t.isActive)
            {
                t.isActive = true;
                t.element = x;
            }
            else
                t.next = new AvlNode<>(x, null, null, t.next);
        }
        return balance( t );
    }

    /**
     * Internal method to find the smallest active item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> t )
    {
        if( t == null )
            return null;

        else if (t.left == null)
        {
            if (t.isActive)
                return t;
            else
            {
                AvlNode<AnyType> current = t.next;
                while (current != null && !current.isActive)
                {
                    current = current.next;
                }
                return current != null ? current : findMin(t.left);
            }
        }
        else
            return findMin(t.left);
    }

    /**
     * Internal method to find the largest active item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> t )
    {
        if( t == null )
            return null;

        else if (t.right == null)
        {
            if (t.isActive)
                return t;
            else
            {
                AvlNode<AnyType> current = t.next;
                while (current != null && !current.isActive)
                {
                    current = current.next;
                }
                return current != null ? current : findMin(t.right);
            }
        }
        else
            return findMax(t.right);
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType x, AvlNode<AnyType> t )
    {
        while( t != null )
        {
            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else
                return true;
        }

        return false;
    }

    /**
     * Finds all active nodes with the specified key in the AVL tree.
     * @param t The starting node for the search.
     * @param key The value to find within the tree.
     * @param resultList The list to collect matching nodes' values.
     */
    private void findAll(AvlNode<AnyType> t, AnyType key, List<AnyType> resultList) {
        if (t == null)
            return;

        int compareResult = key.compareTo(t.element);

        if (compareResult < 0)
        {
            findAll(t.left, key, resultList);
        }
        else if (compareResult > 0)
        {
            findAll(t.right, key, resultList);
        }
        else
        {
            if (t.isActive)
                resultList.add(t.element);

            AvlNode<AnyType> current = t.next;
            while (current != null)
            {
                if (current.isActive)
                    resultList.add(current.element);
                current = current.next;
            }
        }
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            if (t.isActive)
            {
                System.out.println(t.element);
                AvlNode<AnyType> current = t.next;
                while (current != null)
                {
                    if (current.isActive)
                    {
                        System.out.println(current.element);
                    }
                    current = current.next;
                }
            }
        }
    }

    /**
     * Prints the AVL tree nodes in ascending order.
     * @param t the node to start printing from
     */
    private void printBalTreeAscending(AvlNode<AnyType> t) {
        if (t == null) return;
        printBalTreeAscending(t.left);
        printNode(t);
        printBalTreeAscending(t.right);
    }

    /**
     * Prints the AVL tree nodes in descending order.
     * @param t the node to start printing from
     */
    private void printBalTreeDescending(AvlNode<AnyType> t) {
        if (t == null) return;
        printBalTreeDescending(t.right);
        printNode(t);
        printBalTreeDescending(t.left);
    }

    /**
     * Prints the detailed information of a single node and its children.
     * @param t the node to print
     */
    private void printNode(AvlNode<AnyType> t) {
        int balance = height(t.right) - height(t.left);
        String nodeStatus = t.isActive ? "Active" : "Inactive";
        System.out.printf("Data: %-15s Height: %-12d Balance: %-12d Status: %-8s\n",
                t.element, t.height, balance, nodeStatus);

        System.out.printf("        Left: %-45s\n",
                t.left != null ? "Data: " + t.left.element : "null");
        System.out.printf("        Right: %-45s\n",
                t.right != null ? "Data: " + t.right.element : "null");

        AvlNode<AnyType> current = t.next;
        while (current != null) {
            balance = height(current.right) - height(current.left);
            nodeStatus = current.isActive ? "Active" : "Inactive";
            System.out.printf("        Duplicate: Data: %-15s Height: %-12d Balance: %-12d Status: %-8s\n",
                    current.element, current.height, balance, nodeStatus);
            current = current.next;
        }
    }

    /**
     * Writes the AVL tree nodes to a file in ascending order.
     * @param t the node to start writing from
     * @param writer the PrintWriter to write to the file
     */
    private void writeBalTreeAscending(AvlNode<AnyType> t, PrintWriter writer) {
        if (t == null) return;
        writeBalTreeAscending(t.left, writer);
        writeNode(t, writer);
        writeBalTreeAscending(t.right, writer);
    }

    /**
     * Writes the AVL tree nodes to a file in descending order.
     * @param t the node to start writing from
     * @param writer the PrintWriter to write to the file
     */
    private void writeBalTreeDescending(AvlNode<AnyType> t, PrintWriter writer) {
        if (t == null) return;
        writeBalTreeDescending(t.right, writer);
        writeNode(t, writer);
        writeBalTreeDescending(t.left, writer);
    }

    /**
     * Writes the information of a single node and its children to a file.
     * @param node the node to write out
     * @param writer the PrintWriter to handle file output
     */
    private void writeNode(AvlNode<AnyType> node, PrintWriter writer) {
        int balance = height(node.right) - height(node.left);
        String nodeStatus = node.isActive ? "Active" : "Inactive";
        writer.printf("Data: %-15s Height: %-12d Balance: %-12d Status: %-8s\n",
                node.element, node.height, balance, nodeStatus);

        writer.printf("        Left: %-45s\n",
                node.left != null ? "Data: " + node.left.element : "null");
        writer.printf("        Right: %-45s\n",
                node.right != null ? "Data: " + node.right.element : "null");

        AvlNode<AnyType> current = node.next;
        while (current != null) {
            balance = height(current.right) - height(current.left);
            nodeStatus = current.isActive ? "Active" : "Inactive";
            writer.printf("        Duplicate: Data: %-15s Height: %-12d Balance: %-12d Status: %-8s\n",
                    current.element, current.height, balance, nodeStatus);
            current = current.next;
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> t )
    {
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 )
    {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 )
    {
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;

        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    /**
     * Finds the mode (most frequently occurring value) of the AVL tree.
     * @return a Result object containing the mode and its occurrence count.
     */
    public Result<AnyType> findMode() {
        currentMode = null;
        modeCount = 0;
        lastElementSeen = null;
        lastCount = 0;

        inOrderTraversal(root);

        return new ModeResult<>(currentMode, modeCount);
    }

    /**
     * Performs an in-order traversal to calculate mode.
     * counts the current node and its duplicates, changing mode count accordingly.
     * @param node the current node to process during traversal
     */
    private void inOrderTraversal(AvlNode<AnyType> node) {
        if (node == null)
            return;

        inOrderTraversal(node.left);
        updateMode(node.element, node.isActive);

        AvlNode<AnyType> current = node.next;
        while (current != null)
        {
            updateMode(current.element, current.isActive);
            current = current.next;
        }

        inOrderTraversal(node.right);
    }


    private void updateMode(AnyType element, boolean isActive) {
        if (!isActive)
            return;

        if (element.equals(lastElementSeen))
            lastCount++;

        else
        {
            lastElementSeen = element;
            lastCount = 1;
        }
        if (lastCount > modeCount)
        {
            modeCount = lastCount;
            currentMode = element;
        }
    }

    /**
     * Implementation of the Result interface
     */
    private static class ModeResult<AnyType> implements Result<AnyType> {
        private AnyType mode;
        private int count;

        public ModeResult(AnyType mode, int count)
        {
            this.mode = mode;
            this.count = count;
        }

        public AnyType mode()
        {
            return mode;
        }

        public int count()
        {
            return count;
        }
    }

    private static class AvlNode<AnyType>
    {
            // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null, null);
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt, AvlNode<AnyType> nt)
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            next     = nt;
            height   = 0;
            isActive = true;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
        boolean           isActive;     // Indicates if node is active
        AvlNode<AnyType>  next;         // pointers to duplicates
    }

    public String author() {
        return "James Luke C. Isidro";
    }

      /** The tree root. */
    private AvlNode<AnyType> root;
}
