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

    
    static int count = 0;
    static ArrayList<Member> memberList = new ArrayList<>();
    static ArrayList<Member> selectedMemberList = new ArrayList<>();
    static ArrayList<Task> taskList = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        registerMembers();
        registerTasks();
        int devCounter = 0;

        while (true) {
            
//            主な振り分け処理、組み合わせによっては止まるので再振り分けの処理も行う
//            decideMemberで選ばれたMemberをselectedMemberListで管理
            decideMember();
            ArrayList<Member> tmpSelectedMemberList = null;

            boolean reselectFlg = false;
            do {
                if(reselectFlg){
                    LogOutPut.writeLog("振り直し");
                    for(int i = 0; i < taskList.size(); i ++){
                        if(!taskList.get(i).currentPerson.isEmpty()){
                            taskList.get(i).currentPerson = "";
                            taskList.get(i).count --;
                        }
                    }
                }
                
//                selectedMemberListをディープコピー
                tmpSelectedMemberList = cloneMemberList();
                for (int i = 0; i < taskList.size(); i++) {
                    Member selectedMember = tmpSelectedMemberList.get(i);
                    
//            selectedMemberにどのTaskも振れない場合
                    if (!selectedMember.ecsperiencedTasks.isEmpty()) {
                        reselectFlg = selectedMember.isStateToReselect(taskList);
                        if(reselectFlg){break;}
                    }
                    decideTask(selectedMember);
                }
            } 
            while (reselectFlg);
            
//            tmpSelectedMemberListをMemberListと同期
            uploadMemberList(tmpSelectedMemberList);
            selectedMemberList.clear();
            
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                clearExperience(task);
            }

//            結果出力
            String Output = "";
            for (int i = 0; i < taskList.size(); i++) {
                String task = taskList.get(i).name;
                String name = taskList.get(i).currentPerson;
                System.out.println(task + ":" + name);
            }
            System.out.println("------------------------");
            
            devCounter ++;
            LogOutPut.writeLog(taskList ,devCounter);

//            次の繰り返しのためのmemberとtaskの変数整理
            setInfo();
        }
    }

    static public void registerMembers() {
//        memberList.add(new Member("dev"));
        memberList.add(new Member("kosugi"));
        memberList.add(new Member("tomizawa"));
        memberList.add(new Member("nagahama"));
        memberList.add(new Member("wakita"));
        memberList.add(new Member("morihuzi"));
        memberList.add(new Member("sen"));
        memberList.add(new Member("you"));
    }

    static public void registerTasks() {
        taskList.add(new Task("トイレ"));
        taskList.add(new Task("ゴミ出し"));
        taskList.add(new Task("床"));
        taskList.add(new Task("郵便"));
    }

    static public void decideMember() {
        while (selectedMemberList.size() != taskList.size()) {
//            memberList格納数未満の乱数
            int rnd = new Random().nextInt(memberList.size());
            Member selectedMember = memberList.get(rnd);
//            dev選択
//            Member selectedMember = memberList.get(0);

//            MemberListの中で未選択かつ今回の担当にも選ばれていない場合
            if (!selectedMember.selectFlg && !selectedMember.isThisLoop) {
                selectedMemberList.add(selectedMember);
                Member.numOfDecidedMember++;
                selectedMember.selectFlg = true;
                selectedMember.isThisLoop = true;
                clearSelectFlg();
            }
        }

//        isThisLoop初期化
        for (int i = 0; i < selectedMemberList.size(); i++) {
            selectedMemberList.get(i).isThisLoop = false;
        }
    }

//    selectedMemberに仕事を割り振る
    static public void decideTask(Member selectedMember) {
        Task selectedTask;
        while (true) {
            int rnd = new Random().nextInt(taskList.size());
            selectedTask = taskList.get(rnd);

            LogOutPut.writeLog(taskList);
            LogOutPut.writeLog(selectedMember);
//            selectedMemberが未経験かつ前回未担当
            if (selectedMember.isExperiencedTasks(selectedTask.name) && selectedTask.isExperiencedMember(selectedMember.name)) {
                selectedMember.ecsperiencedTasks.add(selectedTask.name);
                selectedTask.currentPerson = selectedMember.name;
                selectedTask.count++;
                break;
            }
        }
        LogOutPut.writeLog(selectedMember.name + "　get " + selectedTask.name);
    }

//    1つのTaskに対して全Memberが経験を持つ場合、ecsperiencedTasksから該当Task消去
    static public void clearExperience(Task task) {
        if (task.count == memberList.size()) {
            for (int j = 0; j < memberList.size(); j++) {
                Member member = memberList.get(j);
                member.ecsperiencedTasks.remove(task.name);

            }
            LogOutPut.writeLog(task.name + "の経験クリア");
        }

    }

//    全MemberのselectFlg = true の場合消去
    static public void clearSelectFlg() {
        if (Member.numOfDecidedMember == memberList.size()) {
            for (int i = 0; i < memberList.size(); i++) {
                memberList.get(i).selectFlg = false;
            }
            Member.numOfDecidedMember = 0;
            count ++;
            LogOutPut.writeLog("セレクトフラグクリア");
        }
    }

//    Taskの担当者変数の移動など
    static public void setInfo() {
        if (Member.numOfDecidedMember == memberList.size()) {
            Member.numOfDecidedMember = 0;
            for (int i = 0; i < memberList.size(); i++) {
                memberList.get(i).selectFlg = false;
            }
        }

        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).chengePersonInfo();
        }
    }

    static public ArrayList<Member> cloneMemberList() {
        ArrayList<Member> clone = new ArrayList<>();
        for (int i = 0; i < selectedMemberList.size(); i++) {
            clone.add(selectedMemberList.get(i).clone());
        }
        return clone;
    }
    
    static public void uploadMemberList(ArrayList<Member> clone){
        for(int i = 0; i < memberList.size(); i ++){
            for(int j = 0; j < clone.size(); j ++){
                if(memberList.get(i).name.equals(clone.get(j).name)){
                    memberList.remove(i);
                    memberList.add(i, clone.get(j));
                    break;
                }
            }
        }
    }

}
