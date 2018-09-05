package DGS;

import org.junit.Test;
import util.Utils;

import static org.junit.Assert.assertTrue;

public class DGSTest
{
    @Test
    public void test0()
    {
        String inputFile = "src/test/resources/input.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);

        int[] owners = DGS.DGS(weights);
        assertTrue(owners!= null);
        assertTrue(owners.length == 3);
        assertTrue(owners[0] == 0);
        assertTrue(owners[1] == 2);
        assertTrue(owners[2] == 1);
        int weight = DGS.getMatchingWeight(weights,owners);
        assertTrue(weight == 23);
    }


    @Test
    public void test1()
    {
        String inputFile = "src/test/resources/Test_cases/test1.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] owners = DGS.DGS(weights);
        assertTrue(owners!= null);
        assertTrue(owners.length == 5);

        // Note: the index into owners is j, the value at the index is i
        assertTrue(owners[1] == 0);
        assertTrue(owners[3] == 1);
        assertTrue(owners[2] == 2);
        assertTrue(owners[0] == 3);
        assertTrue(owners[4] == 4);

        int weight = DGS.getMatchingWeight(weights,owners);
        assertTrue(weight == 449);
    }

    @Test
    public void test2()
    {
        String inputFile = "src/test/resources/Test_cases/test2.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] owners = DGS.DGS(weights);
        assertTrue(owners!= null);
        assertTrue(owners.length == 10);
        assertTrue(owners[0] == 7);
        assertTrue(owners[1] == 2);
        assertTrue(owners[2] == 0);
        assertTrue(owners[3] == 1);
        assertTrue(owners[4] == 8);
        assertTrue(owners[5] == 3);
        assertTrue(owners[6] == 5);
        assertTrue(owners[7] == 9);
        assertTrue(owners[8] == 6);
        assertTrue(owners[9] == 4);

        int weight = DGS.getMatchingWeight(weights,owners);
        assertTrue(weight == 577);
    }

    @Test
    public void test3()
    {
        String inputFile = "src/test/resources/Test_cases/test3.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] owners = DGS.DGS(weights);
        assertTrue(owners != null);
        assertTrue(owners.length == 30);

        int weight = DGS.getMatchingWeight(weights,owners);
        /**
         * TODO: assert individual elements
         */
        assertTrue(weight == 17889);

    }

    @Test
    public void test4()
    {
        String inputFile = "src/test/resources/Test_cases/test4.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] owners = DGS.DGS(weights);
        assertTrue(owners != null);
        assertTrue(owners.length == 50);

        int weight = DGS.getMatchingWeight(weights,owners);
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
        int[] owners = DGS.DGS(weights);
        assertTrue(owners != null);
        assertTrue(owners.length == 10);

        int weight = DGS.getMatchingWeight(weights,owners);
        // There are multiple perfect matchings, so can only assert for the weight
        assertTrue(weight == 94);

    }
}
