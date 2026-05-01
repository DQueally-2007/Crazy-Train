import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;             
public class csvReader                                                                                                //Class to read the first csv type used with 3 values max per line
{
    private File fileToRead;                                                                                          //Stores file to be read
    private String linesOfText[];                                                                                     //Stores the lines of text from the file in an array


    public csvReader(String fileName) throws FileNotFoundException                                                    //Constructor for the csv reader, takes in filename as string currently
    {                   
        fileToRead = new File("Metro.csv");                                                                           //Trying to fix compile errors with csv reader, replace with fileName variable later
        fileToRead.canRead();                                                                                         //Lets the file be read
        linesOfText = new String[linesArraySize(fileToRead)];                                                         //Stores the lines of text in a large array of strings
        readCSVLines(fileToRead);                                                                                     // Reads the csv file into the array for use later in the data reader
    }

    private int linesArraySize(File toRead) throws FileNotFoundException                                              //Checks the size of the csv file so the array can be the perfect length
    {
        Scanner sizeReader = new Scanner(toRead);                                                                     //Creates new scanner for use in determining number of lines in provided file
        sizeReader.nextLine();                                                                                        //Skips first row of column names
        int lineCount = 0;                                                                                            //Stores number of lines    
        while (sizeReader.hasNextLine())                                                                              //While the next line can be read
        {
            sizeReader.nextLine();                                                                                    //Move to the next line
            lineCount++;                                                                                              //Increment the count
        }
        sizeReader.close();                                                                                           //Close the reader
        return lineCount;                                                                                             //returns nujmber of lines in csv file
    }

    private void readCSVLines(File toRead) throws FileNotFoundException                         //Read the CSV file into the linesOfText array
    {
        String lineRead;                                                                        //Stores the line that was just read from the file
        Scanner lineReader = new Scanner(toRead);                                               //Creates new scanner for use in reading lines
        lineReader.nextLine();                                                                  //Skips first row of column names

        for(int x = 0; x < linesOfText.length; x++)                                             //Reads the CSV into the relevant string array  
        {
            lineRead = lineReader.nextLine();                                                   //Read next line from the reader
            linesOfText[x] = lineRead;                                                          //Add the line of text to the index
        }
        lineReader.close();                                                                     //Close reader
    }

    public lineOfText[] readCSVData()                               //Reads the csv file's data from the linesOfText array into an array of linesOfText, which is then returned for use in the parent class
    {
        lineOfText csvData[] = new lineOfText[linesOfText.length];  //Creates an array of lines of text to store the data in and to return at the function's end
        for (int x = 0; x < linesOfText.length; x++)                //For every line of text in the array
        {
            Scanner rowScanner = new Scanner(linesOfText[x]);       //New scanner for the individual line 
            rowScanner.useDelimiter(",");                           //Separates  values with commas
            String one = null;                                      //Set all values used in creating the lineOfText to null to prevent exceptions
            String two = null;
            float num = 0;
            one = rowScanner.next();                                //Sets the value of one as the first value in the line, there is always one of these so no check is required

            if (rowScanner.hasNext() == true)                       //Checks there is a second value
            {
                two = rowScanner.next();

                if (rowScanner.hasNext() == true)                   //Checks there is a third value
                {
                    num = rowScanner.nextFloat();
                }  
            }
            
            csvData[x] = new lineOfText(one,two,num);               //Stores the line of text in the csvData array so it can be stored and acessed properly
            rowScanner.close();                                     //Close the reader
        } 
        
        return csvData;                                             //Returns the csv data array
    }   
}
