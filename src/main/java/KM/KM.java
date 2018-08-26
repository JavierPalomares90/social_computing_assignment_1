package KM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KM
{
    public static void main(String[] args)
    {
        System.out.println(args.length);

        if(args.length < 1)
        {
            System.out.println("Usage: KM [input.txt]");
            return;
        }
        String inputFile = args[0];
        int[][] weights = readInputFile(inputFile);
        if(weights == null)
        {
            System.err.println("Input file is malformed");
        }
        KM(weights);
    }

    private static void KM(int[][] weights)
    {

        Map<String, Integer> labeling = getInitialLabeling(weights);
        /**
         * TODO: Finish implmenation
         */
    }

    /**
     * Return the initial labeling for a bipartite graph
     * @param weights the weights for the bipartitie graph
     * @return
     */
    private static Map<String,Integer> getInitialLabeling(int[][] weights)
    {
        Map<String, Integer> map = new HashMap<String, Integer>();
        int sizeX = weights.length;
        int sizeY = weights[0].length;

        // Initialize all y labels to 0
        for(int y = 0; y < sizeY; y++)
        {
            map.put("y"+y, 0);
        }
        // Initialize all x labels to its max incident weight
        for(int x = 0; x < sizeX; x++)
        {
            int max = getMaxWeight(weights,x);
            map.put("x"+x, max);
        }
        return map;

    }

    private static int getMaxWeight(int[][] weights, int i)
    {
        int result = Integer.MIN_VALUE;
        for(int j = 0; j < weights[i].length;j++)
        {
            if(weights[i][j] > result)
            {
                result = weights[i][j];
            }
        }
        return result;
    }

    private static int getFirstInteger(String line)
    {
        // Reg ex for first number in line. throw everything else away
        Pattern p = Pattern.compile("([0-9]+) .*");
        Matcher m = p.matcher(line);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return  -1;

    }

    private static int[][] readInputFile(String inputFile)
    {
        try
        {
            File file = new File(inputFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            int i = -1;
            int[][] weights = null;
            // number of rows and columns
            int n;
            while (true)
            {
                line = br.readLine();
                if(line == null)
                {
                    break;
                }
                if(i == -1)
                {
                    // reading first line
                    n = getFirstInteger(line);
                    if(n < 0)
                    {
                        return null;
                    }
                    // Initialize the matrix
                    weights = new int[n][n];
                }else
                {
                    // Split line on whitespace
                    String[] w_i = line.split("\\s+");
                    for(int j = 0; j < w_i.length; j++)
                    {
                        weights[i][j] = Integer.parseInt(w_i[j]);
                    }
                }
                i++;
            }
            return weights;
        }catch (IOException e)
        {
            System.err.println("Unable to read input file.");
            e.printStackTrace();
        }
        return null;
    }
}
