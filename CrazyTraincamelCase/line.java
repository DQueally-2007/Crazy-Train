import java.sql.Connection;

public class line                                                                                               //A class contatainging a number of stations
{
    private String lineName;                                                                                    //String to store the name of the line
    private int lineNumber;                                                                                     //int to store the line's number
    private int numberOfStations;                                                                               //int to store the number of stations the line will have
    private station stations[];                                                                                 //Array to store the stations contained in the line
    private int totalNumberOfLines;                                                                             //int to store the total number of lines in the network

    public line(String line, int number,int numOfLines, int stationNum)                                         //Constructor creates line with all of its values, the number of stations is calculated externally and put into the constructor as a parameter, may want to move into line class to shrink CrazyTrain and make it more understandable
    {
        lineName = line;                                                                                        //Sets name to one provided
        lineNumber = number;                                                                                    //Sets line number to one provided
        numberOfStations = stationNum;                                                                          //Sets number of station to the one provided
        stations = new station[numberOfStations];                                                               //Initialises the array with a length of the number of stations the program says will be in it
        totalNumberOfLines = numOfLines;                                                                        //Sets the total number of lines in the network to the one provided
    }

    private void addStation(String stationName)                                                                     //Adds a train station to the list of stations on the line, provided the station's name is not already on the stations list, private as it is just a subdivision of the connect stations functions that exists to make the code easier to break up in case of bugs
    {
        if(checkForStation(stationName) == false)                                                                   //If the station is not already in the array   
        {
            stations[findNextFreeArrayValue()] = new station(stationName, lineName, totalNumberOfLines);            //Locates next free spot in the array and adds the new station to it
        }
    }

    public int getStationCount()                                                                                    //Returns the number of stations the array has in its array
    {
        return stations.length;
    }

    public station[] getStationArray()                                                                              //Returns the stations array in its entirety
    {
        return stations;
    }
    
    public void connectStations(String fromStation, String toStation, float timeToStation)      //Utilises all of the data on each line of the csv file to add every station to the array and add the relevant connection each line represents to both stations 
    {
        addStation(fromStation);                                                                //Tries to add both stations to the list of stations to make sure connections can be added as necessary
        addStation(toStation);

        for (int x = 0; x < numberOfStations; x++)                                              //Adds the same connection to both the to and from stations so the user can move in both directions
        {
            if(stations[x] != null)                                                             //If index x in stations is not null
            {
                if(stations[x].nameOfStation().equals(fromStation) == true)                     //If the from station has the same name as an existing station
                {
                    stations[x].connectTo(toStation, timeToStation);                            //Connect that station to the fromstation
                }
            
                if(stations[x].nameOfStation().equals(toStation) == true)                       //If the to station has the same name as an existing station
                {
                    stations[x].connectTo(fromStation, timeToStation);                          //Connect that station to the fromstation
                }

            }
        }
    }

    private int findNextFreeArrayValue()                                                        //Finds the next free position in the array, private as its not used outside of this class
    {
        int x = 0;
        while (stations[x] != null)                                                             //While what is stored at the index x on the stations array does not equal null
        {
            x++;                                                                                //Move along the array
        }
        return x;                                                                               //Return the index when the while loop ends
    }

    public boolean checkForStation(String stationNameToCheck)                                   //Checks if a station is in the list of stations, made much more efficient thanks to the number of stations being known already
    {
        for (int x = 0; x < numberOfStations; x++)                                              //For every position in the array
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

    public void addCrossConnections(String stationToAddTo, connection[] newConnections)         //function to add the connections to a station that it has in another line
    {
        int whereinArrayToAdd = whereInStationArray(stationToAddTo);                            //Stores value of where the station we are adding to is in the stations array 
        for (int x = 0; x < newConnections.length; x++)                                         //For every index in the newConnections array
        {
            if(newConnections[x] != null)                                                       //If there is a connection stored there
            {
                String getToCheck = newConnections[x].goingTo();                                //Store the station that connection goes to
                if(stations[whereinArrayToAdd].canIGetTo(getToCheck) == 0)                      //If station x in this line does not already have a connection to that station
                {
                    stations[whereinArrayToAdd].ammendConnections(newConnections[x]);           //Add the new connection to station x
                }

            }
        }
    }

    public connection[] returnStationConnections(String stationName)                            //returns the connections array of a station it is given the name of
    {
        connection[] connectionsArray = new connection[numberOfStations * 2];                   //Creates connections array to store the connections to be returned  
        if(checkForStation(stationName) == true)                                                //If the station name provided belongs to a station in the array
        {
            connectionsArray = stations[whereInStationArray(stationName)].returnConnections();  //connectionsArray equals that station's connections array
        }
        return connectionsArray;                                                                //returns connectionsArray and consequently the data from the station or lack thereoff if the station is not in the line

    }

    private int whereInStationArray(String stationName)                                         //returns the position a station holds in the stations array when given its name
    {
        for(int x = 0; x < stations.length; x++)                                                //For every station in the array
        {
            if (stations[x].nameOfStation().equals(stationName) == true)                        //If the station being checked's name is the same as the one we are looking for
            {
                return x;                                                                       //Return the current index
            }
        }
        return -1;                                                                              //return -1 if it is not in the array
    }

}