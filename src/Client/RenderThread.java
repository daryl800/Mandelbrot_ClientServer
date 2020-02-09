package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * RenderThread class
 * A Callable thread which returns a image
 *
 * (2) Wrap all parameters in a string
 * (3) Send the wrapped parameters to the dedicated server
 * (4) Wait for the returned byte stream from the remote server
 * (5) Convert the byte stream to an image
 * (6) Close connection with the remote server
 * (7) Return the image to the caller
 *
 */
public class RenderThread implements Callable <BufferedImage> {

    private MandelbrotClient mandelbrotClient;
    private float min_c_re;
    private float min_c_im;
    private float max_c_re;
    private float max_r_im;
    int width;
    int height;
    private int inf_n = 50;
    private String serverID;

    /**
     * Constructor
     * @param mandelbrotClient
     * @param min_c_re
     * @param min_c_im
     * @param max_c_re
     * @param max_r_im
     * @param width
     * @param height
     * @param inf_n
     * @param serverID
     */
    public RenderThread(MandelbrotClient mandelbrotClient, float min_c_re, float min_c_im, float max_c_re, float max_r_im,
                        int width, int height, int inf_n, String serverID) {

        this.mandelbrotClient = mandelbrotClient;
        this.min_c_re = min_c_re;
        this.min_c_im = min_c_im;
        this.max_c_re = max_c_re;
        this.max_r_im = max_r_im;
        this.width = width;
        this.height = height;
        this.inf_n = inf_n;
        this.serverID = serverID;
    }

    /**
     * Thread to
     * @return
     * @throws Exception
     */
    public BufferedImage call () throws Exception{
        BufferedImage bImage = null;
        try {
            String[] hostAddr = serverID.split(":");

            // Console message
            System.out.println("*** Using server: " + serverID);

            // get ip add
            InetAddress ip = InetAddress.getByName(hostAddr[0]);
            // get port number
            Socket s = new Socket(ip, Integer.parseInt(hostAddr[1]));

            /**
             * Wrap all parameters in a String for passing to servers
             */
            Map<String, String> map = new HashMap<String, String>();
            map.put("min_c_re", Float.toString(min_c_re));
            map.put("min_c_im", Float.toString(min_c_im));
            map.put("max_c_re", Float.toString(max_c_re));
            map.put("max_r_im", Float.toString(max_r_im));
            map.put("width", Integer.toString(width));
            map.put("height", Integer.toString(height));
            map.put("inf_n", Integer.toString(inf_n));

            String str = convertMapToString(map);

            // obtaining input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            dos.writeUTF(str);
            String received = dis.readUTF();

            // Console message
            System.out.println(">>> received: " + received);

            // Obtain the image size to prepare for unwrapping the image in byte array format
            int imageSize = 0;
            try {
                    imageSize = Integer.parseInt(received);
                // Console message
                    System.out.println(">>> Received image size: " + imageSize);
                } catch (NumberFormatException ex) {
                // Console message
                    System.err.println("*** Error: Invalid string in argument!!!");
                }

            InputStream in = s.getInputStream();
            byte imgBytes[]  = readByteFromInputStream(in, imageSize);
            ByteArrayInputStream bIs = new ByteArrayInputStream(imgBytes);
            bImage = ImageIO.read(bIs);

            dos.writeUTF("Exit");
            // Console message
            System.out.println("*** Closing this connection: " + s);
            s.close();
            // Console message
            System.out.println("*** Connection closed.");

            // closing resources
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bImage;
    }

    /**
     * Wrap the parameters by converting the Map to a String
     * @param map
     * @return the Wrapped string
     */
    public String convertMapToString (Map<String, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", "));
        return mapAsString;
    }

    /**
     *
     * @param input stream
     * @param size of the stream
     * @return Byte array containing the image in byte format
     * @throws IOException
     */
    public static byte[] readByteFromInputStream(InputStream input, int size) throws IOException
    {
        System.out.println(">>> Converting input stream to bytes ...");
        byte[] data = new byte[size];
        int index = 0;
        while (index < size)
        {
            int bytesRead = input.read(data, index, size - index);
            if (bytesRead < 0)
            {
                throw new IOException("*** Error: Insufficient data in Stream!!!");
            }
            index += size;
        }
        return data;
    }
}

