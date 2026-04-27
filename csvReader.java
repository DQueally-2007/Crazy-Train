import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;             
public class csvReader 
{
    private File fileToRead;                            //Stores file to be read
    private String linesOfText[];                             //Stores the lines of text from the file in an array


    public csvReader(String fileName)                   //Constructor for the csv reader, takes in filename as string currently
    {                   
        fileToRead = new File(fileName);
        fileToRead.canRead();
        linesOfText = new String[linesArraySize()];
        readCSVLines();
    }

    private int linesArraySize()
    {
        Scanner sizeReader = new Scanner(fileToRead);   //Creates new scanner for use in reading lines
        sizeReader.nextLine();                          //Skips first row of column names
        int lineCount = 0;
        while (sizeReader.hasNextLine())                    //Reads all of the CSV's into ;ines array
        {
            sizeReader.nextLine();
            lineCount++;
        }
        return lineCount;
    }

    private void readCSVLines()
    {
        String lineRead;                            //Stores the line that was just read from the file
        Scanner lineReader = new Scanner(fileToRead);   //Creates new scanner for use in reading lines
        lineReader.nextLine();                          //Skips first row of column names

        for(int x = 0; x < linesOfText.length; x++)
        {
            lineRead = lineReader.next();
            linesOfText[x] = lineRead;
        }
    }

    public lineOfText[] readCSVData()
    {
        lineOfText csvData[] = new lineOfText[linesOfText.length];
        for (int x = 0; x < linesOfText.length; x++)
        {
            Scanner rowScanner = new Scanner(linesOfText[x]); //New scanner for the individual line 
            rowScanner.useDelimiter(",");
            String one = null;
            String two = null;
            int num = 0;
            if (rowScanner.hasNext() == true)
            {
                one = rowScanner.next();

                if (rowScanner.hasNext() == true)
                {
                    two = rowScanner.next();

                    if (rowScanner.hasNext() == true)
                    {
                        num = rowScanner.nextInt();
                    }  
                }
            }       
            csvData[x] = new lineOfText(one,two,num);
        } 
        return csvData;
    }   
}
