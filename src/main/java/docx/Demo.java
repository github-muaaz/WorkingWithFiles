package docx;


import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Demo {
    Scanner scanner = new Scanner(System.in);
    Scanner scannerLn = new Scanner(System.in);
    public void createDocx() throws IOException {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph p = document.createParagraph();

        XWPFRun text = p.createRun();
        text.setText("PDP ACADEMY ");
        text.setBold(true);

        text = p.createRun();
        text.setText("rahbari ");

        text = p.createRun();
        text.setText("O.Mirzayev ");
        text.setBold(true);

        text = p.createRun();
        text.setText("ga\n" +
                " B-20 guruh talabasi ");

        text = p.createRun();

        System.out.print("name: ");
        String name = scanner.next();
        name = name.toUpperCase();

        System.out.print("surname: ");
        String surname = scanner.next();
        surname = surname.toUpperCase();

        text.setText(surname + " " + name);
        text.setBold(true);

        text = p.createRun();
        text.setText(" tomonidan");
        text.setFontFamily("src/main/resources/fonts/times.ttf");

        p.setAlignment(ParagraphAlignment.CENTER);
        p.setIndentationLeft(5500);


        p = document.createParagraph();
        p.setSpacingBeforeLines(200);
        text = p.createRun();
        text.setText("ARIZA");
        text.setBold(true);
        text.setFontFamily("src/main/resources/fonts/times.ttf");

        text.setTextPosition(30);
        p.setAlignment(ParagraphAlignment.CENTER);


        p = document.createParagraph();
        p.setFirstLineIndent(600);
        p.setAlignment(ParagraphAlignment.BOTH);
        text = p.createRun();
        String matn = "Men B-20 guruhda o’qish davomida berilgan bilimlarni o’zlashtirishda sustkashlikka yo’l qo’yganlgim, darsga o’z vaqtida kelmaganligim, mentorimiz tomonidan berilgan vazifalarni o’z vaqtida va sifatli bajara olmaganligim uchun meni B-36 guruhda o’qshimga ruxsat berishingizni so’rayman.";
        System.out.println("do you want to change text");
        System.out.println("1 - yes");
        System.out.println("2 - no");

        int option = scanner.nextInt();
        if (option == 1){
            System.out.print("enter text: ");
            matn = scannerLn.nextLine();
        }
        text.setText(matn);
        text.setFontFamily("src/main/resources/fonts/times.ttf");



        p = document.createParagraph();
        p.setAlignment(ParagraphAlignment.RIGHT);
        p.setSpacingBeforeLines(500);
        text = p.createRun();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        text.setText("Sana: " + format.format(date));
        text.setBold(true);


        p = document.createParagraph();
        p.setAlignment(ParagraphAlignment.RIGHT);
        p.setSpacingBeforeLines(200);
        text = p.createRun();
        text.setText("Imzo: ____________");
        text.setBold(true);




        FileOutputStream out = new FileOutputStream( "src/main/resource/ariza.docx");
        document.write(out);

        out.close();

        System.out.println("created");
    }
}
