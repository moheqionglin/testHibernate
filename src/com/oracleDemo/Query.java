package com.oracleDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @created 2017-07-07 5:54 PM.
 */
public class Query {
    public static void main(String[] args) {
        Connection conn = OracleConnection.createConn();
        try {
            List<String> list = Files.lines(Paths.get("/Users/zhouwanli/Desktop/ppp-16.csv")).collect(Collectors.toList());
            System.out.println(list.size());
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            List<Integer> resuts = new ArrayList<>();
            String sql = "select product_id from product_chemicals where chemical_id in ( select id from chemical_structures where unique_molecule_id in( ## ) )";

            int i = 1;
            for(String s: list){
                ResultSet rst = stm.executeQuery(s);
                for(; rst.next();){
                    resuts.add(rst.getInt(1));
                }

                System.out.println("====" + resuts.size() + "====");
                PrintWriter out = new PrintWriter(new File("/Users/zhouwanli/Desktop/ddd/" + i++));
                out.print("{\"productIds\": [" + resuts.stream().map(iii -> iii.toString()).collect(Collectors.toList()) + "]}");
                out.flush();
                out.close();
                resuts.clear();
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
