import java.io.*;
import javax.swing.*;
import java.io.File;                  
import java.io.FileNotFoundException;

public class CrazyTrain 
{
    private String nameOfFile;                              //Stores the name of the file to be acessed
    private csvReader reader;                               //Stores the reader needed to read the csv file
    private Line lines[];                                   //Stores the lines objects 
    private lineOfText[] csvData;                           //Stores the csv data for use throughout the program

    public CrazyTrain() throws FileNotFoundException        //Constructor makes the overall file reader, gets the arrays ready and calls the buildlines function to get everything going
    {
        nameOfFile = "Metrolink_times_linecolour(in).csv";
        reader = new csvReader(nameOfFile);
        csvData = new lineOfText[(reader.readCSVData()).length];
        csvData = reader.readCSVData();
        lines = new Line[numberOfTrainLines()];
        buildLines();                                       //Function that fills out the lines array and the subarrays of each line to fill out the network
        System.err.println("Good Job");
    }

    private void buildLines()                               //Fills out the lines arrays with the stations and adds the connections between the stations to the stations, fills out every level of the graph structure
    {
        
        String lineColour = null;
        int linesIndex = 0;
        for (int x = 0; x < csvData.length; x++)                //Goes through all the csv data      
        { 
            if (csvData[x].lineChecker() == true)               //If the linechecker tells us this is a new line then the line colour/name is set, the new line is created and the lines index increments 
            {
                System.out.println("True");
                lineColour = csvData[x].firstWord;
                System.out.println(lineColour);
                lines[linesIndex] = new Line(lineColour, linesIndex, countStationsInLine(x));
                linesIndex++;
            }

            if (csvData[x].lineChecker() == false)              //If the line checker tells us the current line is not creating a new line then it adds the new stations and connection to the relevant arrays
            {
                lines[linesIndex - 1].connectStations(csvData[x].getFirstWord(), csvData[x].getSecondWord(), csvData[x].getNumber());
            }
        }
    }

    private int countStationsInLine(int startingIndex)       //Counts the number of stations in a line to help improve efficeny by reducing the number of empty array positions, should apply to other areas lacking modularity
    {
        int x = startingIndex + 1;                           //Tells the search where to start
        int numStationsInLine = 0;
        while(csvData[x].lineChecker() == false)             //Until a new line setting entry is found...
        {
            x++;
            numStationsInLine++;                             //Incriment the number of stations each time
        } 
        return numStationsInLine + 1;                        //Return the number of stations in the line (The +1 is because there is always 1 more station in a line than connection entrys under it)
    }

    private int numberOfTrainLines()                         //Returns the number of train lines in the csv data and returns it
    {
        int numOfLines = 0;
        for(int x = 0; x < csvData.length; x++)             //For every entry in the data
        {
            
            if(csvData[x].lineChecker() == true)            //If its one that establishes a line
            {
                numOfLines++;                               //Add to the count of the number of lines
            }
        }
        return numOfLines;                                  //Return the number of lines
    }
    public static void main(String[] args) throws FileNotFoundException
    {
        CrazyTrain c = new CrazyTrain();
    }
}
