package cmsc335_project_3_elinkowski;

import java.util.Scanner;

/*******************************************************************************
 * File name: Dock class
 * Date: 20181025 1450L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class.  Specifies which ships are docked.
*******************************************************************************/
public class Dock extends Thing{
    private Ship ship;
    
    /***************************************************************************
     * Dock Constructor
     * @param scanner 
    ***************************************************************************/
    public Dock(Scanner scanner) {
        super(scanner);
    }
    
    /***************************************************************************
     * Getter for ship
    ***************************************************************************/
    public Ship getShip() {
        return ship;
    }
    
    /***************************************************************************
     * Setter for ship
     * @param s
    ***************************************************************************/
    public void setShip(Ship s) {
        ship = s;
    }
    
    /***************************************************************************
     * @Override of toString() Method
    ***************************************************************************/
    @Override
    public String toString() {
        String outDock = String.format("%s ", super.toString());
        if(ship != null) {
            outDock += ship.toString();
        }
        else {
            outDock = "";
        }
        return outDock;
    }
}