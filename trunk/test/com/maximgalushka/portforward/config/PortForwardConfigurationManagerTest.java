package com.maximgalushka.portforward.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.cglib.core.CollectionUtils;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 18/08/2011
 */
public class PortForwardConfigurationManagerTest {

    private static Log log = LogFactory.getLog(PortForwardConfigurationManagerTest.class);

    private ConfigurationManager cm;

    @Before
    public void init(){
    }

    @After
    public void clear(){
    }

    @Test
    public void testGetConfiguration() throws Exception {
        cm = PortForwardConfigurationManager.getConfigurationManager();
        log.debug(cm.getConfiguration().toString());


    }
}
