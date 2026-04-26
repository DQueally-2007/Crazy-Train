

public class Connection
{
    private String lineColour;
    private String fromStation;
    private String toStation;
    private int travelTime;

    public Connection(String colour, String from, String to, int time)          //Constructor makes a new connection, some parameters may be unecessary, may remove to reduce complexity
    {
        lineColour = colour;
        fromStation = from;
        toStation = to;
        travelTime = time;
    }

    public String goingTo()                                                     //Returns the station a connection takes you to
    {
        return toStation;
    }
    
    public int howLong()                                                        //Returns the time it takes to traverse a connection
    {
        return travelTime;
    }

}
