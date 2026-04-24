import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;             
public class csvReader 
{
    private File fileToRead;                    //Stores file to be read
    private String lineRead;                    //Stores the line that was just read from the file
    private String lines[];                     //Stores the lines of text from the file in an array
    private int textLinesIndex;                 //Stores the index for the point we are at in the lines array
    private int breakdownIndex;                 //Stores the index for the point we are at in the breakdown array
    public String lineArray[];                  //Stores the component strings of each line in an array
    private int linesArrayLength;               //Stores the length of the lines array for use across the class
    private String stationNames[];              //Stores the array of unique station names


    public csvReader(String fileName)
    {
        linesArrayLength = 200;
        textLinesIndex = 0;
        breakdownIndex = 0;
        lines = new String[linesArrayLength];
        fileToRead = new File(fileName);
        fileToRead.canRead();
        Scanner lineReader = new Scanner(fileToRead);
        lineReader.nextLine();
        while (reader.hasNextLine())
        {
            lineRead = lineReader.nextLine();
            lines[textLinesIndex] = lineRead;
            textLinesIndex++;
        }
        textLinesIndex = 0; //Lines index is reset for the sake of navigation in breakdownTextLine
        uniqueStations(); //Number of unique station names is determined
    }

    public void breakdownTextLine() //Each line of text must be individually broken down before being checked to see if it is a station input or a line specifier
    {
        Scanner rowScanner = new Scanner(lines[textLinesIndex]);
        rowScanner.useDelimiter(COMMA_DELIMITER);
        if (lines[textLinesIndex] != null)
        {
            lineArray = new String[3];
            breakdownIndex = 0;
            while (breakdownIndex< 3)
            {
                lineArray[breakdownIndex] = rowScanner.next();
                breakdownIndex++;
            }
            textLinesIndex++;
        }
        
    }

    public boolean lineChecker() //Checks text line array to see if this line is a train line specifier
    {
        if (lineArray[1] == null)
        {
            return true;
        }
        return false;
    }


    public int numberOfTrainStations()
    {
        int stations = 0;
        int index = 0;
        while(stationNames[index] != null)
        {
            if(stationNames[index] != null)
            {
                stations++;
            }
                
            index++;
        }
        return stations;
    }


    private void uniqueStations()
    {
        stationNames = new String[linesArrayLength];
        int stationNamesIndex = 0;
        while (lines[textLinesIndex] != null)
        {
            breakdownTextLine();
            if (lineChecker() == false)
            {
                if (stationNames[stationNamesIndex] != null)
                {
                    if (notInList(lineArray[1]))
                    {
                        stationNames[stationNamesIndex] = lineArray[1];
                    }

                    if (notInList(lineArray[2]))
                    {
                        stationNames[stationNamesIndex] = lineArray[2];
                    }
                }
            }
            textLinesIndex++;
        }
        textLinesIndex = 0;
    }



    public String[] listOfStations()
    {
        return stationNames;
    }

    public Boolean notInList(String input)
    {
        for(x = 0; x < linesArrayLength; x++)
        {
            if(input == stationNames[x])
            {
                return false;
            }
        }
        return true;
    }


    public int numberOfTrainLines()
    {
        int numofLines = 0;
        for (x = 0; x < linesArrayLength; x++)
        {
            breakdownTextLine();
            if (lineChecker() == true)
            {
                numofLines++;
            }
        }
        textLinesIndex = 0;
        return numofLines;
    }

    public int numberOfRailConnections()
    {
        int connections = 0;
        for (x = 0; x < linesArrayLength; x++)
        {
            if(lines[x] != null)
            {
                breakdownTextLine();
                if (lineChecker() == false)
                {
                    connections++;
                }
            }
        }
        textLinesIndex = 0;
        return connections;
    }

}
