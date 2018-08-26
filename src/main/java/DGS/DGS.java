package DGS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DGS
{
    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            System.out.println("Usage: DGS [input.txt]");
            return;
        }
        String inputFile = args[0];
        int[][] weights = readInputFile(inputFile);
        if(weights == null)
        {
            System.err.println("Input file is malformed");
        }
        //TODO: Run DGS on weights
        DGS(weights);
    }

    private static void DGS(int[][] weights)
    {
        int numGoods = weights[0].length;
        int numBidders = weights.length;
        // The prices and owner of each item
        double[] prices = new double[numGoods];
        // TODO: init this to -1
        int[] owners = new int[numGoods];
        // Initially all goods are unassigned
        for (int j = 0; j < owners.length; j++)
        {
            owners[j] = -1;
        }
        // TODO: Check if this is the correct expression for delta
        double delta = 1.0 / (numGoods + 1);
        Queue<Integer> q = new LinkedList<Integer>();
        // add all bidders to the queue
        for (int i = 0; i < numBidders; i++)
        {
            q.add(i);
        }

        while(q.isEmpty() == false)
        {
            int i = q.poll();
            double max = -1.0 * Double.MAX_VALUE;
            int item = 0;
            // Find the item j that maximizes w_i_j - p_j
            for (int j = 0; j < numGoods; j++)
            {
                double price = prices[j];
                double tmp = 1.0 * weights[i][j] - price;
                if(tmp > max)
                {
                    max = tmp;
                    item = j;
                }
            }
            if (max > 0)
            {
                int currOwner = owners[item];
                owners[item] = i;
                if(currOwner != -1)
                {
                    q.add(currOwner);
                }
                prices[item] += delta;
            }
        }

        int matchingWeight = 0;
        // print out the owner of item j, and how much they value the item by
        for (int j = 0;j < owners.length; j++)
        {
            int i = owners[j];
            System.out.println("(" + (i+1) + "," + (j+1) + ")");
            matchingWeight += weights[i][j];
        }

        // TODO: Print this before the matches
        System.out.println(matchingWeight);


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
