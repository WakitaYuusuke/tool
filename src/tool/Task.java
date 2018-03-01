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
    
    int count;
    String name = "";
    String currentPerson = "";
    String lastPerson = "";

    Task(String name) {
        this.name = name;
    }

//    前回の担当者と重複しないか確認
    boolean isExperiencedMember(String memberName) {
        boolean check = false;
        if (currentPerson.isEmpty() && !lastPerson.equals(memberName)) {
            check = true;
        }
        return check;
    }

//    currentPersonをlastPersonへ移動後currentPerson初期化
    void chengePersonInfo() {
        lastPerson = currentPerson;
        currentPerson = "";
    }
}
