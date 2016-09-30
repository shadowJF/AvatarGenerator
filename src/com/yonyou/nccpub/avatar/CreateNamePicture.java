package com.yonyou.nccpub.avatar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * @Title: ImgGenerator.java
 * @Package com.yonyou.nccpub.avatar
 * @project java app
 * @Description: 将用户的姓名生成图片
 */

public class CreateNamePicture {
  /**
   * @throws IOException
   * @throws
   *
   **/
  public static void main(String[] args) throws IOException {
    String fileName = "D:/name.txt";
    List<String> nameList = readFileByLine(fileName);
    for (int i = 0; i < nameList.size(); i++) {
      generateImg(nameList.get(i), "D:/name", nameList.get(i));
    }

  }

  /**
   * 
   * readFileByLine(将文件中的文字一行一行读取出来并存放在List中返回) TODO(这里描述这个方法适用条件 – 不允许是图片等特殊文件)
   * 
   * @Title: readFileByLine
   * @Description: TODO
   * @param @param filename
   * @param @return
   * @param @throws IOException
   * @param @throws FileNotFoundException 设定文件
   * @return List<String> 返回类型
   * @throws
   */
  public static List<String> readFileByLine(String filename) throws IOException,
      FileNotFoundException {
    List<String> nameList = new ArrayList<String>();
    File file = new File(filename);
    InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
    BufferedReader reader = new BufferedReader(isr);
    String tmp;
    while ((tmp = reader.readLine()) != null) {
      nameList.add(tmp);
    }
    reader.close();
    return nameList;
  }

  /**
   * @Title: generateImg(生成图片)
   * @Description: TODO
   * @param name 姓名
   * @param outputPath 图片生成路径 如：D:/name
   * @param outputName 图片文件名，结合生成路径，最后生成文件为 D:/name/outputName.jpg
   * @throws IOException 设定文件
   * @return void 返回类型
   */
  public static void generateImg(String name, String outputPath, String outputName)
      throws IOException {
    int width = 100;
    int height = 100;
    int nameLen = name.length();
    String nameWritten;
    if (nameLen <= 2) {
      nameWritten = name;
    } else {
      String first = name.substring(0, 1);
      if (isChinese(first)) {
        nameWritten = name.substring(nameLen - 2);
      } else {
        nameWritten = name.substring(0, 2).toUpperCase();
      }
    }

    String filename = outputPath + File.separator + outputName + ".jpg";
    File file = new File(filename);
    Font font = new Font("微软雅黑", Font.PLAIN, 30);

    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    Graphics2D g2 = (Graphics2D) bi.getGraphics();
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setBackground(getRandomColor());

    g2.clearRect(0, 0, width, height);

    g2.setFont(font);
    g2.setPaint(Color.WHITE);

    String firstWritten = nameWritten.substring(0, 1);
    String secondWritten = nameWritten.substring(1, 2);

    if (isChinese(firstWritten) && isChinese(secondWritten)) {
      g2.drawString(nameWritten, 20, 60);
    } else if (!isChinese(firstWritten) && !isChinese(secondWritten)) {
      g2.drawString(nameWritten, 32, 60);
    } else if (!isChinese(firstWritten) && isChinese(secondWritten)) {
      g2.drawString(nameWritten, 26, 60);
    } else if (isChinese(firstWritten) && !isChinese(secondWritten)) {
      g2.drawString(nameWritten, 27, 60);
    }

    BufferedImage rounded = ChangeImageUtil.makeRoundedCorner(bi, 99);
    ImageIO.write(rounded, "png", file);
  }

  /**
   * drawStar(如果名字为三个字，调用这个方法画五角星)
   * 
   * @Title: drawStar
   * @Description: TODO
   * @param @param g2 设定文件
   * @return void 返回类型
   * @throws
   */
  public static void drawStar(Graphics2D g2) {
    // 定义外切圆和内切圆的半径
    int R = 16;
    int r = (int) (R * Math.sin(Math.PI / 10) / Math.sin(3 * Math.PI / 10));
    // 定义两个数组，分别存放10个顶点的X，Y坐标
    int[] x = new int[10];
    int[] y = new int[10];
    for (int i = 0; i < 10; i++) {
      if (i % 2 == 0) {
        x[i] = 30 + (int) (R * Math.cos(Math.PI / 10 + (i - 1) * Math.PI / 5));
        y[i] = 70 + (int) (R * Math.sin(Math.PI / 10 + (i - 1) * Math.PI / 5));
      } else {
        x[i] = 30 + (int) (r * Math.cos(Math.PI / 10 + (i - 1) * Math.PI / 5));
        y[i] = 70 + (int) (r * Math.sin(Math.PI / 10 + (i - 1) * Math.PI / 5));
      }
    }
    g2.setPaint(Color.white);
    // 调用fillPolygon方法绘制
    g2.fillPolygon(x, y, 10);
  }

  public static boolean isChinese(String str) {
    String regEx = "[\\u4e00-\\u9fa5]+";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    if (m.find())
      return true;
    else
      return false;
  }

  /**
   * getRandomColor(随机产生颜色)
   * 
   * @Title: getRandomColor
   * @Description: TODO
   * @param @return 设定文件
   * @return Color 返回类型
   * @throws
   */
  private static Color getRandomColor() {
    String[] beautifulColors =
        new String[] {"232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52",
            "237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154",
            "249,205,173", "200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7",
            "118,77,57", "17,63,61", "60,79,57", "95,92,51", "179,214,110", "248,147,29",
            "227,160,93", "178,190,126", "114,111,238", "56,13,49", "89,61,67", "250,218,141",
            "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53", "137,190,178",
            "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84",
            "217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128",
            "178,200,187", "69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46",
            "171,92,37", "100,107,48", "98,65,24", "54,37,17", "137,157,192", "250,227,113",
            "29,131,8", "220,87,18", "29,191,151", "35,235,185", "213,26,33", "160,191,124",
            "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213", "167,220,224",
            "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202",
            "224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67",
            "38,157,128", "201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186",
            "255,66,93", "147,224,255", "247,68,97", "185,227,217"};
    int len = beautifulColors.length;
    Random random = new Random();
    String[] color = beautifulColors[random.nextInt(len)].split(",");
    return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]),
        Integer.parseInt(color[2]));
  }
}
