/*
 * $Revision$ $Date$
 */

package net.sf.yajcache.core;

import junit.framework.*;
import net.sf.yajcache.util.TestSerializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.yajcache.annotate.*;

/**
 *
 * @author Hanson Char
 */
@TestOnly
public class SafeCacheManagerTest extends TestCase {
    private Log log = LogFactory.getLog(this.getClass());
    
    public void testGetCache() {
        log.debug("Test getCache and get");
        ICacheSafe<String> c = SafeCacheManager.inst.getCache("myCache", String.class);
        assertTrue(null == c.get("bla"));
        log.debug("Test getCache and put");
        c = SafeCacheManager.inst.getCache("myCache", String.class);
        c.put("bla", "First Put");
        assertTrue("First Put" == c.get("bla"));
        assertEquals(c.size(), 1);
        log.debug("Test getCache and remove");
        c = SafeCacheManager.inst.getCache("myCache", String.class);
        c.remove("bla");
        assertTrue(null == c.get("bla"));
        log.debug("Test getCache and two put's");
        c = SafeCacheManager.inst.getCache("myCache", String.class);
        c.put("1", "First Put");
        c.put("2", "Second Put");
        assertEquals(c.size(), 2);
        assertTrue("Second Put" == c.get("2"));
        assertTrue("First Put" == c.get("1"));
        log.debug("Test getCache and clear");
        c = SafeCacheManager.inst.getCache("myCache", String.class);
        c.clear();
        assertEquals(c.size(), 0);
        assertTrue(null == c.get("2"));
        assertTrue(null == c.get("1"));
        log.debug("Test getCache and getValueType");
        ICacheSafe c1 = SafeCacheManager.inst.getCache("myCache");
        assertTrue(c1.getValueType() == String.class);
        log.debug("Test checking of cache value type");
        try {
            ICacheSafe<Integer> c2 = SafeCacheManager.inst.getCache("myCache", Integer.class);
            fail("Bug: Cache for string cannot be used for Integer.");
        } catch(ClassCastException ex) {
            // should go here.
        }
    }

    public void testGetCacheRaceCondition() {
        log.debug("Test simulation of race condition in creating cache");
        ICacheSafe intCache = SafeCacheManager.inst.testCreateCacheRaceCondition("race", Integer.class);
        ICacheSafe intCache1 = SafeCacheManager.inst.testCreateCacheRaceCondition("race", Integer.class);
        log.debug("Test simulation of the worst case scenario: "
                + "race condition in creating cache AND class cast exception");
        try {
            ICacheSafe<Double> doubleCache = SafeCacheManager.inst.testCreateCacheRaceCondition("race", Double.class);
            fail("Bug: Cache for Integer cannot be used for Double.");
        } catch(ClassCastException ex) {
            // should go here.
        }
        assertTrue(intCache == intCache1);
    }

    public void testRemoveCache() {
        log.debug("Test remove cache");
        ICacheSafe<Integer> intCache = SafeCacheManager.inst.getCache("race", Integer.class);
        intCache.put("1", 1);
        assertEquals(intCache.size(), 1);
        assertEquals(intCache, SafeCacheManager.inst.removeCache("race"));
        assertEquals(intCache.size(), 0);
        ICacheSafe intCache1 = SafeCacheManager.inst.getCache("race", Integer.class);
        assertFalse(intCache == intCache1);
        SafeCacheManager.inst.removeCache("race");
        ICacheSafe<Double> doubleCache = SafeCacheManager.inst.testCreateCacheRaceCondition("race", Double.class);
        doubleCache.put("double", 1.234);
        assertEquals(1.234, doubleCache.get("double"));
    }

    public void testGetSafeCache() {
        log.debug("Test getCache and getCopy");
        {
            ICacheSafe<String> c = SafeCacheManager.inst.getCache("myCache", String.class);
            assertTrue(null == c.getCopy("bla"));
            log.debug("Test getCache and putCopy");
            c = SafeCacheManager.inst.getCache("myCache", String.class);
            c.putCopy("bla", "First Put");
            assertTrue("First Put" == c.getBeanClone("bla"));
            assertEquals(c.size(), 1);
            log.debug("Test getCache and remove");
            c = SafeCacheManager.inst.getCache("myCache", String.class);
            c.remove("bla");
            assertTrue(null == c.getCopy("bla"));
            log.debug("Test getCache and two putCopy's");
        }
        SafeCacheManager.inst.removeCache("myCache");
        ICacheSafe<TestSerializable> c = SafeCacheManager.inst.getCache("myCache", TestSerializable.class);
        TestSerializable[] ta = {
                new TestSerializable("First Put"), 
                new TestSerializable("Second Put"),
                new TestSerializable("Third Put")
        };
        log.debug("test putCopy");
        c.putCopy("1", ta[0]);
        assertFalse(ta[0] == c.get("1"));
        assertEquals(ta[0], c.get("1"));
        log.debug("test putBeanCopy");
        c.putBeanCopy("2", ta[1]);
        assertFalse(ta[1] == c.get("2"));
        assertEquals(ta[1], c.get("2"));
        log.debug("test putBeanClone");
        c.putBeanClone("2a", ta[1]);
        assertFalse(ta[1] == c.get("2a"));
        assertEquals(ta[1], c.get("2a"));
        
        c.put("3", ta[2]);
        assertEquals(c.size(), 4);
        assertFalse(ta[1] == c.getBeanClone("2"));
        assertFalse(ta[0] == c.getBeanCopy("1"));
        assertFalse(ta[0] == c.get("1"));
        log.debug("Test get, getBeanClone, getBeanCopy and getCopy");
        assertTrue(ta[2] == c.get("3"));
        assertFalse(ta[2] == c.getBeanClone("3"));
        assertFalse(ta[2] == c.getBeanCopy("3"));
        assertFalse(ta[2] == c.getCopy("3"));
        assertEquals(ta[2], c.getBeanClone("3"));
        assertEquals(ta[2], c.getBeanCopy("3"));
        assertEquals(ta[2], c.getCopy("3"));
        log.debug("Test getCache and clear");
        c = SafeCacheManager.inst.getCache("myCache", TestSerializable.class);
        c.clear();
        assertEquals(c.size(), 0);
        assertTrue(null == c.getCopy("2"));
        assertTrue(null == c.getCopy("1"));
        log.debug("Test getCache and getValueType");
        ICacheSafe c1 = SafeCacheManager.inst.getCache("myCache");
        assertTrue(c1.getValueType() == TestSerializable.class);
        log.debug("Test checking of cache value type");
        try {
            ICacheSafe<Integer> c2 = SafeCacheManager.inst.getCache("myCache", Integer.class);
            fail("Bug: Cache for string cannot be used for Integer.");
        } catch(ClassCastException ex) {
            // should go here.
        }
    }
}
