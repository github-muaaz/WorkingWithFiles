package exel;

import com.google.gson.Gson;
import exel.User.User;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {

    private List<User> users = new ArrayList<>();
    private final XSSFWorkbook document = new XSSFWorkbook();

    private void getUsers() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resource/excel/users.txt"));
        Gson gson = new Gson();
        String str = reader.readLine();

        StringBuilder builder = new StringBuilder();

        while (str != null){
            builder.append(str);
            str = reader.readLine();
        }

        User[] usersArray = gson.fromJson(builder.toString(), User[].class);

        users = new ArrayList<>(Arrays.asList(usersArray));
    }

    private void createCells(XSSFRow row, int columnIndex, String value){
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellValue(value);

        CellStyle style = document.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        cell.setCellStyle(style);
    }

    private void merging(XSSFSheet sheet){
        marge(sheet, 0, 2, 0, 0);
        marge(sheet, 0, 2, 1, 1);
        marge(sheet, 0, 2, 2, 2);
        marge(sheet, 0, 2, 3, 3);
        marge(sheet, 1, 2, 6, 6);
        marge(sheet, 1, 2, 5, 5);
        marge(sheet, 0, 0, 5, 10);
        marge(sheet, 0, 2, 4, 4);
        marge(sheet, 1, 2, 7, 7);
        marge(sheet, 1, 2, 8, 8);
        marge(sheet, 1, 1, 9, 10);

        marge(sheet, 0, 2, 11, 11);
        marge(sheet, 0, 2, 12, 12);
        marge(sheet, 0, 0, 13, 15);


        marge(sheet, 1, 2, 13, 13);
        marge(sheet, 1, 2, 14, 14);
        marge(sheet, 1, 2, 15, 15);
    }

    private void createHeader(XSSFSheet sheet){
        merging(sheet);
        XSSFRow row = sheet.createRow(0);

        createCells(row, 0, "TR");
        createCells(row, 1, "ID");
        createCells(row, 2, "NAME");
        createCells(row, 3, "USERNAME");
        createCells(row, 4, "EMAIL");
        createCells(row, 5, "ADDRESS");

        XSSFRow row1 = sheet.createRow(1);
        createCells(row1, 5, "STREET");
        createCells(row1, 6, "SUITE");
        createCells(row1, 7, "CITY");
        createCells(row1, 8, "ZIPCODE");
        createCells(row1, 9, "GEO");

        XSSFRow row2 = sheet.createRow(2);
        createCells(row2, 9, "LAT");
        createCells(row2, 10, "LNG");

        createCells(row, 11, "PHONE");
        createCells(row, 12, "WEBSITE");
        createCells(row, 13, "COMPANY");

        createCells(row1, 13, "NAME");
        createCells(row1, 14, "CATCHPHRASE");
        createCells(row1, 15, "BS");

    }

    private void marge(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol){
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    public void createXlsx() throws IOException {
        getUsers();

        XSSFSheet sheet = document.createSheet();

        sheet.autoSizeColumn(0, true);
        createHeader(sheet);

        createUsersList(sheet);

        for (int i = 0; i < 16; i++) {
            sheet.autoSizeColumn(i);
        }


        FileOutputStream out = new FileOutputStream("src/main/resource/excel/users.xlsx");

        document.write(out);
        out.close();

        System.out.println("created");
    }

    private void createUsersList(XSSFSheet sheet) {
        XSSFRow row;
        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i+3);
            createCells(row, 0, i+1+"");
            createCells(row, 1, users.get(i).getId()+"");
            createCells(row, 2, users.get(i).getName());
            createCells(row, 3, users.get(i).getUsername());
            createCells(row, 4, users.get(i).getEmail());
            createCells(row, 5, users.get(i).getAddress().getStreet());
            createCells(row, 6, users.get(i).getAddress().getSuite());
            createCells(row, 7, users.get(i).getAddress().getCity());
            createCells(row, 8, users.get(i).getAddress().getZipcode());
            createCells(row, 9, users.get(i).getAddress().getGeo().getLat());
            createCells(row, 10, users.get(i).getAddress().getGeo().getLng());
            createCells(row, 11, users.get(i).getPhone());
            createCells(row, 12, users.get(i).getWebsite());
            createCells(row, 13, users.get(i).getCompany().getName());
            createCells(row, 14, users.get(i).getCompany().getCatchPhrase());
            createCells(row, 15, users.get(i).getCompany().getBs());
        }

    }

}
