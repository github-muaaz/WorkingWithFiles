package year;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Year {
    Scanner scanner = new Scanner(System.in);
    public void start(){
        System.out.print("enter year: ");
        int year = scanner.nextInt();

        System.out.println("where you want to open");
        System.out.print("enter path: ");
        String path = scanner.next();

        if (!checkPath(path)){
            if (!createPath(path))
                return;
        }

        createCalendarFiles(year, path);
    }

    private void createCalendarFiles(int year, String path) {
        LocalDate dateMain = LocalDate.of(year, 1, 1);
        path+="/"+year;
        File file = new File(path);
        file.mkdir();

        String[] months = new DateFormatSymbols().getMonths();

        int monthDays;
        for (int i = 0; i < 12; i++) {
            String monthsPath = path +"/"+ months[i];
            File file1 = new File(monthsPath);
            file1.mkdir();

            monthDays = monthDays(year, i);
            for (int j = 0; j < monthDays; j++) {
                String daysPath = monthsPath + "/";
//                File file2 = new File(daysPath);
//                file2.mkdir();


//creating txt file
//                FindDate findDate = new

                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String month = month(dateMain);
                String day = day(dateMain);
                String year1 = year(dateMain);
                String dayText = daysPath + "/" + day + "."+ month + "." + year1 + ".txt";
                File file3 = new File(dayText);
                dateMain = dateMain.plusDays(1);

                try {
                    if (file3.createNewFile()){
//                        System.out.println(file3.getName() + " created");
                    }
                    else
                        System.out.println(file3.getName() + " can't created");
                } catch (IOException e) {
                    System.err.println(file3.getName() + " no such file");
                }

                try (FileWriter writer = new FileWriter(file3)
                ) {
                    writer.write("File name: " + file3.getName());
                    writer.write("\nFile path: " + file3.getPath());
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    writer.write("\nCreated data: " + format.format(file3.lastModified()));

                } catch (IOException e) {
                    System.out.println("file is not exist");
                }
            }
        }

        System.out.println("created successfully");

//        /Users/user/IdeaProjects/Year

    }

    private String year(LocalDate dateMain) {
        int y = dateMain.getYear();
        if (y < 10) return "0" + y;
        else return "" + y;
    }

    private String day(LocalDate dateMain) {
        int d = dateMain.getDayOfMonth();
        if (d < 10) return "0" + d;
        else return "" + d;
    }

    private String month(LocalDate dateMain) {
        int m = dateMain.getMonth().getValue();
        if (m < 10) return "0" + m;
        else return "" + m;
    }

    private int monthDays(int year, int month) {
        switch (month){
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                if (isLeapYear(year)) return 29;
                else return 28;
        }
    }

    private boolean isLeapYear(int year) {
        return (year%4==0 && year%100!=0) || year%400==0;
    }

    private boolean createPath(String path){
        System.out.println("no such direction");
        System.out.println("1 - create");
        System.out.println("2 - cancel");

        int option = scanner.nextInt();
        if (option == 1){
            File file = new File(path);
            if (!file.mkdirs())
                System.out.println("can't crate path");
            else{
                System.out.println("Path created successfully");
                return true;
            }

        }
        return false;
    }

    private boolean checkPath(String path){
        File file = new File(path);
        return file.exists();
    }
}
