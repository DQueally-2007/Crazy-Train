public class connection                                                     //A connection between two stations on a certain line
{
    private String lineColour;                                              //String to store the colour of the line the connection is on
    private int fromStation;                                             //String to store the name of the station this connection goes from
    private int toStation;                                               //String to store the name of the station this conneciton goes to
    private float travelTime;                                               //float to store the time in minutes the connection takes to traverse

    public connection(String colour, int from, int to, float time)    //Constructor makes a new connection, some parameters may be unecessary, may remove to reduce complexity
    {
        lineColour = colour;                                                //Sets the line colour to the string provided
        fromStation = from;                                                 //Sets the from station to the string provided
        toStation = to;                                                     //Sets the to station to the string provided
        travelTime = time;                                                  //Sets the travel time to the one provided
    }

    public int goingFrom()                                               //Returns the fromStation
    {
        return fromStation;
    }

    public int goingTo()                                                 //Returns the toStation
    {
        return toStation;
    }
    
    public float howLong()                                                  //Returns the travelTime
    {
        return travelTime;
    }

    public String getLineColour()                                           //Returns the lineColour
    {
        return lineColour;
    }

    public void lineChange()
    {
        travelTime = travelTime + 2;
    }
}
