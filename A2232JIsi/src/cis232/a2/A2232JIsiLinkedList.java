package cis232.a2;

import java.util.Random;

// LinkedList class
//
// CONSTRUCTION: with no initializer
// Access is via LinkedListIterator class
//
// ******************PUBLIC OPERATIONS*********************
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// LinkedListIterator zeroth( )
//                        --> Return position to prior to first
// LinkedListIterator first( )
//                        --> Return first position
// void insert( x, p )    --> Insert x after current iterator position p
// void remove( x )       --> Remove x
// LinkedListIterator find( x )
//                        --> Return position that views x
// LinkedListIterator findPrevious( x )
//                        --> Return position prior to x
// ******************ERRORS********************************
// No special errors

/**
 * Linked list implementation of the list
 *    using a header node.
 * Access to the list is via LinkedListIterator.
 * @author Mark Allen Weiss
 * @see LinkedListIterator
 */
public class A2232JIsiLinkedList<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the list
     */
    public A2232JIsiLinkedList( )
    {
        header = new ListNode<AnyType>( null );
    }

    /**
     * Test if the list is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return header.next == null;
    }

    /**
     * Make the list logically empty.
     */
    public void makeEmpty( )
    {
        header.next = null;
    }

    /**
     * Return an iterator representing the header node.
     */
    public LinkedListIterator<AnyType> zeroth( )
    {
        return new LinkedListIterator<AnyType>( header );
    }

    /**
     * Return an iterator representing the first node in the list.
     * This operation is valid for empty lists.
     */
    public LinkedListIterator<AnyType> first( )
    {
        return new LinkedListIterator<AnyType>( header.next );
    }

    /**
     * Adds an item in ascending order, ignores null objects
     * @param x item to add on list
     */
    public void add(AnyType x)
    {
        if (x == null)
        {
            LinkedListIterator<AnyType> itr = first();
            while (itr.isValid() && itr.current.next != null)
            {
                itr.advance();
            }
            itr.current.next = new ListNode<>(null);
            return;
        }

        LinkedListIterator<AnyType> itr = zeroth();

        while (itr.current.next != null &&
                itr.current.next.element.compareTo(x) < 0)
        {
            itr.advance();
        }
        itr.current.next = new ListNode<>(x,itr.current.next);
    }

    /**
     * Insert after p.
     * @param x the item to insert.
     * @param p the position prior to the newly inserted item.
     */
    private void insert( AnyType x, LinkedListIterator<AnyType> p )
    {
        if( p != null && p.current != null )
            p.current.next = new ListNode<AnyType>( x, p.current.next );
    }

    /**
     * replace an item on the list with a new item
     * @param replace item to be replaced
     * @param x item to be replaced with
     * @return true if the item exist in the list and false if it doesn't
     */
    public boolean replace( AnyType replace, AnyType x )
    {
        LinkedListIterator<AnyType> itr = first();
        while( itr.isValid() )
        {
            if( itr.retrieve().equals(replace) )
            {
                remove(replace);
                add(x);
                return true;
            }
            itr.advance();
        }
        return false;
    }

    /**
     * Return iterator corresponding to the first node containing an item.
     * @param x the item to search for.
     * @return an iterator; iterator is not valid if item is not found.
     */
    public LinkedListIterator<AnyType> find(AnyType x )
    {
        ListNode<AnyType> itr = header.next;

        while( itr != null && !itr.element.equals( x ) )
            itr = itr.next;

        return new LinkedListIterator<AnyType>( itr );
    }

    /**
     * Return iterator prior to the first node containing an item.
     * @param x the item to search for.
     * @return appropriate iterator if the item is found. Otherwise, the
     * iterator corresponding to the last element in the list is returned.
     */
    public LinkedListIterator<AnyType> findPrevious(AnyType x )
    {
        ListNode<AnyType> itr = header;

        while( itr.next != null && !itr.next.element.equals( x ) )
            itr = itr.next;

        return new LinkedListIterator<AnyType>( itr );
    }

    /**
     * Remove the first occurrence of an item.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        LinkedListIterator<AnyType> p = findPrevious( x );

        if( p.current.next != null )
            p.current.next = p.current.next.next;  // Bypass deleted node
    }

    // Simple print method
    public static <AnyType extends Comparable<? super AnyType>>
            void printList(A2232JIsiLinkedList<AnyType> theList )
    {
        if( theList.isEmpty( ) )
            System.out.print( "Empty list" );
        else
        {
            LinkedListIterator<AnyType> itr = theList.first( );
            for( ; itr.isValid( ); itr.advance( ) )
                System.out.print( itr.retrieve( ) + " " );
        }

        System.out.println( );
    }

    public void showList()
    {
        if( isEmpty() )
            System.out.println("The list is currently empty");

        else
        {
            LinkedListIterator<AnyType> itr = first();
            while( itr.isValid() )
            {
                System.out.println( itr.current.element );
                itr.advance();
            }
        }
        System.out.println();
    }

    // Print list with a set amount of items perLine
    public void showList(int perLine)
    {
        if( isEmpty() )
            System.out.println("The list is currently empty");

        else
        {
            LinkedListIterator<AnyType> itr = first();
            for( int i = 1; i <= listSize(this); i++)
            {
                System.out.print(itr.retrieve() + " ");
                if( i % perLine == 0)
                    System.out.println();
                itr.advance();
            }
        }

        System.out.println();
    }

    public void author()
    {
        System.out.println("James Luke C. Isidro");
    }

    public Result<AnyType> getMode()
    {
        if( isEmpty() )
            return new A2232JIsiLinkedListResult(null, 0);

        LinkedListIterator<AnyType> itr = first();
        int count = 0;
        int maxCount = 1;
        AnyType key = itr.retrieve();
        AnyType mode = itr.retrieve();

        while(itr.isValid())
        {
            if( itr.retrieve().equals(key) )
            {
                count++;
            }
            else
            {
                if(maxCount < count)
                {
                    maxCount = count;
                    mode = key;
                }
                count = 0;
                key = itr.retrieve();
            }
            itr.advance();
        }

        return new A2232JIsiLinkedListResult(mode, maxCount);
    }

    /**
     * Implementation of Result class to store mode and count of a list
     */
    class A2232JIsiLinkedListResult implements Result<AnyType>
    {
        public A2232JIsiLinkedListResult(AnyType newMode, int newCount)
        {
            mode = newMode;
            count = newCount;
        }

        @Override
        public AnyType mode()
        {
            return mode;
        }

        @Override
        public int count()
        {
            return count;
        }

        private AnyType mode;
        private int count;
    }

    private ListNode<AnyType> header;

    // In this routine, LinkedList and LinkedListIterator are the
    // classes written in Section 17.2.
    public static <AnyType extends Comparable<? super AnyType>>
            int listSize( A2232JIsiLinkedList<AnyType> theList )
    {
        LinkedListIterator<AnyType> itr;
        int size = 0;
        
        for( itr = theList.first(); itr.isValid(); itr.advance() )
            size++;
            
        return size;    
    }

    public static void main( String [ ] args )
    {
        A2232JIsiLinkedList<Integer> theList = new A2232JIsiLinkedList<Integer>( );
        LinkedListIterator<Integer> theItr;
        int i;
        Random rnd = new Random();

        theItr = theList.zeroth( );
        theList.showList();

        for( i = 0; i < 10; i++ )
        {
            theList.add(rnd.nextInt(10));
            theList.showList( );
            theItr.advance( );
        }
        System.out.println( "Size was: " + listSize( theList ) );

        for( i = 0; i < 10; i += 2 )
            theList.replace( i , rnd.nextInt(10)+1);

        for( i = 0; i < 10; i++ )
            if( ( i % 2 == 0 ) == ( theList.find( i ).isValid( ) ) )
                System.out.println( "Find fails!" );

        System.out.println( "Finished deletions" );
        theList.showList(rnd.nextInt(5)+1);
        System.out.println( "Size: " + listSize( theList ) );

    }

}
