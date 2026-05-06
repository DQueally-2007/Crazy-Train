import java.sql.Connection;

public class station                               //Class to represent a station, an object which contains multiple connections to other stations
{
    private String stationName;                    //String to store the name of the station
    private String lineColour;
    private int stationNumber;
    private connection[] connections;

    public station(int num, String name, String lC, int nS)  //Method to make a new station with its name, line name and an empty array for connections to be added to
    {
        stationNumber = num;
        stationName = name;  
        lineColour = lC;                                       //The array's maximum needed length can be determined by multiplying the total number of stations by 2, as a single station can only be connected to a maximum of 2 stations adjacent to it per line
        connections = new connection[nS + 90];
    }                                                                   //Works perfectly!!! most connected station is cornbrook at 12 connections and the program throws an error at 11 but works at 12 maxium connection, so the connections are crossing perfectly

    public String nameOfStation()                                       //Simply returns the name of the station
    {
        return stationName;                     
    }

    public connection[] getConnections() 
    {
        return connections;
    }

    public int getStationNumber() 
    {
        return stationNumber;
    }

    public void connectTo(int stationToConnectTo, float travelTime, String connectionColour)                                          //Adds a conection to a station's list of connections
    {
        if(connectedTo(stationToConnectTo) == false)                                                            //Checks that there is not already a connection to that station, nothing happens if its already connected, prevents infinite loop
        {
            int x = 0;
            while(connections[x] != null)
            {
                x++;
            }
            
            connections[x] = new connection(connectionColour, stationNumber, stationToConnectTo, travelTime);
        }
    }


    private boolean connectedTo(int stationNm)                     //Checks if a station is connected to annother, private as it is not needed elsewhere and it only exists to subdivide internal processes into easier to understand chunks
    {
        for(int x = 0; x < connections.length; x++)
        {
            if(connections[x] != null)
            {
                if(connections[x].goingTo() == stationNm)
                {
                    return true;
                }
            }
        }
        return false;                                                   //We cannot get to it if the loop has ended with no true being returned
    }

    public String getLineColour()
    {
        return lineColour;
    }
    /* 
    public void terminateStationconnections()
    {
        connection1 = null;
        connection2 = null;
    }

    public void cutOff(int stationToCutOff)
    {
        if (connection1.goingTo() == stationToCutOff)
        {
            connection1 = null;
        }
        if (connection2.goingTo() == stationToCutOff)
        {
            connection2 = null;
        }
    }
    */

}
