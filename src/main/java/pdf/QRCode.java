package pdf;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class QRCode {

    private void qr(String content, String path) throws WriterException, IOException {
        int size = 200;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size);

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(Color.RED);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bitMatrix.get(i, j))
                    graphics.fillRect(i, j, 1, 1);
            }
        }

        ImageIO.write(image, "png", new FileOutputStream(path));
    }

    public void createQR(String content) throws IOException, WriterException {
        String path = "src/main/resource/pdf/QR.png";

        qr(content, path);

        System.out.println("QR Code created successfully.");
    }
}
