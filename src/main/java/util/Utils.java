package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
    private static int getFirstInteger(String line)
    {
        // Reg ex for first number in line. throw everything else away
        Pattern p = Pattern.compile("([0-9]+).*");
        Matcher m = p.matcher(line);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return  -1;

    }
    public static int[][] readInputFile(String inputFile)
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
