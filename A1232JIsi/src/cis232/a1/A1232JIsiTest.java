package cis232.a1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class A1232JIsiTest {

    @Test
    void testAdd() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        assertTrue(list.add(null));
        assertTrue(list.add(null));
        assertTrue(list.add(null));
        assertEquals(3, list.size());
    }


    @Test
    void testGet() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(3);
        list.add(2);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testRemove() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(3);
        list.add(2);
        assertEquals(3, list.size());

        assertTrue(list.remove((Object) 2));
        assertEquals(2, list.size());
    }

    @Test
    public void test_smallest_element() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(5);
        list.add(10);
        int result = list.appropriateIndex(2);
        assertEquals(0, result);
    }

    @Test
    public void test_appropriateIndexNullElement() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);

        int index = list.appropriateIndex(null);

        assertEquals(4, index);
    }

    @Test
    void testContains() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(3);
        list.add(2);
        assertTrue(list.contains(1));
        assertTrue(list.contains(3));
        assertFalse(list.contains(4));
    }

    @Test
    void testClear() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(3);
        list.add(2);
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    public void test_set_item_at_beginning() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(2);
        list.add(3);
        list.add(4);

        int oldVal = list.set(0, 1);

        assertEquals(2, oldVal);
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(4, list.get(2));
    }

    @Test
    public void test_set_item_at_end() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int oldVal = list.set(2, 4);

        assertEquals(3, oldVal);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(4, list.get(2));
    }

    @Test
    public void test_set_item_in_middle() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(2);
        list.add(4);

        int oldVal = list.set(1, 3);

        assertEquals(2, oldVal);
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(4, list.get(2));
    }

    @Test
    public void test_set_item_already_in_list() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int oldVal = list.set(1, 2);

        assertEquals(2, oldVal);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void test_unique_elements() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Result<Integer> result = list.getMode();

        assertEquals(1, result.mode());
        assertEquals(1, result.count());
    }

    @Test
    public void test_duplicate_elements() {
        A1232JIsi<Integer> list = new A1232JIsi<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);

        Result<Integer> result = list.getMode();

        assertEquals(2, result.mode());
        assertEquals(2, result.count());
    }

}

