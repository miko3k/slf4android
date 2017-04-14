# slf4android

[SLF4J]: https://www.slf4j.org/
[isLoggable]: https://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String,%20int)
[sandroid]: https://www.slf4j.org/android/

An alternative [SLF4J] binding for Android. It is a copy of the [official one][sandroid], but adds additional configuration options.

## Why?

Because offical binding always uses `isLoggable` before issuing logging calls and its probably [impossible][isLoggable] to enable `DEBUG`/`VERBOSE` logs globaly,
only per tag.

It seems most Android apps do not bother to call `isLoggable` and happily use debug logs. This library allows you to do exactly the same, 
if you want. It also allows you to customize logger to tag name mapping.

## How?

Like this:

```java
import android.app.Application;
import org.deletethis.slf4android.DefaultConfiguration;
import org.deletethis.slf4android.Slf4Android;

// feel flee to override any number of methods
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
        Slf4Android.setConfiguration(new ExampleConfiguration());
    }
}
```

## Addional info

Without any configuration, `slf4android` should behave identically to official binding. 
