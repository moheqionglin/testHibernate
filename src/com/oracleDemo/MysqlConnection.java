package com.oracleDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli zhou
 * @created 2017-07-13 2:37 PM.
 */
public class MysqlConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/molecules?createDatabaseIfNotExist=true";
    private static final String NAME = "";
    private static final String PSWD = "";

    public static Connection createConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, NAME, PSWD);
            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String args[]) {
        Connection conn = createConn();
        try {
            Statement stm = conn.createStatement();
            stm.setFetchSize(1000);
            ResultSet rst = stm.executeQuery("select chemical_id from product_chemicals");
            List<Integer> chemicalIds = new ArrayList<>();
            PrintWriter out = new PrintWriter(new File("/Users/zhouwanli/Desktop/all"));
            int i = 1;
            for (; rst.next(); ) {
                System.out.println(i++);
                out.println(rst.getInt(1));
            }
            out.flush();
            out.close();
            System.out.println(chemicalIds.size());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
