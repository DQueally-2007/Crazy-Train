public class lineOfText                                         //Class to contain the data of the first type of csv file this project uses with two strings and a float
{
    private String firstWord;                                   //String to store the first word of the line
    private String secondWord;                                  //String to store the second word of the line
    private float number;                                       //Float to store the 3rd value on the line, a number
    
    public lineOfText(String first, String second, float num)   //Constructor simply assigns the values to the variables
    {
        firstWord = first;                                      //Sets the first word to be the string provided
        secondWord = second;                                    //Sets the second word to be the string provided
        number = num;                                           //Sets the number to the int provided

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
