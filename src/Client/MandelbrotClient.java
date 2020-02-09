package Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Mandelbrot class
 *
 */
public class MandelbrotClient extends JFrame {

    private MandelbrotClient instance;
    private int width;
    private int height;
    private int inf_n;
    private int noOfDivisions;

    private float min_c_re;
    private float min_c_im;
    private float max_c_re;
    private float max_c_im;

    private BufferedImage bufferedImage, concatImage = null;

    /**
     * (1) Read in arguments from parameter object
     * (2) Calculate and identify correct X,Y of the divided Mandelbrot image
     * (3) Assign tasks to threads
     * (4) Distribute tasks evenly (by round-ribbon algorithm) to designated servers
     * (5) Merge the returned images
     * (6) Save a copy of the full (combined) image
     * (7) Display the full image on screen
     * @param parameters
     * @throws Exception
     */
    public void exec(Parameters parameters) throws Exception {

        instance = this;

        width = parameters.getDimensionX();
        height = parameters.getDimensionY();
        inf_n = parameters.getIterations();
        noOfDivisions = parameters.getNoOfDivisions();
        min_c_re = parameters.getMin_c_re();
        min_c_im = parameters.getMin_c_im();
        max_c_re = parameters.getMax_c_re();
        max_c_im = parameters.getMax_c_im();

        setTitle("MandelbrotSet.Mandelbrot Set Drawing Main");
        setMinimumSize(new Dimension(width, height));
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        /**
         * Define image buffer to hold and merge the returned images
         */
        concatImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = concatImage.createGraphics();
        repaint();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        float stepRe = (max_c_re - min_c_re) / noOfDivisions;
        float stepIm = (max_c_im - min_c_im) / noOfDivisions;

        /*  For debug purpose (keep here)
        System.out.println("stepRe = " + stepRe + "; stepIm = " + stepIm);
        */

        /**
         * Divide the Mandelbrot image into small portion
         * Assign them to different server based on Round-ribbon algorithm
         */

        /**
         * Calculate the co-ordinate from top left corner to bottom right corner
         */
        for(int i = 0; i< noOfDivisions ; i++) {
            for(int j = 0; j< noOfDivisions ; j++) {
                 float startRe = min_c_re + j*stepRe;
                 float endRe = startRe + stepRe;
                 float endIm = max_c_im - i*stepIm;
                 float startIm = endIm - stepIm;

                /*  For debug purpose (keep here)
                    System.out.println("startRe = " + startRe + " endRe = " + endRe);
                    System.out.println("startIm = " + startIm + " endIm = " + endIm);
                 */

                /**
                 * Distribute tasks here
                 */
                RoundRobin roundRobin = new RoundRobin();
                String serverId = roundRobin.getServer(parameters);
                Future<BufferedImage> bufferedImageFuture = executor.submit(
                        new RenderThread(instance, startRe, startIm, endRe, endIm,
                                width/noOfDivisions,  height/noOfDivisions, inf_n, serverId));
                bufferedImage = bufferedImageFuture.get();
                /**
                 * Merge images here
                 */
                g2d.drawImage(bufferedImage, width/noOfDivisions * j , height/noOfDivisions * i, null);
                repaint();
            }
            System.out.println("<<< *** Client : Drawing completed *** >>>");
        }

        /**
         * Save the combined full Mandelbrot image in a file.
         */
        String filename = "FullMandelbrotImage.jpg";
        ImageIO.write(concatImage, "jpg", new File(filename));

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
    }

    /**
     * Draw the combined full Mandelbrot image on screen
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(concatImage, 0, 0, this);
    }
}