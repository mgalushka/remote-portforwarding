package com.db.portforward;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.db.portforward.config.ConfigurationManager;

/**
 * @author Maxim Galushka
 * @since 09.01.2011
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    @Test(expected = ApplicationException.class)
    public void startNullConfigurationManagerTest() throws Exception{
        ConfigurationManager configurationManager = null;
        Application application = new Application(configurationManager);
        application.start();
    }

    @Test(expected = ApplicationException.class)
    public void startSimpleWrongConfigurationTest() throws Exception{
        ConfigurationManager configurationManager = mock(ConfigurationManager.class);
        when(configurationManager.getConfiguration()).thenReturn(null);

        Application application = new Application(configurationManager);
        application.start();
    }
}
