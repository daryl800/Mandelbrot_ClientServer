package Client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientMainTest {

    @Test
    public void testGetIteration_String() {
        try {
            Parameters parameters = new Parameters();
            parameters.setIterations("1024");
            System.out.println( "testGetIteration_String in progress ..." );
            assertEquals("1024", parameters.getIterations());

        } catch (Exception e) {
            System.out.println( "testGetIteration_String Failed: " + e);
        }
    }

    @Test
    public void testGetIteration_Integer() {
        try {
            Parameters parameters = new Parameters();
            parameters.setIterations("1024");
            System.out.println( "testGetIteration_Integer in progress ..." );
            assertEquals(1024, parameters.getIterations());

        } catch (Exception e) {
            System.out.println( "testGetIteration_Integer Failed: " + e);
        }
    }

    @Test
    public void testDrawMandelbrotPicture() {
        try {
            Parameters parameters = new Parameters();
            parameters.setMin_c_re("-1");
            parameters.setMin_c_im("-1.5");
            parameters.setMax_c_re("2");
            parameters.setMax_c_im("1.5");
            parameters.setIterations("1024");
            parameters.setDimensionX("800");
            parameters.setDimensionY("800");
            parameters.setNoOfDivisions("4");
            parameters.setHostID("192.168.1.128:5056");

            System.out.println( "testDrawMandelbrotPicture in progress ..." );

            MandelbrotClient mandelbrotClient = new MandelbrotClient();
            try {
                mandelbrotClient.exec(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println( "testDrawMandelbrotPicture Failed: " + e);
        }
    }


}