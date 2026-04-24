public class Station 
{
    private String stationName;
    private String lineColour;
    private int numberOfConnections;
    private boolean canChangeLine;

    public Station(String name, String colour, int connections, boolean change)
    {
        stationName = name;
        lineColour = colour;
        numberOfConnections = connections;
        canChangeLine = change;
    }

}
