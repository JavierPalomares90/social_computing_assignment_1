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
        assertTrue(owners[10] == 0);
        assertTrue(owners[19] == 1);
        assertTrue(owners[25] == 2);
        assertTrue(owners[11] == 3);
        assertTrue(owners[8] == 4);
        assertTrue(owners[24] == 5);
        assertTrue(owners[26] == 6);
        assertTrue(owners[22] == 7);
        assertTrue(owners[6] == 8);
        assertTrue(owners[13] == 9);
        assertTrue(owners[29] == 10);
        assertTrue(owners[2] == 11);
        assertTrue(owners[17] == 12);
        assertTrue(owners[16] == 13);
        assertTrue(owners[3] == 14);
        assertTrue(owners[18] == 15);
        assertTrue(owners[12] == 16);
        assertTrue(owners[0] == 17);
        assertTrue(owners[5] == 18);
        assertTrue(owners[14] == 19);
        assertTrue(owners[23] == 20);
        assertTrue(owners[20] == 21);
        assertTrue(owners[28] == 22);
        assertTrue(owners[21] == 23);
        assertTrue(owners[1] == 24);
        assertTrue(owners[4] == 25);
        assertTrue(owners[9] == 26);
        assertTrue(owners[7] == 27);
        assertTrue(owners[15] == 28);
        assertTrue(owners[27] == 29);
        
        int weight = DGS.getMatchingWeight(weights,owners);
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
        assertTrue(owners[36] == 0);
        assertTrue(owners[2] == 1);
        assertTrue(owners[47] == 2);
        assertTrue(owners[29] == 3);
        assertTrue(owners[46] == 4);
        assertTrue(owners[18] == 5);
        assertTrue(owners[31] == 6);
        assertTrue(owners[23] == 7);
        assertTrue(owners[8] == 8);
        assertTrue(owners[13] == 9);
        assertTrue(owners[25] == 10);
        assertTrue(owners[43] == 11);
        assertTrue(owners[49] == 12);
        assertTrue(owners[1] == 13);
        assertTrue(owners[10] == 14);
        assertTrue(owners[7] == 15);
        assertTrue(owners[6] == 16);
        assertTrue(owners[40] == 17);
        assertTrue(owners[44] == 18);
        assertTrue(owners[5] == 19);
        assertTrue(owners[27] == 20);
        assertTrue(owners[16] == 21);
        assertTrue(owners[0] == 22);
        assertTrue(owners[3] == 23);
        assertTrue(owners[17] == 24);
        assertTrue(owners[35] == 25);
        assertTrue(owners[39] == 26);
        assertTrue(owners[38] == 27);
        assertTrue(owners[19] == 28);
        assertTrue(owners[22] == 29);
        assertTrue(owners[15] == 30);
        assertTrue(owners[45] == 31);
        assertTrue(owners[42] == 32);
        assertTrue(owners[20] == 33);
        assertTrue(owners[26] == 34);
        assertTrue(owners[9] == 35);
        assertTrue(owners[14] == 36);
        assertTrue(owners[30] == 37);
        assertTrue(owners[48] == 38);
        assertTrue(owners[34] == 39);
        assertTrue(owners[37] == 40);
        assertTrue(owners[33] == 41);
        assertTrue(owners[4] == 42);
        assertTrue(owners[28] == 43);
        assertTrue(owners[21] == 44);
        assertTrue(owners[41] == 45);
        assertTrue(owners[32] == 46);
        assertTrue(owners[12] == 47);
        assertTrue(owners[24] == 48);
        assertTrue(owners[11] == 49);
        
        int weight = DGS.getMatchingWeight(weights,owners);
        assertTrue(weight == 473);
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
