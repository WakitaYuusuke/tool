/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.util.ArrayList;

/**
 *
 * @author Yusuke
 */
public class Member {

    static int numOfMember = 0;
    static int numOfDecidedMember = 0;
    String name;
    String currentTask;
    boolean selectFlg = false;
    boolean isThisLoop = false;
    ArrayList<String> ecsperiencedTasks = new ArrayList<>();

    Member(String name) {
        if (name == "dev") {
            this.name = "dev";
//                selectFlg  = true;
                isThisLoop = true;
            ecsperiencedTasks.add("toire");
            ecsperiencedTasks.add("gomidasi");
            ecsperiencedTasks.add("yuubinnkaisyuu");
            ecsperiencedTasks.add("yuka");
        } else {
            this.name = name;
            numOfMember++;
        }
    }

//    引数のTaskを経験してるか確認
    boolean isExperiencedTasks(String task) {
        boolean check = false;

//        スタート時の空のListを拾う処理
        if (ecsperiencedTasks.isEmpty()) {
            check = true;
        }
//        担当してないタスクか確認する処理
        else {
            for (int i = 0; i < ecsperiencedTasks.size(); i++) {
                if (ecsperiencedTasks.get(i).equals(task)) {
                    check = false;
                    break;
                } else {
                    check = true;
                }
            }
        }
        return check;
    }

//    再振り分けする状態のMemberか確認
    boolean isStateToReselect(ArrayList<Task> taskList){
        boolean check = false;
        ArrayList<String> selectedTaskList = new ArrayList<>();
        int count = 0;
        
//        選択済みTaskのArrayList作成
        for(int i = 0; i < taskList.size(); i ++){
            if(taskList.get(i).currentPerson.isEmpty()){
                selectedTaskList.add(taskList.get(i).name);
            }
        }
        
//        selectedTaskListとecsperiencedTasksに格納していないTaskがすべて一致すると
//        振り分けることができないので判定する処理
        for(int i = 0; i < selectedTaskList.size(); i ++){
            for(int j = 0; j < ecsperiencedTasks.size(); i ++){
                if(selectedTaskList.get(i).equals(ecsperiencedTasks.get(j))){
                    count ++;
                }
            }
        }
        
        if(count == 0){
            check = true;
        }
        
        return check;
    }
}
