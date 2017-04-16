# slf4android

[SLF4J]: https://www.slf4j.org/
[isLoggable]: https://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String,%20int)
[sandroid]: https://www.slf4j.org/android/

An alternative [SLF4J] binding for Android. It is a copy of the [official one][sandroid], but adds additional configuration options.

## Why?

Because offical binding always uses `isLoggable` before issuing logging calls and its probably [impossible][isLoggable] to enable `DEBUG`/`VERBOSE` logs globaly,
only per tag.

It seems most Android apps do not bother to call `isLoggable` and happily use debug logs. This library allows you to do exactly the same.

You can also customize logger to tag name mapping. For example, small application may live happily with a single tag name.

## How?

```java
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
        Slf4Android.setConfiguration(new DefaultConfiguration());
    }
}
```

## Installation

This project is not in any official maven repository, in order to use it, you should

* Build `slf4android`

  1) download it
  2) `mvn install`

* Use `slf4android` in an android project

  3) add `mavenLocal()` to `repositories` of your gradle script
  4) add `dependencies`

     * `compile 'org.deletethis:slf4android:1.0.0'`
     * `compile 'org.slf4j:slf4j-api:1.7.25''`

  5) register your `Application` in `AndroidManifest.xml`

     ```xml
     ...
     <application
        android:name="org.deletethis.slf4android.examples.ExampleApplication"
        ...
     ```


## Additional info

Without any configuration, `slf4android` should behave identically to official binding.

## License

[MIT](https://opensource.org/licenses/MIT), as the original SLF4J-Android binding.
