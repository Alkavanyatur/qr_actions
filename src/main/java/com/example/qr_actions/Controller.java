package com.example.qr_actions;

/**
 * 
 * @author jelorza
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@RestController
public class Controller {

	private final HtmlService htmlService;

	@Autowired
	public Controller(HtmlService htmlService) {
	        this.htmlService = htmlService;
}

	/**
	 * Returns a simple test string.
	 *
	 * @return The test string "test".
	 */
	@GetMapping("/test")
	public String hello() {
		return "test";
	}

	/**
	 * Generates a QR code image from the provided QR code content.
	 *
	 * @param qrCode The content to encode into the QR code.
	 * @return ResponseEntity containing the QR code image as a byte array.
	 * @throws Exception If QR code generation fails.
	 */
	@GetMapping("/qr/{qrCode}")
	public ResponseEntity<byte[]> generateQRCode(@PathVariable String qrCode) throws Exception {

		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			Map<EncodeHintType, Object> hints = new HashMap<>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			String qrContent = qrCode + "@" + System.currentTimeMillis(); // Append timestamp

			// Generate QR code as BufferedImage
			BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200, hints);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			// Convert BufferedImage to byte array
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(qrImage, "png", stream);
			byte[] imageBytes = stream.toByteArray();

			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
		} catch (WriterException ex) {
			throw new Exception("Error generating QR code", ex);
		}
	}

	@GetMapping("/qr-page/{qrCode}")
    public String qrPage(@PathVariable String qrCode) {
        return htmlService.generateHtml(qrCode);
    }
}