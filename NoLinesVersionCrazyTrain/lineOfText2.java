public class lineOfText2                                         //Class to contain the data of the first type of csv file this project uses with two strings and a float
{
    private String[] data;

    public lineOfText2(String[] inputStrings)   //Constructor simply assigns the values to the variables
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
    
    public String readValue(int position)
    {
        return data[position];
    }

    public int getDataLength()
    {
        return data.length;
    }
    


}