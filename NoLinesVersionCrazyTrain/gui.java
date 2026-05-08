import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionListener;

public class gui implements ActionListener
{
    //CrazyTrain object used for extracting various pieces of data and tests as well as calculating the final route
    private crazyTrain mainCode;
    private String startingPointString;
    private String destinationString;
    


    //Components for first menu interface
    private JFrame menuWindow;
    private JPanel overallConatiner;
    private BorderLayout overallLayout;

    //Panel to hold the introductory text to the program
    private JPanel whatsWhat;
    private FlowLayout wwLayout;
    private JTextArea introText;
    //Panel to hold the first two buttons to either run or quit the program
    private JPanel firstChoice;
    private FlowLayout fcLayout;
    private JButton goTime;
    private JButton quit;

    //Panel for file route input before program menu
    private String stationFilepath;
    private String walkingTimesFilepath;
    private JPanel fileRouteEntry;
    private FlowLayout freLayout;
    private JTextField filePath1;
    private JButton confirmFP1;
    private JButton addWalkingFile;
    private JTextField filePath2;
    private JButton confirmFP2;
    private JButton continueToMainProgram;


    //Components for secondary input interface
    private JPanel twoDsAccessPanel;
    private FlowLayout tdapLayout;
    private JButton opentwoDsPanel;
    //Buttons to run the program, only appear after a valid starting point and destination are confirmed
    private JPanel runButtons;
    private FlowLayout rbLayout;
    private JButton runProgram;
    //Interface to access the interface to change the preferences of the user for their route
    private JPanel accessPreferencesPanel;
    private FlowLayout appLayout;
    private JCheckBox walking;
    private JButton preferencesButton;
    //Panel for the input text and buttons for delays and closure(deletions) on stations
    private JPanel deletionsPanel;
    private FlowLayout delsLayout;
    private JTextField deletionInput;
    private JButton deletionConfirmation;
    private String lsDeleted;
    private JLabel lastStationDeleted;

    private JPanel delaysPanel;
    private BorderLayout delaysLayout;
    private JPanel delaysInputsPanel;
    private FlowLayout delaysInputsLayout;
    private JTextField delayFrom;
    private JTextField delayTo;
    private JTextField delayTime;
    private JButton delayConfirmation;
    private JPanel ltdPanel;
    private FlowLayout ltdlayout;
    private JCheckBox[] lineToDelay;
    private JLabel lastStationDelayed;
    private String lsDelayed;

    private JPanel resetAndMenu;
    private FlowLayout ramLayout;
    private JButton backToMainArea;
    private JButton resetDelays;
    private JButton resetDeletions;
    //Main panel to input starting and ending locations as well as the buttons to input and confirm them
    private JPanel startAndDestPanel;
    private FlowLayout sadLayout;
    private JLabel spDisplay;
    private JLabel destDisplay;
    private JTextField startInput;
    private JButton confirmStart;
    private JTextField destinationInput;
    private JButton confirmDestination;
    private JButton resetSAndDInputs;
    //Panel for checkmarks on constraints you want to apply to the journey e.g shortest time, fewest changes, open to walking...
    private JPanel constraintsPanel;
    private FlowLayout constrainsLayout;
    private JCheckBox constraint1;
    //Button to reset main program in case of accidental inputs
    private JPanel resetOrQuitPanel;
    private FlowLayout roqLayout;
    private JButton resetButton;

    //Holds text version of final route output
    private JPanel textVersion;
    private FlowLayout tvLayout;
    private JTextArea tvText;
    //Used to draw the graph/visualisation of the route onto and display to the user
    private DrawingPanel visualisation;
    //Holds the buttons to either go back to the mainProgram menu or quit the program entirely
    private JPanel restartOrLeaveButtons;
    private FlowLayout rolLayout;
    private JButton provideVisualisationButton;
    private JButton restartButton;
    private JButton leaveButton;
    

    public gui()
    {

        lsDelayed = "";
        lsDeleted = "";
        startingPointString = new string("");
        destinationString = new String("");
        mainCode = new crazyTrain();
        menuWindow = new JFrame("Train Network Navigation Software (TNNS)");
        menuWindow.setSize(700, 700);
        menuWindow.isVisible(true);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        overallLayout = new BorderLayout();
        overallConatiner = new JPanel(overallLayout);
        menuWindow.setContentPane(overallConatiner);
        whatsWhat = new JPanel(null);
        introText = new JTextArea("");
        whatsWhat.add(introText);
        overallConatiner.add("North",whatsWhat);
        fcLayout = new FlowLayout();
        firstChoice = new JPanel(fcLayout);
        goTime = new JButton("Go ahead");
        goTime.addActionListener(this);
        quit = new JButton("Quit");
        quit.addActionListener(this);
        firstChoice.add(goTime);
        firstChoice.add(quit);
        overallConatiner.add("Center", firstChoice);
    }

