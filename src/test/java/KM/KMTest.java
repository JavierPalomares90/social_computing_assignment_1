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

    }

}
