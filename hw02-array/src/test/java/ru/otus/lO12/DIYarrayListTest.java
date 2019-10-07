package ru.otus.lO12;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class DIYarrayListTest {

    private List<Integer> integers = new DIYarrayList<>();

    @Test
    public void testAddElement() {
        for (int i = 0; i < 20; i++) {
            integers.add(i);
        }
        assertEquals(20, integers.size());
    }

    @Test
    public void testCollectionAddAll() {
        for (int i = 0; i < 20; i++) {
            integers.add(i);
        }
        assertEquals(20, integers.size());
        Collections.addAll(integers, 25, 27, 29, 40);
        assertEquals(24, integers.size());
        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            expectedList.add(i);
        }
        expectedList.add(25);
        expectedList.add(27);
        expectedList.add(29);
        expectedList.add(40);

        for (int i = 0; i < integers.size(); i++) {
            assertEquals(expectedList.get(i), integers.get(i));
        }

    }

    @Test
    public void testCollectionCopy() {
        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            integers.add(i);
            expectedList.add(i);
        }
        List<Integer> newIntegerList = new ArrayList<>(integers);
        Collections.copy(newIntegerList, integers);
        for (int i = 0; i < integers.size(); i++) {
            assertEquals(expectedList.get(i), newIntegerList.get(i));
        }
    }

    @Test
    public void testCollectionShort() {
        for (int i = 0; i < 20; i++) {
            integers.add(i);
        }
        Collections.sort(integers, Collections.reverseOrder());
        List<Integer> expectedList = new ArrayList<>();
        for (int i = 19; i >= 0; i--) {
            expectedList.add(i);
        }
        for (int i = 0; i < integers.size(); i++) {
            assertEquals(expectedList.get(i), integers.get(i));
        }
    }

}