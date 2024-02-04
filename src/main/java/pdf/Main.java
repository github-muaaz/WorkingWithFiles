package pdf;

import com.google.zxing.WriterException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, WriterException {
        Demo demo = new Demo();
        demo.createPdf();
    }
}
