package Server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * RenderImage class - to Render Mandelbrot Image
 */
public class RenderImage<renderImage> {

    private float min_c_re;
    private float min_c_im;
    private float max_c_re;
    private float max_c_im;
    private int width;
    private int height;
    private int iterations;
    private BufferedImage renderedImage;

    // Constructor
    public RenderImage(float min_c_re, float min_c_im, float max_c_re, float max_c_im, int width, int height, int iterations)
    {
        this.min_c_re = min_c_re;
        this.min_c_im = min_c_im;
        this.max_c_re = max_c_re;
        this.max_c_im = max_c_im;
        this.width = width;
        this.height = height;
        this.iterations = iterations;
    }

    /**
     * Calculate and construct Mandelbrot image
     * @return the part of rendered Mandelbrot image - remember this is only a part of the whole Mandlebrot image
     * @throws IOException
     */
    public BufferedImage renderMandelbrotSet() throws IOException {

        renderedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Calculate the delta
        float xStep = (max_c_re - min_c_re) / width;
        float yStep = (max_c_im - min_c_im) / height;

        // Calculate the pixel color for the given x,y location
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // draw from left to right from top to bottom
                int drawPoint = calculatePoint(min_c_re + x*xStep, max_c_im - y*yStep);
                renderedImage.setRGB(x, y, drawPoint);
            }
        }
        return renderedImage;
    }

    /**
     * Mandlebrot image calucation
     * @param x co-oridnate
     * @param y co-oridinate
     * @return color of that pixel
     */
    public int calculatePoint (float x, float y) {

        float cx = x;
        float cy = y;

        int i = 0;
        for (; i < iterations; i++) {
            float nx = x * x - y * y + cx;
            float ny = 2 * x * y + cy;
            x = nx;
            y = ny;
            if (x * x + y * y > 4)
                break;
        }

        if (i == iterations)
            return 0x00000000;

        return Color.HSBtoRGB(((float) i / iterations ) % 256f, 0.5f, 1);
    }
}
