package ca.jrvs.practice.dataStructure;

import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumSolutionTest {

    @Test
    public void twoSum1() {
        int[] nums = new int[] {2,7,10,13};
        int target = 9;

        int[] expected = new int[] {0,1};

        assertArrayEquals(expected, TwoSumSolution.twoSum1(nums, target));
    }
}