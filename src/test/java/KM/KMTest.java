package KM;

import org.junit.Test;
import util.Utils;

import static org.junit.Assert.assertTrue;

public class KMTest
{
    @Test
    public void test0()
    {
        String inputFile = "src/test/resources/input.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        assertTrue(matches!= null);
        assertTrue(matches.length == 3);
        assertTrue(matches[0] == 0);
        assertTrue(matches[1] == 2);
        assertTrue(matches[2] == 1);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 23);

    }

    @Test
    public void test1()
    {
        String inputFile = "src/test/resources/Test_cases/test1.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 449);
        /**
         * TODO: assert individual elements
         */

    }

    @Test
    public void test2()
    {
        String inputFile = "src/test/resources/Test_cases/test2.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 577);
        /**
         * TODO: assert individual elements
         */
    }

    @Test
    public void test3()
    {
        String inputFile = "src/test/resources/Test_cases/test3.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 17889);
        /**
         * TODO: assert individual elements
         */
    }

    @Test
    public void test4()
    {
        String inputFile = "src/test/resources/Test_cases/test4.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 473);
        /**
         * TODO: assert individual elements
         */
    }

    @Test
    public void testM3()
    {
        String inputFile = "src/test/resources/Test_cases/M3.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 94);
        /**
         * TODO: assert individual elements
         */

    }

}
