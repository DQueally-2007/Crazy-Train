public class lineOfText 
{
    String firstWord;
    String secondWord;
    int number;
    
    public lineOfText(String first, String second, int num)
    {
        firstWord = first;
        second = secondWord;
        number = num;

    }

    public boolean lineChecker()                                //Checks text line array to see if this line is a train line specifier
    {
        if (second == null)                               //Second value is always null on a line specifying a line colour
        {
            return true;                                        //So this certainly confirms the line of text being checked demarks a train line set
        }
        return false;                                           //Otherwise return false as it is a standard line
    }
}
