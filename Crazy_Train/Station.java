public class Station                           //Class to represent a station, an object which contains multiple connections to other stations
{
    private String stationName;
    private String lineColour;
    private int numberOfConnections;
    private boolean canChangeLine;
    private Connection connections[];

    public Station(String name, String colour) //Method to make a new station with its name, line name and an empty array for connections to be added to
    {
        stationName = name;
        lineColour = colour;
        connections = new Connection[10];      //10 was aritarily chosen as the array's size since I have yet to see an underground map with more than that many connections from a single station
    }

    public String nameOfStation()              //Simply returns the name of the station
    {
        return stationName;                     
    }

    public void connectTo(String stationToConnectTo, float travelTime)    //Adds a conection to a station's list of connections
    {
        if(connectedTo(stationToConnectTo) == false)                    //Checks that there is not already a connection to that station, nothing happens if its already connected
        {
            boolean added = false;
            int x = 0;
            while (added == false)                                      //Adds the connection to the list in the next null position, assumes a full list won't happen, may need to change in future but perfectly servicable
            {
                if (connections[x] == null)                             //if there is no connection in that position
                {
                    connections[x] = new Connection(lineColour, stationName, stationToConnectTo, travelTime);   //add new connection
                    added = true;                                       //ends while loop
                }
                x++;                                                    //incriment x to move along array
            }
        }
    }

    public float canIGetTo(String stationToGoTo)                        //Checks if a station is connected to this station and returns the time it would take to do so, returns 0 if the station cannot be reached, maybe change to -1 or something clearer in future
    {
        if(connectedTo(stationToGoTo) == true)                          //Checks station is connected
        {
            for (int x = 0; x < 10; x++)                                //For length of connections array, may need to make bigger? May add function to calculate exact number of connections later
            {
                if(connections[x].goingTo() == stationToGoTo )          //Searches through connections to find the correct one and returns the time it takes
                {
                    return connections[x].howLong();
                }
            }
            
        }
        return 0;                                                       //Returns 0 if the station cannot be reached
    }

    private boolean connectedTo(String stationName)                     //Checks if a station is connected to annother, private as it is not needed elsewhere and it only exists to subdivide internal processes into easier to understand chunks
    {
        for (int x = 0; x < 10; x++)                                    //For every position in the connections array
        {
            if(connections[x] != null)                                  //If it is not a null
            {
                if(stationName == connections[x].goingTo())             //If the station is the one we want to go to
                {
                    return true;                                        //We can get to it
                }
            }
        }
        return false;                                                   //We cannot get to it
    }

}
