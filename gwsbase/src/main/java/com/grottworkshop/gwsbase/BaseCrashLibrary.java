package com.grottworkshop.gwsbase;

/**
 * BaseCrashLibrary class, 3rd party dev extends this and overrides methods to
 * integrate with their real crash library.
 * Created by fgrott on 9/18/2015.
 */
@SuppressWarnings("unused")
public  class BaseCrashLibrary {

    public static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer.
    }

    public static void logWarning(Throwable t) {
        // TODO report non-fatal warning.
    }

    public static void logError(Throwable t) {
        // TODO report non-fatal error.
    }

    private BaseCrashLibrary() {
        throw new AssertionError("No instances.");
    }

}
