import java.io.*;
import javax.sound.sampled.Line;
import java.io.File;                  
import java.io.FileNotFoundException;

public class crazyTrain 
{
    private String stationsFile;                              //String to store the name of the file to be acessed
    private csvReader reader;                               //csvReader to store the reader needed to read the csv file
    private lineOfText[] csvData;                           //Array of lines of text to store the csv data for use throughout the program
    private navigator routeFinder;
    private station[] stations;
    private String stationNames;
    private String startingPoint;
    private String destination;
    private int numberOfTrainLines;
    private String walkingFile;
    private boolean allowWalk;
    private boolean preferTime;
    private boolean preferSwaps;

    public crazyTrain(String nameOfStationsFile, String nameOfWalkingTimesFile) throws FileNotFoundException                    //Constructor makes the overall file reader, gets the arrays ready and calls the buildlines function to get everything going
    {   
        preferTime = true;
        preferSwaps = false;
        stationsFile = nameOfStationsFile;                              //Should be input late in the interface or some other way
        walkingFile = nameOfWalkingTimesFile;
        reader = new csvReader(stationsFile);                             //Creates new reader for the file
        csvData = new lineOfText[(reader.readCSVData()).length];        //Creates new array of linesOfText to returned array from the csvReader
        csvData = reader.readCSVData();                                 //Reads the csv data into the csvData array
        stations = new station[numberOfStations()];
        numberOfTrainLines = numberOfLines();
        allowWalk = false;
        buildGraph();


    }


    public void setSP(String sp)
    {
        startingPoint = sp;
        System.out.println(startingPoint);
    }

    public void setDest(String dest)
    {
        destination = dest;
        System.out.println(destination);
    }

    public void setPreferTime(boolean tOf)
    {
        preferTime = tOf;
    }

    public void setPreferSwaps(boolean tOf)
    {
        preferSwaps = tOf;
    }

    public void setAllowWalk(boolean tOf)
    {
        allowWalk = tOf;
    }


    public String[] getRoute() throws FileNotFoundException
    {
        String[] route;
        System.out.println(startingPoint);
        System.out.println(destination);

        if(walkingFile == null)
        {
            routeFinder = new navigator(stations, startingPoint, destination, preferTime, preferSwaps);
            route = routeFinder.shortestRouteToDestination();
        }

        else
        {
            routeFinder = new navigator(stations, startingPoint, destination, preferTime, preferSwaps, walkingFile);
            route = routeFinder.shortestRouteToDestination();
        }

        return route;
    }

    public String[] returnLineNames()
    {
        String[] lN = new String[numberOfTrainLines];
        for(int x = 0; x < stations.length; x++)
        {
            if(stations[x] != null)
            {
                boolean newColour = true;

                for (int y = 0; y < lN.length; y++)
                {
                    if(lN[y] != null)
                    {

                        if(lN[y].equals(stations[x].getLineColour()))
                        {
                            newColour = false;
                        }
                        
                    }
                }
                
                if(newColour = true)
                {
                    int z = 0;
                    while(lN[z] != null);
                    {
                        z++;
                    }
                    
                    lN[z] = stations[x].getLineColour();
                }
            }
        }

        return lN;
    }

    public String[] returnStationNamesInOrder()
    {
        String[] toReturn = null; //= routeFinder.getStationNamesInOrder();
        return toReturn;
    }

    public int[] returnTimesInOrder()
    {
        int[] toReturn = null; //= routeFinder.getStationDistancesInOrder();
        return toReturn;

    }

    public String[] returnColoursInOrder()
    {
        String[] toReturn = null; //= routeFinder.getColoursInOrder();
        return toReturn;
    }

    private void buildGraph()                                                                                                           //Fills out the lines arrays with the stations and adds the connections between the stations to the stations, fills out every level of the graph structure
    {
        
        String lineColour = null;
        int stationsIndex = 0;                                                                                                            //index to keep track of what line we have just added
        for (int x = 0; x < csvData.length; x++)                                                                                        //For every entry in the csvData array      
        { 
            if (csvData[x].lineChecker() == true)                                                                                       //If the linechecker tells us this is a new line then the line colour/name is set, the new line is created and the lines index increments 
            {
                lineColour = csvData[x].getFirstWord();                                                                                 //Sets line colour for use in creating the new line  
            }

            else
            {
                if (csvData[x].lineChecker() == false)                                                                                      //If the line checker tells us the current line is not creating a new line then it adds the new stations and connection to the relevant arrays
                {

                    if(notAlreadyThere(csvData[x].getFirstWord(), lineColour))
                    {
                        addStation(stationsIndex, csvData[x].getFirstWord(), lineColour, numberOfTrainLines);
                        stationsIndex++;
                    }

                    if(notAlreadyThere(csvData[x].getSecondWord(), lineColour))
                    {
                        addStation(stationsIndex, csvData[x].getSecondWord(), lineColour, numberOfTrainLines);
                        stationsIndex++;

                    }
                    connectStations(getStationNumber(lineColour, csvData[x].getFirstWord()), getStationNumber(lineColour, csvData[x].getSecondWord()), lineColour,csvData[x].getNumber());
                }
            }

        }

        for(int z = 0; z < stations.length; z++)
        {
            int numRepeats = 0;
            String name = stations[z].nameOfStation();
            String colour = stations[z].getLineColour();
            for(int y = 0; y < stations.length; y++)
            {
                if(stations[y] != null)
                {

                    if(stations[y].getLineColour().equals(colour) == false)
                    {
                        if(stations[y].nameOfStation().equals(name))
                        {
                            numRepeats++;
                        }
                    }
                }
            }

            int[] repeatLocations = new int[numRepeats];
            int reps = 0;

            for(int v = 0; v < stations.length; v++)
            {
                if(stations[v] != null)
                {
                    if(stations[v].getLineColour().equals(colour) == false)
                    {
                        if(stations[v].nameOfStation().equals(name))
                        {
                            repeatLocations[reps] = stations[v].getStationNumber();
                            reps++;
                        }
                    }
                }
            }

            for(int i = 0; i < repeatLocations.length; i++)
            {
                connectStations(stations[z].getStationNumber(), repeatLocations[i], stations[repeatLocations[i]].getLineColour(), 2);
            }           
        }
    }

