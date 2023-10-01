package cis232.a1;
import weiss.util.*;

/**
 * The A1232JIsi implements a growable array.
 * Insertions are always placed in ascending order.
 */
public class A1232JIsi<AnyType extends Comparable<? super AnyType>>
        extends AbstractCollection<AnyType> implements List<AnyType>
{
    /**
     * Construct an empty A1232JIsi.
     */
    public A1232JIsi()
    {
        clear( );
    }

    /**
     * Construct an A1232JIsi with same items as another Collection
     * and Initializes with the same size.
     */
    public A1232JIsi( Collection<? extends AnyType> other )
    {
        clear();
        for( AnyType obj : other )
            add( obj );

        theSize = other.size();
    }

    /**
     * Construct an empty A1232JIsi with a specific size
     * @param size of the A1232JIsi
     */
    public A1232JIsi( int size )
    {
        theSize = size;
        theItems = ( AnyType[] ) new Comparable[theSize];
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size()
    {
        return theSize;
    }

    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        if( idx < 0 || idx >= size() )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx
                    + "; size " + size( ) );
        return theItems[ idx ];
    }

    /**
     * Removes the at item at the index and places a new item in the
     * appropriate place in the ascending list.
     * @param idx the index to remove.
     * @param newVal the new value.
     * @return the old value.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        if( idx < 0 || idx >= size() )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx
                    + "; size " + size( ) );
        AnyType old = theItems[ idx ];

        int appIdx = appropriateIndex( newVal );
        if( idx >= appIdx )
        {
            for( int j = idx - 1; j >= appIdx; j-- )
                theItems[j + 1] = theItems[j];
        }
        else
        {
            appIdx--;
            for (int j = idx; j < appIdx; j++)
                theItems[j] = theItems[j + 1];
        }

        theItems[ appIdx ] = newVal;
        modCount++;
        return old;
    }

    /**
     * Tests if some item is in this collection.
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    public boolean contains( Object x )
    {
        return findPos( x ) != NOT_FOUND;
    }

    /**
     * Returns the position of first item matching x in this collection,
     * or NOT_FOUND if not found.
     * @param x any object.
     * @return the position of first item matching x in this collection,
     * or NOT_FOUND if not found.
     */
    private int findPos( Object x )
    {
        for( int i = 0; i < size(); i++ )
            if( x == null )
            {
                if( theItems[i] == null )
                    return i;
            }
            else if( x.equals(theItems[i]) )
                return i;

        return NOT_FOUND;

    }

    /**
     * Use Binary Search to return the index of an item in a sorted A1232JIsi
     * @param list A1232JIsi to conduct Binary Search on
     * @param item being searched for in the collection
     * @return position of matching item or -1 if not found
     * @param <AnyType> must extend Comparable
     */
    public static <AnyType extends Comparable<? super AnyType>>
            int binSearch( A1232JIsi<AnyType> list, AnyType item )
    {
        int low = 0;
        int high = list.size() - 1;
        while ( low <= high )
        {
            int mid = ( low + high ) / 2;
            if( item.equals(list.get(mid)) )
                return mid;
            if( item.compareTo(list.get(mid)) < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return NOT_FOUND;
    }

    /**
     * Using Binary Searches for where the element should be inserted in the
     * list of ascending order
     * @param x any object
     * @return the index of where the object is most appropriate to position
     * within the list
     */
    public int appropriateIndex( AnyType x )
    {
        int low = 0;
        int high = theSize - 1;
        while ( low <= high )
        {
            int mid = (low + high) / 2;
            if( x.equals(theItems[mid]) )
                return mid;
            if( x.compareTo(theItems[mid]) < 0 )
                high = mid - 1;
            else
                low = mid + 1;
        }
        return low;
    }

    /**
     * Adds an item to this collection, at the ascending order.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        if( theItems.length == size() )
        {
            AnyType [] old = theItems;
            theItems = (AnyType []) new Comparable[ theItems.length * 2 + 1 ];
            for( int i = 0; i < size(); i++ )
                theItems[ i ] = old[ i ];
        }

        int i = appropriateIndex(x);
        for( int j = size() - 1; j >= i; j-- )
            theItems[j + 1] = theItems[j];

        theItems[i] = x;

        theSize++;
        modCount++;
        return true;
    }

    /**
     * adds the item at the specified index, if placement does not follow
     * ascending order, it is added to the list like normal.
     * @param item is any object
     * @param idx  is requested placement of item
     * @return true
     */
    public boolean addAt( AnyType item, int idx )
    {
        if( idx < 0 || idx >= size() )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx
                    + "; size " + size() );

        if( !(item.compareTo(theItems[idx]) == 0) )
            return add(item);

        if( theItems.length == size() )
        {
            AnyType [ ] old = theItems;
            theItems = (AnyType []) new Comparable[ theItems.length * 2 + 1 ];
            for( int i = 0; i < size(); i++ )
                theItems[ i ] = old[ i ];
        }

        for (int j = size() - 1; j > idx; j--)
            theItems[j + 1] = theItems[j];

        theItems[idx] = item;

        theSize++;
        modCount++;
        return true;
    }

    /**
     * Removes an item from this collection.
     * @param x any object.
     * @return true if this item was removed from the collection.
     */
    public boolean remove( Object x )
    {
        int pos = findPos( x );

        if( pos == NOT_FOUND )
            return false;
        else
        {
            remove( pos );
            return true;
        }
    }

    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        AnyType removedItem = theItems[ idx ];

        for( int i = idx; i < size( ) - 1; i++ )
            theItems[ i ] = theItems[ i + 1 ];
        theSize--;

        modCount++;
        return removedItem;
    }

    /**
     * Change the size of this collection to zero.
     */
    public void clear()
    {
        theSize = 0;
        theItems = (AnyType []) new Comparable[ DEFAULT_CAPACITY ];
        modCount++;
    }

    /**
     * Creates a Result object to calculate an A1232JIsi most occurring
     * item and the number of times it occurs.
     * @return Result Type
     */
    public Result<AnyType> getMode()
    {
        return new A1232JIsiResult();
    }

    /**
     * Implementation of the Result interface that determines which
     * item occurs the most (mode) and the count of occurrences for that item.
     */
    private class A1232JIsiResult implements Result<AnyType>
    {
        private AnyType mode;
        private int maxCount;


        /**
         * returns the mode of the list
         * @return most occurring item
         */
        public AnyType mode()
        {
            calculateModeAndCount();
            return mode;
        }

        /**
         * Returns the count of the current mode
         * @return the count of the current mode
         */
        public int count()
        {
            calculateModeAndCount();
            return maxCount;
        }

        /**
         * Calculates the Mode and counts the number of occurrences
         */
        public void calculateModeAndCount()
        {
            int count = 1;
            maxCount = 0;
            AnyType keyItem = theItems[0];
            for( int i = 1; i < size(); i++ )
            {
                if( keyItem.equals(theItems[i]) )
                    count++;
                else
                {
                    if( maxCount < count )
                    {
                        maxCount = count;
                        mode = keyItem;
                    }
                    count = 1;
                    keyItem = theItems[i];
                }
            }
        }
    }


    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public Iterator<AnyType> iterator( )
    {
        return new A1232JIsiIterator( 0 );
    }
    /**
     * Obtains a ListIterator object used to traverse the collection bidirectionally.
     * @return an iterator positioned prior to the requested element.
     * @param idx the index to start the iterator. Use size() to do complete
     * reverse traversal. Use 0 to do complete forward traversal.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public ListIterator<AnyType> listIterator( int idx )
    {
        return new A1232JIsiIterator( idx );
    }

    /**
     * This is the implementation of the A1232JIsiIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the A1232JIsi.
     */
    private class A1232JIsiIterator implements ListIterator<AnyType>
    {
        private int current;
        private int expectedModCount = modCount;
        private boolean nextCompleted = false;
        private boolean prevCompleted = false;

        A1232JIsiIterator(int pos )
        {
            if( pos < 0 || pos > size( ) )
                throw new IndexOutOfBoundsException( );
            current = pos;
        }

        public boolean hasNext( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );
            return current < size( );
        }

        public boolean hasPrevious( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );
            return current > 0;
        }

        public AnyType next( )
        {
            if( !hasNext( ) )
                throw new NoSuchElementException( );
            nextCompleted = true;
            prevCompleted = false;
            return theItems[ current++ ];
        }

        public AnyType previous( )
        {
            if( !hasPrevious( ) )
                throw new NoSuchElementException( );
            prevCompleted = true;
            nextCompleted = false;
            return theItems[ --current ];
        }

        public void remove( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );

            if( nextCompleted )
                A1232JIsi.this.remove( --current );
            else if( prevCompleted )
                A1232JIsi.this.remove( current );
            else
                throw new IllegalStateException( );

            prevCompleted = nextCompleted = false;
            expectedModCount++;
        }
    }

    private static final int DEFAULT_CAPACITY = 10;
    private static final int NOT_FOUND = -1;

    private AnyType [ ] theItems;
    private int theSize;
    private int modCount = 0;

}

