package cmsc335_project_3_elinkowski;

import java.util.Scanner;

/*******************************************************************************
 * File name: Person class
 * Date: 20181026 1642L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class.  Creates a person object and stores their Skill 
 * and assigns them to the specific port.
*******************************************************************************/
public class Person extends Thing{
    private String skills;
    
    /***************************************************************************
     * Person Constructor
     * @param scanner
    ***************************************************************************/
    public Person(Scanner scanner) {
        super(scanner);
        if(scanner.hasNext()) {
            skills = (scanner.next());
        }
        else {
            skills = "Error! No Skills found!\n";
        }
    }
    
    /***************************************************************************
     * Getter for skills
    ***************************************************************************/
    public String getSkill() {
            return skills;
    }
    
    /***************************************************************************
     * Setter for skills
     * @param skill
    ***************************************************************************/
    public void setSkill(String skill) {
        skills = skill;
    }
    
    /***************************************************************************
     * @Override for toString() Method
    ***************************************************************************/
    @Override
     public String toString(){
        return String.format("Contractor %s is a skilled %s", super.toString(), 
                skills);
    }
}