    private boolean notAlreadyThere(String nameToCheck, String lineToCheck)
    {
        String name = nameToCheck;
        String col = lineToCheck;
        for(int x = 0; x < stations.length; x++)
        {
            if(stations[x] != null)
            {
                if(stations[x].getLineColour().equals(col))
                {
                    if(stations[x].nameOfStation().equals(name))
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private int getStationNumber(String col, String name)
    {
        for(int x = 0; x < stations.length; x++)
        {
            if(stations[x] != null)
            {
                if(stations[x].getLineColour().equals(col))
                {
                    if(stations[x].nameOfStation().equals(name))
                    {
                        return x;
                    }
                }
            }
        }
        return -1;
    }

    public void connectStations(int fromStation, int toStation, String  connectionColour, float timeToStation)      //Utilises all of the data on each line of the csv file to add every station to the array and add the relevant connection each line represents to both stations 
    {
        for (int x = 0; x < stations.length; x++)                                              //Adds the same connection to both the to and from stations so the user can move in both directions
        {
            if(stations[x] != null)                                                             //If index x in stations is not null
            {
                if(x == fromStation)                     //If the from station has the same name as an existing station
                {
                    stations[x].connectTo(toStation, timeToStation, connectionColour);                            //Connect that station to the fromstation
                }
            
                if(x == toStation)                       //If the to station has the same name as an existing station
                {
                    stations[x].connectTo(fromStation, timeToStation, connectionColour);                          //Connect that station to the fromstation
                }
            }
        }
    }

    private void addStation(int arrayPosition, String stationName, String linCol, int numberOfLines)                                                                     //Adds a train station to the list of stations on the line, provided the station's name is not already on the stations list, private as it is just a subdivision of the connect stations functions that exists to make the code easier to break up in case of bugs
    {
        stations[arrayPosition] = new station(arrayPosition, stationName, linCol, numberOfLines);            //Locates next free spot in the array and adds the new station to it
            
    }

    public boolean checkForStation(String stationNameToCheck)                                   //Checks if a station is in the list of stations, made much more efficient thanks to the number of stations being known already
    {
        for (int x = 0; x < stations.length; x++)                                              //For every position in the array
        {
            if(stations[x] != null)                                                             //If it is not null
            {
                if(stationNameToCheck.equals(stations[x].nameOfStation()))                      //If the name of that station is the one we are checking for
                {
                    return true;                                                                //Return true, the station is already in the array
                }
            }
        }
        return false;                                                                           //Return false, the station is not in the array if we leave the for loop without returning true
    }

    private int numberOfStations()                            //Returns the number of train lines in the csv data and returns it
    {
        int numOfStations = 0;
        for(int x = 0; x < csvData.length; x++)                 //For every entry in the data array
        {
            
            if(csvData[x].lineChecker() == false)                //If its one that establishes a line
            {
                numOfStations++;                                   //Add to the count of the number of lines
            }
        }
        return numOfStations + numberOfLines();                                      //Return the number of lines
    }

    private int numberOfLines()                            //Returns the number of train lines in the csv data and returns it
    {
        int numOfLines = 0;
        for(int x = 0; x < csvData.length; x++)                 //For every entry in the data array
        {
            
            if(csvData[x].lineChecker() == true)                //If its one that establishes a line
            {
                numOfLines++;                                   //Add to the count of the number of lines
            }
        }
        return numOfLines;                                      //Return the number of lines
    }


    public boolean delayConnection(String delayed1, String delayed2, String lineCol, float delayTime)
    {
        boolean returnValue = routeFinder.stationDelayed(delayed1, delayed2, lineCol, delayTime);
        return returnValue;
    }

     
    public boolean closeStation(String closure)
    {
        int[] needClosing = whereInArray(closure);
        if(needClosing == null)
        {
            return false;
        }

        for(int x = 0; x < needClosing.length; x++)
        {
            for(int y = 0; y < stations.length; y++)
            {
                if(stations[y] != null)
                {
                    stations[y].cutOff(needClosing[x]);
                }
            }
            stations[needClosing[x]] = null;
        }
        return true;
    }


    private int[] whereInArray(String stationName)                                         //returns the position a station holds in the stations array when given its name
    {
        int arraylength = 0;
        for(int x = 0; x < stations.length; x++)                                                //For every station in the array
        {
            if(stations[x] != null)
            {
                if(stations[x].nameOfStation().equals(stationName) == true)                        //If the station being checked's name is the same as the one we are looking for
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
        for(int z = 0; z < stations.length; z++)                                                //For every station in the array
        {
            if(stations[z] != null)
            {
                if(stations[z].nameOfStation().equals(stationName) == true)                        //If the station being checked's name is the same as the one we are looking for
                {
                    placesInArray[y] = z;
                    y++;                                                                     //Return the current index
                }
            }
        }
        return placesInArray;
                                                                                      //return -1 if it is not in the array
    }




}
