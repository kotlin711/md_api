package org.example.api.common.io;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class Qrcode {
    public  static  byte[] getQRCodeImage( String text,int width,int height) {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix encode;
        try {
            encode = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(encode, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException | IOException ignored) {
        }
        return null;
    }
}
