

public class Connection                                                  //A connection between two stations on a certain line
{
    private String lineColour;
    private String fromStation;
    private String toStation;
    private float travelTime;

    public Connection(String colour, String from, String to, float time)          //Constructor makes a new connection, some parameters may be unecessary, may remove to reduce complexity
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
    
    public float howLong()                                                        //Returns the time it takes to traverse a connection
    {
        return travelTime;
    }

}
