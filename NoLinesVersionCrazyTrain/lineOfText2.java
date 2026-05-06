public class lineOfText2                                         //Class to contain the data of the first type of csv file this project uses with two strings and a float
{
    private String[] data;

    public lineOfText(String[] inputStrings)   //Constructor simply assigns the values to the variables
    {
        data = inputStrings;
    }

    public int stationNumberInArray(String stationName)
    {
        for(int x = 0; x < data.length; x++)
        {
            if(data[x].equals(stationName))
            {
                return x;
            }
        }
        return -1;
    }


    public String nameOfShortestWalktimeStation(int arrayPosition)
    {
        return data[arrayPosition];
    }
    

    public float shortestWalktimeFromStation()
    {
        float smallestWalktime = 999999;

        for(int x = 1; x < data.length; x++)
        {
            if(Integer.parseInt(data[x]) < smallestWalktime)
            {
                smallestWalktime = Integer.parseInt(data[x]); 
            }
        }
        return smallestWalktime;

    }

    public int shortestWalktimeArrayPosition()
    {

        int smallestWalktimePosition = -1;
        float smallestWalktime = 999999;

        for(int x = 1; x < data.length; x++)
        {
            if(Integer.parseInt(data[x]) < smallestWalktime)
            {
                smallestWalktime = Integer.parseInt(data[x]);
                smallestWalktimePosition = x; 
            }
        }
        return smallestWalktime;

    }