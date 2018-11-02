package com.company;

public class Main {

    public static void main(String args[])throws Exception{
        if (args.length == 0){
            System.out.println("需要zk server的ip地址和端口作为参数");
            System.exit(1);
        }
        primary m = new primary(args[0]);
        m.startZK();
        m.runForMaster();
        m.check();
        m.stopZK();
    }
}
