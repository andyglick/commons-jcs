/*
 * SerializeUtilsTest.java
 * JUnit based test
 *
 * $Revision$ $Date$
 */

package net.sf.yajcache.util;

import junit.framework.*;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.yajcache.annotate.*;

/**
 *
 * @author Hanson Char
 */
@TestOnly
public class SerializeUtilsTest extends TestCase {
    private Log log = LogFactory.getLog(this.getClass());
    /**
     * Test of dup method, of class net.sf.yajcache.util.SerializeUtils.
     */
    public void testDup() {
        // Test string dup: no clone
        log.debug("Test string dup: no clone");
        String s1 = "Abc Def";
        String s2 = SerializeUtils.inst.dup(s1);
        assertTrue(s1 == s2);
        // Test non-string Seriailzable dup: deep clone
        log.debug("Test non-string Seriailzable dup: deep clone");
        TestSerializable o1 = new TestSerializable("abc Def");
        TestSerializable o2 = SerializeUtils.inst.dup(o1);
        assertFalse(o1 == o2);
        assertEquals(o1, o2);
        // Test string array dup: shallow clone
        log.debug("Test string array dup: shallow clone");
        String[] sa = {"1", "2", "3"};
        String[] sa1 = SerializeUtils.inst.dup(sa);
        assertFalse(sa == sa1);
        assertTrue(Arrays.equals(sa, sa1));
        for (int i=sa.length-1; i > -1; i--)
            assertTrue(sa[i] == sa1[i]);
        // Test int array dup: shallow clone
        log.debug("Test int array dup: shallow clone");
        int[] ia = {1,2,3};
        int[] ia1 = SerializeUtils.inst.dup(ia);
        assertFalse(ia == ia1);
        assertTrue(Arrays.equals(ia, ia1));
        for (int i=ia.length-1; i > -1; i--)
            assertTrue(ia[i] == ia1[i]);
        // Test Integer array dup: shallow clone
        log.debug("Test Integer array dup: shallow clone");
        Integer[] inta = {1,2,3};
        Integer[] inta1 = SerializeUtils.inst.dup(inta);
        assertFalse(inta == inta1);
        assertTrue(Arrays.equals(inta, inta1));
        
        for (int i=inta.length-1; i > -1; i--) {
            assertTrue(inta[i] == inta1[i]);
        }
        // Test non-string Seriailzable array dup: deep clone
        log.debug("Test non-string Seriailzable array dup: deep clone");
        TestSerializable[] ta1 = {new TestSerializable("abc Def"), new TestSerializable("123"), new TestSerializable("assdk")};
        TestSerializable[] ta2 = SerializeUtils.inst.dup(ta1);
        assertFalse(ta1 == ta2);
        assertTrue(Arrays.equals(ta1, ta2));

        for (int i=ta1.length-1; i > -1; i--) {
            assertFalse(ta1[i] == ta2[i]);
            assertEquals(ta1[i], ta2[i]);
        }
    }
}
