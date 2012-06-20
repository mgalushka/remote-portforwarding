package com.maximgalushka.portforward.config.global;

/**
 * @author Maxim Galushka
 * @since 30.12.2010
 */
public final class GlobalConstants {


    public static final class Client{
        public static final String REFRESH_FREEQUENCY = "refresh.frequency.seconds";

        public static final String JMXMP_HOST = "jmxmp.host";
    }

    // TODO: do we need this?
    public static final class Server{
    }


    public static final String PROTOCOL = "jmxmp";
    public static final String JMXMP_PORT = "jmxmp.port";

}
