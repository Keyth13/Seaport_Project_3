package cmsc335_project_3_elinkowski;

import java.util.ArrayList;
import java.util.Scanner;

/*******************************************************************************
 * File name: World class
 * Date: 20181025 1009L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class. Primary class that organizes all other Thing 
 * objects. For this program, this class acts as the world for the scanned 
 * simulation file. It accepts Scanner input data and assigns it.  It assembles 
 * instances of the programs classes and moves it to the proper ArrayList. This 
 * class also contains code for the search functionality.
*******************************************************************************/
public class World extends Thing {
    private ArrayList<SeaPort> ports;
    private final PortTime time = new PortTime();
    
    /***************************************************************************
     * World Constructor
     * @param scanner
    ***************************************************************************/
    public World(Scanner scanner) {
        super(scanner);
        ports = new ArrayList<>();
        setPortTime();
    }
   
    /***************************************************************************
     * Getter for ports
    ***************************************************************************/
    public ArrayList<SeaPort> getPorts() {
        return ports;
    }
    
    /***************************************************************************
     * Setter for ports
     * @param p
    ***************************************************************************/    
    public void setPorts(ArrayList<SeaPort> p) {
        ports = p;
    }
    
    /***************************************************************************
     * Getter for time
    ***************************************************************************/
    public PortTime getPortTime() {
        return time;
    }
    
    /***************************************************************************
     * Setter for time
    ***************************************************************************/
    public void setPortTime() {
        this.time.setPortTime();
    }
    
    /***************************************************************************
     * Methods used to assign Things to their proper port.  If the given
     * thing has a parent index that matches its super class, it will be 
     * added to the proper list 
     * @param port
    ***************************************************************************/
    public void assignSeaPort(SeaPort port) {
        ports.add(port);
    }
    
    /***************************************************************************
     * assignDock Method
     * @param dock
     * @param port
    ***************************************************************************/
    public void assignDock(Dock dock, SeaPort port) {
        port.getDocks().add(dock);
    }
    
    /***************************************************************************
     * assignShip Method
     * @param ship
     * @param port
     * @param dock
    ***************************************************************************/
    public void assignShip(Ship ship, SeaPort port, Dock dock) {
        port.getShips().add(ship);
        if(dock != null) {
            dock.setShip(ship);
        }
        else {
            port.getQueue().add(ship);
        }
    }
    
    /***************************************************************************
     * assignPerson Method
     * @param person
     * @param port
    ***************************************************************************/
    public void assignPerson(Person person, SeaPort port) {
        port.getPersons().add(person);
    }
    
    /***************************************************************************
     * assignJob Method
     * @param thing
    ***************************************************************************/
    public void assignJob(Job job, Thing thing) {
        if(thing instanceof Ship) {
            ((Ship)thing).getJobs().add(job);
        }
        else {
            ((Dock)thing).getShip().getJobs().add(job);
            job.setParent(((Dock)thing).getShip().getIndex());
        }
    }
    
    /***************************************************************************
     * Search method to verify user selected Type and finds all instances of the 
     * requested Type 
     * @param requestedType
    ***************************************************************************/
    public ArrayList<Thing> searchByType(String requestedType) {
        ArrayList<Thing> searchByTypeResults = new ArrayList<>();
        String searchType = requestedType.toUpperCase().replace(" ", "");
        for(SeaPort port : ports) {
            switch(searchType) {
                case "SEAPORT":
                    searchByTypeResults.add(port);
                    break;
                case "PORT":
                    searchByTypeResults.add(port);
                    break;
                case "DOCK":
                    for(Dock dock : port.getDocks()) {
                        searchByTypeResults.add(dock);
                    }
                    break;
                case "PIER":
                    for(Dock dock : port.getDocks()) {
                        searchByTypeResults.add(dock);
                    }
                    break;
                case "PERSON":
                    for(Person person : port.getPersons()) {
                        searchByTypeResults.add(person);
                    }
                    break;
                case "PEOPLE":
                    for(Person person : port.getPersons()) {
                        searchByTypeResults.add(person);
                    }
                    break;
                case "SHIP":
                    for(Ship ship : port.getShips()) {
                        searchByTypeResults.add(ship);
                    }
                    break;
                case "CSHIP":
                    for(Ship ship : port.getShips()) {
                        if(ship instanceof CargoShip) {
                            searchByTypeResults.add(ship);
                        }
                    }
                    break;
                case "CARGOSHIP":
                    for(Ship ship : port.getShips()) {
                        if(ship instanceof CargoShip) {
                            searchByTypeResults.add(ship);
                        }
                    }
                    break;
                case "PSHIP":
                    for(Ship ship : port.getShips()) {
                        if(ship instanceof PassengerShip) {
                            searchByTypeResults.add(ship);
                        }
                    }
                    break;
                case "PASSENGERSHIP":
                    for(Ship ship : port.getShips()) {
                        if(ship instanceof PassengerShip) {
                            searchByTypeResults.add(ship);
                        }
                    }
                    break;
                case "JOB":
                    for(Ship ship : port.getShips()) {
                        for(Job job : ship.getJobs()) {
                            searchByTypeResults.add(job);
                        }
                    }
                    break;
                case "SKILL":
                    for(Person person : port.getPersons()) {
                        searchByTypeResults.add(person);
                    }
                    break;
                default:
                    break;
            }
        }
        return searchByTypeResults;
    }
   
    /***************************************************************************
     * Search method that looks through each list for each Type.  All 
     * found matching names are then stored in an Arraylist and returned to 
     * the console
     * @param name
    ***************************************************************************/
    public ArrayList<Thing> searchByName(String name) {
        ArrayList<Thing> searchResults = new ArrayList<>();
        for(SeaPort port : ports) {
            if(port.getName().equalsIgnoreCase(name)) {
                    searchResults.add(port);
            }
            if(name != null){
                try {
                    searchResults.addAll(findName(port.getDocks(), name));
                } catch (NullPointerException e) {
                    return null;
                }
                try {
                    searchResults.addAll(findName(port.getShips(), name));
                } catch (NullPointerException e) {
                    return null;
                }
                try {
                    searchResults.addAll(findName(port.getPersons(), name));
                } catch (NullPointerException e) {
                    return null;
                }
            }
        }
        return searchResults;
    }
            
    /***************************************************************************
     * Helper method to find all instances of user requested Name.  
     * Returns an ArrayList of all Things that match the User requested Name.
    ***************************************************************************/
    private<T extends Thing> ArrayList<Thing> findName(ArrayList<T> list, String name) {
        ArrayList<Thing> findResults = new ArrayList<>();
        for(T thing : list) {
            if(thing.getName().equalsIgnoreCase(name)) {
                findResults.add(thing);
            }
        }
        return findResults;
    }
    
    /***************************************************************************
     * Helper method to find all People who have the user requested Skill. 
     * Returns an ArrayList of all People with the user requested Skill.
     * @param skill
    ***************************************************************************/
    public ArrayList<Thing> findSkill(String skill) {
        ArrayList<Thing> skillResults = new ArrayList<>();
        for(SeaPort port : ports) {
            for(Person person : port.getPersons()){
                if(person.getSkill().equalsIgnoreCase(skill)){
                    skillResults.add(person);
                }
            }
        }
        return skillResults;
    }
       
    /***************************************************************************
     * toString method that builds a list of all ports in Simulation world.
    ***************************************************************************/
    @Override
    public String toString() {
        String outWorld = "Welcome to my World! >>> \n";
        for(SeaPort port : ports) {
            outWorld += port;
        }
        return outWorld;
    }
}
