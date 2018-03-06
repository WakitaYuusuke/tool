/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Yusuke
 */
public class LogOutPut {

//    引数のメンバーの経験した仕事をログに
    static public void writeLog(Member m) {
        try {
            File file = new File("C:\\Users\\Yusuke\\Desktop\\Log.txt");
            FileWriter filewriter = new FileWriter(file, true);
            String tmp = "[";
            for (int i = 0; i < m.ecsperiencedTasks.size(); i++) {
                tmp += " " + m.ecsperiencedTasks.get(i);
            }
            tmp += " ]";
            filewriter.write(m.name + tmp + "\r\n------------------\r\n");
            filewriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static public void writeLog(ArrayList<Task> t) {
        try {
            String tmp = "";
            for (int i = 0; i < t.size(); i++) {
                tmp += t.get(i).name;
                if (t.get(i).currentPerson.isEmpty()) {
                    tmp += "NOTSELECT";
                } else {
                    tmp += t.get(i).currentPerson;
                }
                tmp += "\r\n\r\n";
            }

            File file = new File("C:\\Users\\Yusuke\\Desktop\\Log.txt");
            FileWriter filewriter = new FileWriter(file, true);
            filewriter.write(tmp);
            filewriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static public void writeLog(ArrayList<Task> t, int count) {
        try {
            String tmp = "-result-\r\n";
            for (int i = 0; i < t.size(); i++) {
                String task = t.get(i).name;
                String name = t.get(i).currentPerson;
                tmp += task + ":" + name + "\r\n";
            }
            tmp += count + "回目終了\r\n------------------\r\n";
            File file = new File("C:\\Users\\Yusuke\\Desktop\\Log.txt");
            FileWriter filewriter = new FileWriter(file, true);
            filewriter.write(tmp);
            filewriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

//    static public void writeLog(ArrayList<Member> m) {
//        try {
//            String tmpS1 = "";
//            String tmpS2 = "";
//
//            for (int i = 0; i < m.size(); i++) {
//                Member tmpM = m.get(i);
//                tmpS1 += tmpM.name +":";
//
//                if (!tmpM.ecsperiencedTasks.isEmpty()) {
//                    for (int j = 0; j < tmpM.ecsperiencedTasks.size(); j++) {
//                        tmpS1 += tmpM.ecsperiencedTasks.get(j) + ",";
//                    }
//                }
//
//                tmpS1 += "\r\n";
//
//                tmpS1 += String.valueOf(tmpM.selectFlg) + "\r\n \r\n";
//            }
//
//            File file = new File("C:\\Users\\Yusuke\\Desktop\\Log.txt");
//            FileWriter filewriter = new FileWriter(file,true);
//            filewriter.write(tmpS1 + "\r\n------------------------------------\r\n");
//            filewriter.close();
//        } 
//        catch (IOException e) {
//            System.out.println(e);
//        }
//    }
    static public void writeLog(String s) {
        try {
            File file = new File("C:\\Users\\Yusuke\\Desktop\\Log.txt");
            FileWriter filewriter = new FileWriter(file, true);
            filewriter.write(s + "\r\n------------------\r\n");
            filewriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
