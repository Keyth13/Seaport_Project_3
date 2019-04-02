package cmsc335_project_3_elinkowski;

import java.time.Instant;

/*******************************************************************************
 * File name: PortTime class
 * Date: 20181025 1012L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class.  Timestamps when a ship arrives to a port and 
 * when it is docked.
 ******************************************************************************/
public class PortTime {
    private int time;

    /***************************************************************************
     * PortTime Constructor
    ***************************************************************************/ 
    public PortTime() {
    }
    
    /***************************************************************************
     * Getter for getPortTime
    ***************************************************************************/    
    public int getPortTime() {
        return time;
    }
    /***************************************************************************
     * Setter for getPortTime
    ***************************************************************************/    
    public void setPortTime() {
        this.time = ((int) (Instant.now().toEpochMilli()/1000));
    }
    
    /***************************************************************************
     * Overridden toString Method
    ***************************************************************************/
    @Override
    public String toString(){
        return String.format(String.valueOf(getPortTime()));
    }
}