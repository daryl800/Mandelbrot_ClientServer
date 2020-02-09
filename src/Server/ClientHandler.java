package Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * ClientHandler class - A thread
 */
class ClientHandler extends Thread
{
    // Message contents from client

    private float min_c_re;
    private float min_c_im;
    private float max_c_re;
    private float max_c_im;
    private int width;
    private int height;
    private int inf_n;

    private BufferedImage bufferedImage = null;

    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;
    final OutputStream out;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, OutputStream out)
    {
        this.s = s;
        this.out = out;
        this.dis = dis;
        this.dos = dos;
    }

    /**
     * (1) handle a new socket
     * (2) Check if income stream is an "Exit", if so close the socket and end the connection
     * (3) If the income stream is NOT an "Exit", unwrap the string and put the value into the right variables
     * (4) Call RenderImage method to render the image according to the given parameters
     * (5) Convert the image into byte array
     * (6) Send the byte array size
     * (7) Send the byte array
     */
    @Override
    public void run()
    {
        String msgFromClient;

        while (true)
        {
            try {
                // receive a message from client
                msgFromClient = dis.readUTF();
                System.out.println(">>> Received message from Client : " + msgFromClient);

                if(msgFromClient.equals("Exit"))
                {
                    System.out.println(">>> Received 'Exit' request from : " + this.s);
                    System.out.println("*** Closing this connection.");
                    this.s.close();
                    System.out.println("*** Connection closed");
                    break;
                }

                Map<String, String> map =  convertStringToMap(msgFromClient);

                min_c_re = Float.parseFloat(map.get("min_c_re"));
                min_c_im = Float.parseFloat(map.get("min_c_im"));
                max_c_re = Float.parseFloat(map.get("max_c_re"));
                max_c_im = Float.parseFloat(map.get("max_r_im"));
                width = Integer.parseInt(map.get("width"));
                height = Integer.parseInt(map.get("height"));
                inf_n = Integer.parseInt(map.get("inf_n"));

                /* This block is for debug purpose  (keep here)
                System.out.println("min_c_re = " + min_c_re + " max_c_re = " + max_c_re);
                System.out.println("min_c_im = " + min_c_im + " max_c_im = " + max_c_im);
                */

                RenderImage renderImage = new RenderImage(min_c_re, min_c_im, max_c_re, max_c_im, width, height, inf_n);
                bufferedImage =  renderImage.renderMandelbrotSet();

                // Convert image to byte stream
                ByteArrayOutputStream bImg = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", bImg);
                byte imgBytes[] = bImg.toByteArray();
                bImg.close();

                // Send the byte array size
                dos.writeUTF(String.valueOf(imgBytes.length));
                // Send the byte array
                out.write(imgBytes, 0, imgBytes.length);

                /* This block is for debug purpose (keep here)
                // Save the image in the disk for trouble shooting purpose
                String filename = "image_" + min_c_re + "_" + min_c_im + ".jpg";
                ImageIO.write(bufferedImage, "jpg", new File(filename));
                 */

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // Close resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("<<< *** Server : Drawing completed *** >>>");
    }

    public Map<String, String> convertStringToMap(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0].trim(), entry -> entry[1].trim()));
        return map;
    }

}
