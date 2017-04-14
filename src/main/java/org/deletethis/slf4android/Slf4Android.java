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

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AndroidLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriately named {@link Slf4Android} instance.
 *
 * @author Andrey Korzhevskiy <a.korzhevskiy@gmail.com>
 * @author Peter Hanula <miko3k@gmail.com>
 */
public class Slf4Android implements ILoggerFactory {

    private final ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
    private static Configuration configuration = new DefaultConfiguration();

    /**
     * Sets configuration of android SLF4J binding. <b>This method is NOT thread safe,
     * please call it only during application initialization.
     */
    static public void setConfiguration(Configuration cfg) { configuration = cfg; }

    /**
     * Return an appropriate {@link AndroidLogger} instance by name.
     */
    @Override
    public Logger getLogger(String name) {
        String tag = configuration.getTag(name);
        Logger logger = loggerMap.get(tag);
        if (logger == null) {
            Logger newInstance = new AndroidLogger(tag);
            Logger oldInstance = loggerMap.putIfAbsent(tag, newInstance);
            logger = oldInstance == null ? newInstance : oldInstance;
        }
        return logger;
    }
    
    /**
     * Alias for configuration.isLoggable.
     * 
     * Package access only.
     */
    static boolean isLoggable(String tagName, int level) {
        return configuration.isLoggable(tagName, level);
    }

}