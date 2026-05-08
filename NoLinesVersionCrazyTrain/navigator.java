import java.io.FileNotFoundException;
import java.sql.Connection;

import javax.sound.sampled.Line;

public class navigator 
{
    private String currentStation;
    private String startingStation;
    private String destination;
    private boolean [] visitedStations;
    private int [] previousStations;
    private float [] distanceFromSource;
    private String [] previousStationLineColour;
    private station [] network;
    private float distanceTotal;
    private csvReader2 walkingTimesReader;
    private lineOfText2[] walkingTimesData;
    private int[] numberOfSwapsPrior;
    private boolean preferTime;
    private boolean preferSwap;

    public navigator(station[] networkToTraverse, String startStation, String endStation, boolean prefTime, boolean prefSwap)
    {
        walkingTimesData = null;
        network = networkToTraverse;
        startingStation = startStation;
        currentStation = startingStation;
        destination = endStation;
        distanceTotal = 0;
        visitedStations = new boolean[network.length];
        previousStations = new int[network.length];
        distanceFromSource = new float[network.length];
        previousStationLineColour = new String[network.length];
        numberOfSwapsPrior = new int[network.length];

        preferTime = prefTime;
        preferSwap = prefSwap;

        for(int z = 0; z < network.length; z++)
        {
            visitedStations[z] = false;
            previousStations[z] = -999;
            distanceFromSource[z] = 999;
            previousStationLineColour[z] = null;

        }

        int [] sppSInArray = whereInArray(startStation);

        for(int x = 0; x < sppSInArray.length; x++)
        {
            if(sppSInArray[x] != 0)
            {
                previousStations[sppSInArray[x]] = sppSInArray[x];
                distanceFromSource[sppSInArray[x]] = 0;
                numberOfSwapsPrior[sppSInArray[x]] = 0;
            }
        }
    }

    public navigator(station[] networkToTraverse, String startStation, String endStation, boolean prefTime, boolean prefSwap, String walktimesFileName) throws FileNotFoundException
    {
        walkingTimesReader = new csvReader2(walktimesFileName);
        walkingTimesData = walkingTimesReader.readCSVData2();
        network = networkToTraverse;
        startingStation = startStation;
        currentStation = startingStation;
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

        int[] startingPointPositionsInArray = whereInArray(currentStation);

        for(int x = 0; x < startingPointPositionsInArray.length; x++)
        {
            previousStations[startingPointPositionsInArray[x]] = startingPointPositionsInArray[x];
            distanceFromSource[startingPointPositionsInArray[x]] = 0;
        }
    }

