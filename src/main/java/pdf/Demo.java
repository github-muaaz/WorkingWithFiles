package pdf;

import com.google.zxing.WriterException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;

import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Demo {
    Scanner scanner = new Scanner(System.in);
    Scanner scannerLn = new Scanner(System.in);

    public void createPdf() throws IOException, WriterException {
        System.out.println("taxpayer information");
        System.out.print("name: ");
        String name = scanner.next();

        System.out.print("surname: ");
        String surname = scanner.next();

        System.out.print("family name: ");
        String familyName = scanner.next();

        System.out.print("taxpayer ID: ");
        String id = scanner.next();

        System.out.print("ЖШШИР: ");
        String jshir = scanner.next();

        System.out.print("Region: ");
        String region = scannerLn.nextLine();
//        region = "YAKKASAROY TUMANI\nЯККАСАРАЙСКИЙ р-н";

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String inn = formatter.format(date);


        PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/times.ttf", PdfEncodings.IDENTITY_H);

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter("src/main/resource/pdf/Docs/"+surname+"_"+id+".pdf"));
        Document doc = new Document(pdfDoc);

        Div body = new Div();

//        top
        body.add(top());

//        header title
        header(body);

//        taxpayer info
        body.add(taxpayerInfo(name, surname, familyName));

//        content about tax
        body.add(taxContent());

//        taxpayer info table
        body.add(table2(id, jshir, region, inn));

//        QR code
        body.add(qrCode(name+" "+surname+" "+familyName, inn));


        body.setFont(font);
        body.setPadding(5F);
        body.setPaddingTop(10);

        doc.add(body);
        doc.close();

        System.out.println("Done");
    }

    private Div taxContent() {
        Div div = new Div();
        Paragraph p = createParagraph("солик тўловчининг идентификация раками берилди ва солик тўловчи хакидаги ҳисобга куйиш маълумотлари Ўзбекистон Республикаси солик туловчиларининг Ягона реестрига киритилди\n\n" +
                "присвоен идентификационный номер налогоплательщика с введением учетных данных о налогоплательщике в Единый реестр налогоплательщиков Республики Узбекистан", false, 12.4F, false);
        div.add(p);
        div.setPadding(5);

        return div;
    }

    private Cell createCell(String content, float margin, float marginTop){
        Paragraph p = new Paragraph(content);
        p.setMargin(margin);
        p.setMarginTop(marginTop);
        p.setFixedLeading(12F);
        Cell cell = new Cell().add(p);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Table top(){
        Table table = new Table(2);

        table.addCell(createCell("ЎЗБЕКИСТОН РЕСПУБЛИКАСИ АДЛИЯ ВАЗИРЛИГИ ҲУЗУРИДАГИ ДАВЛАТ ХИЗМАТЛАРИ АГЕНТЛИГИ", 0, 0));
        table.addCell(createCell("ЎЗБЕКИСТОН РЕСПУБЛИКАСИ ДАВЛАТ СОЛИҚ ҚЎМИТАСИ", 0, 0));
        table.addCell(createCell("АГЕНТСТВО ГОСУДАРСТВЕННЫХ УСЛУГ ПРИ МИНИСТЕРСТВЕ ЮСТИЦИИ УЗБЕКИСТАНА", 10, 7));
        table.addCell(createCell("ГОСУДАРСТВЕННЫЙ НАЛОГОВЫЙ КОМИТЕТ РЕСПУБЛИКИ УЗБЕКИСТАН", 10, 7));

        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setFontSize(12F);
        table.setMarginLeft(-10);
        table.setBold();

        return table;
    }

    private Paragraph createParagraph(String content, boolean bold, float fontSize, boolean center){
        Paragraph p = new Paragraph(content);
        if (bold)
            p.setBold();
        p.setFontSize(fontSize);
        p.setFixedLeading(fontSize);
        if (center)
            p.setTextAlignment(TextAlignment.CENTER);

        return p;
    }

    private Cell cell(Paragraph p, boolean border, float width){
        Cell cell = new Cell().add(p);
        if (!border)
            cell.setBorder(Border.NO_BORDER);
        cell.setWidth(width);

        return cell;
    }

    private Table qrCode(String NSF, String inn) throws IOException, WriterException {
        NSF = NSF.toUpperCase();
        Table table = new Table(2);

        QRCode qrCode = new QRCode();
        qrCode.createQR("FISH : "+NSF+"\nSTIR/INN : "+ inn);

        ImageData data = ImageDataFactory.create("src/main/resource/pdf/QR.png");
        Image image = new Image(data);
        image.setWidth(80);
        image.setHeight(80);

        Cell cell = new Cell().add(image);
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(70);
        table.addCell(cell);


        Paragraph p = createParagraph("Маълумотномада кўрсатилган маълумотлар тўгрилигини текшириш учун мобил телефон ёрдамида QR - кодни сканер килинг ." +
                "\n Отсканируйте QR - код при помощи мобильного устройства для проверки подлинности информации .", false, 12.4F, false);
        cell = cell(p, false, 4000);
        cell.setPaddingTop(17);
        table.addCell(cell);

        return table;
    }

    private Table table2(String id, String jshir, String region, String inn) {
        region = region.toUpperCase();

        Table table = new Table(2);
        Cell cell;

        Paragraph p = createParagraph("Солик тўловчининг идентификация раками :\n Идентификационный номер налогоплательщика :", true, 13F, false);
        cell = cell(p, false, 330);
        table.addCell(cell);


        p = createParagraph(id, true, 18, true);
        p.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell = cell(p, true, 190);
        cell.setPaddingTop(5);
        table.addCell(cell);


        p = createParagraph("ЖШШИР :\n ПИНФЛ : ", false, 12.4F, false);
        cell = cell(p, false, 330);
        cell.setPaddingTop(15);
        table.addCell(cell);



        p = createParagraph(jshir, false, 14F, false);
        cell = cell(p, false, 190);
        cell.setPaddingTop(20);
        table.addCell(cell);


        p = createParagraph("Ариза кабул килган давлат хизмати маркази :\n Заявление принято центром государственном услуг : ", false, 12.4F, false);
        cell = cell(p, false, 330);
        cell.setPaddingTop(15);
        table.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);


        p = createParagraph("Ҳисобга куйган давлат солик инспекцияси :\n Поставлен на учет в государственой налоговой инспекции :", false, 12.4F, false);
        cell = cell(p, false, 330);
        cell.setPaddingTop(15);
        table.addCell(cell);


        p = createParagraph(region, false, 12.4f, false);
        p.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell = cell(p, false, 190);
        cell.setPaddingTop(15);
        table.addCell(cell);

        p = createParagraph("СТИР берилган сана :\n Дата присвоения ИНН :", false, 12.4f, false);
        cell = cell(p, false,330);
        cell.setPaddingTop(15);
        table.addCell(cell);


        p = createParagraph(inn, false, 14F, false);
        cell = cell(p, false, 190);
        cell.setPaddingTop(20);
        table.addCell(cell);

        table.setMarginTop(15);
        table.setMarginBottom(15);
        return table;
    }

    private Table taxpayerInfo(String name, String surname, String familyName) {
        name = name.toUpperCase();
        surname = surname.toUpperCase();
        familyName = familyName.toUpperCase();

        Table table = new Table(2);

        table.addCell(tableCell("Фамилияси :\n Фамилия : ", 13F).setWidth(220));

        table.addCell(tableCell(name, 17));

        table.addCell(tableCell("Исми :\n Имя : ", 13F).setWidth(220));

        table.addCell(tableCell(surname, 17));

        table.addCell(tableCell("Отасини исми :\n Отчество :", 13F).setWidth(220));

        table.addCell(tableCell(familyName, 17));

        table.setMarginLeft(5);
        table.setMarginTop(15);

        return table;
    }

    private Cell tableCell(String str, float fontSize){
        Paragraph p = new Paragraph(str);
        p.setMarginBottom(10);
        Cell cell = new Cell().add(p);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(fontSize);

        return cell;
    }

    private void header(Div body) {
        Paragraph p = createParagraph("Солик тўловчи хисобга куйилганлиги тўғрисида", true, 13F, true);
        p.setMarginLeft(-5);
        body.add(p);

        p = createParagraph("ГУВОХНОМА", true, 13F, true);
        p.setCharacterSpacing(5F);
        p.setPaddingTop(-5F);
        body.add(p);


        p = createParagraph("СВИДЕТЕЛЬСТВО о постановке на учет налогоплательщика", true, 13F, true);
        p.setPaddingTop(-5F);
        body.add(p);


        p = createParagraph("СОЛИҚ ТЎЛОВЧИ - ЖИСМОНИЙ ШАХСГА", false, 13F, true);
        p.setCharacterSpacing(3);
        p.setMarginTop(10);
        body.add(p);

        p = createParagraph("НАЛОГОПЛАТЕЛЬЩИКУ - ФИЗИЧЕСКОМУ ЛИЦУ", false, 13F, true);
        p.setCharacterSpacing(3);
        p.setPaddingTop(-10F);
        body.add(p);
    }

}
