import java.sql.Connection;

public class line                                           //A class contatainging a number of stations
{
    private String lineName;
    private int lineNumber;
    private int numberOfStations;
    private station stations[]; 

    public line(String line, int number, int stationNum)                                        //Constructor creates line with all of its values, the number of stations is calculated externally and put into the constructor as a parameter, may want to move into line class to shrink CrazyTrain and make it more understandable
    {
        lineName = line;
        lineNumber = number;
        numberOfStations = stationNum;
        stations = new station[numberOfStations];
    }

    private void addStation(String stationName)                                                 //Adds a train station to the list of stations on the line, provided the station's name is not already on the stations list, private as it is just a subdivision of the connect stations functions that exists to make the code easier to break up in case of bugs
    {
        if(checkForStation(stationName) == false)
        {
            stations[findNextFreeArrayValue()] = new station(stationName, lineName);            //Locates next free spot in the array and adds the new station to it
            
        }
    }

    public station returnStation(String whichStation)
    {
        return stations[whereInStationArray(whichStation)];
    }

    public int getStationCount()
    {
        return stations.length;
    }

    public station[] getStationArray()
    {
        return stations;
    }
    
    public void connectStations(String fromStation, String toStation, float timeToStation)        //Utilises all of the data on each line of the csv file to add every station to the array and add the relevant connection each line represents to both stations 
    {
        addStation(fromStation);                                                                //Tries to add both stations to the list of stations to make sure connections can be added as necessary
        addStation(toStation);

        for (int x = 0; x < numberOfStations; x++)                                                  //Adds the same connection to both the to and from stations so the user can move in both directions
        {
            if(stations[x] != null)                                                                 //If index x in stations is not null
            {
                if(stations[x].nameOfStation().equals(fromStation) == true)                         //If the from station has the same name as an existing station
                {
                    stations[x].connectTo(toStation, timeToStation);                                //Connect that station to the fromstation
                }
            
                if(stations[x].nameOfStation().equals(toStation) == true)                           //If the to station has the same name as an existing station
                {
                    stations[x].connectTo(fromStation, timeToStation);                              //Connect that station to the fromstation
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
        return x;
    }

    public boolean checkForStation(String stationNameToCheck)                                   //Checks if a station is in the list of stations, made much more efficient thanks to the number of stations being known already
    {
        for (int x = 0; x < numberOfStations; x++)                                              //For every position in the array
        {
            if(stations[x] != null)                                                             //If it is not null
            {
                if(stationNameToCheck.equals(stations[x].nameOfStation()))                      //If the name of that station is the one we are checking for
                {
                    return true;                                                                    //Return true, the station is already in the array
                }
            }
        }
        return false;                                                                           //Return false, the station is not in the array
    }

    public void addCrossConnections(String stationToAddTo, connection[] newConnections)
    {
        int whereinArrayToAdd = whereInStationArray(stationToAddTo);
        for (int x = 0; x < newConnections.length; x++)
        {
            if(newConnections[x] != null)
            {
                String getToCheck = newConnections[x].goingTo();
                if(stations[whereinArrayToAdd].canIGetTo(getToCheck) == 0)
                {
                    
                    stations[whereinArrayToAdd].ammendConnections(newConnections[x]);
                }

            }
        }
    }



    public connection[] returnStationConnections(String stationName)
    {
        connection[] connectionsArray = new connection[10];
        for (int x = 0; x < stations.length; x++)
        {
            if(stationName.equals(stations[x].nameOfStation()))
            {
                connectionsArray = stations[x].returnConnections();
            }
        }
        return connectionsArray;

    }

    private int whereInStationArray(String stationName)
    {
        for(int x = 0; x < stations.length; x++)
        {
            if (stations[x].nameOfStation().equals(stationName) == true)
            {
                return x;
            }
        }
        return -1;
    }

}