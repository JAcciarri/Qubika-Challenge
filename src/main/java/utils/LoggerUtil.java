package utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerUtil {

    // Utility class for getting logger
    public static Logger getLogger(Class<?> c) {
        return LoggerFactory.getLogger(c.getName());
    }
}