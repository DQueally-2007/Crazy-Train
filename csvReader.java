import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;             
public class csvReader 
{
    private File fileToRead; 
    private String lineRead;
    private String lines[];
    private int linesIndex;
    private int breakdownIndex;
    public String lineArray[];


    public csvReader(String fileName)
    {
        linesIndex = 0;
        breakdownIndex = 0;
        lines = new String[100];
        fileToRead = new File(fileName);
        fileToRead.canRead();
        Scanner lineReader = new Scanner(fileToRead);
        lineReader.nextLine();
        while (reader.hasNextLine())
        {
            lineRead = lineReader.nextLine();
            lines[linesIndex] = lineRead;
            linesIndex++;
        }
        linesIndex = 0; //Lines index is reset for the sake of navigation in breakdownLine
    }

    public void breakdownLine() //Each line of text must be individually broken down before being checked to see if it is a station input or a line specifier
    {
        Scanner rowScanner = new Scanner(lines[linesIndex]);
        rowScanner.useDelimiter(COMMA_DELIMITER);
        lineArray = new String[3];
        breakdownIndex = 0;
        if (lines[linesIndex] != null)
        {
            while (breakdownIndex< 3)
            {
                lineArray[breakdownIndex] = rowScanner.next();
                breakdownIndex++;
            }
            linesIndex++;
        }
        
    }

    public boolean lineChecker() //Checks line array to see if it is a line specifier
    {
        if (lineArray[1] == null)
        {
            return true;
        }
        return false;
    }


}
