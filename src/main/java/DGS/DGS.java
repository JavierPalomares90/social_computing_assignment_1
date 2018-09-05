package DGS;

import util.Utils;

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
        int[][] weights = Utils.readInputFile(inputFile);
        if(weights == null)
        {
            System.err.println("Input file is malformed");
        }
        DGS(weights);
    }

    public static int[] DGS(int[][] weights)
    {
        // Get the start time
        long startTime=System.nanoTime();

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

        long endTime=System.nanoTime();
        long totalTime=endTime-startTime;

        System.out.println("Total time taken for DGS is "+totalTime);

        int matchingWeight = getMatchingWeight(weights,owners);

        // TODO: Print this before the matches
        System.out.println(matchingWeight);
        return owners;
    }

    public static int getMatchingWeight(int[][] weights, int[] owners)
    {
        int matchingWeight = 0;
        // print out the owner of item j, and how much they value the item by
        for (int j = 0;j < owners.length; j++)
        {
            int i = owners[j];
            System.out.println("(" + (i+1) + "," + (j+1) + ")");
            matchingWeight += weights[i][j];
        }
        return matchingWeight;
    }



}
