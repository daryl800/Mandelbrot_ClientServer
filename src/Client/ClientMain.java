package Client; /**
 * ToDo List App.
 *
 * @author Daryl Ng
 * @version  2019.02.18
 */

/**
 * Main Function
 *
 */

public class ClientMain {
    public static void main(String[] args) {

        /**
         * Put command line arguments into Parameters object
         * arg 1: min_c_re (min real num)
         * arg 2: min_c_im (min imaginary num)
         * arg 3: max_c_re (max real num)
         * arg 4: max_c_im (max imaginary num)
         * arg 5: No of iterations
         * arg 6: Full image X dimension
         * arg 7: Full image Y dimension
         * arg 8: Image division number
         * arg 8 ... : List of servers (ip + port)
         */

        if (args.length < 9)
            throw new IllegalArgumentException("Invalid argument!!!");
        else {
            Parameters parameters = new Parameters();
            parameters.setMin_c_re(args[0]);
            parameters.setMin_c_im(args[1]);
            parameters.setMax_c_re(args[2]);
            parameters.setMax_c_im(args[3]);
            parameters.setIterations(args[4]);
            parameters.setDimensionX(args[5]);
            parameters.setDimensionY(args[6]);
            parameters.setNoOfDivisions(args[7]);
            for (int i = 0; i < args.length - 8; i++) {
                parameters.setHostID(args[8 + i]);
            }

            MandelbrotClient mandelbrotClient = new MandelbrotClient();
            try {
                mandelbrotClient.exec(parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}