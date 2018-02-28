/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

/**
 *
 * @author Yusuke
 */
public class Task {

    static int numOfTask;
    int count;
    String name = "";
    String currentPerson = "";
    String lastPerson = "";

    Task(String name) {
        this.name = name;
        numOfTask++;
    }

    boolean checkExperiencedMember(String memberName) {
        boolean check = false;
        if (currentPerson.isEmpty() && !lastPerson.equals(memberName)) {
            check = true;
        }
        return check;
    }

    void chengePerspnInfo() {
        lastPerson = currentPerson;
        currentPerson = "";
    }
}
