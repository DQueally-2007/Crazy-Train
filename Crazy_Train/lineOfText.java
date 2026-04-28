public class lineOfText 
{
    String firstWord;
    String secondWord;
    float number;
    
    public lineOfText(String first, String second, float num)
    {
        firstWord = first;
        secondWord = second;
        number = num;

    }

    public boolean lineChecker()                                //Checks text line array to see if this line is a train line specifier
    {
        if (number == 0.0)                               //Second value is always null on a line specifying a line colour
        {
            return true;                                        //So this certainly confirms the line of text being checked demarks a train line set
        }
        return false;                                           //Otherwise return false as it is a standard line
    }

    public String getFirstWord()
    {
        return firstWord;
    }

    public String getSecondWord()
    {
        return secondWord;
    }

    public float getNumber()
    {
        return number;
    }
}
