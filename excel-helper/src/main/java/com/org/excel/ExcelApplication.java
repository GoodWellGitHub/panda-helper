package com.org.excel;

import com.google.common.collect.Table;
import com.org.excel.client.ExcelClient;

import java.util.Map;

public class ExcelApplication {
    public static void main(String[] args) {
        String filePath = "/Users/admin/main/xls/天美意推送人员.xlsx";
        ExcelClient excelClient = new ExcelClient(filePath);
        Map<String, Table> allSheet = excelClient.allSheetValue();
        allSheet.entrySet().forEach(System.out::println);
    }
}
