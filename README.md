# log4android

[SLF4J]: https://www.slf4j.org/
[isLoggable]: https://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String,%20int)
[sandroid]: https://www.slf4j.org/android/

An alternative [SLF4J] binding for Android. It is a copy of the [official one][sandroid], but adds additional configuration options.

## Why?

Because offical binding always uses `isLoggable` before issuing logging calls and its [very hard][isLoggable] to enable `DEBUG`/`VERBOSE` logs globaly.

It seems most Android apps do not attempt to bother call `isLoggable` therefor don't have to solve this problem.

## How?

```java
import android.app.Application;
import org.deletethis.log4android.DefaultConfiguration;
import org.deletethis.log4android.Slf4Android;

// feel flee to override any number of methods
class ExampleConfiguration extends DefaultConfiguration {
    // log every message
    @Override
    public boolean isLoggable(String tag, int level) {
        return true;
    }
    
    // ignore logger name and always use ExampleApplication as tag name
    @Override
    public String getTag(String loggerName) {
        return "ExampleApplcation";
    }
}

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Slf4Android.setConfiguration(new DefaultConfiguration());
    }
}
```

## Addional info
By default, `slf4anroid` should behave identically to official binding.

