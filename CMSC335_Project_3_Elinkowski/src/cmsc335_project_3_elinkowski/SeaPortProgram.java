package cmsc335_project_3_elinkowski;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/******************************************************************************
 * File name: SeaPortProgram class
 * Date: 20181024 1417L
 * Author: Keith R. Elinkowski
 * Purpose: Primary program, contains the Main method.  It initializes the 
 * program, creates the GUI and instantiates a World.  It also includes 
 * actionListeners for the "Read", "Display", and "Search" buttons as well 
 * as a ComboBox that allows the User to select their search target. Contains
 * a method to generate a JTree object based off of the User input simulation
 * file.
 ******************************************************************************/
public class SeaPortProgram extends JFrame {
    private World world;
    private Scanner scanner;
    private JPanel structurePanel;
    private JTextArea console;
    private JTextArea workConsole;
    private JComboBox<String> searchComboBox;
    private JComboBox<String> sortTypeComboBox;
    private JComboBox<String> sortTargetComboBox;
    private JTextField searchField;
    private JTree root;
    private Dimension screenSize;
    private HashMap<Integer, Thing> structureMap;
    private JobTableTemplate workTableModel;
    private JTable workTable;
    private JPanel workTablePanel;
    private JPanel workButtonPanel;
    public boolean running;
    public boolean ready;
      
