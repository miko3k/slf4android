/*
 * Copyright (c) 2004-2013 QOS.ch
 * Copyright (c) 2017 Peter Hanula
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.deletethis.slf4android;

import org.deletethis.slf4android.DefaultConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicConfigurationTest {
    @Test
    public void shortLoggerNames() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("o.test.p.TestClass", cfg.getTag("o.test.p.TestClass"));
        assertEquals("ex.test.TestClass", cfg.getTag("ex.test.TestClass"));
        assertEquals("MyClass", cfg.getTag("MyClass"));
    }

    @Test
    public void emptyLoggerNames() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals(DefaultConfiguration.ANONYMOUS_TAG, cfg.getTag(null));
        assertEquals("", cfg.getTag(""));
    }

    @Test
    public void simpleLoggerName() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("o*.t*.p*.TestClass", cfg.getTag("org.test.package.TestClass"));
    }

    @Test
    public void loggerNameWithOneCharPackage() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("o.t*.p*.p*.TestClass", cfg.getTag("o.test.project.package.TestClass"));
        assertEquals("o.t*.p*.p.TestClass", cfg.getTag("o.test.project.p.TestClass"));
    }

    @Test
    public void longLoggerName() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("AndroidLoggerFactory", cfg.getTag("org.slf4j.impl.AndroidLoggerFactory"));
    }

    @Test
    public void veryLongLoggerName() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("*meAndShouldBeTruncated", cfg.getTag("IAmAVeryLongLoggerNameAndShouldBeTruncated"));
    }

    @Test
    public void oneWordLoggerName() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("TestClass", cfg.getTag("TestClass"));
    }

    @Test
    public void weirdLoggerNames() {
        DefaultConfiguration cfg = new DefaultConfiguration();
        
        assertEquals("WeirdLoggerName.", cfg.getTag("WeirdLoggerName."));
        assertEquals(".WeirdLoggerName", cfg.getTag(".WeirdLoggerName"));
        assertEquals(".WeirdLoggerName.", cfg.getTag(".WeirdLoggerName."));
        assertEquals(".", cfg.getTag("."));
        assertEquals("..", cfg.getTag(".."));
    }
}