    private void makeFileEntryInterface()
    {
        stationFilepath = null;
        walkingTimesFilepath = null;
        overallConatiner.removeAll();
        freLayout = new FlowLayout();
        fileRouteEntry = new JFrame(freLayout);
        filePath1 = new JTextField("Enter the filepath for the csv of the stations you want to use:");
        filePath2 = new JTextField("Enter the filepath for the csv of the walkingtimes you want to use:");
        confirmFP1 = new JButton("Click to confirm filepath for stations csv.");
        confirmFP1.addActionListener(this);
        confirmFP2 = new JButton("Click to confirm filepath for walkingtimes csv.");
        confirmFP2.addActionListener(this);
        addWalkingFile = new JButton("Click to open up the option to add a walking times csv");
        addWalkingFile.addActionListener(this);
        fileRouteEntry.add(filePath1);
        fileRouteEntry.add(confirmFP1);
        fileRouteEntry.add(addWalkingFile);
        overallConatiner.add("Center",fileRouteEntry);
    }

    private void makeStartpointAndDestInterface()
    {
        sadLayout = new FlowLayout();
        startAndDestPanel = new JPanel(sadLayout);
        spDisplay = new JLabel("Current starting point is: " + startingPointString);
        destDisplay = new JLabel("Current destination is: " + destinationString);
        startInput = new JTextField("Enter starting location here:");
        destinationInput = new JTextField("Enter Destination here:");
        confirmStart = new JButton("Attempt to confirm starting location");
        confirmStart.addActionListener(this);
        confirmDestination = new JButton("Attempt to confirm destination");
        confirmDestination.addActionListener(this);
        resetSAndDInputs = new JButton("Reset input values");
        resetSAndDInputs.addActionListener(this);
        startAndDestPanel.add(spDisplay);
        startAndDestPanel.add(destDisplay);
        startAndDestPanel.add(startInput);
        startAndDestPanel.add(confirmStart);
        startAndDestPanel.add(destinationInput);
        startAndDestPanel.add(resetSAndDInputs);
    }

    private void makeMainInterface()
    {
        overallConatiner.removeAll();
        makeStartpointAndDestInterface();
        overallConatiner.add("Central", startAndDestPanel);


        roqLayout = new FlowLayout();
        resetOrQuitPanel = new JPanel(roqLayout);
        resetButton = new JButton("RESET");
        resetOrQuitPanel.add(resetButton);
        resetOrQuitPanel.add(quit);
        overallConatiner.add("South", resetOrQuitPanel);

        
        tdapLayout = new FlowLayout();
        twoDsAccessPanel = new JPanel(tdapLayout);
        opentwoDsPanel = new JButton("Open menu to input delays or station deletions");
        opentwoDsPanel.addActionListener(this);
        twoDsAccessPanel.add(opentwoDsPanel);
        overallConatiner.add("West", twoDsAccessPanel);

        appLayout = new FlowLayout();
        accessPreferencesPanel = new JPanel(appLayout);
        preferencesButton = new JButton("Access route generation preferences menu");
        preferencesButton.addActionListener(this);
        overallConatiner.add("North",accessPreferencesPanel);


        if(startingPointString != "" && destinationString != "")
        {
            rbLayout = new FlowLayout();
            runButtons = new JPanel(rbLayout);
            runProgram = new JButton("Initiate the program with the current parameters you have input");
            runprogram.addActionListener(this);
            runButtons.add(runProgram);
            overallConatiner.add("East", runButtons);
        }


    }

    private void makeTwoDsInterface()
    {
        overallConatiner.removeAll();

        delsLayout = new FlowLayout();
        deletionsPanel = new JPanel(delsLayout);
        deletionInput = new JTextField("Enter the name of the station you want deleted from the network:");
        deletionConfirmation = new JButton("Confirm deletion");
        deletionConfirmation.addActionListener(this);
        lastStationDeleted = new JLabel(lsDeleted);
        deletionsPanel.add(lastStationDeleted);
        deletionsPanel.add(deletionInput);
        deletionsPanel.add(deletionConfirmation);
        overallConatiner.add("West", deletionsPanel);


        delaysLayout = new BorderLayout();
        delaysPanel = new JPanel(delaysLayout);
        
        delaysInputsLayout = new FlowLayout();
        delaysInputsPanel = new JPanel(delaysInputsLayout);
        delayFrom = new JTextField("Enter the name of the first of the stations you want to make the delay between:");
        delayTo = new JTextField("Enter the name of the station you want to delay to:");
        delayTime = new JTextField("Enter the amount of time you want the delay to add to the connection between these stations:");
        delayConfirmation = new JButton("Attempt to implement this delay");
        delayConfirmation.addActionListener(this);
        lastStationDelayed = new JLabel(lsDelayed);
        delaysInputsPanel.add(lastStationDelayed);
        delaysInputsPanel.add(delayFrom);
        delaysInputsPanel.add(delayTo);
        delaysInputsPanel.add(delayTime);
        delaysPanel.add("East", delaysInputsPanel);


        ltdlayout = new FlowLayout();
        ltdPanel = new JPanel(ltdlayout);
        String[] linesNames = mainCode.returnLineNames();
        lineToDelay = new JCheckBox[linesNames.length];
        for(int x = 0; x < linesNames.length; x++)
        {
            lineToDelay[x] = new JCheckBox(linesNames[x]); 
            linesNames[x].addActionListener(this);
            ltdPanel.add(lineToDelay[x]);
        }
        delaysPanel.add("West",ltdPanel);

        overallConatiner.add("East", delaysPanel);


        ramLayout = new FlowLayout();
        resetAndMenu = new JPanel(ramLayout);
        resetDeletions = new JButton("Reset Deletions");
        resetDeletions.addActionListener(this);
        resetDelays = new JButton("Reset Delays");
        resetDelays.addActionListener(this);
        backToMainArea = new JButton("Back to main section");
        backToMainArea.addActionListener(this);
        resetAndMenu.add(resetDeletions);
        resetAndMenu.add(resetDelays);
        resetAndMenu.add(backToMainArea);

        overallConatiner.add("South", resetAndMenu);
    }

