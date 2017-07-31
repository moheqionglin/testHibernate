package com.oracleDemo;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wanli zhou
 * @created 2017-06-22 9:45 PM.
 */
public class MultiTh {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("/Users/zhouwanli/Desktop/de1"), StandardCharsets.UTF_8);
            for(List<String> lss : Lists.partition(lines, 10)){
                new Thread(() ->{
                    String tn = Thread.currentThread().getName();
                    Connection conn = OracleConnection.createConn();
                    try {
                        Statement stm = conn.createStatement();
                        for(String s : lss){
                            System.out.println(tn + " -> " + s);
                            stm.execute(s.replaceAll(";", ""));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
