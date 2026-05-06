import java.sql.Connection;

import javax.sound.sampled.Line;

public class navigator 
{
    String currentStation;
    String startingPoint;
    String destination;
    boolean [] visitedStations;
    int [] previousStations;
    float [] distanceFromSource;
    String [] previousStationLineColour;
    station[] network;
    connection[] route;
    float distanceTotal;


    public navigator(station[] networkToTraverse, String startStation, String endStation)
    {
        network = networkToTraverse;
        //System.out.println("**********************************************");
        for(int w = 0; w < networkToTraverse.length; w++)
        {
            //System.out.println(networkToTraverse[w].nameOfStation());
        }
        startingPoint = startStation;
        currentStation = startStation;
        destination = endStation;
        distanceTotal = 0;
        visitedStations = new boolean[network.length];
        previousStations = new int[network.length];
        distanceFromSource = new float[network.length];
        previousStationLineColour = new String[network.length];
        for(int z = 0; z < network.length; z++)
        {
            visitedStations[z] = false;
            previousStations[z] = -999;
            distanceFromSource[z] = 999;
            previousStationLineColour[z] = null;
        }

        int [] startingPointPositionsInArray = whereInArray(startStation);
        for(int x = 0; x < startingPointPositionsInArray.length; x++)
        {
            previousStations[startingPointPositionsInArray[x]] = startingPointPositionsInArray[x];
            distanceFromSource[startingPointPositionsInArray[x]] = 0;
            //System.out.println(startingPointPositionsInArray[x]);
        }
    }

    //RECURSIVE!!!!!
    private void dijkstraAlgorithm()
    {   
        int index;
        while(allVisited() == false)
        {
            //System.out.println("dijkstras loop");
            index = indexOfLowestDistanceFromSource();   
            currentStation = network[index].nameOfStation();
            connection[] connectionsOfCurrentStation = network[index].getConnections();

            

            //IMPORTANT
            visitedStations[index] = true;
            /* 
            int[] vis = whereInArray(network[index].nameOfStation());
            for(int g = 0; g < vis.length; g++)
            {
                visitedStations[vis[g]] = true;
            }
            */
            //IMPORTANT

            for(int x = 0; x < connectionsOfCurrentStation.length; x++)
            {
                if(connectionsOfCurrentStation[x] != null)
                {
                    float estimatedDistance = distanceFromSource[index] + connectionsOfCurrentStation[x].howLong();
                    

                    if(estimatedDistance < distanceFromSource[connectionsOfCurrentStation[x].goingTo()])
                    {
                        distanceFromSource[connectionsOfCurrentStation[x].goingTo()] = estimatedDistance;
                        previousStations[connectionsOfCurrentStation[x].goingTo()] = index;
                        previousStationLineColour[connectionsOfCurrentStation[x].goingTo()] = network[index].getLineColour();
                    }
                }
            }

        }        
        System.out.println("Dijkstra's finished");
    }


