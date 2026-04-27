public class Line 
{
    private String lineName;
    private int lineNumber;
    private int numberOfStations;
    private Station stations[]; 

    public Line(String line, int number, int stationNum)                                        //Constructor creates line with all of its values, the number of stations is calculated externally and put into the constructor as a parameter, may want to move into line class to shrink CrazyTrain and make it more understandable
    {
        lineName = line;
        lineNumber = number;
        numberOfStations = stationNum;
        stations = new Station[numberOfStations];
    }

    private void addStation(String stationName)                                                 //Adds a train station to the list of stations on the line, provided the station's name is not already on the stations list, private as it is just a subdivision of the connect stations functions that exists to make the code easier to break up in case of bugs
    {
        if(checkForStation(stationName) == false)
        {
            stations[findNextFreeArrayValue()] = new Station(stationName, lineName);            //Locates next free spot in the array and adds the new station to it
        }
    }
    
    public void connectStations(String fromStation, String toStation, int timeToStation)        //Utilises all of the data on each line of the csv file to add every station to the array and add the relevant connection each line represents to both stations 
    {
        addStation(fromStation);                                                                //Tries to add both stations to the list of stations to make sure connections can be added as necessary
        addStation(toStation);
        for (int x = 0; x < numberOfStations; x++)                                                  //Adds the same connection to both the to and from stations so the user can move in both directions
        {
            if(stations[x].nameOfStation() == fromStation)
            {
                stations[x].connectTo(toStation, timeToStation);
            }
            
            if(stations[x].nameOfStation() == toStation)
            {
                stations[x].connectTo(fromStation, timeToStation);
            }
        }
    }

    private int findNextFreeArrayValue()                                                        //Finds the next free position in the array, private as its not used outside of this class
    {
        int x = 0;
        while (stations[x] != null)
        {
            x++;
        }
        return x;
    }

    public boolean checkForStation(String stationNameToCheck)                                   //Checks if a station is in the list of stations, made much more efficient thanks to the number of stations being known already
    {
        for (int x = 0; x < numberOfStations; x++)
        {
            if(stations[x].nameOfStation() == stationNameToCheck)
            {
                return true;                                                                    //True if the station is in the list
            }
        }
        return false;                                                                           //False if not
    }

}
