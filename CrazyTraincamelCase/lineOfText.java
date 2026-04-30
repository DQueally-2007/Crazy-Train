public class lineOfText                                         //Class to contain the data of the first type of csv file this project uses with two strings and a float
{
    private String firstWord;
    private String secondWord;
    private float number;
    
    public lineOfText(String first, String second, float num)   //Constructor simply assigns the values to the variables
    {
        firstWord = first;
        secondWord = second;
        number = num;

    }

    public boolean lineChecker()                                //Checks text line array to see if this line is a train line specifier
    {
        if (number == 0.0)                                      //Second value is always null on a line specifying a line colour
        {
            return true;                                        //So this certainly confirms the line of text being checked demarks a train line set
        }
        return false;                                           //Otherwise return false as it is a standard line
    }

    public String getFirstWord()                                //Returns the first string of the line
    {
        return firstWord;
    }

    public String getSecondWord()                               //Returns the second string of the line
    {
        return secondWord;
    }

    public float getNumber()                                    //Returns the number of the line
    {
        return number;
    }
}