    public String[] shortestRouteToDestination()
    {
        dijkstraAlgorithm();
        String stationName = destination;
        int numberOfStationChanges = 0;
        int numberOfLineChanges = 0;
        int[] potentialEndings = whereInArray(stationName);
        int definiteEnding = 0;
        float distanceToSource = 9999;
        for(int w = 0; w < potentialEndings.length; w++)
        {
            if(distanceFromSource[potentialEndings[w]] < distanceToSource)
            {
                distanceToSource = distanceFromSource[potentialEndings[w]];
                definiteEnding = potentialEndings[w];
            }
        }
        int indexWhere = definiteEnding;

        String colour = previousStationLineColour[indexWhere];

        
        while(previousStations[indexWhere] != indexWhere)
        {
            if(previousStationLineColour[indexWhere] != colour)
            {
                numberOfLineChanges++;
                colour = previousStationLineColour[indexWhere];
            }
            numberOfStationChanges++;
            indexWhere = previousStations[indexWhere];
        }

        int numberOfSteps = numberOfStationChanges + 2;
        String[] routeInReverse = new String[numberOfSteps];
        String[] routeProper = new String [numberOfSteps + 1]; 
        stationName = destination;
        indexWhere = definiteEnding;
        colour = previousStationLineColour[indexWhere];
        
        for(int x = 0; x < numberOfSteps; x++)
        {
            
            
            if(previousStationLineColour[indexWhere] != null && previousStationLineColour[indexWhere].equals(colour) == false)
            {
                routeInReverse[x] = ("CHANGE LINE AT " + stationName + " STATION TO THE " + colour + " LINE, THIS WILL ADD 2 MINUTES TO YOUR JOURNEY.");
                colour = previousStationLineColour[indexWhere];
                
            }
            
            else
            {
                routeInReverse[x] = "Go to " + stationName + " on the " + colour + " line. Distance from source is " + distanceFromSource[indexWhere];
            }
            
            if(x == routeInReverse.length - 1)
            {
                routeInReverse[routeInReverse.length - 1] = ("Start your journey at " + stationName +" standing at the " + colour + " platform.");
            }

            if(x == 1)
            {
                routeInReverse[1] = ("Go to " + stationName + " station on the " + colour + " line, this will end your journey for a total travel time of " + distanceFromSource[indexWhere]);
            }

            

            stationName = network[previousStations[indexWhere]].nameOfStation();
            indexWhere = previousStations[indexWhere];
        }

        int z = 0;
        for(int y = numberOfSteps-1; y >= 0; y--)
        {
            routeProper[z] = routeInReverse[y];
            z++;
        }
        routeProper[routeProper.length-1] = ("Number of line changes: " + numberOfLineChanges);

        return routeProper;
    }



    private int indexOfLowestDistanceFromSource()
    {
        
        float lowestDistance = 9999999;
        int lowestIndex = -1;
        //System.out.println(lineCol);
        for (int x = 0; x < network.length; x++)
        {

                if(network[x] != null)
                {
                    if(visitedStations[x] == false)
                    {    
                        if(distanceFromSource[x] < lowestDistance)
                        {
                            lowestDistance = distanceFromSource[x];
                            lowestIndex = x;
                        }
                    }
                    
                    //System.out.println(x);
                    //System.out.println(network.length);
                }
                
        
        }

        return lowestIndex;
    }

    

    private boolean allVisited()
    {
        for(int x = 0; x < visitedStations.length; x++)
        {   
            if(visitedStations[x] == false)
            {
                return false;
            }
        }
        return true;
    }

    public void stationsDelayed(String[] delayedConnections, int[] delayTimes)
    {
        for (int x = 0; x < delayedConnections.length; x++)
        {

        }
    }
    /* 
    public void stationsClosed(String[] closures)
    {
        
        for(int x = 0; x < closures.length; x++)
        {
            int[] needClosing = whereInArray(closures[x]);
            for(int z = 0; z < needClosing.length; z++)
            {
                for(int y = 0; y < network.length; y++)
                {
                    if(network[y] != null)
                    {
                        network[y].cutOff(needClosing[z]);
                    }
                }
                network[needClosing[z]] = null;
                visitedStations[needClosing[z]] = true;
            }
        }
    }
    */

    private int[] whereInArray(String stationName)                                         //returns the position a station holds in the stations array when given its name
    {
        int arraylength = 0;
        for(int x = 0; x < network.length; x++)                                                //For every station in the array
        {
            if(network[x] != null)
            {
                if(network[x].nameOfStation().equals(stationName) == true)                        //If the station being checked's name is the same as the one we are looking for
                {
                    arraylength ++;                                                                       //Return the current index
                }
            }
        }
        if(arraylength == 0)
        {
            return null;
        }

        int[] placesInArray = new int[arraylength];
        int y = 0;
        for(int x = 0; x < network.length; x++)                                                //For every station in the array
        {
            if(network[x] != null)
            {
                if(network[x].nameOfStation().equals(stationName) == true)                        //If the station being checked's name is the same as the one we are looking for
                {
                    placesInArray[y] = x;
                    y++;                                                                     //Return the current index
                }
            }
        }

        return placesInArray;

                                                                                      //return -1 if it is not in the array
    }



    //Walking, at every stage of the dijstras algorithm, make a new connections array as long as the number of stations in the whole network and recursively compute potential routes including walking times
}
