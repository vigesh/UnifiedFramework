package utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ExcelUtility {
    String xlname = "testdata.xls";
    public Workbook workbook = null;
    public String Xlpath = System.getProperty("user.dir") + "\\resources\\";
    public Hashtable<Integer, String> Hash_dict = new Hashtable<Integer, String>();
    public Sheet sheet;
    public Row row;
    public int rowcount;
    public Cell cell;

    public void readExcel(String sheetname) throws Exception {
        String cellvalue = null;
        File file = new File(Xlpath + xlname);
        Constants.Cellvalue.clear();
        Constants.sheet_name = sheetname;
        try {
            FileInputStream fis = new FileInputStream(file);
            String fileXtnName = xlname.substring(xlname.indexOf("."));
            if (fileXtnName.equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileXtnName.equalsIgnoreCase(".xls")) {
                workbook = new HSSFWorkbook(fis);

            }

            sheet = workbook.getSheet(sheetname);
            rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            Constants.iterationcount = rowcount;
            int coliterator;
            ArrayList<String> cellData = new ArrayList<String>();
            cellData.clear();
            row = sheet.getRow(1);
            int totalcell = row.getLastCellNum();
            for (coliterator = 0; coliterator < totalcell; coliterator++) {
                cell = row.getCell(coliterator);
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                DataFormatter formatter = new DataFormatter();
                switch (cell.getCellType()) {
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellvalue = String.valueOf(cell);
                            cellvalue = sdf.format(sdf1.parse(cellvalue));
                            break;
                        } else {
                            cellvalue = String.valueOf(((XSSFCell) cell).getRawValue());
                            break;
                        }
                    case STRING:
                        cellvalue = cell.getStringCellValue();
                        break;
                    default:
                        break;
                }
                if (!cellvalue.isEmpty()) {
                    cellData.add(cellvalue);
                } else {
                    break;
                }
                Row r = sheet.getRow(0);
                String strkey = r.getCell(coliterator).getStringCellValue();
                Constants.Cellvalue.put(strkey, cellData.get(coliterator));
            }

            fis.close();
        } catch (Exception e) {
            throw new Exception(e);
        }


    }


    public String xlvalue(String Colname) throws IOException {
        String temp = null;
        for (Map.Entry<String, String> hashdata : Constants.Cellvalue.entrySet()) {
            String key = hashdata.getKey();
            String inputvalue = hashdata.getValue();
            if (key.equals(Colname)) {
                if (inputvalue.length() > 0) {
                    temp = inputvalue;
                    break;
                }
            }
        }
        return temp;
    }

    public void ReadExcel_list(String sheetname, int rowno) throws Exception {
        String xlname;
        if(Constants.appDetails.get("testdata").equalsIgnoreCase("full")){
            xlname="testdata_full.xls";
        }
        else{
            xlname="testdata_dev.xls";
        }
        File file = new File(Xlpath + xlname);
        Constants.Cellvalue.clear();
        try {
            FileInputStream fis = new FileInputStream(file);
            String fileXtnName = xlname.substring(xlname.indexOf("."));
            if (fileXtnName.equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileXtnName.equalsIgnoreCase(".xls")) {
                workbook = new HSSFWorkbook(fis);

            }

            sheet = workbook.getSheet(sheetname);
            rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            int coliterator;
            String cellvalue = null;
            ArrayList<String> cellData = new ArrayList<String>();
            cellData.clear();
            for (int rowiterator = rowno; ; ) {
                if (rowno <= rowcount) {
                    row = sheet.getRow(rowiterator);
                    int totalcell = row.getLastCellNum();
                    for (coliterator = 0; coliterator < totalcell; coliterator++) {
                        Cell cell;
                        cell = row.getCell(coliterator);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        DataFormatter formatter = new DataFormatter();
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cellvalue = String.valueOf(cell);
                                    cellvalue = sdf.format(sdf1.parse(cellvalue));
                                } else {
                                    cellvalue = formatter.formatCellValue(cell);
                                }
                                break;
                            case STRING:
                                cellvalue = cell.getStringCellValue();
                                break;
                            default:
                                break;
                        }
//                        if(coliterator==13){
//                        DataFormatter formatter = new DataFormatter();
//                            cellvalue = formatter.formatCellValue(cell);}
//                        else{
//                         cellvalue=cell.getStringCellValue()}
                        if (!cellvalue.isEmpty()) {
                            cellData.add(cellvalue);
                        } else {
                            break;
                        }
                        Row r = sheet.getRow(0);
                        String strkey = r.getCell(coliterator).getStringCellValue();
                        Constants.Cellvalue.put(strkey, cellData.get(coliterator));
                    }
                    break;
                }


            }
            fis.close();
        } catch (Exception e) {
            throw (e);
        }


    }

    public void Write_excel(String Sheet_name) throws Exception {
        String xlname = "Testdata.xlsx";
        File file = new File(Xlpath + xlname);
        Constants.Xlheader_name.clear();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            String fileXtnName = xlname.substring(xlname.indexOf("."));
            if (fileXtnName.equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileXtnName.equalsIgnoreCase(".xls")) {
                workbook = new HSSFWorkbook(fis);

            }

            sheet = workbook.getSheet(Sheet_name);
            int totalrowcount = sheet.getLastRowNum();
            row = sheet.getRow(0);
            for (int coliterator = 0; coliterator < row.getLastCellNum(); coliterator++) {
                cell = row.getCell(coliterator);
                String col_cellvalue = cell.getStringCellValue();
                Constants.Xlheader_name.add(col_cellvalue);
            }
            List<String> celldata = new ArrayList<String>();
            celldata.add(Constants.test_name);
            celldata.add(Constants.Test_status);
            int currentRow = 0;
            for (int setcell = 0; setcell < row.getLastCellNum(); setcell++) {

                String mapkey = Constants.Xlheader_name.get(setcell);
                Constants.Writecellvalue.put(mapkey, celldata.get(setcell));
                if (setcell == 0) {
                    Row newRow = sheet.createRow(totalrowcount + 1);
                    Cell newCell = newRow.createCell(setcell);
                    newCell.setCellValue(Constants.Writecellvalue.get(mapkey));
                    currentRow = totalrowcount + 1;
                } else {
                    Row oldrow = sheet.getRow(currentRow);
                    Cell newCell = oldrow.createCell(setcell);
                    newCell.setCellValue(Constants.Writecellvalue.get(mapkey));
                }

                fos = new FileOutputStream(file);
                workbook.write(fos);
            }

            fis.close();
            fos.close();

        } catch (Exception e) {
            throw (e);
        }
    }

    public void getRowCount(String sheetname) throws Exception {
        File file = new File(Xlpath + xlname);
        Constants.Cellvalue.clear();
        Constants.sheet_name = sheetname;
        try {
            FileInputStream fis = new FileInputStream(file);
            String fileXtnName = xlname.substring(xlname.indexOf("."));
            if (fileXtnName.equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileXtnName.equalsIgnoreCase(".xls")) {
                workbook = new HSSFWorkbook(fis);

            }

            sheet = workbook.getSheet(sheetname);
            rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            Constants.iterationcount = rowcount;
        } catch (Exception Ex) {
            throw (Ex);
        }
    }

    public void removeRow(String sheetname) throws Exception {
        String xlname = "Testdata.xlsx";
        File file = new File(Xlpath + xlname);
        Constants.Xlheader_name.clear();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            String fileXtnName = xlname.substring(xlname.indexOf("."));
            if (fileXtnName.equalsIgnoreCase(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileXtnName.equalsIgnoreCase(".xls")) {
                workbook = new HSSFWorkbook(fis);

            }

            sheet = workbook.getSheet(sheetname);
            rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            XSSFRow removingRow = (XSSFRow) sheet.getRow(1);
            if (removingRow != null) {
                //sheet.removeRow(removingRow);
                sheet.shiftRows(2, rowcount, -1);
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (Exception e) {
            throw (e);
        }
        fis.close();
        fos.close();

    }
}
