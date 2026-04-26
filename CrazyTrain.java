import java.io.*;
import javax.swing.*;
public class CrazyTrain 
{
    private String nameOfFile;
    private csvReader reader;
    private int numConnections;
    private int numStations;
    private int numLines;
    private Line lines[];
    public CrazyTrain()                                     //Constructor makes the overall file reader, finds out the numbers of each element and creates the lines array
    {
        nameOfFile = "Metrolink_times_linecolour(in).csv";
        reader = new csvReader(nameOfFile);
        numConnections = reader.numberOfRailConnections();  //Numbers of each element
        numStations = reader.numberOfTrainStations();
        numLines = reader.numberOfTrainLines();
        lines = new Line[numLines];
        buildLines();                                       //Function that fills out the lines array

    }

    private void buildLines()                               //Fills out the lines arrays with the stations and adds the connections between the stations to the stations, fills out every level of the graph structure
    {
        String lineColour = "";
        String lineContents[] = new String[3];
        int linesIndex = 0;
        for (x = 0; x <200; x++)                            //Reads through whole line array, again should make functions to minimise user involvement and count the number of lines for them to imporve modularity
        {
            reader.breakdownTextLine();
            lineContents = reader.returnLineArray();        //Every iteration gets the data for each line and then inserts it into creating the lines, stations and connections

            if (reader.lineChecker() == true)               //If the linechecker tells us this is a new line then the line colour/name is set, the new line is created and the lines index increments 
            {
                lineColour = lineContents[0];
                lines[linesIndex] = new Line(lineColour, linesIndex, countStationsInLine(x));
                linesIndex++;
            }

            if (reader.lineChecker() == false)              //If the line checker tells us the current line is not creating a new line then it adds the new stations and connection to the relevant arrays
            {
                lines[linesIndex -1].connectStations(lineContents[0], lineContents[1], lineContents[2]);
            }
        }
        reader.resetTLI();                                  //Resets the TLI, terrible for modularity, should be changed
    }

    private int countStationsInLine(int startingIndex)       //Counts the number of stations in a line to help improve efficeny by reducing the number of empty array positions, should apply to other areas lacking modularity
    {
        int x = startingIndex;                              //Stores the TLI position where the search starts and until a new line is detected the code increments the counter, returning the number of stations, the function is private as it is not used elsewhere
        int numStationsInLine = 0;
        reader.breakdownTextLine();                         //Must increment to the next line for counting, may seem odd but under current setup it works, will need to potentially change when TLI stuff and modularity is fixed
        while(reader.lineChecker() == false)
        {
            reader.breakdownTextLine();
            numStationsInLine++;
        } 
        reader.changeTLI(x);                                //Terrible for modularity, remove ASAP
        return numStationsInLine;                       
    }
    public static void main(String[] args)
    {
        CrazyTrain c = new CrazyTrain();
    }
}
