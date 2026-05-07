import javax.swing.*;

public class gui 
{
    //Components for first menu interface
    JFrame menuWindow;
    //Panel to hold the introductory text to the program
    JPanel whatsWhat;
    JLabel introText;
    //Panel to hold the first two buttons to either run or quit the program
    JPanel firstChoice;
    JButton goTime;
    JBUtton quit;


    //Components for secondary input interface
    JPanel mainProgram;
    //Buttons to run the program, only appear after a valid starting point and destination are confirmed
    JPanel runButtons;
    //Panel for the input text and buttons for delays and closure(deletions) on stations
    JPanel twoDsPanel;
    //Main panel to input starting and ending locations as well as the buttons to input and confirm them
    JPanel startAndDestPanel;
    //Panel for checkmarks on constraints you want to apply to the journey e.g shortest time, fewest changes, open to walking...
    JPanel constraintsPanel;


    //Components of final output display interface
    JPanel finalDisplay;
    //Holds text version of final route output
    JPanel textVersion;
    //Used to draw the graph/visualisation of the route onto and display to the user
    JPanel visualisation;
    //Holds the buttons to either go back to the mainProgram menu or quit the program entirely
    JPanel restartOrQuitButtons;


}

