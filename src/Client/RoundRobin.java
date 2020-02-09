package Client;

/**
 * RoundRobin class
 * Method to assign tasks to different servers
 */

public class RoundRobin   {
    private static Integer position = 0;

    /**
     * Obtain an ip address from the parameter object
     * @param parameters
     * @return the needed IP address and the correspond port number
     */
    public String getServer(Parameters parameters) {

        String target = null;

        synchronized (position) {
            if (position > parameters.getHostPoolSize() - 1) {
                position = 0;
            }
//            target = serverList.get(position);
            target = parameters.getHostID(position);
            position++;
        }
        System.out.println("target = " + target);
        return target;
    }
}