    //RECURSIVE!!!!!
    private void dijkstraAlgorithm()
    {   

        if(preferTime)
        {
            int index;
            while(allVisited() == false)
            {

                index = indexOfLowestDistanceFromSource();   
                currentStation = network[index].nameOfStation();
                connection[] connectionsOfCurrentStation = network[index].getConnections();

                
                visitedStations[index] = true;


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

                if(walkingTimesData != null)
                {
                    int rowToCheck = walkingTimesData[0].stationNumberInArray(currentStation);
                    for(int y = 1; y < walkingTimesData.length; y++)
                    {   
                        int[] stationLocations = whereInArray(walkingTimesData[0].readValue(y));
                        float estimatedDistance = distanceFromSource[index] + Float.parseFloat(walkingTimesData[rowToCheck].readValue(y));
                        for(int z = 0; z < stationLocations.length; z++)
                        {
                            if(estimatedDistance < distanceFromSource[stationLocations[z]])
                            {
                                distanceFromSource[stationLocations[z]] = estimatedDistance;
                                previousStations[stationLocations[z]] = index;
                                previousStationLineColour[stationLocations[z]] = "Walking";
                            }
                        }
                    }
                }
            }        
        }   

        
        else
        {
            if(preferSwap)
            {
                System.out.println("SWAP!");
                int index;
                while(allVisited() == false)
                {

                    index = indexOfFewestChangesFromSource();   
                    currentStation = network[index].nameOfStation();
                    connection[] connectionsOfCurrentStation = network[index].getConnections();

                    
                    visitedStations[index] = true;


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
                                if(previousStationLineColour[connectionsOfCurrentStation[x].goingTo()].equals(network[index].getLineColour()) == false)
                                {
                                    numberOfSwapsPrior[connectionsOfCurrentStation[x].goingTo()] = numberOfSwapsPrior[index] + 1;
                                } 
                            }
                        }
                        if(network[x] != null)
                        {
                            System.out.println(distanceFromSource[x]);         
                        }
                    }
                }     
            }
        }
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





        if(distanceToSource >= 999 && walkingTimesData == null)
        {
            String[] noRoute = new String[1];
            noRoute[0] = "There is no route to" + destination + " from " + startingStation +" unless you walk at some point on the journey.";
            return noRoute;
        }





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

                if(walkingTimesData != null)
                {
                    routeInReverse[x] = ("Walk to " + stationName+ ". Time from start is " + distanceFromSource[indexWhere]);
                }    
            }
            
            else
            {
                routeInReverse[x] = "Go to " + stationName + " on the " + colour + " line. Time from start is " + distanceFromSource[indexWhere];

                if(colour.equals("Walking"))
                {
                    routeInReverse[x] = ("Continue walking to " + stationName + ". Time from start is " + distanceFromSource[indexWhere]);
                }
            }
            
            if(x == routeInReverse.length - 1)
            {
                routeInReverse[routeInReverse.length - 1] = ("Start your journey at " + stationName +" standing at the " + colour + " platform.");
                
                if(colour.equals("Walking"))
                {
                    routeInReverse[x] = ("Start your journey at " + stationName + " and get ready to walk.");
                }
            }

            if(x == 0)
            {
                routeInReverse[x] = ("Go to " + stationName + " station on the " + colour + " line, this will end your journey for a total travel time of " + distanceFromSource[indexWhere]);
                
                if(colour.equals("Walking"))
                {
                    routeInReverse[x] = ("Walk to " + stationName + ", this will end your journey for a total travel time of " + distanceFromSource[indexWhere]);
                }
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
            }    
        }

        return lowestIndex;
    }

    private int indexOfFewestChangesFromSource()
    {
        
        float fewestChanges = 9999999;
        int lowestIndex = -1;
        for (int x = 0; x < network.length; x++)
        {
            if(network[x] != null)
            {
                if(visitedStations[x] == false)
                {    
                    if(numberOfSwapsPrior[x] < fewestChanges)
                    {
                        fewestChanges = numberOfSwapsPrior[x];
                        lowestIndex = x;
                    }
                }
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

    public boolean stationDelayed(String delayFirstStation, String delaySecondStation, String lc ,float time)
    {
        boolean delay1 = false;
        boolean delay2 = false;

        int[] potentialPlaces1 = whereInArray(delayFirstStation);
        int definitePlace1 = 0;
        int[] potentialPlaces2 = whereInArray(delaySecondStation);
        int definitePlace2 = 0;

        for(int y = 0; y < potentialPlaces1.length; y++)
        {
            if(network[potentialPlaces1[y]].getLineColour().equals(lc))
            {
                definitePlace1 = potentialPlaces1[y];
            }
        }

        for(int z = 0; z < potentialPlaces2.length; z++)
        {
            if(network[potentialPlaces2[z]].getLineColour().equals(lc))
            {
                definitePlace2 = potentialPlaces2[z];
            }
        }

        if(network[definitePlace1].connectedTo(definitePlace2) && network[definitePlace2].connectedTo(definitePlace1))
        {
            delay1 = network[definitePlace1].delayConnection(definitePlace2, time);
            delay2 = network[definitePlace2].delayConnection(definitePlace1, time);
        }
        
        if(delay1 && delay2 == true)
        {
            return true;
        }

        return false;
    }

     
    public boolean stationClosed(String closing)
    {
    
        int[] needClosing = whereInArray(closing);
        if(needClosing == null)
        {
            return false;
        }

        for(int x = 0; x < needClosing.length; x++)
        {
            for(int y = 0; y < network.length; y++)
            {
                if(network[y] != null)
                {
                    network[y].cutOff(needClosing[x]);
                }
            }
            network[needClosing[x]] = null;
            visitedStations[needClosing[x]] = true;
        }
        return true;
    }
    

    public int[] whereInArray(String stationName)                                         //returns the position a station holds in the stations array when given its name
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
        for(int z = 0; z < network.length; z++)                                                //For every station in the array
        {
            if(network[z] != null)
            {
                if(network[z].nameOfStation().equals(stationName) == true)                        //If the station being checked's name is the same as the one we are looking for
                {
                    placesInArray[y] = z;
                    y++;                                                                     //Return the current index
                }
            }
        }
        return placesInArray;
                                                                                      //return -1 if it is not in the array
    }

    /* 
    public String[] getStationNamesInOrder()
    {

    }

    public int[] getStationDistancesInOrder()
    {

    }

    public String[] getColoursInOrder()
    {

    }
    */


    //Walking, at every stage of the dijstras algorithm, make a new connections array as long as the number of stations in the whole network and recursively compute potential routes including walking times
}
