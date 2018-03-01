/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Yusuke
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Member> memberList = new ArrayList<>();
        ArrayList<Task> taskList = new ArrayList<>();

        registerMembers(memberList);
        registerTasks(taskList);

        while (true) {

            decideDuty(memberList, taskList);

            LogOutPut.writeLog(memberList);
            
            for (int i = 0; i < taskList.size(); i++) {
                String task = taskList.get(i).name;
                String name = taskList.get(i).currentPerson;
                System.out.println(task + ":" + name);
            }

            System.out.println("------------------------");

            setInfo(memberList, taskList);
        }
    }

    static public void registerMembers(ArrayList<Member> memberList) {
//        memberList.add(new Member("dev"));
        memberList.add(new Member("kosugi"));
        memberList.add(new Member("tomizawa"));
        memberList.add(new Member("nagahama"));
        memberList.add(new Member("wakita"));
        memberList.add(new Member("morihuzi"));
        memberList.add(new Member("sen"));
        memberList.add(new Member("you"));
    }

    static public void decideDuty(ArrayList<Member> memberList, ArrayList<Task> taskList) {
        Task selectedTask;

        int counter = 0;

        while (counter != taskList.size()) {
            int rnd = new Random().nextInt(memberList.size());
//            Member selectedMember = memberList.get(0);
            Member selectedMember = memberList.get(rnd);
            
            LogOutPut.writeLog(selectedMember);
            
            if (!selectedMember.selectFlg && !selectedMember.isThisLoop) {
                selectedTask = decideTask(selectedMember, taskList);
                clearExperience(memberList, selectedTask);
                selectedMember.selectFlg = true;
                selectedMember.isThisLoop = true;
                counter++;
                Member.numOfDecidedTask++;
                clearSelectFlg(memberList);
                
                LogOutPut.writeLog(selectedTask.name);
            }
        }
        for(int i = 0; i < memberList.size(); i ++){
            memberList.get(i).isThisLoop = false;
        }
    }

    static public void setInfo(ArrayList<Member> memberList, ArrayList<Task> taskList) {
        if (Member.numOfDecidedTask == memberList.size()) {
            Member.numOfDecidedTask = 0;
            for (int i = 0; i < memberList.size(); i++) {
                memberList.get(i).selectFlg = false;
            }
        }

        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).chengePersonInfo();
        }
    }

    static public void registerTasks(ArrayList<Task> taskList) {
//		int additionalNum = 0;
//		for(int i = 0; i < additionalNum; i ++) {
//			String name = "";
//			taskList.add(new Task(name));
//		}
        taskList.add(new Task("トイレ"));
        taskList.add(new Task("ゴミ出し"));
        taskList.add(new Task("床"));
        taskList.add(new Task("郵便"));
    }

    static public Task decideTask(Member selectedMember, ArrayList<Task> taskList) {
        Task selectedTask;
        while (true) {
            int rnd = new Random().nextInt(taskList.size());
            selectedTask = taskList.get(rnd);

            if (selectedMember.isExperiencedTasks(selectedTask.name) && selectedTask.isExperiencedMember(selectedMember.name)) {
                selectedMember.ecsperiencedTasks.add(selectedTask.name);
                selectedTask.currentPerson = selectedMember.name;
                selectedTask.count++;
                break;
            }
        }
        return selectedTask;
    }

    static public void clearExperience(ArrayList<Member> memberList, Task task) {
        if (task.count == memberList.size()) {
            for (int i = 0; i < memberList.size(); i++) {
                Member member = memberList.get(i);
                member.ecsperiencedTasks.remove(task.name);
            }
        }

    }

    static public void clearSelectFlg(ArrayList<Member> memberList) {
        if (Member.numOfDecidedTask == memberList.size()) {
            for (int i = 0; i < memberList.size(); i++) {
                memberList.get(i).selectFlg = false;
            }
            Member.numOfDecidedTask = 0;
        }
    }

}
