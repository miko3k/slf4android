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

package org.deletethis.log4android;

import android.util.Log;

/**
 * An interface which allows customization of SLF4J binding to android.
 * Instead of directly implementing this interface, it is recommended to
 * subclass {@link BasicConfiguration}.
 *
 * @author Peter Hanula <miko3k@gmail.com>
 */
public interface Configuration {
    /**
     * Must return Android tag name, based on SLF4J logger name.
     * This function must be deterministic, i.e. it must always return
     * same value for any input string and return value may be cached.
     * 
     * Simple application may benefit from returning a fixed string here.
     * 
     * @param loggerName SLF4J Logger name
     * @return Android tag name
     */
    String getTag(String loggerName);
    
    /**
     * Determines if SLF4J should call appropriate logging function.
     * 
     * Default implementation simply calls {@link Log#isLoggable(java.lang.String, int)},
     * however if you want to enable DEBUG or VERBOSE by default, you should simply return
     * true here, because it does not seem to possible to enable this on android globally.
     * 
     * <https://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String,%20int)>
     * 
     * This will mimic beha: never call {@link Log#isLoggable(java.lang.String, int)}
     * 
     * @param tagName
     * @param level
     * @return 
     */
    boolean isLoggable(String tagName, int level);
}
