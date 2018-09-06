package KM;

import util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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
        int[][] weights = Utils.readInputFile(inputFile);
        if(weights == null)
        {
            System.err.println("Input file is malformed");
        }
        KM(weights);
    }

    public static int[] KM(int[][] weights)
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
         * M is a mapping for vertices in X to vertices in Y
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
        boolean addU = true;
        while( M.size() < perfectMatchSize)
        {
            //Find an exposed (non-matched) vertex "u" then add to Set "S"
            int u = exposedXVertex(M, sizeX);
            if(addU == true)
            {
                S.add(u);
            }

            // TODO: Bug. When u is 0, Nls is {0}
            // Get the neighbors of set S that are in the equality graph
            Set<Integer> NlS = getLabelNeighbors(S, Eg);
            
            /* STEP#3 If the set of label Neighbors of Set S ("NlS") equals 
            set T, then update the labels to improve them so that Nls != T 
            the next time through the loop.
            */
            if (NlS.equals(T))
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
                int y = findFreeVertexInNeighbors(NlS,T);

                if (alreadyMatched(y, M))
                {
                    int z = getMatching(M,y);
                    T.add(y);
                    S.add(z);
                    addU = false;
                    // TODO: Go back to step 3 after this (skipping lines 74-75)?
                }
                else
                {
                    // TODO: AugmentMatching is not increasing the size of M
                    M = augmentMatching(M, Eg, u, y);
                    addU = true;
                }
            }
        } // End while NOT perfect Matching loop


        /*
           The Hungarian Algorithm has finished when M is Perfect Matching size 
        */
        long endTime=System.nanoTime();
        long totalTime=endTime-startTime;

        System.out.println("Total time taken for KM is "+totalTime);

        int[] matches = new int[weights.length];
        int matchingWeight = getMatchingWeight(M,weights,matches);

        // First print out the matching weight
        System.out.println(matchingWeight);       
        for (int i = 0; i < matches.length; i++)
        {
            int j = matches[i];
            System.out.println("(" + (i+1) + "," + (j+1) + ")");
        }
        return matches;
    } // End KM()


    private static int getMatchingWeight(Map<Integer,Integer> M, int[][] weights, int[] matches)
    {
        /* Iterate through Matching set "M" hash map to sum the matching weight,
           as well a order the matching edges to print out.
        */
        int matchingWeight = 0;
        for (Map.Entry<Integer, Integer> entry : M.entrySet())
        {
            int i = entry.getKey();
            int j = entry.getValue();
            matchingWeight += weights[i][j];
            matches[i] = j;
        } // End For each loop through Matching set "M"
        return matchingWeight;

    }

    /**
     * Pick y that is a member of the set of label neighbors of
     * Set S, but are not member of set T.
     *
     */
    private static int findFreeVertexInNeighbors(Set<Integer> neighbors, Set<Integer> T)
    {
        for(Integer n: neighbors)
        {
            if(T.contains(n) == false)
            {
                return n;
            }
        }

        return -1;
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
    private static Set getLabelNeighbors(Set<Integer> S, int[][] eqg)
    {
        Set<Integer> N = new HashSet<Integer>();
       
        int sizeY = eqg[0].length;

        for (Integer x:S)
        {
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
        for (Integer x: S)
        {
            for (int y=0; y < sizeY;y++)
            {
                if(T.contains(y) == false)
                {
                    int slack = lmap.get("x"+x) + lmap.get("y"+y) - weights[x][y];
                    if (slack < alpha)
                        alpha = slack;
                }
            }
        }
        // Update the labels for all vertices
        // If the vertex is in S, then subtract alpha from its current label
        // If the vertex is in T, then add alpha to its current label
        // else, leave the label as is
        for (int x=0; x < sizeX;x++)
        {
            if(S.contains(x))
            {
                int currLabel = lmap.get("x"+x);
                lmap.put("x"+x,currLabel-alpha);
            }
        }

        for (int y=0; y < sizeY;y++)
        {
            if(T.contains(y))
            {
                int currLabel = lmap.get("y"+y);
                lmap.put("y"+y,currLabel+alpha);
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
        // Check if some x is matched to y
        return matchMap.containsValue(y);
    }

    /**
     * Finds the key for a value in the matching map (gets the x from the y)
     * @param map
     * @param valueToLookFor
     * @return
     */
    private static int getMatching(Map<Integer,Integer> map, int valueToLookFor)
    {
        for (Integer x: map.keySet())
        {
            int y = map.get(x);
            if(y == valueToLookFor)
            {
                return x;
            }
        }
        return -1;
    }
    
    /**
     * Return the augmented Matching map based on the Equality graph "
     * @param matchMap
     * @param eqg
     * @param u
     * @param y
     * @return 
     */
    private static Map<Integer,Integer> augmentMatching(
        Map<Integer,Integer> matchMap,
        int[][] eqg,
        int u,
        int y)
    {      
        int sizeX = eqg.length;
        int sizeY = eqg[0].length;
        // u to y is an augmenting path along in the equalityGraph from u to y
        List<Integer> path = getAugmentingPath(matchMap,eqg, u, y);
        // Path is an augmenting path, no flip it to augmentM
        augmentMatch(matchMap, path);
        return matchMap;
    }

    private static void augmentMatch(Map<Integer,Integer> map, List<Integer> path)
    {
        boolean isX = true;
        boolean match = true;
        for (int index =0; index < path.size()-1; index++)
        {
            Integer i = path.get(index);
            if(isX == true)
            {
                if(match == true)
                {
                    // match x to the next vertex in the path
                    Integer next = path.get(index+1);
                    map.put(i,next);

                }else
                {
                    // i should now be unmatched
                    map.put(i, null);
                }

            }else
            {
                int x = getMatching(map,i);
                // X should not be matched with y anymore
                map.put(x,null);
                if(match == true)
                {
                    // match y to the next vertex in the path
                    Integer next = path.get(index+1);
                    map.put(next,i);

                }else
                {
                    // nothing to do
                }

            }

        }

    }

    /**
     * Returns the augmenting path in a matching
     * @param map
     * @param graph
     * @param source
     * @param destination
     * @return
     */
    private static List<Integer> getAugmentingPath(Map<Integer,Integer> map, int[][] graph, int source, int destination)
    {
        List<Integer> path = new ArrayList<Integer>();
        Map<String, Boolean> visited = new HashMap<String, Boolean>();
        path.add(source);
        Set<List<Integer>> paths = new HashSet<List<Integer>>();
        // Populate paths with the possible paths from the source to the destination
        getPaths(paths, graph,source,true,destination,visited,path);

        List<Integer> augmentingPath = findAugmentingPath(paths,map);
        return augmentingPath;
    }

    /**
     * Get the augmenting path from the possible paths
     * @param paths
     * @param map
     * @return
     */
    private static List<Integer> findAugmentingPath(Set<List<Integer>> paths,Map<Integer,Integer> map)
    {
        for(List<Integer> path : paths)
        {
            if( isAugmenting(path,map))
            {
                return path;
            }
        }
        return null;
    }

    /**
     * A path is augmenting if it's alternating between unmatching and matching (both ends are unmatched)
     * @param path
     * @param map
     * @return
     */
    private static boolean isAugmenting(List<Integer> path, Map<Integer,Integer> map)
    {
        // Start of path should be unmatched
        boolean shouldSeeMatch = false;
        // Start at a vertex in X
        boolean startNodeInX = true;
        for(int i = 0; i < path.size() - 1; i++)
        {
            int start = path.get(i);
            int end = path.get(i+1);
            if(startNodeInX == true)
            {
                if(shouldSeeMatch == true)
                {
                    // the start should be matched to the end
                    if(map.get(start) != end)
                    {
                        return false;
                    }

                }else
                {
                    // The start and the end should not be matched
                    if(map.get(start) != null && end == map.get(start))
                    {
                        return false;
                    }
                }
            }else
            {
                if(shouldSeeMatch == true)
                {
                    // the start should be matched to the end
                    if(map.get(end) != start)
                    {
                        return false;
                    }

                }else
                {
                    // The start and the end should not be matched
                    if(map.get(end) != null && start == map.get(end))
                    {
                        return false;
                    }
                }

            }
        }

        /** TODO: Check if this code is needed
            startNodeInX = !startNodeInX;
            shouldSeeMatch = !shouldSeeMatch;
        // The path should end on y and no match
        if(startNodeInX == true || shouldSeeMatch == true)
        {
            return false;
        }
         **/
        return true;
    }

    /**
     * Find the path from the source to the destination along the graph recursively
     * @param graph
     * @param source
     * @param isSourceX true if the source is in X (the destination is always in Y)
     * @param destination
     * @param visited
     * @param path
     * @return
     */
    private static void getPaths(Set<List<Integer>> paths, int[][] graph, int source, boolean isSourceX, int destination, Map<String,Boolean> visited,List<Integer> path)
    {
        String sourceLabel = "x";

        if(isSourceX == false)
        {
            sourceLabel = "y";
        }
        visited.put(sourceLabel + source,true);
        if(isSourceX == false)
        {
            if(source == destination)
            {
                // We found the destination
                // Make a copy of the path, then add it to our set
                List<Integer> copy = new ArrayList<Integer>();
                copy.addAll(path);
                paths.add(copy);
            }
        }

        int sizeX = graph.length;
        int sizeY = graph[0].length;
        // We are looking for a path starting at a node in X
        if(isSourceX == true)
        {
            int x = source;
            for(int y = 0; y < sizeY;y++)
            {
                // There is an edge from x to y in the graph
                if(graph[x][y] > 0)
                {
                    Boolean bool = visited.get("y"+y);
                    if(bool == null || bool.booleanValue() == false)
                    {
                        // We have not visited vertex y
                        // Add y to the path
                        Integer Y = new Integer(y);
                        path.add(Y);
                        getPaths(paths,graph,y,false,destination,visited,path);
                        // Remove the current node from the path
                        path.remove(Y);
                    }
                }

            }
        }
        // We are looking for a subpath starting at a node in Y
        else
        {
            int y = source;
            for (int x =0; x < sizeX; x++)
            {
                // There is an edge from y to x in the graph
                if(graph[x][y] > 0)
                {
                    Boolean bool = visited.get("x"+x);
                    if(bool == null || bool.booleanValue() == false)
                    {
                        // We have not visited vertex x
                        // Add x to the path
                        Integer X = new Integer(x);
                        path.add(X);
                        getPaths(paths,graph,y,true,destination,visited,path);
                        // Remove the current node from the path
                        path.remove(X);
                    }
                }
            }
            // Mark the current node as unvisited
            visited.put(sourceLabel + source,false);
        }
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
}
