package cmsc335_project_3_elinkowski;

import java.util.Scanner;

/*******************************************************************************
 * File name: CargoShip class
 * Date: 20181024 1436L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Ship class. Class is used to hold values for cargo.
******************************************************************************/
public class CargoShip extends Ship{
    private double cargoWeight;
    private double cargoVolume;
    private double cargoValue;
    
    /***************************************************************************   
    * CargoShip Constructor
     * @param scanner
    ***************************************************************************/
    public CargoShip(Scanner scanner) {
        super(scanner);
        if(scanner.hasNextDouble()) {
            cargoWeight = scanner.nextDouble();
        }
        else {
            cargoWeight = 0.0;
        }
        if(scanner.hasNextDouble()) {
            cargoVolume = scanner.nextDouble();
        }
        else {
            cargoVolume = 0.0;
        }
        if(scanner.hasNextDouble()) {
            cargoValue = scanner.nextDouble();
        }
        else {
            cargoValue = 0.0;
        }
    }
    
    /***************************************************************************   
    * Getter for cargoValue
    ***************************************************************************/    
    public double getCargoValue() {
        return cargoValue;
    }
    
    /***************************************************************************   
    * Setter for cargoValue
     * @param cValue
    ***************************************************************************/
    public void setCargoValue(double cValue) {
        if(cValue >= 0.0) {
            cargoValue = cValue;
        }
    }
    
    /***************************************************************************   
    * Getter for cargoVolume
    ***************************************************************************/
    public double getCargoVolume() {
        return cargoVolume;
    }
    
    /***************************************************************************   
    * Setter for cargoVolume
     * @param cVolume
    ***************************************************************************/
    public void setCargoVolume(double cVolume) {
        if(cVolume >= 0.0) {
            cargoVolume = cVolume;
        }
    }
    
    /***************************************************************************   
    * Getter for cargoWeight
    ***************************************************************************/
    public double getCargoWeight() {
        return cargoWeight;
    }
    
    /***************************************************************************   
    * Setter for cargoWeight
     * @param cWeight
    ***************************************************************************/
    public void setCargoWeight(double cWeight) {
        if(cWeight >= 0.0) {
            cargoWeight = cWeight;
        }
    }
    
    /***************************************************************************   
    * @Override for toString() Method
    ***************************************************************************/
    @Override
    public String toString() {
        String outCShip = String.format("%s ", super.toString());
        outCShip += String.format("Cargo Weight: %.2f MT, ", cargoWeight);
        outCShip += String.format("Cargo Volume: %.2f M^3, ", cargoVolume);
        outCShip += String.format("Cargo Value: $%.2f\n", cargoValue);
        return outCShip;
    }
}