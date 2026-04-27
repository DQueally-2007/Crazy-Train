import java.io.*;
import javax.swing.*;
public class CrazyTrain 
{
    private String nameOfFile;
    private csvReader reader;
    private int numLines;
    private Line lines[];
    private lineOfText[] csvData;

    public CrazyTrain()                                     //Constructor makes the overall file reader, finds out the numbers of each element and creates the lines array
    {
        nameOfFile = "Metrolink_times_linecolour(in).csv";
        reader = new csvReader(nameOfFile);
        numLines = numberOfTrainLines();
        csvData = new lineOfText[reader.readCSVData().length];
        csvData = reader.readCSVData();
        lines = new Line[numLines];
        buildLines();                                       //Function that fills out the lines array

    }

    private void buildLines()                               //Fills out the lines arrays with the stations and adds the connections between the stations to the stations, fills out every level of the graph structure
    {
        String lineColour = null;
        int linesIndex = 0;
        for (int x = 0; x < csvData.length; x++)                            //Reads through whole line array, again should make functions to minimise user involvement and count the number of lines for them to imporve modularity
        { 
            if (csvData[x].lineChecker() == true)               //If the linechecker tells us this is a new line then the line colour/name is set, the new line is created and the lines index increments 
            {
                lineColour = csvData[x].firstWord;
                lines[linesIndex] = new Line(lineColour, linesIndex, countStationsInLine(x));
                linesIndex++;
            }

            if (csvData[x].lineChecker() == false)              //If the line checker tells us the current line is not creating a new line then it adds the new stations and connection to the relevant arrays
            {
                lines[linesIndex -1].connectStations(csvData[x].getFirstWord(), csvData[x].getSecondWord(), csvData[x].getNumber());
            }
        }
    }

    private int countStationsInLine(int startingIndex)       //Counts the number of stations in a line to help improve efficeny by reducing the number of empty array positions, should apply to other areas lacking modularity
    {
        int x = startingIndex + 1;                              //Stores the TLI position where the search starts and until a new line is detected the code increments the counter, returning the number of stations, the function is private as it is not used elsewhere
        int numStationsInLine = 0;
        while(csvData[x].lineChecker() == false)
        {
            x++;
            numStationsInLine++;
        } 
        return numStationsInLine + 1;                       
    }

    private int numberOfTrainLines()
    {
        int numOfLines = 0;
        for(int x = 0; x < csvData.length; x++)
        {
            if(csvData[x].lineChecker() == true)
            {
                numOfLines++;
            }
        }
        return numOfLines;
    }
    public static void main(String[] args)
    {
        CrazyTrain c = new CrazyTrain();
    }
}
