package cmsc335_project_3_elinkowski;

import java.util.ArrayList;
import java.util.Scanner;

/*******************************************************************************
 * File name: Ship class
 * Date: 20181025 1339L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class. This class holds all the requirements a ship 
 * in this World, would need to dock.
*******************************************************************************/
public class Ship extends Thing{
    private PortTime arrivalTime;
    private PortTime dockTime;
    double draft;
    double length;
    double weight;
    double width;
    private ArrayList<Job> jobs;
    
    /***************************************************************************
     * Ship Constructor
     * @param scanner
    ***************************************************************************/
    public Ship(Scanner scanner) {
        super(scanner);
        jobs = new ArrayList<>();
        if(scanner.hasNextDouble()) {
            weight = scanner.nextDouble();
        }
        else {
            weight = 0.0;
        }
        if(scanner.hasNextDouble()) {
            length = scanner.nextDouble();
        }
        else {
            length = 0.0;
        }
        if(scanner.hasNextDouble()) {
            width = scanner.nextDouble();
        }
        else {
            width = 0.0;
        }
        if(scanner.hasNextDouble()) {
            draft = scanner.nextDouble();
        }
        else {
            draft = 0.0;
        }
    }
    
    /***************************************************************************
     * Getter for arrivalTime
    ***************************************************************************/
    public PortTime getArrivalTime() {
        return arrivalTime;
    }
    
    /***************************************************************************
     * Setter for arrivalTime
     * @param aTime
    ***************************************************************************/
    public void setArivalTime(PortTime aTime) {
        arrivalTime = aTime;
    }
    
   /***************************************************************************
     * Getter for dockTime
    ***************************************************************************/
    public PortTime getDockTime() {
        return dockTime;
    }
    
    /***************************************************************************
     * Setter for dockTime
     * @param dTime
    ***************************************************************************/
    public void setDockTime(PortTime dTime) {
        dockTime = dTime;
    }
    
    /***************************************************************************
     * Getter for draft
    ***************************************************************************/    
    public double getDraft() {
        return draft;
    }
    
    /***************************************************************************
     * Setter for draft
     * @param d
    ***************************************************************************/
    public void setDraft(double d) {
        if(d >= 0.0) {
            draft = d;
        }
    }
    
    /***************************************************************************
     * Getter for length
    ***************************************************************************/
    public double getLength() {
        return length;
    }
    
    /***************************************************************************
     * Setter for length
     * @param l
    ***************************************************************************/
    public void setLength(double l) {
        if(l >= 0.0) {
            length = l;
        }
    }
    
    /***************************************************************************
     * Getter for weight
    ***************************************************************************/
    public double getWeight() {
        return weight;
    }
    
    /***************************************************************************
     * Setter for weight
     * @param we
    ***************************************************************************/
    public void setWeight(double we) {
        if(we >= 0.0) {
            weight = we;
        }
    }
    
    /***************************************************************************
     * Getter for width
    ***************************************************************************/
    public double getWidth() {
        return width;
    }
    
    /***************************************************************************
     * Setter for width
     * @param wi
    ***************************************************************************/
    public void setWidth(double wi) {
        if(wi >= 0.0) {
            width = wi;
        }
    }
    
    /***************************************************************************
     * Getter for jobs
    ***************************************************************************/
    public ArrayList<Job> getJobs() {
        return jobs;
    }
    
    /***************************************************************************
     * Setter for jobs
     * @param job
    ***************************************************************************/
    public void setJobs(ArrayList<Job> job) {
        jobs = job;
    }

    /***************************************************************************
     * @Override for toString() Method
    ***************************************************************************/
    public String toString() {
        String outShip = String.format("%s: ", super.toString());
        outShip += String.format("DRAFT: %.2fm, ", draft);
        outShip += String.format("LENGTH: %.2fm, ", length);
        outShip += String.format("WEIGHT: %.2fMT, ", weight);
        outShip += String.format("WIDTH: %.2fm, ", width);
        outShip += String.format("Work Orders: \n");
        if(jobs != null) {
            outShip += String.format("%s\n", jobs);
        }
        else {
            outShip += String.format("Work Orders: NONE\n");
        }
        outShip += "Arrival Time: " + getArrivalTime() + "\n";
        outShip += "Dock Time: " + getDockTime() + "\n";
        return outShip;
    }
}
