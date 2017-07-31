package com.tools;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanli zhou
 * @created 2017-07-07 1:27 PM.
 */
public class ReadFileFrom {
    public static void returnMysql() throws IOException {
        String sqlPre = "select * from molecules.data_clean_log where id > 149573 and lnid in ( # );";
        List<String> lines = Files.lines(Paths.get("/Users/zhouwanli/Desktop/cas-es")).collect(Collectors.toList());
        Lists.partition(lines, 1000).forEach(lis -> {
            String lnids = lis.stream().collect(Collectors.joining("', '", "'", "'"));
            System.out.println(sqlPre.replace("#", lnids));
        });
    }
    public static void rr() throws IOException {
        String sqlPre = "update chemical_structures set cas = null, version = version + 1, updatedAt = CURRENT_TIMESTAMP where unique_molecule_id in ( # );";
        List<String> lines = Files.lines(Paths.get("/Users/zhouwanli/Desktop/ppp-16.csv")).collect(Collectors.toList());
        Lists.partition(lines, 1000).forEach(lis -> {
            String lnids = lis.stream().map(lnid -> Integer.valueOf(lnid.replaceAll("LN", "")).toString()).collect(Collectors.joining(", "));
            System.out.println(sqlPre.replace("#", lnids));
        });
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Paths.get("/Users/zhouwanli/Desktop/Edit8")).collect(Collectors.toList());
        String sqlPre = "select product_id  from product_chemicals where chemical_id in ( select id from  chemical_structures  where unique_molecule_id in ( # ))";
        Lists.partition(lines, 1000).forEach(lis -> {
            String lnids = lis.stream().map(lnid -> Integer.valueOf(lnid.replaceAll("LN", "")).toString()).collect(Collectors.joining(", "));
            System.out.println(sqlPre.replace("#", lnids));
        });
    }
}
