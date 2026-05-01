import java.sql.Connection;

public class station                               //Class to represent a station, an object which contains multiple connections to other stations
{
    private String stationName;                    //String to store the name of the station
    private String lineColour;                     //String to store the colour of the line this station is on
    private connection connections[];              //Array to store the connections of this station

    public station(String name, String colour, int numberOfTrainLines)  //Method to make a new station with its name, line name and an empty array for connections to be added to
    {
        stationName = name;                                             //Sets the station's name to the one provided
        lineColour = colour;                                            //Sets the line colour to the one provided
        connections = new connection[numberOfTrainLines*2];             //The array's maximum needed length can be determined by multiplying the total number of stations by 2, as a single station can only be connected to a maximum of 2 stations adjacent to it per line
    }                                                                   //Works perfectly!!! most connected station is cornbrook at 12 connections and the program throws an error at 11 but works at 12 maxium connection, so the connections are crossing perfectly

    public String nameOfStation()                                       //Simply returns the name of the station
    {
        return stationName;                     
    }

    public void connectTo(String stationToConnectTo, float travelTime)                                          //Adds a conection to a station's list of connections
    {
        if(connectedTo(stationToConnectTo) == false)                                                            //Checks that there is not already a connection to that station, nothing happens if its already connected, prevents infinite loop
        {
            boolean added = false;
            int x = 0;
            while (added == false)                                                                              //While nothing has been added
            {
                if (connections[x] == null)                                                                     //if there is no connection in that position
                {
                    connections[x] = new connection(lineColour, stationName, stationToConnectTo, travelTime);   //add new connection
                    added = true;                                                                               //ends while loop
                }
                x++;                                                                                            //incriment x to move along array
            }
        }
    }

    public float canIGetTo(String stationToGoTo)                        //Checks if a station is connected to this station and returns the time it would take to do so, returns 0 if the station cannot be reached, maybe change to -1 or something clearer in future
    {
        if(connectedTo(stationToGoTo) == true)                          //Checks station is connected
        {
            for (int x = 0; x < connections.length; x++)                //For every entry in the connections array
            {
                if(connections[x].goingTo() == stationToGoTo )          //If the connection at x allows us to go to the stationToGoTo
                {
                    return connections[x].howLong();                    //Return the time it takes to get to the station given from this one
                }
            }
        }
        return 0;                                                       //Returns 0 if the station cannot be reached
    }

    public connection[] returnConnections()                             //Returns all of a station's connections in the form of the connections array
    {
        return connections;
    }

    private boolean connectedTo(String stationName)                     //Checks if a station is connected to annother, private as it is not needed elsewhere and it only exists to subdivide internal processes into easier to understand chunks
    {
        for (int x = 0; x < connections.length; x++)                    //For every position in the connections array
        {
            if(connections[x] != null)                                  //If it is not a null
            {
                if(stationName == connections[x].goingTo())             //If the station is the one we want to go to
                {
                    return true;                                        //We can get to it
                }
            }
        }
        return false;                                                   //We cannot get to it if the loop has ended with no true being returned
    }

    public void ammendConnections(connection toBeAdded)                 //Function to ammend the connections array with new connections
    {
        boolean added = false;
        int x = 0;
        while (added == false)                                          //While the new connection has not been added
        {
            if (connections[x] == null)                                 //if there is no connection in position x
            {
                connections[x] = toBeAdded;                             //add new connection to the blank position in the connections array
                added = true;                                           //end while loop 
            }
            x++;                                                        //incriment x to move along array
        }   
    }

}
