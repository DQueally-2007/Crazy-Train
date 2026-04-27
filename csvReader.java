import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;             
public class csvReader 
{
    private File fileToRead;                                  //Stores file to be read
    private String linesOfText[];                             //Stores the lines of text from the file in an array


    public csvReader(String fileName)                   //Constructor for the csv reader, takes in filename as string currently
    {                   
        fileToRead = new File("/home/queally/h-drive/Crazy-Train/Metrolink_times_linecolour(in).csv");  //Trying to fix compile errors with csv reader, replace with fileName variable later
        fileToRead.canRead();                                                                           //Lets the file be read
        linesOfText = new String[linesArraySize()];                                                     //Stores the lines of text in a large array of strings
        readCSVLines();                                                                                 // Reads the csv file into the array for use later in the data reader
    }

    private int linesArraySize()                        //Checks the size of the csv file so the array can be the perfect length
    {
        Scanner sizeReader = new Scanner(fileToRead);   //Creates new scanner for use im determining number of lines in provided file
        sizeReader.nextLine();                          //Skips first row of column names
        int lineCount = 0;                              //Stores number of lines    
        while (sizeReader.hasNextLine())                //While the next line can be read
        {
            sizeReader.nextLine();                      //Move to the next line
            lineCount++;                                //Increment the count
        }
        sizeReader.close();                             //Close the reader
        return lineCount;                               //returns nujmber of lines in csv file
    }

    private void readCSVLines()                         //Read the CSV file into the linesOfText array
    {
        String lineRead;                                //Stores the line that was just read from the file
        Scanner lineReader = new Scanner(fileToRead);   //Creates new scanner for use in reading lines
        lineReader.nextLine();                          //Skips first row of column names

        for(int x = 0; x < linesOfText.length; x++)     //Reads the CSV into the relevant string array  
        {
            lineRead = lineReader.next();               
            linesOfText[x] = lineRead;
        }
        lineReader.close();                             //Close reader
    }

    public lineOfText[] readCSVData()
    {
        lineOfText csvData[] = new lineOfText[linesOfText.length];
        for (int x = 0; x < linesOfText.length; x++)
        {
            Scanner rowScanner = new Scanner(linesOfText[x]);       //New scanner for the individual line 
            rowScanner.useDelimiter(",");                           //Separates  values with commas
            String one = null;                      
            String two = null;
            int num = 0;
            if (rowScanner.hasNext() == true)                       //Series of if statements to ensure the values only attempt to update if they can, ensuring no exceptions occur
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
            csvData[x] = new lineOfText(one,two,num);               //Stores the line of text in the csvData array so it can be stored and acessed properly
            rowScanner.close();                                     //Close the reader
        } 
        
        return csvData;                                             //Returns the csv data array
    }   
}
