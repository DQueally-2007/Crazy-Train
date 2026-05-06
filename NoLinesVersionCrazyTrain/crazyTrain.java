import java.io.*;
import javax.sound.sampled.Line;
import javax.swing.*;
import java.io.File;                  
import java.io.FileNotFoundException;

public class crazyTrain 
{
    private String nameOfFile;                              //String to store the name of the file to be acessed
    private csvReader reader;                               //csvReader to store the reader needed to read the csv file
    private lineOfText[] csvData;                           //Array of lines of text to store the csv data for use throughout the program
    private navigator routeFinder;
    private station[] stations;
    private String stationNames;
    int numberOfTrainLines;

    public crazyTrain() throws FileNotFoundException                    //Constructor makes the overall file reader, gets the arrays ready and calls the buildlines function to get everything going
    {       
        nameOfFile = "Metro.csv";              //Should be input late in the interface or some other way
        reader = new csvReader(nameOfFile);                             //Creates new reader for the file
        csvData = new lineOfText[(reader.readCSVData()).length];        //Creates new array of linesOfText to returned array from the csvReader
        csvData = reader.readCSVData();                                 //Reads the csv data into the csvData array
        stations = new station[numberOfStations()];
        numberOfTrainLines = numberOfLines();
        buildGraph();
        System.out.println("Graph Built");                              //Informs us that the lines have been succesfully built

        for(int y = 0; y < stations.length; y++)
        {
            //System.out.println(stations[y].nameOfStation());
        }
        routeFinder = new navigator(stations,"Abraham Moss" , "Baguley");
        String[] route = routeFinder.shortestRouteToDestination();
        for(int x = 0; x < route.length; x++)
        {
            System.out.println(route[x]);
        }

        walkingRouteFinder = new navigator(stations, "Abraham Moss", "Baguley", "walktimes.csv");

        String[] walkRoute = walkingRouteFinder.shortestRouteToDestination();
        for(int x = 0; x < walkRoute.length; x++)
        {
            System.out.println(walkRoute[x]);
        }


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
                System.out.println(lineColour);
            }

            else
            {
                if (csvData[x].lineChecker() == false)                                                                                      //If the line checker tells us the current line is not creating a new line then it adds the new stations and connection to the relevant arrays
                {
                    //System.out.println(csvData[x].getFirstWord());
                    //System.out.println(csvData[x].getSecondWord());
                    
                    if(notAlreadyThere(csvData[x].getFirstWord(), lineColour))
                    {
                        //System.out.println("Station " + csvData[x].getFirstWord() + " added");
                        addStation(stationsIndex, csvData[x].getFirstWord(), lineColour, numberOfTrainLines);
                        stationsIndex++;
                    }

                    if(notAlreadyThere(csvData[x].getSecondWord(), lineColour))
                    {
                        //System.out.println("Station " + csvData[x].getSecondWord() + " added");
                        addStation(stationsIndex, csvData[x].getSecondWord(), lineColour, numberOfTrainLines);
                        stationsIndex++;

                    }
                    //System.out.println(getStationNumber( lineColour, csvData[x].getFirstWord()));
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
                    //System.out.println(stations[x].nameOfStation());
                    //System.out.println(stations[x].getLineColour());
                    if(stations[y].getLineColour().equals(colour) == false)
                    {
                        if(stations[y].nameOfStation().equals(name))
                        {
                            //System.out.println(stations[z].nameOfStation());
                            //System.out.println(stations[z].getLineColour());
                            //System.out.println(stations[y].nameOfStation());
                            //System.out.println(stations[y].getLineColour());
                            numRepeats++;
                        }
                    }
                }
            }

            int[] repeatLocations = new int[numRepeats];
            //System.out.println(numRepeats);
            int reps = 0;

            for(int v = 0; v < stations.length; v++)
            {
                if(stations[v] != null)
                {
                    //System.out.println(stations[x].nameOfStation());
                    //System.out.println(stations[x].getLineColour());
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
                //System.out.println(stations[x].nameOfStation());
                //System.out.println(stations[x].getLineColour());
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

    public static void main(String[] args)          //Main method
    {
        //try and catch statements to handle file not found exceptions
        try                                     
        {
            crazyTrain c = new crazyTrain();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("No file found");
        }
    }
}
