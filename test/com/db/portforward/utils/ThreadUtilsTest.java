package com.db.portforward.utils;

import java.util.concurrent.*;
import org.apache.commons.logging.*;
import org.junit.*;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author mgalushka
 */
@RunWith(MockitoJUnitRunner.class)
public class ThreadUtilsTest {

    private static Log log;
    private ThreadUtils instance;

    @Mock
    private Runnable runnable;

    public ThreadUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log = LogFactory.getLog(ThreadUtilsTest.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = ThreadUtils.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ThreadUtils.
     */
    @Test
    public void testGetInstance() {
        log.debug("getInstance");
        ThreadUtils secondResult = ThreadUtils.getInstance();
        assertEquals(instance, secondResult);
    }

    /**
     * Test of scheduleThread method, of class ThreadUtils.
     */
    @Test
    public void testScheduleThread() {
        log.debug("scheduleThread");
        instance.scheduleThread(runnable);
    }

    /**
     * Test of scheduleAtFixedRate method, of class ThreadUtils.
     */
    @Test
    public void testScheduleAtFixedRate() {
        log.debug("scheduleAtFixedRate");
        ScheduledFuture result = instance.scheduleAtFixedRate(runnable, 1, TimeUnit.SECONDS);
        assertThat(result, is(notNullValue()));
    }

}