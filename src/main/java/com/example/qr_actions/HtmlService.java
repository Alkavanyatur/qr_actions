package com.example.qr_actions;
/**
 * 
 * @author jelorza
 */
import org.springframework.stereotype.Service;

@Service
public class HtmlService {

    public String generateHtml(String qrCode) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Refresh QR Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<img id=\"qrImage\" src=\"/qr/" + qrCode + "\" alt=\"QR Code\" />\n" +
                "\n" +
                "<script>\n" +
                "    function refreshQRCode() {\n" +
                "        var qrImage = document.getElementById(\"qrImage\");\n" +
                "        qrImage.src = \"/qr/" + qrCode + "?\" + new Date().getTime();\n" +
                "    }\n" +
                "\n" +
                "    setInterval(refreshQRCode, 5000);\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}