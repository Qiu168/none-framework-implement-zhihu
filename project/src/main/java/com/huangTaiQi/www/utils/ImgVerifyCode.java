package com.huangTaiQi.www.utils;

import com.huangTaiQi.www.constant.ImgConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import static com.huangTaiQi.www.constant.ImgConstants.*;

/**
 * @author 14629
 */
public class ImgVerifyCode {



    /**
     *  用来保存验证码的文本内容
     */
    private String text;
    /**
     * 获取随机数对象
     */
    private final Random r = new Random();




    /**
     * 获取随机的颜色
     *
     * @return 返回一个随机颜色
     */
    private Color randomColor() {
        //这里为什么是225，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int r = this.r.nextInt(225);
        int g = this.r.nextInt(225);
        int b = this.r.nextInt(225);
        return new Color(r, g, b);
    }

    /**
     * 获取随机字体
     *
     * @return 返回一个随机的字体
     */
    private Font randomFont() {
        //获取随机的字体
        int index = r.nextInt(FONT_NAMES.length);
        String fontName = FONT_NAMES[index];
        //随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int style = r.nextInt(4);
        //随机获取字体的大小
        int size = r.nextInt(10) + 24;
        return new Font(fontName, style, size);
    }

    /**
     * 获取随机字符
     *
     * @return 随机字符
     */
    private char randomChar() {
        int index = r.nextInt(CODES.length());
        return CODES.charAt(index);
    }

    /**
     * 画干扰线，验证码干扰线用来防止计算机解析图片
     *
     * @param image 图片
     */
    private void drawLine(BufferedImage image) {
        //定义干扰线的数量
        int num = r.nextInt(10);
        Graphics2D g = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(WEIGHT);
            int y1 = r.nextInt(HEIGHT);
            int x2 = r.nextInt(WEIGHT);
            int y2 = r.nextInt(HEIGHT);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 创建图片的方法
     *
     * @return 返回一个图片
     */
    private BufferedImage createImage() {
        //创建图片缓冲区
        BufferedImage image = new BufferedImage(WEIGHT, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        //设置背景色随机
        g.setColor(new Color(255, 255, r.nextInt(245) + 10));
        g.fillRect(0, 0, WEIGHT, HEIGHT);
        return image;
    }

    /**
     * 获取验证码图片的方法
     *
     * @return 最终的图片
     */
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();
        //画四个字符即可
        for (int i = 0; i < TEST_NUMS; i++)
        {
            //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            String s = randomChar() + "";
            //添加到StringBuilder里面
            sb.append(s);
            //定义字符的x坐标
            float x = i * 1.0F * WEIGHT / 4;
            //设置字体，随机
            g.setFont(randomFont());
            //设置颜色，随机
            g.setColor(randomColor());
            g.drawString(s, x, HEIGHT - 5);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 获取验证码文本的方法
     *
     * @return 验证码文本
     */
    public String getText() {
        return text;
    }

    /**
     * 将验证码图片写出的方法
     */
    public static void output(BufferedImage image, OutputStream out) throws IOException
    {
        ImageIO.write(image, "JPEG", out);
    }
}
