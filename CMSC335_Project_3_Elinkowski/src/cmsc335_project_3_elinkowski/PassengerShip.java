package cmsc335_project_3_elinkowski;

import java.util.Scanner;

/*******************************************************************************
 * File name: PassengerShip class
 * Date: 20181024 1442L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Ship class. Class is used to hold values for passengers.
*******************************************************************************/
public class PassengerShip extends Ship{
    private int numberOfPassengers;
    private int numberOfRooms;
    private int numberOfOccupiedRooms;

    /***************************************************************************
     * PassengerShip Constructor
     * @param scanner
    ***************************************************************************/
    public PassengerShip(Scanner scanner) {
        super(scanner);
        if(scanner.hasNextInt()) {
            numberOfPassengers = scanner.nextInt();
        }
        else {
            numberOfPassengers = 0;
        }
        if(scanner.hasNextInt()) {
            numberOfRooms = scanner.nextInt();
        }
        else {
            numberOfRooms = 0;
        }
        if(scanner.hasNextInt()) {
            numberOfOccupiedRooms = scanner.nextInt();
        }
        else {
            numberOfOccupiedRooms = 0;
        }
    }
    
    /***************************************************************************
     * Getter for numberOfPassengers
    ***************************************************************************/
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
    
    /***************************************************************************
     * Setter for numberOfPassengers
     * @param passengers
    ***************************************************************************/
    public void setNumberOfPassengers(int passengers) {
        if(passengers >= 0) {
            numberOfPassengers = passengers;
        }
    }
    
    /***************************************************************************
     * Getter for numberOfRooms
    ***************************************************************************/
    public int getNumberOfRooms() {
        return numberOfRooms;
    }
    
    /***************************************************************************
     * Setter for numberOfRooms
     * @param rooms
    ***************************************************************************/
    public void setNumberOfRooms(int rooms) {
        if(rooms >= 0) {
            numberOfRooms = rooms;
        }
    }
    
    /***************************************************************************
     * Getter for numberOfOccupiedRooms
    ***************************************************************************/    
    public int getNumberOfOccupiedRooms() {
        return numberOfOccupiedRooms;
    }
    
    /***************************************************************************
     * Setter for numberOfOccupiedRooms
     * @param occupiedRooms
    ***************************************************************************/
    public void setNumberOfOccupiedRooms(int occupiedRooms) {
        if(occupiedRooms >= 0) {
            numberOfOccupiedRooms = occupiedRooms;
        }
    }

    /***************************************************************************
     * @Override for toString() Method
    ***************************************************************************/
    @Override
    public String toString() {
        String outCShip = String.format("%s ", super.toString());
        outCShip += String.format("PAX: %d, ", numberOfPassengers);
        outCShip += String.format("Rooms: %d, ", numberOfRooms);
        outCShip += String.format("Occupancy: %d of %d\n", (numberOfOccupiedRooms), (numberOfRooms));
        return outCShip;
    }
}