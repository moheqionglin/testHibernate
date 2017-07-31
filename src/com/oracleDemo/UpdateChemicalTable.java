package com.oracleDemo;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

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
import java.util.stream.Stream;

/**
 * @author wanli zhou
 * @created 2017-07-13 2:51 PM.
 */
public class UpdateChemicalTable {

    static volatile int value;

    public static void main(String[] args) throws IOException {
//        Connection conn = OracleConnection.createConn();
        try {
//            Statement stm = conn.createStatement();
            String filePath = "/Users/zhouwanli/Desktop/es.1";
            List<String> lines = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
            String s = lines.stream().map(line -> "'" + line.trim().toLowerCase() + "'").collect(Collectors.joining(","));
            System.out.println(s);
        }catch (Exception e){
//            try {
//                conn.close();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
        }

    }

    public static void guavaCollectionsTestL() throws IOException {
        List<String> lists = Stream.of("1", "2", "3").collect(Collectors.toList());
        List<List<String>> listLists = Lists.partition(lists, 10).stream().collect(Collectors.toList());

        ClassPath classPath = ClassPath.from(UpdateChemicalTable.class.getClassLoader());
        for(ClassPath.ClassInfo clazz : classPath.getTopLevelClasses("com.oracleDemo")){
            System.out.println(clazz.getPackageName() + "\t" + clazz.getName());
        }

    }


    private static void getReIndexProductIdByIC(){
        String sql = "select product_id from product_chemicals where chemical_id in (select CHEMICAL_ID from controlled_chemicals)";
        Connection conn = OracleConnection.createConn();
        try {
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            String directory = "/Users/zhouwanli/Desktop/rollback-data-clean-danni/";
            int i = 1;
            ResultSet rs = stm.executeQuery(sql);
            List<Integer> pids = new ArrayList<>();
            System.out.println(1);
            for(; rs.next(); ){
                Integer pid = rs.getInt(1);
                pids.add(pid);
            }
            List<List<Integer>> resultProductIds = Lists.partition(pids, 1000).stream().collect(Collectors.toList());


            for(List<Integer> ls : resultProductIds){
                PrintWriter out = new PrintWriter(new File(directory + i++));
                String s = ls.stream().map(l -> l.toString()).collect(Collectors.joining(", "));
                out.print("{\"productIds\": [" + s + "]}");
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void getReIndexProductId(){
        String sql = "select product_id from product_chemicals where chemical_id in (select CHEMICAL_ID from controlled_chemicals)";
        Connection conn = OracleConnection.createConn();
        try {
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            String path = "/Users/zhouwanli/Desktop/ppp-16.csv";
//            String path = "/Users/zhouwanli/Desktop/ex.t";
            List<String> lnids = Files.lines(Paths.get(path)).collect(Collectors.toList());
            String directory = "/Users/zhouwanli/Desktop/rollback-data-clean-danni/";
            int i = 1;

            List<List<String>> lls = Lists.partition(lnids, 1000).stream().collect(Collectors.toList());
            for(List<String> ls : lls){
                PrintWriter out = new PrintWriter(new File(directory + i++));
                String s = ls.stream().map(l -> Integer.valueOf(l.replaceAll("LN", "")).toString()).collect(Collectors.joining(", "));
                ResultSet rs = stm.executeQuery(sql.replace("##", s));
                List<Integer> pids = new ArrayList<>();
                for(; rs.next(); ){
                    Integer pid = rs.getInt(1);
                    pids.add(pid);
                }
                out.print("{\"productIds\": [" + pids.stream().map(iii -> iii.toString()).collect(Collectors.joining(",")) + "]}");
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void backCas(){
        Connection conn = OracleConnection.createConn();
        try {
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            PrintWriter out = new PrintWriter(new File("/Users/zhouwanli/Desktop/back-cas-uniq"));
            List<String> lnids = Files.lines(Paths.get("/Users/zhouwanli/Desktop/ex.t")).collect(Collectors.toList());


            String sql = "select  cas,unique_molecule_id from chemical_structures  where unique_molecule_id in ( ## )";
            Lists.partition(lnids, 1000).forEach(ls -> {

                String s = ls.stream().map(l -> Integer.valueOf(l.replaceAll("LN", "")).toString()).collect(Collectors.joining(", "));
                String sss = sql.replace("##", s);
                System.out.println(sss);
                ResultSet rs = null;
                try {
                    rs = stm.executeQuery(sss);
                    for(; rs.next();){
                        String cas = rs.getString(1);
                        Integer lnid = rs.getInt(2);
                        out.println(cas + "\t" + lnid);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCas(){
        Connection conn = OracleConnection.createConn();
        try {
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            String path = "/Users/zhouwanli/Desktop/sssql";
            System.out.println(path);
            List<String> lnids = Files.lines(Paths.get(path)).collect(Collectors.toList());

            lnids.forEach(sql -> {
                try {
                    stm.execute(sql);
                    System.out.println("SUCCESS " + value++);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });



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
    public static void generateSql(){
        try {
            List<String> lnids = Files.lines(Paths.get("/Users/zhouwanli/Desktop/ex.t")).collect(Collectors.toList());

            PrintWriter out = new PrintWriter(new File("/Users/zhouwanli/Desktop/sssql"));

            String sql = "update chemical_structures set cas = null, version = version + 1, updatedAt = CURRENT_TIMESTAMP where unique_molecule_id in ( ## )";
            Lists.partition(lnids, 1000).forEach(ls -> {

                String s = ls.stream().map(l -> Integer.valueOf(l.replaceAll("LN", "")).toString()).collect(Collectors.joining(", "));
                out.println(sql.replace("##", s));
            });


            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
