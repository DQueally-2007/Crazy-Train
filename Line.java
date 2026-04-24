public class Line 
{
    private String lineName;
    private int lineNumber;
    private int numberOfStations;
    private Station stations[]; 

    public Line(String line, int number, int numOfStations)
    {
        lineName = line;
        lineNumber = number;
        numberOfStations = numOfStations;
        stations = new Station[numberOfStations];
    }
}
