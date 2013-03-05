/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/util/ImageUtil.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author elihuwu
 * @version $Revision: 29 $
 */
public class ImageUtil {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 29 $";
	
	public static ValidationCode getValidationCode(int width, int height, int codeAmount, char[] randomSequence){
		int fontHeight;
		int rectWidth, rectHeight;
		int offsetWidth;
		int addition;
		int codeHight;

		fontHeight = height - 2;
		rectWidth = width - 1;
		rectHeight = height - 1;
		offsetWidth = width / (codeAmount + 1);
		addition = offsetWidth / 2;
		codeHight = height - 4;

		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		Random random = new Random();

		g.setColor(getRangeColor(200, 250));

		g.fillRect(0, 0, width, height);

		Font font = new Font("Times New Roman", Font.PLAIN, fontHeight);
		g.setFont(font);

		g.setColor(Color.BLACK);
		g.drawRect(0, 0, rectWidth, rectHeight);

		g.setColor(getRangeColor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		StringBuffer code = new StringBuffer();

		int red = 0, green = 0, blue = 0;

		for (int i = 0; i < codeAmount; i++) {
			int index = random.nextInt(35);
			String strRand = String.valueOf(randomSequence[index]);

			code.append(strRand);

			red = random.nextInt(110);
			green = random.nextInt(50);
			blue = random.nextInt(50);

			g.setColor(new Color(20 + red, 20 + green, 20 + blue));
			g.drawString(strRand, offsetWidth * i + addition, codeHight);

		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] imageBytes;
		
		try {
	        ImageIO.write(buffImg, "jpeg", baos);
	        imageBytes = baos.toByteArray();
        } catch (IOException e) {
	        throw new RuntimeException(e);
        } finally {
        	try {
	            baos.close();
            } catch (IOException e) {
            	 throw new RuntimeException(e);
            }
        }
		return new ValidationCode(code.toString(), imageBytes);
	}
	
	private static Color getRangeColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 200;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}


}
