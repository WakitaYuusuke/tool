/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//resetInfoToReselectを実装していないのでまだ繰り返し再振り分け処理が最後までできません

        
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
            
            boolean reselectFlg = false;
            
//            主な振り分け処理、組み合わせによっては止まるので再振り分けの処理も行う
//            do{reselectFlg = decideDuty(memberList, taskList);}
//            while(reselectFlg);
            
            decideDuty(memberList, taskList);
            
//            LogOutPut.writeLog(memberList);
            
//            結果出力
            for (int i = 0; i < taskList.size(); i++) {
                String task = taskList.get(i).name;
                String name = taskList.get(i).currentPerson;
                System.out.println(task + ":" + name);
            }

            System.out.println("------------------------");

//            次の繰り返しのためのmemberとtaskの変数整理
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
    
    static public void registerTasks(ArrayList<Task> taskList) {
        taskList.add(new Task("トイレ"));
        taskList.add(new Task("ゴミ出し"));
        taskList.add(new Task("床"));
        taskList.add(new Task("郵便"));
    }

//    振り分ける大元、振り分け処理系の各メソッドはここで呼び出し
    static public boolean decideDuty(ArrayList<Member> memberList, ArrayList<Task> taskList) {
        Task selectedTask;
        boolean reselectFlg = false;

        int counter = 0;
        while (counter != taskList.size()) {
//            memberList格納数未満の乱数
            int rnd = new Random().nextInt(memberList.size());
            Member selectedMember = memberList.get(rnd);
//            dev選択
//            Member selectedMember = memberList.get(0);
            
//            selectedMemberにどのTaskも振れない場合
//            reselectFlg = selectedMember.isStateToReselect(taskList);
//            resetInfoToReselect(memberList);
//            if(reselectFlg){break;}
            
//            LogOutPut.writeLog(selectedMember);
            
//            MemberListの中で未選択かつ今回の担当にも選ばれていない場合
            if (!selectedMember.selectFlg && !selectedMember.isThisLoop) {
                selectedTask = decideTask(selectedMember, taskList);
                clearExperience(memberList, selectedTask);
                selectedMember.selectFlg = true;
                selectedMember.isThisLoop = true;
                counter++;
                Member.numOfDecidedTask++;
                clearSelectFlg(memberList);
                
//                LogOutPut.writeLog(selectedTask.name);
            }
        }
        
//        isThisLoop初期化
        for(int i = 0; i < memberList.size(); i ++){
            memberList.get(i).isThisLoop = false;
        }
        return reselectFlg;
    }
//    selectedMemberに仕事を割り振る
    static public Task decideTask(Member selectedMember, ArrayList<Task> taskList) {
        Task selectedTask;
        while (true) {
            int rnd = new Random().nextInt(taskList.size());
            selectedTask = taskList.get(rnd);
            
//            selectedMemberが未経験かつ前回未担当
            if (selectedMember.isExperiencedTasks(selectedTask.name) && selectedTask.isExperiencedMember(selectedMember.name)) {
                selectedMember.ecsperiencedTasks.add(selectedTask.name);
                selectedTask.currentPerson = selectedMember.name;
                selectedTask.count++;
                break;
            }
        }
        return selectedTask;
    }

//    1つのTaskに対して全Memberが経験を持つ場合、ecsperiencedTasksから該当Task消去
    static public void clearExperience(ArrayList<Member> memberList, Task task) {
        if (task.count == memberList.size()) {
            for (int i = 0; i < memberList.size(); i++) {
                Member member = memberList.get(i);
                member.ecsperiencedTasks.remove(task.name);
            }
        }

    }

//    全MemberのselectFlg = true の場合消去
//    新しく作る　ArrayList selectedMembers で管理するように直したいのでなくなるかもしれません
    static public void clearSelectFlg(ArrayList<Member> memberList) {
        if (Member.numOfDecidedTask == memberList.size()) {
            for (int i = 0; i < memberList.size(); i++) {
                memberList.get(i).selectFlg = false;
            }
            Member.numOfDecidedTask = 0;
        }
    }
    
//    再選択する際にループ途中で加えられた変数をループ前にリセット
//    まだ実装途中です
    static public void resetInfoToReselect(ArrayList<Member> memberList){
        for(int i = 0;  i < memberList.size(); i ++){
            Member member = memberList.get(i);
            if(member.isThisLoop){
                member.selectFlg = false;
                member.currentTask = "";
                member.ecsperiencedTasks.remove(member.ecsperiencedTasks.size() - 1);
            }
        }
    }
    
//    Taskの担当者変数の移動など
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

}
