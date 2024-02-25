import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ImageProcessor image = new ImageProcessor("images/image2.jpg");
        image.invert();


    }
}