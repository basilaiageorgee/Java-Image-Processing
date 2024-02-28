import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {
    private BufferedImage image;
    private final int width;
    private final int height;


    public ImageProcessor(String path) {
        try {
            this.image = ImageIO.read(new File("images/image2.jpg"));
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        this.width = image.getWidth();
        this.height  = image.getHeight();
    }

    public void printPixelValues(){
        for (int y= 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int pixel = image.getRGB(x,y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                System.out.println("Pixel values at (" + x + "," + y + "): Red = " + red + ", Green = " + green + ", Blue = " + blue);
            }
        }
    }

    public void invert(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p>>24) & 0xff;
                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;

                // manipulate the color values of the pixels here
                // in this case we are inverting the color values
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(x, y, p);
            }
        }

        display();
    }


    public void grayscale(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                // Calculate the gray value based on weighted averaging technique
                // Using the formula 0.3 * red + 0.59 * green + 0.11 * blue
                //   int gray = (int)(0.3 * red + 0.59 * green + 0.11 * blue);
                // Calculate the gray value by simple averaging the red, green, and blue values
                int gray = (red + green + blue) / 3;
                // Combine the gray value into a single int representing the gray pixel
                int newPixel = (gray << 16) | (gray << 8) | gray;
                // Set the new pixel value at (x, y)
                image.setRGB(x, y, newPixel);
            }
        }
        display();
    }

    public void display(){
        JFrame frame = new JFrame();

        frame.setSize(this.width, this.height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);

    }

    public void sepia(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p>>24) & 0xff;
                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;

                // Sepia filter calculation
                int tr = (int)(0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int)(0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int)(0.272 * r + 0.534 * g + 0.131 * b);

                // ensuring the calculated values are within range
                r = tr > 255 ? 255 : tr;
                g = tg > 255 ? 255 : tg;
                b = tb > 255 ? 255 : tb;

                p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(x, y, p);
            }
        }
        display();
    }

    public void binarize(int threshold){
        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;
                if (avg > threshold) {
                    // Set pixel to white
                    thresholdedImage.setRGB(x, y, 0xffffff);
                } else {
                    // Set pixel to black
                    thresholdedImage.setRGB(x, y, 0x000000);
                }
            }
        }

        display();
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
