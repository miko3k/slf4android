/*
 * The MIT License
 *
 * Copyright 2017 Peter Hanula.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.deletethis.slf4android.examples;

import android.app.Application;
import org.deletethis.slf4android.DefaultConfiguration;
import org.deletethis.slf4android.Slf4Android;

// only override a method if default is not good enough
class ExampleConfiguration extends DefaultConfiguration {
    // log every message
    @Override
    public boolean isLoggable(String tag, int level) {
        return true;
    }
    
    // ignore logger name and always use "ExampleApplication" as tag name
    @Override
    public String getTag(String loggerName) {
        return "ExampleApplcation";
    }
}

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // this is not thread safe, beware!
        Slf4Android.setConfiguration(new ExampleConfiguration());
    }
}
