package cmsc335_project_3_elinkowski;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.*;

/*******************************************************************************
 * File name: World class
 * Date: 20181025 1009L
 * Author: Keith R. Elinkowski
 * Purpose: Extends AbstractTableModel class.  Class is used to generate a table 
 * template with specific headers that can add and remove rows from the table 
 * as needed.
 ******************************************************************************/
public class JobTableTemplate extends AbstractTableModel{
    private final ArrayList<String[]> stringList;
    private final String[] tableHeader;

    /***************************************************************************
     * JobTableTemplate Constructor
    ***************************************************************************/
    JobTableTemplate(String[] header){
        this.tableHeader = header;
        stringList = new ArrayList<>();
    }
    
    /***************************************************************************
     * @Override of getRowCount() from AbstractTableModel class.  returns the 
     * number of rows from the JTable
    ***************************************************************************/
    public int getRowCount(){
        return stringList.size();
    }
    
    /***************************************************************************
     * @Override of getColumnCount() from AbstractTableModel class.  returns the 
     * number of columns from the JTable
    ***************************************************************************/
    public int getColumnCount(){
        return tableHeader.length;
    }
    
    /***************************************************************************
     * @param row
     * @param col
     * @Override of getValueAt() from AbstractTableModel class.  Returns the 
     * value at requested x and y coordinate of JTable
    ***************************************************************************/
    public String getValueAt(int row, int col){
        String getValueAtReturn = "";
        try {
            if(stringList.get(row)[col] != null) {
                getValueAtReturn = stringList.get(row)[col];
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
            return null;
        }catch(IndexOutOfBoundsException e) {
            System.out.println(e);
            return null;
        }
        return getValueAtReturn;
    }
    
    /***************************************************************************
     * @param index
     * @Override of getColumnName() from AbstractTableModel class.  Returns the 
     * column name for the passed index of the JTable
    ***************************************************************************/
    public String getColumnName(int index){
        String getColumnNameReturn = "";
        try {
            if(index > 0) {
                getColumnNameReturn = tableHeader[index];
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
            return null;
        }catch(IndexOutOfBoundsException e) {
            System.out.println(e);
            return null;
        }
        return getColumnNameReturn;
    }
    
    /***************************************************************************
     * Add a new row to the JTable
     * @param ship
     * @param structureMap
     * @param job
    ***************************************************************************/
    public void add(Ship ship, HashMap<Integer, Thing>structureMap, Job job) {
        String[] row = new String[4];
        row[0] = ship.getName();
        Thing thing = structureMap.get(ship.getParent());
        row[1] = structureMap.get(thing.getParent()).getName() + ":" + thing.getName();
        row[2] = job.getName();
        row[3] = "";
        try {
            if(job.getRequirements().size() > 0) {
                for(int i = 0; i <= job.getRequirements().size()-1; i++){
                    row[3] += job.getRequirements().get(i) + " ";
                }
            }
            else{
                row[3] = "No Requirements";
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        stringList.add(row);
        fireTableDataChanged();
    }
    
    /***************************************************************************
     * Removes a row from the JTable
     * @param name
    ***************************************************************************/
    public void remove(String name){
        for(String[] stringArray : stringList){
            if(stringArray[2].equals(name)){
                stringList.remove(stringArray);
                fireTableDataChanged();
                break;
            }
        }
    }
}
