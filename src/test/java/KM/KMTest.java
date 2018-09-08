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
        assertTrue(matches != null);
        assertTrue(matches.length == 5);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 449);
        assertTrue(matches[0] == 1);
        assertTrue(matches[1] == 3);
        assertTrue(matches[2] == 2);
        assertTrue(matches[3] == 0);
        assertTrue(matches[4] == 4);

    }

    @Test
    public void test2()
    {
        String inputFile = "src/test/resources/Test_cases/test2.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        assertTrue(matches != null);
        assertTrue(matches.length == 10);

        int weight = KM.getMatchingWeight(weights,matches);

        assertTrue(weight == 577);
        assertTrue(matches[0] == 2);
        assertTrue(matches[1] == 3);
        assertTrue(matches[2] == 1);
        assertTrue(matches[3] == 5);
        assertTrue(matches[4] == 9);
        assertTrue(matches[5] == 6);
        assertTrue(matches[6] == 8);
        assertTrue(matches[7] == 0);
        assertTrue(matches[8] == 4);
        assertTrue(matches[9] == 7);
    }

    @Test
    public void test3()
    {
        String inputFile = "src/test/resources/Test_cases/test3.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        assertTrue(matches != null);
        assertTrue(matches.length == 30);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 17889);
        assertTrue(matches[0] == 10);
        assertTrue(matches[1] == 19);
        assertTrue(matches[2] == 25);
        assertTrue(matches[3] == 11);
        assertTrue(matches[4] == 8);
        assertTrue(matches[5] == 24);
        assertTrue(matches[6] == 26);
        assertTrue(matches[7] == 22);
        assertTrue(matches[8] == 6);
        assertTrue(matches[9] == 13);
        assertTrue(matches[10] == 29);
        assertTrue(matches[11] == 2);
        assertTrue(matches[12] == 17);
        assertTrue(matches[13] == 16);
        assertTrue(matches[14] == 3);
        assertTrue(matches[15] == 18);
        assertTrue(matches[16] == 12);
        assertTrue(matches[17] == 0);
        assertTrue(matches[18] == 5);
        assertTrue(matches[19] == 14);
        assertTrue(matches[20] == 23);
        assertTrue(matches[22] == 28);
        assertTrue(matches[23] == 21);
        assertTrue(matches[24] == 1);
        assertTrue(matches[25] == 4);
        assertTrue(matches[26] == 9);
        assertTrue(matches[27] == 7);
        assertTrue(matches[28] == 15);
        assertTrue(matches[29] == 27);
    }

    @Test
    public void test4()
    {
        String inputFile = "src/test/resources/Test_cases/test4.txt";
        int[][] weights = Utils.readInputFile(inputFile);
        assertTrue(weights != null);
        int[] matches = KM.KM(weights);
        assertTrue(matches != null);
        assertTrue(matches.length == 50);
        int weight = KM.getMatchingWeight(weights,matches);
        assertTrue(weight == 473);
        assertTrue(matches[0] == 36);
        assertTrue(matches[1] == 2);
        assertTrue(matches[2] == 47);
        assertTrue(matches[3] == 29);
        assertTrue(matches[4] == 46);
        assertTrue(matches[5] == 18);
        assertTrue(matches[6] == 31);
        assertTrue(matches[7] == 23);
        assertTrue(matches[8] == 8);
        assertTrue(matches[9] == 13);
        assertTrue(matches[10] == 25);
        assertTrue(matches[11] == 43);
        assertTrue(matches[12] == 49);
        assertTrue(matches[13] == 1);
        assertTrue(matches[14] == 10);
        assertTrue(matches[15] == 7);
        assertTrue(matches[16] == 6);
        assertTrue(matches[17] == 40);
        assertTrue(matches[18] == 44);
        assertTrue(matches[19] == 5);
        assertTrue(matches[20] == 27);
        assertTrue(matches[21] == 16);
        assertTrue(matches[22] == 0);
        assertTrue(matches[23] == 3);
        assertTrue(matches[24] == 17);
        assertTrue(matches[25] == 35);
        assertTrue(matches[26] == 39);
        assertTrue(matches[27] == 38);
        assertTrue(matches[28] == 19);
        assertTrue(matches[29] == 22);
        assertTrue(matches[30] == 15);
        assertTrue(matches[31] == 45);
        assertTrue(matches[32] == 42);
        assertTrue(matches[33] == 20);
        assertTrue(matches[34] == 26);
        assertTrue(matches[35] == 9);
        assertTrue(matches[36] == 14);
        assertTrue(matches[37] == 30);
        assertTrue(matches[38] == 48);
        assertTrue(matches[39] == 34);
        assertTrue(matches[40] == 37);
        assertTrue(matches[41] == 33);
        assertTrue(matches[42] == 4);
        assertTrue(matches[43] == 28);
        assertTrue(matches[44] == 21);
        assertTrue(matches[45] == 41);
        assertTrue(matches[46] == 32);
        assertTrue(matches[47] == 12);
        assertTrue(matches[48] == 24);
        assertTrue(matches[49] == 11);
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
