package KM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
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
        // Get the start time
        long startTime=System.nanoTime();

        int sizeX = weights.length;
        int sizeY = weights[0].length;
        // Set Perfect Match size, assuming a square graph where |X| = |Y|
        int perfectMatchSize = weights.length;
        
        /* STEP#1: Get initial feasible labeling of each vertex x and y of 
           sets X and Y respectively. All y labels will be intialize to 0, 
           while the x verticies will be set to the maximum weight of all of 
           their connected edges. Then, build equality graph of all tight edges.
        */
        Map<String, Integer> labeling = getInitialLabeling(weights);
        /* Based on intial labeling, populate Equality graph "Eg" with all 
           vertex pairs that form "tight" edges based on the current labeling.
        */
        int[][] Eg = getEqualityGraph(labeling, weights);
        /* Initialize matching set "M" to the empty set
        */
        Map<Integer, Integer> M = new HashMap<Integer, Integer>();
        M.clear();
        /* Initialize Set "S", subset of "X", to the empty set
        */
        Set<Integer> S = new HashSet<Integer>();
        S.clear();
        /* Initialize Set "T", subset of "Y", to the empty set
        */
        Set<Integer> T = new HashSet<Integer>();
        T.clear();

        /* STEP#2: If matching set "M" is perfect, then stop. Otherwise, pick an
           exposed vertex "u" from set X.
        */
        while( M.size() < perfectMatchSize)
        {            
            //Find an exposed (non-matched) vertex "u" then add to Set "S"
            int u = exposedXVertex(M, sizeX);
            S.add(u);
            
            // Get the neighbors of set S that are in the equality graph
            Set<Integer> NlS = getLabelNeighbors(S, Eg);
            
            /* STEP#3 If the set of label Neighbors of Set S ("NlS") equals 
            set T, then update the labels to improve them so that Nls != T 
            the next time through the loop.
            */
            if (NlS == T)
            {
                // Improve labeling map
                labeling = updateLabeling(labeling, weights, S, T);
                // Get updated Equality graph based on updated labeling
                Eg = getEqualityGraph(labeling, weights);
            }
            /* STEP#4 If NlS != T, then try to augment the matching set "M" by 
            picking a vertex "y" belonging to (NlS - T). If y is free, then 
            augment Matching set and go back to STEP#2 at the begining of while 
            loop. Otherwise, if y is already matched to vertex "z", then add z
            to set  S and add y to set T, then go back through the while loop 
            to get back to STEP#3. 
            */
            else
            {
                /* Pick y that is a member of the set of label neighbors of 
                Set S, but are not member of set T.
                */
                Iterator nIterator = NlS.iterator();
                int y = 0;
                while (nIterator.hasNext())
                {
                    y = (Integer) nIterator.next();
                    if (!T.contains(y))
                        break;
                }
                
                if (alreadyMatched(y, M))
                {
                    int z = M.get(y);
                    T.add(y);
                    S.add(z);
                }
                else
                {
                    M = augmentMatching(M, Eg, u, y);
                }
            }
        } // End while NOT perfect Matching loop
        
        /*
           The Hungarian Algorithm has finished when M is Perfect Matching size 
        */
        long endTime=System.nanoTime();
        long totalTime=endTime-startTime;

        System.out.println("Total time taken for KM is "+totalTime);

        int matchingWeight = 0;
        int[] matches = new int[weights.length];
        /* Iterate through Matching set "M" hash map to sum the matching weight, 
           as well a order the matching edges to print out.
        */
        for (Map.Entry<Integer, Integer> entry : M.entrySet())
        {
            int i = entry.getKey();
            int j = entry.getValue();
            matchingWeight += weights[i][j];
            matches[i] = j;
        } // End For each loop through Matching set "M"
        
        // First print out the matching weight
        System.out.println(matchingWeight);       
        for (int i = 0; i < matches.length; i++)
        {
            int j = matches[i];
            System.out.println("(" + (i+1) + "," + (j+1) + ")");
        }
    } // End KM()

    
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

    /**
     * Return the Equality graph of tight edges given the input labeling map and
     *    original weight bipartite graph. Equality graph modeled as a matrix 
     *    with a value of 1 indicating the existence of an edge, 0 otherwise.
     * @param lmap
     * @param weights
     * @return 
     */
    private static int [][] getEqualityGraph(
            Map<String,Integer> lmap,
            int[][] weights)
    {      
        int sizeX = weights.length;
        int sizeY = weights[0].length;
        int [][] eqg = new int[sizeX][sizeY];
        for( int y=0; y < sizeY; y++)
        {
            for( int x=0; x < sizeX; x++)
            {
                eqg[x][y] = 0;
                int xlabel = lmap.get("x"+x);
                int ylabel = lmap.get("y"+y);
                /* If the edge is tight, where the weight of the labels values
                   is equal to the sum of the label of each vertext end.
                */
                if (weights[x][y] == (xlabel + ylabel))
                {
                    eqg[x][y] = 1;
                }
            } // End for loop through x labels
        } // End for loop through y labels
        return eqg;
    }
        
    /**
     * Return the set of neighbors of input set "S" in the equality graph "eqg"
     * of tight edges base on the current labeling.
     * @param S
     * @param eqg
     * @return 
     */
    private static Set getLabelNeighbors(Set S, int[][] eqg)       
    {
        Set<Integer> N = new HashSet<Integer>();
       
        int sizeY = eqg[0].length;
        
        Iterator itr = S.iterator();
        while (itr.hasNext())
        {
            int x = (Integer) itr.next();
            for (int y=0; y<sizeY; y++)
            {
                if (eqg[x][y] > 0)
                    N.add(y);
            }
        }
        return N;
    }
        
    /**
     * Return an updated/refined Labeling map for the bipartite graph
     * @param lmap
     * @param weights
     * @param S
     * @param T
     * @return 
     */
    private static Map<String,Integer> updateLabeling(
            Map<String,Integer> lmap,
            int[][] weights,
            Set<Integer> S,
            Set<Integer> T)
    {
        int sizeX = weights.length;
        int sizeY = weights[0].length;
        
        int alpha = Integer.MAX_VALUE;
        // Find the minimum slack
        Iterator itr = S.iterator();
        while (itr.hasNext())
        {
            int x = (Integer) itr.next();
            for (int y=0; y<sizeY; y++)
            {
                if (!T.contains(y))
                {
                    int slack = lmap.get("x"+x) + lmap.get("y"+y) - weights[x][y];
                    if (slack < alpha)
                        alpha = slack;
                }
            }
        }
        // Improve the appropriate labels based on alpha
        for (int y=0; y<sizeY; y++)
        {
            if (T.contains(y))
            {
                for (int x=0; x<sizeX; x++)
                {
                    if (S.contains(x))
                    {
                        int newLabelX = lmap.get("x"+x) - alpha;
                        int newLabelY = lmap.get("y"+y) + alpha;
                        lmap.put("x"+x, newLabelX);
                        lmap.put("y"+y, newLabelY);
                    }
                }
            }
        }
        
        return lmap; 
    }

    /**
     * Return the first exposed/free (unmatched) vertex of Set X
     * @param map   The input hash map of current matching set
     * @param sizeX The number of elements in set X
     * @return 
     */
    private static int exposedXVertex( Map<Integer,Integer> map, int sizeX)
    {
        for (int x=0; x<sizeX; x++)
        {
            if (map.get(x) == null);
                return x;
        }
        return -1;
    }

    /**
     * Returns T/F if a given vertex y has already been matched.
     * @param y
     * @param matchMap
     * @return 
     */
    private static boolean alreadyMatched(int y, Map<Integer, Integer> matchMap)
    {      
        if(matchMap.containsValue(y))
            return true;
        else
            return false;
    }
    
    /**
     * Return the augmented Matching map based on the Equality graph "
     * @param matchMap
     * @param eqg
     * @param x
     * @param y
     * @return 
     */
    private static Map<Integer,Integer> augmentMatching(
        Map<Integer,Integer> matchMap,
        int[][] eqg,
        int x, 
        int y)
    {      
        int sizeX = eqg.length;
        int sizeY = eqg[0].length;
        /*
         * TODO: Implementation macthing augmentation
         */
        return matchMap;
    }


    /**
     * Gets the maximum weight of edges (i,j) for some i
     * @param weights
     * @param i
     * @return
     */
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
