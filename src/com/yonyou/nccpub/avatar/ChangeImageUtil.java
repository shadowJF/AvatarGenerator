package com.yonyou.nccpub.avatar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 改变图片的工具类
 * 
 * @ClassName: ChangeImage
 * @Description: TODO
 * @author lixunhui@qq.com
 * @date 2013年10月14日 下午3:34:56
 *
 */
public class ChangeImageUtil {

  /**
   * makeRoundedCorner(图片做圆角处理)
   * 
   * @Title: makeRoundedCorner
   * @Description: TODO
   * @param @param image
   * @param @param cornerRadius
   * @param @return 设定文件
   * @return BufferedImage 返回类型
   * @throws
   */

  public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
    int w = image.getWidth();
    int h = image.getHeight();
    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = output.createGraphics();
    g2.setComposite(AlphaComposite.Src);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.WHITE);
    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
    g2.setComposite(AlphaComposite.SrcAtop);
    g2.drawImage(image, 0, 0, null);
    g2.dispose();
    return output;
  }

  /**
   * createResizedCopy(图片缩放处理)
   * 
   * @Title: createResizedCopy
   * @Description: TODO
   * @param @param originalImage
   * @param @param scaledWidth
   * @param @param scaledHeight
   * @param @param preserveAlpha
   * @param @return 设定文件
   * @return BufferedImage 返回类型
   * @throws
   */
  public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth,
      int scaledHeight, boolean preserveAlpha) {
    int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    Graphics2D g = scaledBI.createGraphics();
    if (preserveAlpha) {
      g.setComposite(AlphaComposite.Src);
    }
    g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
    g.dispose();
    return scaledBI;
  }

  public static void main(String[] args) throws IOException {
    BufferedImage icon = ImageIO.read(new File("D:/name/梁竞帆.jpg"));
    BufferedImage rounded = makeRoundedCorner(icon, 99);
    ImageIO.write(rounded, "png", new File("D:/name/mm_梁竞帆.png"));

    // BufferedImage pic = ImageIO.read(new File("D:/name/张强.jpg"));
    // BufferedImage resized = createResizedCopy(pic, pic.getWidth() / 2, pic.getHeight() / 2,
    // true);
    // ImageIO.write(resized, "jpg", new File("D:/name/name2_small.jpg"));
  }

}
