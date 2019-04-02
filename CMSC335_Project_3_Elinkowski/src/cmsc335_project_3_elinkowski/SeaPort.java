package cmsc335_project_3_elinkowski;

import java.util.ArrayList;
import java.util.Scanner;

/*******************************************************************************
 * File name: SeaPort class
 * Date: 20181025 1025L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class.  Holds lists for all docks, ships, and people 
 * of a specific port.  Also holds a queue list of ships waiting to dock at a 
 * specific port.
*******************************************************************************/

public class SeaPort extends Thing{
    private ArrayList<Dock> docks;        
    private ArrayList<Ship> queues;       
    private ArrayList<Ship> ships;        
    private ArrayList<Person> people;  
    
    /***************************************************************************
     * SeaPort Constructor
     * @param scanner
    ***************************************************************************/
    public SeaPort(Scanner scanner) {
        super(scanner);
        docks = new ArrayList<>();
        queues = new ArrayList<>();
        ships = new ArrayList<>();
        people = new ArrayList<>();
    }
    
    /***************************************************************************
     * Getter for docks
    ***************************************************************************/
    public ArrayList<Dock> getDocks() {
        return docks;
    }
    
    /***************************************************************************
     * Setter for docks
     * @param dock
    ***************************************************************************/
    public void setDocks(ArrayList<Dock> dock) {
        docks = dock;
    }
    
    /***************************************************************************
     * Getter for queue
    ***************************************************************************/
    public ArrayList<Ship> getQueue(){
        return queues;
    }
    
    /***************************************************************************
     * Setter for queue
     * @param queue
    ***************************************************************************/
    public void setQueue(ArrayList<Ship> queue) {
        queues = queue;
    }
    
    /***************************************************************************
     * Getter for ships
    ***************************************************************************/
    public ArrayList<Ship> getShips() {
        return ships;
    }
    
    /***************************************************************************
     * Setter for ships
     * @param ship
    ***************************************************************************/
    public void setShips(ArrayList<Ship> ship) {
        ships = ship;
    }
    
    /***************************************************************************
     * Getter for people
    ***************************************************************************/
    public ArrayList<Person> getPersons() {
        return people;
    }
    
    /***************************************************************************
     * Setter for people
     * @param person
    ***************************************************************************/
    public void setPersons(ArrayList<Person> person) {
        people = person;
    }
    
    /***************************************************************************
     * @Override for toString() Method
    ***************************************************************************/
    @Override
    public String toString() {
        String outPorts = "<<<------------Port-------------->>>\n";
        String outDocks = "<<<------------Piers------------>>>\n";
        String outQueue = "<<<------------Queue------------>>>\n";
        String outShips = "<<<------------Ships------------>>>\n";
        String outPersons = "<<<------------People------------>>>\n";
        for(Dock dock : docks) {
            outDocks += String.format(">>> %s\n", dock.toString());
        }
        
        for(Ship queue : queues) {
            outQueue += String.format(">>> %s\n", queue.toString());
        }
        
        for(Ship ship : ships) {
            outShips += String.format(">>> %s\n", ship.toString());
        }
        
        for(Person person : people) {
            outPersons += String.format(">>> %s\n", person.toString());
        }
        outPorts += String.format(">>> %s\n", super.toString());
        return outPorts + outDocks + outQueue + outShips + outPersons;
    }
}