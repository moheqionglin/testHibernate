package com.oracleDemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanli zhou
 * @created 2017-06-22 8:52 PM.
 */
public class DeleteOracle {
    public static void main(String[] args) {
        Connection conn = OracleConnection.createConn();
        try {
            List<Integer> list = Files.lines(Paths.get("/Users/zhouwanli/Desktop/all")).filter(s -> !s.equals("0")).map(s -> Integer.valueOf(s)).collect(Collectors.toList());
            System.out.println(list.size());
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            List<Integer> dbIds = new ArrayList<>();
            ResultSet rst = stm.executeQuery("select id from chemical_structures");
            for(; rst.next();){
                Integer i = rst.getInt(1);
                if(!list.contains(i)){
                    dbIds.add(i);
                }
                if(dbIds.size() == 999){
                    String s = dbIds.stream().map(ss -> ss.toString()).collect(Collectors.joining(","));
                    System.out.println("delete from chemical_structures where id in ("+ s + ");");
                    dbIds.clear();
                }
            }

            if(dbIds.size()> 0){
                String s = dbIds.stream().map(ss -> ss.toString()).collect(Collectors.joining(","));
                System.out.println("delete from chemical_structures where id in ("+ s + ");");
                dbIds.clear();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
