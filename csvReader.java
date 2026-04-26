import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;             
public class csvReader 
{
    private File fileToRead;                            //Stores file to be read
    private String lineRead;                            //Stores the line that was just read from the file
    private String lines[];                             //Stores the lines of text from the file in an array
    private int textLinesIndex;                         //Stores the index for the point we are at in the lines array
    private int breakdownIndex;                         //Stores the index for the point we are at in the breakdown array
    public String lineArray[];                          //Stores the component strings of each line in an array
    private int linesArrayLength;                       //Stores the length of the lines array for use across the class
    private String stationNames[];                      //Stores the array of unique station names


    public csvReader(String fileName)                   //Constructor for the csv reader, takes in filename as string currently
    {
        linesArrayLength = 200;                         //Sets lines array length for use accross the class
        textLinesIndex = 0;                         
        breakdownIndex = 0;
        lines = new String[linesArrayLength];   
        fileToRead = new File(fileName);
        fileToRead.canRead();
        Scanner lineReader = new Scanner(fileToRead);   //Creates new scanner for use in reading lines
        lineReader.nextLine();                          //Skips first row of column names
        while (lineReader.hasNextLine())                    //Reads all of the CSV's into ;ines array
        {
            lineRead = lineReader.nextLine();
            lines[textLinesIndex] = lineRead;
            textLinesIndex++;
        }
        textLinesIndex = 0;                             //Lines index is reset for the sake of navigation in breakdownTextLine
        uniqueStations();                               //Number of unique station names is determined
    }

    public void breakdownTextLine()                     //Each line of text must be individually broken down before being checked to see if it is a station input or a line specifier
    {
        Scanner rowScanner = new Scanner(lines[textLinesIndex]); //New scanner for the individual line 
        rowScanner.useDelimiter(COMMA_DELIMITER);                //Deliniates using commas
        if (lines[textLinesIndex] != null)                       //Only works if the line has some text
        {
            lineArray = new String[3];                           //Only ever 3 strings maximum per line
            breakdownIndex = 0;                                     
            while (rowScanner.hasNext())                         //Checks next value is not null
            {
                lineArray[breakdownIndex] = rowScanner.next();   //Adds the strings to the array for the line
                breakdownIndex++;
            }
            textLinesIndex++;                                    //As this method increments the textLinesIndex itself, it must always have the TLI reset after it is finished, within the function it is being used in
        }
        
    }

    public String[] returnLineArray()                           //Returns the array containing the individual data of the line being read, may want to convert this into an array of a class containing strings, may make the whole reading thing easier,but may be less memory efficient as they whole csv will be stored 3 times... 
    {
        return lineArray;
    }

    public void changeTLI(int input)                            //Changes the textLinesIndex as needed, not good for modularity, should fix soon to improve scalability and modularity
    {
        textLinesIndex = input;
    }

    public void resetTLI()                                      //Same as previous function, really needs to be removed, terrible for modularity
    {
        textLinesIndex = 0;
    }

    public boolean lineChecker()                                //Checks text line array to see if this line is a train line specifier
    {
        if (lineArray[1] == null)                               //Second value is always null on a line specifying a line colour
        {
            return true;                                        //So this certainly confirms the line of text being checked demarks a train line set
        }
        return false;                                           //Otherwise return false as it is a standard line
    }


    public int numberOfTrainStations()                          //Returns the number of unique train stations in the csv file provided 
    {
        int stations = 0;
        int index = 0;
        while(stationNames[index] != null)
        {
            if(stationNames[index] != null)                     //If there is a station name in the list and not a null, add it to the count
            {
                stations++;
            }
                
            index++;                                            
        }
        return stations;
    }


    private void uniqueStations()                              //Counts the number of unique station names in the csv file provided, called once after the file has been fully read
    {
        stationNames = new String[linesArrayLength];
        int stationNamesIndex = 0;
        while (lines[textLinesIndex] != null)
        {
            breakdownTextLine();                               //REMEMBER, THIS FUNCTION INCREMENTS THE textLinesIndex, SO DON'T INCREMENT IT INSIDE OF FUNCTIONS WHERE IT'S USED
            if (lineChecker() == false)                        //If linechecker returns false then the line is for a station, not a trainline, so the station can be added if it is unique
            {
                if (stationNames[stationNamesIndex] != null)   
                {
                    if (notInList(lineArray[1]))               //If the name of the station in the going to or from columb is unique, then it is added to the list of unique station names
                    {
                        stationNames[stationNamesIndex] = lineArray[1];
                        stationNamesIndex++;                   //The stationNamesIndex is incremented to the space for the next name
                    }   

                    if (notInList(lineArray[2]))
                    {
                        stationNames[stationNamesIndex] = lineArray[2];
                        stationNamesIndex++;
                    }
                }
            }
        }
        textLinesIndex = 0;                                     //Obligatory reset of the textLinesIndex due to use of breakdownTextLine
    }



    public String[] listOfStations()                            //Returns the list of stations, may be useful later
    {
        return stationNames;
    }


    public Boolean notInList(String input)                      //Checks if a station's name is inside of the station name list 
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


    public int numberOfTrainLines()                             //Returns the number of trainlines in the provided csv file
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

    public int numberOfRailConnections()                        //Returns the number of connections between stations that are in the csv file
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
