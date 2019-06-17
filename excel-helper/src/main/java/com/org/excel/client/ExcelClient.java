package com.org.excel.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.org.excel.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelClient {
    String filePath;

    public ExcelClient() {
    }

    public ExcelClient(String filePath) {
        this.filePath = filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @param sheetName sheet
     * @param skipRow   跳过行数
     * @return
     */
    public Table excelValue(String sheetName, int skipRow) {
        Workbook workbook = ExcelUtil.readExcel(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        Table table = HashBasedTable.create();
        if (sheet == null) {
            return table;
        }
        int rowNum = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);
        int columnNum = row.getPhysicalNumberOfCells();

        for (int rowIndex = skipRow; rowIndex < rowNum; rowIndex++) {
            row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            for (int columnIndex = 0; columnIndex < columnNum; columnIndex++) {
                table.put(rowIndex, columnIndex, ExcelUtil.getCellFormatValue(row.getCell(columnIndex)));
            }
        }

        return table;
    }

    /**
     * @param sheetIndex sheet
     * @param rowIndex   从0开始
     * @return 返回某一行数据
     */
    public List<Object> excelRowValue(String sheetIndex, int rowIndex) {
        Workbook workbook = ExcelUtil.readExcel(filePath);
        Sheet sheet = workbook.getSheet(sheetIndex);
        if (sheet == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        int columnNum = row.getPhysicalNumberOfCells();
        List<Object> rowValues = Lists.newArrayList();
        for (int columnIndex = 0; columnIndex < columnNum; columnIndex++) {
            rowValues.add(ExcelUtil.getCellFormatValue(row.getCell(columnIndex)));
        }
        return rowValues;
    }


    /**
     * 返回某列值
     *
     * @param sheetIndex
     * @param columnIndex
     * @return
     */
    public List<Object> excelColumnValue(String sheetIndex, int columnIndex) {
        Workbook workbook = ExcelUtil.readExcel(filePath);
        Sheet sheet = workbook.getSheet(sheetIndex);
        if (sheet == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        int rowNum = sheet.getPhysicalNumberOfRows();
        List<Object> columnValues = Lists.newArrayList();
        for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            columnValues.add(ExcelUtil.getCellFormatValue(row.getCell(columnIndex)));
        }
        return columnValues;
    }


    public Map<String, Table> allSheetValue() {
        Workbook workbook = ExcelUtil.readExcel(filePath);
        Iterator sheetIterator = workbook.sheetIterator();
        Map<String, Table> allSheet = Maps.newHashMap();
        while (sheetIterator.hasNext()) {
            Sheet sheet = (Sheet) sheetIterator.next();
            allSheet.put(sheet.getSheetName(), excelValue(sheet.getSheetName(), 0));
        }
        return allSheet;
    }

}