    /***************************************************************************
     * Starts program 
     * @param args
     **************************************************************************/
    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        SeaPortProgram simulation = new SeaPortProgram();
        simulation.seaPortProgramDisplay();
        simulation.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            simulation.running = false;
            if(JOptionPane.showConfirmDialog(simulation, "Quit?", "Exiting.", JOptionPane.OK_OPTION, 0, new ImageIcon(""))!= 0){
                return;
            }
            System.exit(0);
        }});
        while(simulation.running){
            simulation.monitorWork();
        }
    }
    
    /***************************************************************************
     * Method to be called when the User hits the Read button. Uses an
     * instance of JFileChooser to select a simulation file to read. Returns
     * a Scanner object that is then used by the World Class to populate
     * the structure of the World.
    ***************************************************************************/
    private Scanner readSimulation() {
        console.append(">>> You pressed the \"Read\" Button.\n");
        JFileChooser fileChooser = new JFileChooser(".");
        try {
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                scanner = new Scanner(fileChooser.getSelectedFile());
                console.append(">>> Reading simulation file [" + fileChooser.getSelectedFile().getName() + "]\n");
                TimeUnit.MILLISECONDS.sleep(500);
                console.append(">>> . \n");
                TimeUnit.MILLISECONDS.sleep(500);
                console.append(">>> . .\n");
                TimeUnit.MILLISECONDS.sleep(500);
                console.append(">>> . . .\n");
                console.append(">>> Simulation [" + fileChooser.getSelectedFile().getName() + "] successfully loaded.\n");
            }
        } catch (InterruptedException e) {
            
        } catch (FileNotFoundException e) {
            console.append(">>> Error occurred while loading the simulation. Please try again!\n");
        }
        console.append(">>> Structure is ready to be display.\n");
        console.append("<<< Please hit the \"Display\" button!\n");
        return null;
    }
    
    /***************************************************************************
     * Method used to populate the World structure.  Uses HashMaps and method 
     * calls of each Type to store objects.
    ***************************************************************************/ 
    private void buildStructure(Scanner scanner){
        world = new World(scanner);
        structureMap = new HashMap<>();
        HashMap<Integer, SeaPort> portMap = new HashMap<>();
        HashMap<Integer, Dock> dockMap = new HashMap<>();
        HashMap<Integer, Ship> shipMap = new HashMap<>();
        while(scanner.hasNextLine()){
            String lineScanner = scanner.nextLine().trim();
            if(lineScanner.length() == 0) continue;
            Scanner thingScanner = new Scanner(lineScanner);
            if(!thingScanner.hasNext()) return;
            switch(thingScanner.next()){
                case "port":
                    SeaPort port = new SeaPort(thingScanner);
                    portMap.put(port.getIndex(), port);
                    structureMap.put(port.getIndex(), port);
                    world.assignSeaPort(port);
                    console.append(">>> Added new Port - ["+port.getName()+"]\n");
                    break;
                case "dock":
                    Dock dock = new Dock(thingScanner);
                    dockMap.put(dock.getIndex(), dock);
                    structureMap.put(dock.getIndex(), dock);
                    world.assignDock(dock, portMap.get(dock.getParent()));
                    console.append(">>> Added new Pier - ["+dock.getName()+"]\n");
                    break;
                case "pship":
                    PassengerShip passengerShip = new PassengerShip(thingScanner);
                    shipMap.put(passengerShip.getIndex(), passengerShip);
                    structureMap.put(passengerShip.getIndex(), passengerShip);
                    SeaPort passengerPort = portMap.get(passengerShip.getParent());
                    Dock passengerDock = dockMap.get(passengerShip.getParent());
                    if(passengerPort == null) {
                        passengerPort = portMap.get(passengerDock.getParent());
                    }                    
                    world.assignShip(passengerShip, passengerPort, passengerDock);
                    console.append(">>> Added new PassengerShip - ["+passengerShip.getName()+"]\n");
                    break;
                case "cship":
                    CargoShip cargoShip = new CargoShip(thingScanner);
                    shipMap.put(cargoShip.getIndex(), cargoShip);
                    structureMap.put(cargoShip.getIndex(), cargoShip);
                    SeaPort cargoPort = portMap.get(cargoShip.getParent());
                    Dock cargoDock = dockMap.get(cargoShip.getParent());
                    if(cargoPort == null) {
                        cargoPort = portMap.get(cargoDock.getParent());
                    }
                    world.assignShip(cargoShip, cargoPort, cargoDock);
                    console.append(">>> Added new CargoShip - ["+cargoShip.getName()+"]\n");
                    break;
                case "person":
                    Person person = new Person(thingScanner);
                    structureMap.put(person.getIndex(), person);
                    world.assignPerson(person, portMap.get(person.getParent()));
                    console.append(">>> Added new Person - ["+person.getName()+"]\n");
                    break;
                case "job":
                    Job job = new Job(thingScanner);
                    structureMap.put(job.getIndex(), job);
                    world.assignJob(job, structureMap.get(job.getParent()));
                    console.append(">>> Added new Job - ["+job.getName()+"]\n");
                    break;
                default:
                    break;
            }
        }
        for(Ship ship : shipMap.values()){
            if(!ship.getJobs().isEmpty() && structureMap.get(ship.getParent()) instanceof Dock){
                workConsole.append(String.format(">>> SHIP DOCKING: SS %s docking in %s at Port of %s\n", ship.getName(), dockMap.get(ship.getParent()).getName(), portMap.get(dockMap.get(ship.getParent()).getParent()).getName()));
                for(Job job : ship.getJobs()){
                    workTableModel.add(ship, structureMap, job);
                    job.displayWork(workButtonPanel);
                    job.startWork();
                }
            }
        }
        workButtonPanel.setLayout(new GridLayout(workTableModel.getRowCount(),3, 3, 3));
        workButtonPanel.setBorder(new EmptyBorder(0,3,0,3));
        workButtonPanel.setPreferredSize(new Dimension(400, workTableModel.getRowCount() * 25));
        ready = true;
    }
    
    /***************************************************************************
     * Draws the GUI and handles all of the buttons, panels and comboBoxes.
     * Uses BorderLayout to display everything in a neat, but non-intuitive 
     * manner
    ***************************************************************************/
    private void seaPortProgramDisplay() {
        /* GUI setup */
        running = true;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        screenSize = toolkit.getScreenSize();
        setTitle ("Keith R. Elinkowski Seaport Simulation");
        setSize((screenSize.width)-300, 900);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        
        /* Console Text Area */
        console = new JTextArea();
        console.setFont(new Font("Monospaced", 0, 12));
        console.setEditable(false);
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setPreferredSize(new Dimension(300, screenSize.height/2));
        consolePanel.setBorder(new EmptyBorder(10,0,25,25));
        JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setPreferredSize(new Dimension(300, 375));
        consolePanel.add(scrollPane, BorderLayout.NORTH);
        add(consolePanel, BorderLayout.CENTER);
        
        /*Job Console */
        workConsole = new JTextArea();
        workConsole.setFont(new Font("Monospaced", 0, 12));
        workConsole.setEditable(false);
        JScrollPane jobConsoleScrollPane = new JScrollPane(workConsole);
        jobConsoleScrollPane.setPreferredSize(new Dimension(300, 375));
        consolePanel.add(jobConsoleScrollPane, BorderLayout.SOUTH);
        
        /* Read Button */
        JButton readButton = new JButton("Read");
        readButton.addActionListener((ActionEvent e)->readSimulation());

        /* Display Button */
        JButton displayButton = new JButton("Display");
        displayButton.addActionListener((ActionEvent e)->displayStructure());

        /* Sort Button */
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener((ActionEvent e)->sort());
        
        /* Clear Button */
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener((ActionEvent e)->clearConsole());
        
        /* Sort Target ComboBox */
        JLabel sortTargetLabel = new JLabel("Sort Target");
        sortTargetComboBox = new JComboBox<>();
        sortTargetComboBox.addItem("Queue");
        sortTargetComboBox.addItem("Ports");
        sortTargetComboBox.addItem("Piers");
        sortTargetComboBox.addItem("Ships");
        sortTargetComboBox.addItem("Cargo Ships");
        sortTargetComboBox.addItem("Passenger Ships");
        sortTargetComboBox.addItem("People");
        sortTargetComboBox.addItem("Jobs");
        sortTargetComboBox.addItem("World");
        sortTargetComboBox.addActionListener(e->updateSortTypeComboBox());
        
        /* Sort Type ComboBox */
        JLabel sortTypeLabel = new JLabel("Sort Type");
        sortTypeComboBox = new JComboBox<>();
        updateSortTypeComboBox();
        
        /* Search ComboBox */
        JLabel searchLable = new JLabel("Search Target");
        searchField = new JTextField(15);
        searchComboBox = new JComboBox<>();
        searchComboBox.addItem("Index");
        searchComboBox.addItem("Type");
        searchComboBox.addItem("Name");
        searchComboBox.addItem("Skill");
        
        /* Search Button */
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener((ActionEvent e)->search((String)(searchComboBox.getSelectedItem()), searchField.getText()));

        /* Action Panel */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10,25,0,25));
        JPanel actionPanel = new JPanel();
        actionPanel.setFont(new Font("Monospaced", 0, 12));
        actionPanel.add(readButton);
        actionPanel.add(displayButton);
        actionPanel.add(searchLable);
        actionPanel.add(searchField);
        actionPanel.add(searchComboBox);
        actionPanel.add(searchButton);
        actionPanel.add(sortTargetLabel);
        actionPanel.add(sortTargetComboBox);
        actionPanel.add(sortTypeLabel);
        actionPanel.add(sortTypeComboBox);
        actionPanel.add(sortButton);
        actionPanel.add(clearButton);
        topPanel.add(actionPanel,BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        /* Structure Panel */
        structurePanel = new JPanel(new BorderLayout());
        structurePanel.setFont(new Font("Monospaced", 0, 12));
        structurePanel.setPreferredSize(new Dimension(375, screenSize.height/2));
        structurePanel.setBorder(new EmptyBorder(10,25,25,25));
        add(structurePanel, BorderLayout.WEST);
        
        /* Job Table */
        workTablePanel = new JPanel(new BorderLayout());
        workTablePanel.setPreferredSize(new Dimension(800, screenSize.height / 2));
        workTablePanel.setBorder(new EmptyBorder(10, 0, 25, 25));
        add(workTablePanel, BorderLayout.EAST);
        
        validate();
    }
    
    /***************************************************************************
     * Search helper method called when user hits search button.  Uses the
     * combobox input to determine what the target of the search is.  dumps 
     * results into the console window.
     **************************************************************************/
    private void search(String searchType, String searchTarget) {
        console.append(">>> You pressed the \"Search\" button!\n");
        if(scanner == null) {
            displayStructure();
        }
        if(searchTarget.equals("")) {
            console.append(">>> Please try again!\n");
            return;
        }
        console.append(">>> You selected the following \"Type\": [" + searchType + "], and are searching for, [" + searchTarget + "]\n\n");
        ArrayList<Thing> searchResults = new ArrayList<>();
        ArrayList<String> skillSearchResults = new ArrayList<>();
         switch(searchType) {
            case "Index":
                try {
                    int requestedIndex = Integer.parseInt(searchTarget);
                    searchResults.add(structureMap.get(requestedIndex));
                }
                catch(NumberFormatException e) {
                    console.append(">>> Invalid \"Index\" input, please try again!");
                }
                break;
            case "Type":
                try {
                    searchResults = world.searchByType(searchTarget);
                    if("SKILL".equals(searchTarget.toUpperCase())){
                        for(Thing thing : searchResults) {
                            if(thing instanceof Person) {
                                if(((Person)thing).getSkill() != null && !skillSearchResults.contains(((Person)thing).getSkill())){
                                    skillSearchResults.add(((Person)thing).getSkill());
                                }
                            }
                        }
                    }
                    else {
                    if(searchResults == null) {
                            console.append(">>> Type not found!\n");
                            return;
                        }
                    }
                } catch (NullPointerException e) {
                    console.append(">>> Invalid \"Type\" input, please try again!");
                }
                break;
            case "Name":
                try {
                searchResults = world.searchByName(searchTarget);
                if(searchResults.size() <= 0) {
                        console.append(">>> Name not found!\n");
                        return;
                    }
                } catch (NullPointerException e) {
                    console.append(">>> Invalid \"Name\" input, please try again!");
                }
                break;
            case "Skill":
                try {
                    searchResults = world.findSkill(searchTarget);
                    if(searchResults.size() <= 0) {
                        console.append(">>> Skill not found!\n");
                        return;
                    }
                } catch (NullPointerException e) {
                    console.append(">>> Invalid \"Skill\" input, please try again!");
                }
                break;
        }
        if(searchResults.size() > 0 && !"SKILL".equals(searchTarget.toUpperCase())) {
            for(Thing thing : searchResults) {
                if(thing != null) {
                    console.append(thing + "\n");
                }
                else {
                    console.append("Your search returned ZERO results.\n");
                }
            }
        }
        else if(skillSearchResults.size() > 0 && "SKILL".equals(searchTarget.toUpperCase())) {
            for(String string : skillSearchResults) {
                console.append(string+"\n");
            }
        }
    }
    
    /***************************************************************************
     * Simple method used to change the contents of the sortTypeComboBox based 
     * which target selected. 
    ***************************************************************************/
    private void updateSortTypeComboBox(){
        sortTypeComboBox.removeAllItems();
        if(sortTargetComboBox.getSelectedItem().equals("Queue")){
            sortTypeComboBox.addItem("Weight");
            sortTypeComboBox.addItem("Width");
            sortTypeComboBox.addItem("Length");
            sortTypeComboBox.addItem("Draft");
            sortTypeComboBox.addItem("Queued Ship Name");
        }
        else if(sortTargetComboBox.getSelectedItem().equals("Cargo Ships")) {
            sortTypeComboBox.addItem("Cargo Weight");
            sortTypeComboBox.addItem("Cargo Volume");
            sortTypeComboBox.addItem("Cargo Value");
            sortTypeComboBox.addItem("Cargo Ship Name");
        }
        else if(sortTargetComboBox.getSelectedItem().equals("Passenger Ships")) {
            sortTypeComboBox.addItem("Passengers");
            sortTypeComboBox.addItem("Rooms");
            sortTypeComboBox.addItem("Occupied");
            sortTypeComboBox.addItem("Passenger Ship Name");
        }
        else {
            sortTypeComboBox.addItem("Name");
        }
        validate();
    }
    
    /***************************************************************************
     * simple helper method used to determine the target of the sort and what 
     * type of sort to do. 
    ***************************************************************************/
    private void sort(){
        console.append("\nYou pressed the \"Sort\" Button\n");
        if(scanner == null) {
            displayStructure();
        }
        String sortType = sortTypeComboBox.getSelectedItem().toString();
        String sortTarget = sortTargetComboBox.getSelectedItem().toString();
        sortThings(sortTarget, sortType);
    }
    
    /***************************************************************************
     * method called by sort() and is passed via sort() the target of the sort 
     * and the type of sort to do.  Using Collections sort method an ArrayList of 
     * objects and the Comparator generated by the Thing class implementation of 
     * Comparator interface and its compare() method are passed.  Sorting then 
     * happens on the original ArrayList of objects. 
    ***************************************************************************/
    private void sortThings(String target, String sortBy) {
        ArrayList<?> things = new ArrayList<>();
        try {
            switch (target) {
                case "World":
                    sortThings("Ports", sortBy);
                    sortThings("Piers", sortBy);
                    sortThings("Ships", sortBy);
                    sortThings("Jobs", sortBy);
                    sortThings("People", sortBy);
                    return;
                case "Ports":
                    ArrayList<SeaPort> ports = world.getPorts();
                    Collections.sort(ports, new Thing(sortBy));
                    world.setPorts(ports);
                    things = world.getPorts();
                    console.append(String.format("\nKeith's SeaPorts sorted by Name:\n"));
                    for(Object obj : things) {
                        console.append(String.format("Port %s\n", ((SeaPort)obj).getName()));
                    }
                    break;
                default:
                    for(SeaPort port : world.getPorts()) {
                        switch(target) {
                            case "Queue":
                                Collections.sort(port.getQueue(), new Thing(sortBy));
                                things = port.getQueue();
                                console.append(String.format("\n%s's Queued Ship's sorted by %s:\n", port.getName(), sortBy));
                                switch(sortBy){
                                    case "Queued Ship Name":
                                        for(Object obj : things) {
                                            console.append(String.format("SS %s\n", ((Ship)obj).getName()));
                                        }
                                        break;
                                    case "Draft":
                                        for(Object obj : things) {
                                            console.append(String.format("SS %s's %s: %.2f m\n", ((Ship)obj).getName(), sortBy, ((Ship)obj).getDraft()));
                                        }
                                        break;
                                    case "Length":
                                        for(Object obj : things) {
                                            console.append(String.format("SS %s's %s: %.2f m\n", ((Ship)obj).getName(), sortBy, ((Ship)obj).getLength()));
                                        }
                                        break;
                                    case "Weight":
                                        for(Object obj : things) {
                                            console.append(String.format("SS %s's %s: %.2f MT\n", ((Ship)obj).getName(), sortBy, ((Ship)obj).getWeight()));
                                        }
                                        break;
                                    case "Width":
                                        for(Object obj : things) {
                                            console.append(String.format("SS %s's %s: %.2f m\n", ((Ship)obj).getName(), sortBy, ((Ship)obj).getWidth()));
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case "Piers":
                                Collections.sort(port.getDocks(), new Thing(sortBy));
                                things = port.getDocks();
                                console.append(String.format("\nPort %s's %s sorted by %s:\n", port.getName(), target, sortBy));
                                for(Object obj : things) {
                                    console.append(String.format("%s\n", ((Dock)obj).getName()));
                                }
                                break;
                            case "Ships":
                                Collections.sort(port.getShips(), new Thing(sortBy));
                                things = port.getShips();
                                console.append(String.format("\nPort %s's %s sorted by %s:\n", port.getName(), target, sortBy));
                                for(Object obj : things) {
                                    console.append(String.format("%s\n", ((Ship)obj).getName()));
                                }
                                break;
                            case "Cargo Ships":
                                Collections.sort(port.getShips(), new Thing(sortBy));
                                things = port.getShips();
                                console.append(String.format("\nPort %s's %s sorted by %s:\n",  port.getName(), target, sortBy));
                                switch(sortBy) {
                                    case "Cargo Ship Name":
                                        for(Object obj : things) {
                                            if(obj instanceof CargoShip) {
                                                console.append(String.format("SS %s\n", ((CargoShip)obj).getName()));
                                            }
                                        }
                                        break;
                                    case "Cargo Weight":
                                        for(Object obj : things) {
                                            if(obj instanceof CargoShip) {
                                                console.append(String.format("Cargo Ship %s's %s: %.2f MT\n", ((CargoShip)obj).getName(), sortBy, ((CargoShip)obj).getCargoWeight()));
                                            }
                                        }
                                        break;
                                    case "Cargo Volume":
                                        for(Object obj : things) {
                                            if(obj instanceof CargoShip) {
                                                console.append(String.format("Cargo Ship %s's %s: %.2f m^3\n", ((CargoShip)obj).getName(), sortBy, ((CargoShip)obj).getCargoVolume()));
                                            }
                                        }
                                        break;
                                    case "Cargo Value":
                                        for(Object obj : things) {
                                            if(obj instanceof CargoShip) {
                                                console.append(String.format("Cargo Ship %s's %s: $%.2f million\n", ((CargoShip)obj).getName(), sortBy, ((CargoShip)obj).getCargoValue()));
                                            }
                                        } 
                                        break;
                                    default:
                                        break;
                                }   
                                break;                       
                            case "Passenger Ships":
                                Collections.sort(port.getShips(), new Thing(sortBy));
                                things = port.getShips();
                                console.append(String.format("\nPort %s's %s sorted by %s:\n",  port.getName(), target, sortBy));
                                switch(sortBy) {
                                    case "Passenger Ship Name":
                                        for(Object obj : things) {
                                            if(obj instanceof PassengerShip) {
                                                console.append(String.format("SS %s\n", ((PassengerShip)obj).getName()));
                                            }
                                        }
                                        break;
                                    case "Passengers":
                                        for(Object obj : things) {
                                            if(obj instanceof PassengerShip) {
                                                console.append(String.format("Passenger Ship %s's %s: %d people\n", ((PassengerShip)obj).getName(), sortBy, ((PassengerShip)obj).getNumberOfPassengers()));
                                            }
                                        }
                                        break;
                                    case "Rooms":
                                        for(Object obj : things) {
                                            if(obj instanceof PassengerShip) {
                                                console.append(String.format("Cargo Ship %s's %s: %d rooms\n", ((PassengerShip)obj).getName(), sortBy, ((PassengerShip)obj).getNumberOfRooms()));
                                            }
                                        }
                                        break;
                                    case "Occupied":
                                        for(Object obj : things) {
                                            if(obj instanceof PassengerShip) {
                                                console.append(String.format("Passenger Ship %s's has %d rooms %s\n",((PassengerShip)obj).getName(), ((PassengerShip)obj).getNumberOfOccupiedRooms(), sortBy));
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }   
                                break;
                            case "Jobs":
                                for(Ship ship : port.getShips()) {
                                    Collections.sort(ship.getJobs(), new Thing(sortBy));
                                    things = ship.getJobs();
                                    console.append(String.format("\nSS %s's %s sorted by %s:\n",  ship.getName(), target, sortBy));
                                    for(Object obj : things) {
                                        console.append(String.format("%s\n", ((Job)obj).getName()));
                                    }
                                }
                                break;
                            case "People":
                                Collections.sort(port.getPersons(), new Thing(sortBy));
                                things = port.getPersons();
                                console.append(String.format("\nPort %s's %s sorted by %s:\n", port.getName(), target, sortBy));
                                for(Object obj : things) {
                                    console.append(String.format("%s\n", ((Person)obj).getName()));
                                }
                                break;
                            default:
                                break;
                        }
                    }   
                    break;
            }
        } catch (IllegalArgumentException e) {
            //console.append("ERROR! " + e + "in sortThings() \n");
            System.out.println(e);
        }
    }
    
    /***************************************************************************
     * Simple method used to display the world structure in the left most pane
     * of the gui.
    ***************************************************************************/
    private void drawStructure() {
        root = new JTree(createBranch("Root"));
        JScrollPane structurePane = new JScrollPane(root);
        JButton structureCollapseButton = new JButton("Collaspe");
        JButton structureExpandButton = new JButton("Expand");
        JPanel structureButtonPanel = new JPanel();
        structureButtonPanel.setBorder(new EmptyBorder(10,0,0,0));
        structureButtonPanel.add(structureCollapseButton);
        structureButtonPanel.add(structureExpandButton);
        structureCollapseButton.addActionListener(e -> collapseStructure());
        structureExpandButton.addActionListener(e -> expandStructure());
        structurePanel.add(structureButtonPanel, BorderLayout.SOUTH);
        structurePanel.add(structurePane, BorderLayout.CENTER);
        
        //added functionality to search for node clicked in sturcuture pane
        root.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        root.getSelectionModel().addTreeSelectionListener((TreeSelectionEvent e) -> {
            String selected = e.getPath().toString();
            String[] bits = selected.split(" ");
            selected = bits[bits.length-1].replace("]", "");
            search("Name", selected);
        });
        validate();
    }
    
    /***************************************************************************
     * Method used to display the structure of the simulation world.  Calls
     * the buildStructure method to do the work of populating the HashMap
     * and the drawStructure method to display it in the left most pane of the
     * GUI.
     **************************************************************************/ 
    private void displayStructure(){
        if(scanner == null){
            console.append(">>> File not loaded. Loading file before display.\n");
            readSimulation();
        }
        console.append(">>> Display World Button pressed.\n");
        drawWorkTable();
        buildStructure(scanner);
        drawStructure();
        ready = true;
    }
    
    /***************************************************************************
     * Helper method to build world structure, using basic tree, branch, 
     * leaf(node) structure.  This method adds a branch for each thing of a type.
    ***************************************************************************/
    private synchronized DefaultMutableTreeNode createBranch(String title) {
        DefaultMutableTreeNode branch = new DefaultMutableTreeNode(title);
        DefaultMutableTreeNode rootBranch;
        DefaultMutableTreeNode jobBranch;
        DefaultMutableTreeNode shipBranch;
        DefaultMutableTreeNode shipsBranch;
        DefaultMutableTreeNode pierBranch;
        DefaultMutableTreeNode dockBranch;
        DefaultMutableTreeNode skillsBranch;
        DefaultMutableTreeNode personBranch;
        DefaultMutableTreeNode peopleBranch;
        DefaultMutableTreeNode jobRequiredSkillsBranch;
        DefaultMutableTreeNode workOrderBranch;
        DefaultMutableTreeNode portTimeBranch;
        DefaultMutableTreeNode arrivalTimeBranch;
        DefaultMutableTreeNode dockTimeBranch;
        for(SeaPort port : world.getPorts()) {
            try {
                rootBranch = new DefaultMutableTreeNode(port.getName());
                branch.add(rootBranch);
            if(port.getDocks() != null) {
                dockBranch = new DefaultMutableTreeNode("Docks");
                rootBranch.add(dockBranch);
                for(Dock dock : port.getDocks()) {
                    if(dock.getShip() != null) {
                        pierBranch = new DefaultMutableTreeNode(dock.getName());
                        dockBranch.add(pierBranch);
                        shipBranch = new DefaultMutableTreeNode(dock.getShip().getName());
                        pierBranch.add(shipBranch);
                    }
                }
            }
            if(port.getQueue() != null) {
                rootBranch.add(addNode(port.getQueue(), "Queue"));
            }
            if(port.getShips() != null) {
                shipsBranch = new DefaultMutableTreeNode("Ships");
                rootBranch.add(shipsBranch);
                for(Ship ship : port.getShips()) {
                    if(ship.getJobs().size() > 0) {
                        shipBranch = new DefaultMutableTreeNode(ship.getName());
                        shipsBranch.add(shipBranch);
                        shipBranch.add(addNode(ship.getJobs(), "Jobs"));
                    }
                    else if(ship.getJobs().size() <= 0) {
                        shipBranch = new DefaultMutableTreeNode(ship.getName());
                        shipsBranch.add(shipBranch);
                        jobBranch = new DefaultMutableTreeNode("Jobs");
                        shipBranch.add(jobBranch);
                        workOrderBranch = new DefaultMutableTreeNode("No Workorders!");
                        jobBranch.add(workOrderBranch);
                    }
                    else{
                        rootBranch.add(addNode(port.getShips(), "Ships"));
                    }
                }
                ArrayList<CargoShip> cShips = new ArrayList<>();
                for(Ship ship : port.getShips()) {
                    if(ship instanceof CargoShip) {
                        cShips.add(((CargoShip)ship));
                    }
                }
                rootBranch.add(addNode(cShips, "Cargo Ships"));
                ArrayList<PassengerShip> pShips = new ArrayList<>();
                for(Ship ship : port.getShips()) {
                    if(ship instanceof PassengerShip) {
                        pShips.add(((PassengerShip)ship));
                    }
                }
                rootBranch.add(addNode(pShips, "Passenger Ships"));
            }
            if(port.getPersons() != null) {
                peopleBranch = new DefaultMutableTreeNode("People");
                rootBranch.add(peopleBranch);
                for(Person person : port.getPersons()) {
                    if(person.getSkill() != null) {
                        personBranch = new DefaultMutableTreeNode(person.getName());
                        peopleBranch.add(personBranch);
                        skillsBranch = new DefaultMutableTreeNode(person.getSkill());
                        personBranch.add(skillsBranch);
                    }
                }
            }
            if(port.getShips() != null) {
                jobBranch = new DefaultMutableTreeNode("Jobs");
                rootBranch.add(jobBranch);
                for(Ship ship : port.getShips()){
                    if(ship.getJobs() != null) {
                        for(Job job : ship.getJobs()){
                            if(job.getRequirements().size() > 0) {
                                workOrderBranch = new DefaultMutableTreeNode(job.getName());
                                jobBranch.add(workOrderBranch);
                                for(String s : job.getRequirements()) {
                                    jobRequiredSkillsBranch = new DefaultMutableTreeNode(s);
                                    workOrderBranch.add(jobRequiredSkillsBranch);
                                }
                            } else {
                                workOrderBranch = new DefaultMutableTreeNode(job.getName());
                                jobBranch.add(workOrderBranch);
                                jobRequiredSkillsBranch = new DefaultMutableTreeNode("No Requirements!");
                                workOrderBranch.add(jobRequiredSkillsBranch);
                            }
                        }
                    }
                }
            }
            if(port.getShips() != null) {
                portTimeBranch = new DefaultMutableTreeNode("Port Time");
                rootBranch.add(portTimeBranch);
                for(Ship ship : port.getShips()){
                    if(ship.getArrivalTime() != null) {
                        shipBranch = new DefaultMutableTreeNode(ship.getName());
                        portTimeBranch.add(shipBranch);
                        arrivalTimeBranch = new DefaultMutableTreeNode("Arrived: " + ship.getArrivalTime());
                        shipBranch.add(arrivalTimeBranch);
                        dockTimeBranch = new DefaultMutableTreeNode("Docked: " + ship.getDockTime());
                        shipBranch.add(dockTimeBranch);
                        }
                    else {
                        shipBranch = new DefaultMutableTreeNode(ship.getName());
                        portTimeBranch.add(shipBranch);
                        arrivalTimeBranch = new DefaultMutableTreeNode("No Arrival Time!");
                        shipBranch.add(arrivalTimeBranch);
                        dockTimeBranch = new DefaultMutableTreeNode("No Dock Time!");
                        shipBranch.add(dockTimeBranch);
                    }
                }
            }            
            } catch(IllegalArgumentException e) {
                //console.append(">>> Error!" + e + "\n");
                System.out.println(e);
            }
        }
        return branch;
    }
    
    /***************************************************************************
     * Creates a branch for each Type of thing. 
    ***************************************************************************/ 
    private synchronized <T extends Thing> DefaultMutableTreeNode addNode(ArrayList<T> things, String name){
        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(name);
        try {
            for(Thing thing : things) { 
                synchronized(thing) {
                    String displayString = thing.getName();
                    if(thing instanceof Dock) {
                        if(((Dock)thing).getShip() != null) {
                            displayString = ((Dock)thing).getName();
                        }
                        else {
                            displayString = "Empty Berth";
                        }          
                    }
                    if(thing instanceof Ship) {
                        if(((Ship)thing).getDraft() != 0) {
                            displayString = ((Ship)thing).getName();
                        }
                        else {
                            displayString = "";
                        }
                    }
                    if(thing instanceof Person) {
                        if(((Person)thing).getSkill() != null) {
                            displayString = ((Person)thing).getName();
                        }
                    }
                    if(thing instanceof Job) {
                        if(((Job)thing).getName() != null) {
                            displayString = ((Job)thing).getName();
                        }
                    }
                    leaf.add(new DefaultMutableTreeNode(displayString));
                }
            } 
        } catch(ConcurrentModificationException e) {
                System.out.println(e);
        }
        return leaf;
    }
    
    /***************************************************************************
     * Collapses all nodes in JTree structure
     **************************************************************************/
    private void collapseStructure() {
        for(int i = 1; i < root.getRowCount()-1; i++) {
            root.collapseRow(i);
        }
    }
    
    /***************************************************************************
     * Expands all nodes in JTree structure
     **************************************************************************/
    private void expandStructure() {
        for(int i = 0; i <= root.getRowCount()-1; i++) {
            root.expandRow(i);
        }
    }
    
    /***************************************************************************
     * After the initial ingestion of the simulation file in my buildStructure 
     * method, this method monitors a ships' work on jobs.  When all of a ships 
     * Jobs are complete, the ship will undock from its pier and another ship in
     * the Queue will be allowed to dock and begin work on its Jobs.  I started 
     * to use two global "Flags", ready and running, and one internal "Flag", 
     * workComplete to avoid having "Forever" or infinite loops.
    ***************************************************************************/
    public void monitorWork() {   
        if(ready){
            for(SeaPort port : world.getPorts()) {
                for(Dock dock : port.getDocks()) {
                    synchronized(dock) {
                        boolean workComplete = true;
                        if(dock.getShip() == null){
                            continue;
                        }
                        for(Job job : dock.getShip().getJobs()) {
                            if(dock.getShip().getJobs().isEmpty()) {
                                workComplete = true;
                            }
                            if(!job.finished()) {
                                workComplete = false;
                            }
                        }
                        if(workComplete) {
                            workConsole.append(String.format(">>> SHIP DEPARTING: SS %s leaving %s at Port of %s\n", dock.getShip().getName(), dock.getName(), port.getName()));
                            for(Job job : dock.getShip().getJobs()) {
                                if(job.finished()) {
                                    workConsole.append(String.format(">>> JOB DONE: Work order %s finished on SS %s at %s in Port of %s\n", job.getName(), dock.getShip().getName(), dock.getName(), port.getName()));
                                }
                                job.endWork();
                                workTableModel.remove(job.getName());
                                workTable.validate();
                            }
                            dock.setShip(null);
                            if(port.getQueue().isEmpty()){
                                return; 
                            }
                            else {
                                dock.setShip(port.getQueue().remove(0));
                                workConsole.append(String.format(">>> SHIP DOCKING: SS %s docking in %s at Port of %s\n", dock.getShip().getName(), dock.getName(), port.getName()));
                                for(Job job : dock.getShip().getJobs()) {
                                    job.displayWork(workButtonPanel);
                                    dock.getShip().setParent(dock.getIndex());
                                    workTableModel.add(dock.getShip(), structureMap, job);
                                    workTable.validate();
                                    job.startWork();
                                }
                            }
                        }
                        if(workTableModel.getRowCount() > 0) {
                            workButtonPanel.setLayout(new GridLayout(workTableModel.getRowCount(), 3, 3, 3));
                            workButtonPanel.setPreferredSize(new Dimension(400, workTableModel.getRowCount() * 25)); 
                        }
                    }
                }
            } 
        }  
    }
    
    /***************************************************************************
     * Simple method used to display the work table in the right most pane
     * of the gui.
    ***************************************************************************/
    private void drawWorkTable(){
        String[] header = {"Ship", "Location", "Work Order", "Requirements"};
        workTableModel = new JobTableTemplate(header);
        workTable = new JTable(workTableModel);
        workTable.setRowHeight(35);
        JPanel tablePanel = new JPanel(new BorderLayout());
        workButtonPanel = new JPanel();
        tablePanel.add(workTable, BorderLayout.CENTER);
        tablePanel.add(workButtonPanel, BorderLayout.EAST);
        tablePanel.add(workTable.getTableHeader(), BorderLayout.NORTH);
        JScrollPane tableScroll = new JScrollPane(tablePanel);
        workTablePanel.add(tableScroll);
        validate();
    }
    
    /***************************************************************************
     * Simple helper method that clears console textArea
    ***************************************************************************/
    public void clearConsole() {
        console.setText(null);
    }
}