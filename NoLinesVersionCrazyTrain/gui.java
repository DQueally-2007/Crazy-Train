import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import java.io.File;                  
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gui implements ActionListener
{
    //CrazyTrain object used for extracting various pieces of data and tests as well as calculating the final route
    private crazyTrain mainCode;
    private String startingPointString;
    private String destinationString;
    


    //Components for first menu interface
    private JFrame menuWindow;
    private JPanel overallContainer;
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
    private JLabel fp1Display;
    private JButton addWalkingFile;
    private JTextField filePath2;
    private JButton confirmFP2;
    private JLabel fp2Display;

    private JPanel continuePanel;
    private FlowLayout cpLayout;
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
    private JButton preferencesButton;

    //Interface to set the preference for the pathfinding
    private JPanel preferencesPanel;
    private FlowLayout ppLayout;
    private JCheckBox walking;
    private Boolean walkingPreference;
    private JCheckBox minimumTime;
    private Boolean preferTime;
    private JCheckBox minimumSwaps;
    private Boolean preferSwaps;
    private JPanel confirmPrefsPanel;
    private FlowLayout cppLayout;
    private JButton confirmPreferences;


    //Panel for the input text and buttons for delays and closure(deletions) on stations
    private JPanel deletionsPanel;
    private GridLayout delsLayout;
    private JTextField deletionInput;
    private JButton deletionConfirmation;
    private String lsDeleted;
    private JLabel lastStationDeleted;

    private JPanel delaysPanel;
    private GridLayout delaysLayout;
    private JPanel delaysInputsPanel;
    private GridLayout delaysInputsLayout;
    private JTextField delayFrom;
    private String delFrom;
    private JTextField delayTo;
    private String delTo;
    private JTextField delayTime;
    private float delTime;
    private String lineColour;
    private String previousLineColour;
    private JButton delayConfirmation;
    private JPanel ltdPanel;
    private GridLayout ltdlayout;
    private JCheckBox[] lineToDelay;
    private JLabel lastStationDelayed;

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
    private FlowLayout visLayout;


    //Holds the buttons to either go back to the mainProgram menu or quit the program entirely
    private JPanel restartOrLeaveButtons;
    private FlowLayout rolLayout;
    private JButton provideVisualisationButton;
    private JButton leaveButton;
    

    public gui()
    {
        delFrom = "EMPTY"; 
        delTo = "EMPTY";
        delTime = 0;
        previousLineColour = "EMPTY";
        preferTime = true;
        minimumTime = new JCheckBox("Route for minimum time", preferTime);
        preferSwaps = false;
        minimumSwaps = new JCheckBox("Route for minimum swaps", preferSwaps);
        walkingPreference  = false;
        walking = new JCheckBox("Ok with walking", walkingPreference);
        lsDeleted = "EMPTY";
        startingPointString = new String("");
        destinationString = new String("");
        stationFilepath = null;
        walkingTimesFilepath = null;



        menuWindow = new JFrame("Train Network Navigation Software (TNNS)");
        menuWindow.setSize(1000, 1000);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        overallLayout = new BorderLayout();
        overallContainer = new JPanel(overallLayout);
        menuWindow.setContentPane(overallContainer);
        whatsWhat = new JPanel(null);
        introText = new JTextArea("Welcome to the TNNS, please select from the two options below:");
        whatsWhat.add(introText);
        overallContainer.add("North",whatsWhat);
        fcLayout = new FlowLayout();
        firstChoice = new JPanel(fcLayout);
        goTime = new JButton("Go ahead");
        goTime.addActionListener(this);
        quit = new JButton("Quit");
        quit.addActionListener(this);
        firstChoice.add(goTime);
        firstChoice.add(quit);
        overallContainer.add("Center", firstChoice);
        menuWindow.setVisible(true);
    }

    private void makeFileEntryInterface()
    {
        overallContainer.removeAll();
        overallContainer.removeAll();
        freLayout = new FlowLayout();
        fileRouteEntry = new JPanel(freLayout);
        filePath1 = new JTextField("Enter the filepath for the csv of the stations you want to use:");
        fp1Display = new JLabel("Current filepath stored: " + stationFilepath);
        filePath2 = new JTextField("Enter the filepath for the csv of the walkingtimes you want to use:");
        confirmFP1 = new JButton("Click to confirm filepath for stations csv.");
        confirmFP1.addActionListener(this);
        confirmFP2 = new JButton("Click to confirm filepath for walkingtimes csv.");
        confirmFP2.addActionListener(this);
        addWalkingFile = new JButton("Click to open up the option to add a walking times csv");
        addWalkingFile.addActionListener(this);
        fileRouteEntry.add(filePath1);
        fileRouteEntry.add(fp1Display);
        fileRouteEntry.add(confirmFP1);
        fileRouteEntry.add(addWalkingFile);
        overallContainer.add("Center",fileRouteEntry);
        

        cpLayout = new FlowLayout();
        continuePanel = new JPanel(cpLayout);
        continueToMainProgram = new JButton("Continue to main program");
        continueToMainProgram.addActionListener(this);
        continuePanel.add(continueToMainProgram);
        overallContainer.add("East", continuePanel);
        overallContainer.repaint();
        
        SwingUtilities.updateComponentTreeUI(menuWindow);
        
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
        startAndDestPanel.add(confirmDestination);
        startAndDestPanel.add(resetSAndDInputs);

        SwingUtilities.updateComponentTreeUI(menuWindow);
    }

    private void makeMainInterface() throws FileNotFoundException
    {
        mainCode = new crazyTrain(stationFilepath, walkingTimesFilepath);
        overallContainer.removeAll();
        makeStartpointAndDestInterface();
        overallContainer.add("Center", startAndDestPanel);


        roqLayout = new FlowLayout();
        resetOrQuitPanel = new JPanel(roqLayout);
        resetButton = new JButton("RESET");
        resetOrQuitPanel.add(resetButton);
        resetOrQuitPanel.add(quit);
        overallContainer.add("South", resetOrQuitPanel);

        
        tdapLayout = new FlowLayout();
        twoDsAccessPanel = new JPanel(tdapLayout);
        opentwoDsPanel = new JButton("Open menu to input delays or station closures");
        opentwoDsPanel.addActionListener(this);
        twoDsAccessPanel.add(opentwoDsPanel);
        overallContainer.add("West", twoDsAccessPanel);

        appLayout = new FlowLayout();
        accessPreferencesPanel = new JPanel(appLayout);
        preferencesButton = new JButton("Access route generation preferences menu");
        preferencesButton.addActionListener(this);
        accessPreferencesPanel.add(preferencesButton);
        overallContainer.add("North", accessPreferencesPanel);


        if(startingPointString != "" && destinationString != "")
        {
            rbLayout = new FlowLayout();
            runButtons = new JPanel(rbLayout);
            runProgram = new JButton("Initiate the program with the current parameters you have input");
            runProgram.addActionListener(this);
            runButtons.add(runProgram);
            overallContainer.add("East", runButtons);
        }
        
        overallContainer.repaint();
        menuWindow.repaint();
        SwingUtilities.updateComponentTreeUI(menuWindow);

    }

    private void makePreferencesInterface()
    {
        overallContainer.removeAll();
        ppLayout = new FlowLayout();
        preferencesPanel = new JPanel(ppLayout);
        walking = new JCheckBox("Ok with walking?", false);
        walking.addActionListener(this);
        minimumTime = new JCheckBox("Prefer a route of minimum time?", false);
        if(preferTime == true)
        {
            minimumTime.setSelected(true);
        }
        minimumTime.addActionListener(this);
        minimumSwaps = new JCheckBox("Prefer to minimise swaps", false);
        if(preferSwaps == true)
        {
            minimumSwaps.setSelected(true);
        }
        minimumSwaps.addActionListener(this);
        if((walkingTimesFilepath == null) == false)
        {
            preferencesPanel.add(walking);
        }
        preferencesPanel.add(minimumTime);
        preferencesPanel.add(minimumSwaps);

        overallContainer.add("Center", preferencesPanel);

        cppLayout = new FlowLayout();
        confirmPrefsPanel = new JPanel(cppLayout);
        confirmPreferences = new JButton("Confirm Preferences");
        confirmPreferences.addActionListener(this);
        confirmPrefsPanel.add(confirmPreferences);
        overallContainer.add("South", confirmPrefsPanel);

        overallContainer.repaint();
        SwingUtilities.updateComponentTreeUI(overallContainer);
        SwingUtilities.updateComponentTreeUI(menuWindow);

    }

    private void makeTwoDsInterface()
    {
        overallContainer.removeAll();
        lineColour = null;

        delsLayout = new GridLayout(3, 1);
        deletionsPanel = new JPanel(delsLayout);
        deletionInput = new JTextField("Enter the name of the station you want closed in this network:");
        
        deletionConfirmation = new JButton("Confirm closure");
        deletionConfirmation.addActionListener(this);
        lastStationDeleted = new JLabel("Last station deleted was: " +lsDeleted);
        deletionsPanel.add(lastStationDeleted);
        deletionsPanel.add(deletionInput);
        deletionsPanel.add(deletionConfirmation);
        overallContainer.add("West", deletionsPanel);


        delaysLayout = new GridLayout(3, 1);
        delaysPanel = new JPanel(delaysLayout);
        
        delaysInputsLayout = new GridLayout(4,1);
        delaysInputsPanel = new JPanel(delaysInputsLayout);
        delayFrom = new JTextField("Enter the name of the first of the stations you want to make the delay between:");
        delayTo = new JTextField("Enter the name of the station you want to delay to:");
        delayTime = new JTextField("Enter the amount of time you want the delay to add to the connection between these stations:");
        delayConfirmation = new JButton("Attempt to implement this delay");
        delayConfirmation.addActionListener(this);
        lastStationDelayed = new JLabel("Last connection delayed was betweeen: " + delFrom + " and " + delTo + " on the " + previousLineColour +" adding " + delTime + "minutes in delay.");
        delaysInputsPanel.add(lastStationDelayed);
        delaysInputsPanel.add(delayFrom);
        delaysInputsPanel.add(delayTo);
        delaysInputsPanel.add(delayTime);
        delaysPanel.add(delaysInputsPanel);


        String[] linesNames = mainCode.returnLineNames();
        ltdlayout = new GridLayout(linesNames.length, 1);
        ltdPanel = new JPanel(ltdlayout);
        lineToDelay = new JCheckBox[linesNames.length];
        for(int x = 0; x < linesNames.length; x++)
        {
            System.out.println(linesNames[x]);            
            lineToDelay[x] = new JCheckBox(linesNames[x]); 
            lineToDelay[x].addActionListener(this);
            lineToDelay[x].setSelected(false);
            ltdPanel.add(lineToDelay[x]);
        }
        delaysPanel.add(ltdPanel);

        delaysPanel.add(delayConfirmation);
        overallContainer.add("East", delaysPanel);


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

        overallContainer.add("South", resetAndMenu);

        overallContainer.repaint();

        SwingUtilities.updateComponentTreeUI(menuWindow);
    }

    private void makeFinalOutputInterface() throws FileNotFoundException
    {
        overallContainer.removeAll();
        tvLayout = new FlowLayout();
        textVersion = new JPanel(tvLayout);
        String[] routeOutput = mainCode.getRoute();
        tvText = new JTextArea(routeOutput.length + 1, 0);
        tvText.setLineWrap(false);
        tvText.append("The route generated within the given parameters between the stations " + startingPointString + " and " + destinationString + ": \n");
        for(int x = 0; x < routeOutput.length; x++)
        {
            tvText.append(routeOutput[x] + " \n");
        }
        textVersion.add(tvText);
        overallContainer.add("Center", textVersion);

        rolLayout = new FlowLayout();
        resetOrQuitPanel = new JPanel(rolLayout);
        resetButton = new JButton("Reset program back to main interface");
        resetButton.addActionListener(this);
        provideVisualisationButton = new JButton("Provide visualisation of route");
        provideVisualisationButton.addActionListener(this);
        resetOrQuitPanel.add(resetButton);
        resetOrQuitPanel.add(provideVisualisationButton);
        resetOrQuitPanel.add(quit);
        overallContainer.add("South", resetOrQuitPanel);

        overallContainer.repaint();

        SwingUtilities.updateComponentTreeUI(menuWindow);

    }

    private void makeGraphVisualisationInterface() throws FileNotFoundException
    {
        overallContainer.removeAll();

        tvLayout = new FlowLayout();
        textVersion = new JPanel(tvLayout);
        String[] routeOutput = mainCode.getRoute();
        tvText = new JTextArea(routeOutput.length + 1, 0);
        tvText.append("The route generated within the given parameters between the stations " + startingPointString + " and " + destinationString + ": \n");
        for(int x = 0; x < routeOutput.length; x++)
        {
            tvText.append(routeOutput[x] + " \n");
        }
        textVersion.add(tvText);
        overallContainer.add("East", textVersion);

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
        overallContainer.add("South", resetOrQuitPanel);


        visLayout = new FlowLayout();
        visualisation = new DrawingPanel(visLayout);
        overallContainer.add(visualisation);
        String[] stationNamesInOrder = mainCode.returnStationNamesInOrder();
        int[] timeToStationInOrder = mainCode.returnTimesInOrder();
        String[] lineColoursInOrder = mainCode.returnColoursInOrder();
        int yPosition =(visualisation.getHeight()/2);
        for(int y = 0; y < stationNamesInOrder.length; y++)
        {
            int xPosition = (visualisation.getWidth()/stationNamesInOrder.length) * (y+1);
            visualisation.paintPoint(xPosition, yPosition);
            visualisation.paintText(stationNamesInOrder[y], xPosition, yPosition + 10);
            if( y == 0 || y == stationNamesInOrder.length -1)
            {
                visualisation.paintText(lineColoursInOrder[y], xPosition, yPosition -10);
            }

            if(y < stationNamesInOrder.length - 1)
            {
                int lsp = xPosition;
                int lep = (visualisation.getWidth()/stationNamesInOrder.length) * (y+2);
                visualisation.paintLine(lsp, yPosition, lep, yPosition);

                if(stationNamesInOrder[y].equals(stationNamesInOrder[y+1]))
                {
                    visualisation.paintCircle((lsp +(lsp -lep)), yPosition);
                    visualisation.paintText(lineColoursInOrder[y + 1], (lsp +(lsp -lep)), yPosition - 10);
                }
            }
        }

        overallContainer.repaint();

        SwingUtilities.updateComponentTreeUI(menuWindow);
    }


    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if(e.getSource() == goTime)
            {
                makeFileEntryInterface();
            }

            if(e.getSource() == quit)
            {
                System.exit(0);
            }

            if(e.getSource() == confirmFP1)
            {
                stationFilepath = filePath1.getText();
                makeFileEntryInterface();
            }

            if(e.getSource() == addWalkingFile)
            {
                filePath2 = new JTextField("Enter the filepath for the csv of the walkingtimes you want to use:");
                fp2Display = new JLabel("Current walking times filepath is: "+ walkingTimesFilepath);
                confirmFP2 = new JButton("Click to confirm filepath for walkingtimes csv.");
                confirmFP2.addActionListener(this);
                fileRouteEntry.add(filePath2);
                fileRouteEntry.add(fp2Display);
                fileRouteEntry.add(confirmFP2);
                fileRouteEntry.repaint();

                SwingUtilities.updateComponentTreeUI(menuWindow);
            }

            if(e.getSource() == confirmFP2)
            {
                stationFilepath = filePath2.getText();
                makeFileEntryInterface();
            }

            if(e.getSource() == continueToMainProgram)
            {
                try
                {
                    mainCode = new crazyTrain(stationFilepath, walkingTimesFilepath);
                    makeMainInterface();
                    SwingUtilities.updateComponentTreeUI(menuWindow);
                }
                catch(FileNotFoundException s)
                {
                    stationFilepath = "AT LEAST ONE FILE NOTE FOUND";
                    walkingTimesFilepath = "AT LEAST ONE FILE NOT FOUND";
                    makeFileEntryInterface();
                    SwingUtilities.updateComponentTreeUI(menuWindow);
                }
            }
            
            if(e.getSource() == confirmStart)
            {
                String startToTest = startInput.getText();
                boolean testResult = mainCode.checkForStation(startToTest);
                System.out.println("Attempting Test");
                if(testResult == true)
                {
                    System.out.println("ASSIGNED START LOCATION");
                    startingPointString = startToTest;
                    mainCode.setSP(startingPointString);
                }
                spDisplay.repaint();
                makeMainInterface();
            }

            if(e.getSource() == confirmDestination)
            {
                String destToTest = destinationInput.getText();
                boolean testResult = mainCode.checkForStation(destToTest);
                if(testResult == true)
                {
                    destinationString = destToTest;
                    mainCode.setDest(destinationString);
                }
                makeMainInterface();
            }

            if(e.getSource() == resetSAndDInputs)
            {
                startingPointString = null;
                destinationString = null;
                makeMainInterface();
            }

            if(e.getSource() == resetButton)
            {
                startingPointString = null;
                destinationString = null;
                makeMainInterface();
            }

            if(e.getSource() == opentwoDsPanel)
            {
                makeTwoDsInterface();
            }

            if(e.getSource() == preferencesButton)
            {
                makePreferencesInterface();
            }

            if(e.getSource() == walking)
            {
                if(walking.isSelected() == true)
                {
                    walking.repaint();
                    walkingPreference = true;
                    mainCode.setAllowWalk(walkingPreference);

                    if(preferSwaps == true)
                    {
                        preferSwaps = false;
                        mainCode.setPreferSwaps(preferSwaps);
                        minimumSwaps.setSelected(false);
                        minimumSwaps.repaint();
                    }

                    
                }

                else
                {
                    walking.repaint();
                    walkingPreference = false;
                    mainCode.setAllowWalk(walkingPreference);
                }

                SwingUtilities.updateComponentTreeUI(menuWindow);
            }

            if(e.getSource() == minimumTime)
            {
                if(minimumTime.isSelected() == true)
                {
                    minimumTime.repaint();
                    preferTime = true;
                    mainCode.setPreferTime(preferTime);

                    if(preferSwaps == true)
                    {
                        preferSwaps = false;
                        mainCode.setPreferSwaps(preferSwaps);
                        minimumSwaps.setSelected(false);
                        minimumSwaps.repaint();
                    }
                }

                else
                {
                    minimumTime.repaint();
                    preferTime = false;
                    mainCode.setPreferTime(preferTime);

                    if(preferSwaps == false)
                    {
                        preferSwaps = true;
                        mainCode.setPreferSwaps(preferSwaps);
                        minimumSwaps.setSelected(true);
                        minimumSwaps.repaint();

                        if(walkingPreference == true)
                        {
                            walkingPreference = false;
                            mainCode.setAllowWalk(walkingPreference);
                            walking.setSelected(false);
                            walking.repaint();
                        }
                    }
                }

                SwingUtilities.updateComponentTreeUI(menuWindow);
            }

            if(e.getSource() == minimumSwaps)
            {
                if(minimumSwaps.isSelected() == true)
                {
                    minimumSwaps.repaint();
                    preferSwaps = true;
                    mainCode.setPreferSwaps(preferSwaps);

                    if(walkingPreference == true)
                    {
                        walkingPreference = false;
                        mainCode.setAllowWalk(walkingPreference);
                        walking.setSelected(false);
                        walking.repaint();
                    }

                    if(preferTime == true)
                    {
                        preferTime = false;
                        mainCode.setPreferTime(preferTime);
                        minimumTime.setSelected(false);
                        minimumTime.repaint();
                    }
                }

                else
                {
                    minimumSwaps.repaint();
                    preferSwaps = false;
                    mainCode.setPreferSwaps(preferSwaps);

                    if(preferTime == false)
                    {
                        preferTime = true;
                        mainCode.setPreferTime(preferTime);
                        minimumTime.setSelected(true);
                        minimumTime.repaint();
                    }
                }

                SwingUtilities.updateComponentTreeUI(menuWindow);

            }

            if(e.getSource() == confirmPreferences)
            {
                makeMainInterface();
            }

            if(e.getSource() == runProgram)
            {
                mainCode.setSP(startingPointString);
                mainCode.setDest(destinationString);
                System.out.println("***");
                makeFinalOutputInterface();
            }

            if(e.getSource() == deletionConfirmation)
            {
                boolean confirmed = mainCode.closeStation(deletionInput.getText());
                if(confirmed == true)
                {
                    lsDeleted = deletionInput.getText();
                    makeTwoDsInterface();
                    SwingUtilities.updateComponentTreeUI(menuWindow);
                }
                
                else
                {
                    lsDeleted = "DELETION FAILED";
                    makeTwoDsInterface();
                    SwingUtilities.updateComponentTreeUI(menuWindow);
                }
            }

            if(e.getSource() == delayConfirmation)
            {
                boolean confirmed = mainCode.delayConnection(delayFrom.getText(), delayTo.getText(), lineColour, Integer.parseInt(delayTime.getText()));
                if(confirmed == true)
                {
                    delFrom = delayFrom.getText();
                    delTo = delayTo.getText();
                    delTime = Float.parseFloat(delayTime.getText());
                    previousLineColour = lineColour;
                    makeTwoDsInterface();
                }
                
                else
                {
                    delFrom = "DELAY FAILED";
                    delTo = "DELAY FAILED";
                    delTime = 0;
                    previousLineColour = "DELAY FAILED";
                    makeTwoDsInterface();
                }
            }

            if(e.getSource() == backToMainArea)
            {
                makeMainInterface();
            }

            if(e.getSource() == resetDelays)
            {
                delFrom = "EMPTY";
                delTo = "EMPTY";
                delTime = 0;
                previousLineColour = "EMPTY";
                mainCode = new crazyTrain(stationFilepath, walkingTimesFilepath);
                makeTwoDsInterface();
            }

            if(e.getSource() == resetDeletions)
            {
                lsDeleted = "ALL PREVIOUS DELETIONS UNDONE";
                mainCode = new crazyTrain(stationFilepath, walkingTimesFilepath);
                makeTwoDsInterface();
            }

            if(e.getSource() == provideVisualisationButton)
            {
                makeGraphVisualisationInterface();
            }

            if(lineToDelay != null)
            {
                for(int x = 0; x < lineToDelay.length; x++)
                {
                    if(e.getSource() == lineToDelay[x])
                    {
                        if(lineToDelay[x].isSelected())
                        {
                            for(int y = 0; y < lineToDelay.length; x++)
                            {
                                lineToDelay[y].setSelected(false);
                                lineToDelay[y].repaint();   
                            }
                            lineToDelay[x].setSelected(true);
                            lineToDelay[x].repaint();

                            lineColour = lineToDelay[x].getText();

                            menuWindow.repaint();
                             SwingUtilities.updateComponentTreeUI(menuWindow);
                        }
                    }
                }
            }
        }
        catch(FileNotFoundException f)
        {
            makeFileEntryInterface();
        }
    }


    public static void main(String[] args)          //Main method
    {
        //try and catch statements to handle file not found exceptions
        gui display = new gui();
    }


}

