package cmsc335_project_3_elinkowski;

import java.util.Comparator;
import java.util.Scanner;

/*******************************************************************************
 * File name: Thing class
 * Date: 20181024 1436L
 * Author: Keith R. Elinkowski
 * Purpose: Implements Comparable. This class is the base class for all other 
 * objects.
*******************************************************************************/
public class Thing implements Comparator<Thing> {
    private int index;
    private String name;
    private int parent;
    private String attribute;
    
    /***************************************************************************
     * Thing Constructor
     * @param scanner
    ***************************************************************************/
    public Thing(Scanner scanner) {
        if(scanner.hasNext()) {
            name = scanner.next();
        }
        else {
            name = "Error!";
        }
        if(scanner.hasNextInt()) {
            index = scanner.nextInt();
        }
        else {
            index = 0;
        }
        if(scanner.hasNextInt()) {
            parent = scanner.nextInt();
        }
        else {
            parent = 0;
        }
    }
    
    /***************************************************************************
     * Thing Constructor
     * @param attribute
    ***************************************************************************/
    public Thing(String attribute) {
        this.attribute = attribute.toUpperCase();
    }
    
    /***************************************************************************
     * Getter for index
    ***************************************************************************/
    public int getIndex() {
        return index;
    }
    
    /***************************************************************************
     * Setter for Index
     * @param i
    ***************************************************************************/
    public void setIndex(int i) {
        if(i > 0) {
            index = i;
        }
    }
    
    /***************************************************************************
     * Getter for name
    ***************************************************************************/
    public String getName() {
        return name;
    }
    
    /***************************************************************************
     * Setter for Name
     * @param n
    ***************************************************************************/
    public void setName(String n) {
        name = n;
    }
    
    /***************************************************************************
     * Getter for Parent
    ***************************************************************************/
    public int getParent() {
        return parent;
    }
    
    /***************************************************************************
     * Setter for Parent
     * @param p
    ***************************************************************************/
    public void setParent(int p) {
        if(p > 0) {
            parent = p;
        }
    }
    
    /***************************************************************************
     * @param thing1
     * @param thing2
     * @Override compare Method
    ***************************************************************************/
    @Override
    public int compare(Thing thing1, Thing thing2) {
        switch(attribute) {
            case "WEIGHT":
                if(((Ship)thing1).getWeight() == ((Ship)thing2).getWeight()){
                    return 0;
                }
                else if(((Ship)thing1).getWeight() > ((Ship)thing2).getWeight()){
                    return 1;
                }
                else {
                    return -1;
                }
            case "LENGTH":
                if(((Ship)thing1).getLength() == ((Ship)thing2).getLength()){
                    return 0;
                }
                else if(((Ship)thing1).getLength() > ((Ship)thing2).getLength()){
                    return 1;
                }
                else {
                    return -1;
                }
            case "DRAFT":
                if(((Ship)thing1).getDraft() == ((Ship)thing2).getDraft()){
                    return 0;
                }
                else if(((Ship)thing1).getDraft() > ((Ship)thing2).getDraft()){
                    return 1;
                }
                else {
                    return -1;
                }
            case "WIDTH":
                if(((Ship)thing1).getWidth() == ((Ship)thing2).getWidth()){
                    return 0;
                }
                else if(((Ship)thing1).getWidth() > ((Ship)thing2).getWidth()){
                    return 1;
                }
                else {
                    return -1;
                }
            case "CARGO WEIGHT":
                if((thing1 instanceof CargoShip || thing2 instanceof CargoShip)&& (thing1 != null && thing2 != null)){
                    try {
                        if(((CargoShip)thing1).getCargoWeight() == ((CargoShip)thing2).getCargoWeight()) {
                            return 0;
                        }
                        else if(((CargoShip)thing1).getCargoWeight() > ((CargoShip)thing2).getCargoWeight()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                else {
                    break;
                }
            case "CARGO VOLUME":
                if((thing1 instanceof CargoShip || thing2 instanceof CargoShip)&& (thing1 != null && thing2 != null)){
                    try {
                        if(((CargoShip)thing1).getCargoVolume() == ((CargoShip)thing2).getCargoVolume()) {
                            return 0;
                        }
                        else if(((CargoShip)thing1).getCargoVolume() > ((CargoShip)thing2).getCargoVolume()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    } catch (ClassCastException e) {
                        break;
                    }  
                }
                else {
                    break;
                }
            case "CARGO VALUE":
                if((thing1 instanceof CargoShip || thing2 instanceof CargoShip)&& (thing1 != null && thing2 != null)){
                    try {
                        if(((CargoShip)thing1).getCargoValue() == ((CargoShip)thing2).getCargoValue()) {
                            return 0;
                        }
                        else if(((CargoShip)thing1).getCargoValue() > ((CargoShip)thing2).getCargoValue()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                else {
                    break;
                }
            case "PASSENGERS":
                if((thing1 instanceof PassengerShip || thing2 instanceof PassengerShip)&& (thing1 != null && thing2 != null)){
                    try {
                        if(((PassengerShip)thing1).getNumberOfPassengers() == ((PassengerShip)thing2).getNumberOfPassengers()) {
                            return 0;
                        }
                        else if(((PassengerShip)thing1).getNumberOfPassengers() > ((PassengerShip)thing2).getNumberOfPassengers()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                else {
                    break;
                }
            case "ROOMS":
                if((thing1 instanceof PassengerShip || thing2 instanceof PassengerShip)&& (thing1 != null && thing2 != null)){
                    try {
                        if(((PassengerShip)thing1).getNumberOfRooms() == ((PassengerShip)thing2).getNumberOfRooms()) {
                            return 0;
                        }
                        else if(((PassengerShip)thing1).getNumberOfRooms() > ((PassengerShip)thing2).getNumberOfRooms()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                else {
                    break;
                }
            case "OCCUPIED":
                if((thing1 instanceof PassengerShip || thing2 instanceof PassengerShip)&& (thing1 != null && thing2 != null)){
                    try {
                        if(((PassengerShip)thing1).getNumberOfOccupiedRooms() == ((PassengerShip)thing2).getNumberOfOccupiedRooms()) {
                            return 0;
                        }
                        else if(((PassengerShip)thing1).getNumberOfOccupiedRooms() > ((PassengerShip)thing2).getNumberOfOccupiedRooms()) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                else {
                    break;
                }
            case "NAME":
                return thing1.getName().compareTo(thing2.getName());
        }
        return -1337;
    }
    
    /***************************************************************************
     * @Override toString Method
    ***************************************************************************/
    @Override
    public String toString() {
        return String.format("%s (%d): ", name, index);
    }
}