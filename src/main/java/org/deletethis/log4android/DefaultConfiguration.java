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
 * Implementation of {@link Configuration}, feel free to subclass any method.
 *
 * @author Andrey Korzhevskiy <a.korzhevskiy@gmail.com>
 * @author Peter Hanula <miko3k@gmail.com>
 */
public class DefaultConfiguration implements Configuration {
    static final protected String ANONYMOUS_TAG = "null";
    static final protected int TAG_MAX_LENGTH = 23;
    
    public boolean isLoggable(String tagName, int priority) {
         return Log.isLoggable(tagName, priority);
    }
    
    
    /**
     * Tag names cannot be longer than {@value #TAG_MAX_LENGTH} characters on Android platform.
     *
     * Returns the short logger tag (up to {@value #TAG_MAX_LENGTH} characters) for the given logger name.
     * Traditionally loggers are named by fully-qualified Java classes; this
     * method attempts to return a concise identifying part of such names.
     * 
     * The following examples illustrate this.
     * <table border="1">
     * <tr><th><b>Original Name<b></th><th><b>Truncated Name</b></th></tr>
     * <tr><td>org.example.myproject.mypackage.MyClass</td><td>o*.e*.m*.m*.MyClass</td></tr>
     * <tr><td>o.e.myproject.mypackage.MyClass</td><td>o.e.m*.m*.MyClass</td></tr>
     * <tr><td>org.example.ThisNameIsWayTooLongAndWillBeTruncated</td><td>*LongAndWillBeTruncated</td></tr>
     * <tr><td>ThisNameIsWayTooLongAndWillBeTruncated</td><td>*LongAndWillBeTruncated</td></tr>
     * </table>
     * </p>
     * 
     * See also:
     * android/system/core/include/cutils/property.h
     * android/frameworks/base/core/jni/android_util_Log.cpp
     * dalvik.system.DalvikLogging
     *
     */
    public String getTag(String loggerName) {
        // Anonymous logger
        if (loggerName == null) {
            return ANONYMOUS_TAG;
        }

        int length = loggerName.length();
        if (length <= TAG_MAX_LENGTH) {
            return loggerName;
        }

        int tagLength = 0;
        int lastTokenIndex = 0;
        int lastPeriodIndex;
        StringBuilder tagName = new StringBuilder(TAG_MAX_LENGTH + 3);
        while ((lastPeriodIndex = loggerName.indexOf('.', lastTokenIndex)) != -1) {
            tagName.append(loggerName.charAt(lastTokenIndex));
            // token of one character appended as is otherwise truncate it to one character
            int tokenLength = lastPeriodIndex - lastTokenIndex;
            if (tokenLength > 1) {
                tagName.append('*');
            }
            tagName.append('.');
            lastTokenIndex = lastPeriodIndex + 1;

            // check if name is already too long
            tagLength = tagName.length();
            if (tagLength > TAG_MAX_LENGTH) {
                return getSimpleName(loggerName);
            }
        }

        // Either we had no useful dot location at all
        // or last token would exceed TAG_MAX_LENGTH
        int tokenLength = length - lastTokenIndex;
        if (tagLength == 0 || (tagLength + tokenLength) > TAG_MAX_LENGTH) {
            return getSimpleName(loggerName);
        }

        // last token (usually class name) appended as is
        tagName.append(loggerName, lastTokenIndex, length);
        return tagName.toString();
    }

    protected String getSimpleName(String loggerName) {
        // Take leading part and append '*' to indicate that it was truncated
        int length = loggerName.length();
        int lastPeriodIndex = loggerName.lastIndexOf('.');
        
        if(lastPeriodIndex != -1 && length - (lastPeriodIndex + 1) <= TAG_MAX_LENGTH) {
            return loggerName.substring(lastPeriodIndex + 1);
        } else {
            return '*' + loggerName.substring(length - TAG_MAX_LENGTH + 1);
        }
    }
}