    private void makeFinalOutputInterface()
    {
        overallConatiner.removeAll();

        tvLayout = new FlowLayout();
        textVersion = new JPanel(tvLayout);
        String[] routeOutput = mainCode.returnRoute();
        tvText = new JTextArea(routeOutput.length + 1, 1);
        tvText.insert("The route generated within the given parameters between the stations " + startingPointString + " and " + destinationString + ":", 0);
        for(int x = 0; x < routeOutput.length; x++)
        {
            tvText.insert(routeOutput[x], x+1);
        }
        textVersion.add(tvText);
        overallConatiner.add("Center", textVersion);

        rolLayout = new FlowLayout();
        resetOrQuitPanel = new JPanel(rolLayout);
        resetButton = new JButton("Reset program back to main interface");
        resetButton.addActionListener(this);
        provideVisualisationButton = new JButton("Provide visualisation of route");
        provideVisualisationButton.addActionListener(this);
        leaveButton = new JButton("Exit program");
        leaveButton.addActionListener(this);
        resetOrQuitPanel.add(resetButton);
        resetOrQuitPanel.add(provideVisualisationButton);
        resetOrQuitPanel.add(leaveButton);
        overallConatiner.add("South", resetOrQuitPanel);

    }

    private void makeGraphVisualisationInterface()
    {
        overallConatiner.removeAll();

        tvLayout = new FlowLayout();
        textVersion = new JPanel(tvLayout);
        String[] routeOutput = mainCode.returnRoute();
        tvText = new JTextArea(routeOutput.length + 1, 1);
        tvText.insert("The route generated within the given parameters between the stations " + startingPointString + " and " + destinationString + ":", 0);
        for(int x = 0; x < routeOutput.length; x++)
        {
            tvText.insert(routeOutput[x], x+1);
        }
        textVersion.add(tvText);
        overallConatiner.add("East", textVersion);

        rolLayout = new FlowLayout();
        resetOrQuitPanel = new JPanel(rolLayout);
        resetButton = new JButton("Reset program back to main interface");
        resetButton.addActionListener(this);
        provideVisualisationButton = new JButton("Provide visualisation of route");
        provideVisualisationButton.addActionListener(this);
        leaveButton = new JButton("Exit program");
        leaveButton.addActionListener(this);
        resetOrQuitPanel.add(resetButton);
        resetOrQuitPanel.add(provideVisualisationButton);
        resetOrQuitPanel.add(leaveButton);
        overallConatiner.add("South", resetOrQuitPanel);


        visualisationLayout = new GridLayout();
        visualisation = new JPanel(visualisationLayout);
        overallConatiner.add(visualisation);
        String[] stationNamesInOrder = mainCode.returnStationNamesInOrder();
        int[] timeToStationInOrder = mainCode.returnTimesInOrder();
        String[] lineColoursInOrder = mainCode.returnColoursInOrder();
        int yPosition =(visualisation.getHeight()/2);
        for(int y = 0; y < stationNamesInOrder.length; y++)
        {
            int xPosition = (visualisation.getWidth()/stationNamesInOrder.length) * (x+1);
            visualisation.paintPoint(xPosition, yPosition);
            visualisation.paintText(stationNamesInOrder[y], xPosition, yPosition + 10);
            if( y == 0 || y == stationNamesInOrder.length -1)
            {
                visualisation.paintText(lineColoursInOrder[y], xPosition, yPosition -10);
            }

            if(y < stationNamesInOrder.length - 1)
            {
                int lsp = xPosition;
                int lep = (visualisation.getWidth()/stationNamesInOrder.length) * (x+2);
                visualisation.paintLine(lsp, yPosition, lep, yPosition);

                if(stationNamesInOrder[y].equals(stationNamesInOrder[y+1]))
                {
                    visualisation.paintCircle((lsp +(lsp -lep)), yPosition);
                    visualisation.paintText((lsp +(lsp -lep)), yPosition - 10);
                }
            }
        }
    }


    public void actionPerformed(ActiveEvent e)
    {

    }



}

