package Client;

import java.util.ArrayList;

/**
 * Parameter object
 * Getter and Setter for all class members
 */

public class Parameters {
    private String min_c_re;
    private String min_c_im;
    private String max_c_re;
    private String max_c_im;
    private String iterations;
    private String dimensionX;
    private String dimensionY;
    private String noOfDivisions;
    private ArrayList<String> hostPool = new ArrayList<String>();

    public void setMin_c_re(String str)
    {
        this.min_c_im = str;
    }

    public float getMin_c_re()
    {
        return Float.parseFloat(min_c_im);
    }

    public void setMin_c_im(String str)
    {
        this.min_c_re = str;
    }

    public float getMin_c_im()
    {
        return Float.parseFloat(min_c_re);
    }

    public void setMax_c_re(String str)
    {
        this.max_c_re = str;
    }

    public float getMax_c_re()
    {
        return Float.parseFloat(max_c_re);
    }

    public void setMax_c_im(String str)
    {
        this.max_c_im = str;
    }

    public float getMax_c_im()
    {
        return Float.parseFloat(max_c_im);
    }

    public void setIterations(String str)
    {
        this.iterations = str;
    }

    public int getIterations()
    {
        return Integer.parseInt(iterations);
    }

    public void setDimensionX(String str)
    {
        this.dimensionX = str;
    }

    public int getDimensionX()
    {
        return Integer.parseInt(dimensionX);
    }

    public void setDimensionY(String str)
    {
        this.dimensionY = str;
    }

    public int getDimensionY()
    {
        return Integer.parseInt(dimensionY);
    }

    public void setNoOfDivisions(String str)
    {
        this.noOfDivisions = str;
    }

    public int getNoOfDivisions()
    {
        return Integer.parseInt(noOfDivisions);
    }

    public void setHostID(String str)
    {
        this.hostPool.add(str);
    }

    public String getHostID(int i)
    {
        return hostPool.get(i);
    }

    public int getHostPoolSize()
    {
        return hostPool.size();
    }
}