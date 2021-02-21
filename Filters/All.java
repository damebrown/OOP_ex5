package Filters;

import java.io.File;

/**
 * A filter that always returns true.
 */
public class All implements Filter{

    @Override
    public boolean apply(File file, Object first, Object second) {
        return true;
    }
}
