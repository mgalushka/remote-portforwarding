/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.utils;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mgalushka
 */
public class ThreadUtilsTest {

    private static Log log;
    private ThreadUtils instance;
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
        runnable = new Runnable(){
            public void run() {
                log.debug("Thread");
            }
        };
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ThreadUtils.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ThreadUtils secondResult = ThreadUtils.getInstance();
        assertEquals(instance, secondResult);
    }

    /**
     * Test of scheduleThread method, of class ThreadUtils.
     */
    @Test
    public void testScheduleThread() {
        System.out.println("scheduleThread");
        instance.scheduleThread(runnable);
    }

    /**
     * Test of scheduleAtFixedRate method, of class ThreadUtils.
     */
    @Test
    public void testScheduleAtFixedRate() {
        System.out.println("scheduleAtFixedRate");
        int period = 1;
        TimeUnit timeUnit = TimeUnit.SECONDS;
//        ScheduledFuture expResult = null;
        ScheduledFuture result = instance.scheduleAtFixedRate(runnable, period, timeUnit);
        //assertEquals(expResult, result);
    }

}