package utils;

import logs.Log;

import java.io.FileInputStream;

public class ReadProperty {
    public static String getPropertyValue(java.util.Properties props, String name) {
        String value = System.getenv(name);
        if (props != null) {
            value = props.getProperty(name);
        }
        return value;
    }

    public static void loadProperties() throws Exception {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\application.properties");
        try {
            java.util.Properties props = new java.util.Properties();
            props.load(fis);

            for (Object key : props.keySet()) {
                String name = (String) key;
                Constants.appDetails.put(name, getPropertyValue(props, name));
                }
            Log.info("Application properties used: "+Constants.appDetails.toString());
        } catch (Exception EX) {
            throw new Exception("Loading application properties got failed: " + EX.getMessage());
        }
    }

}
