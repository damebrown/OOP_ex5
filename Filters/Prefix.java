package Filters;

import java.io.File;

/**
 * A filter that returns true if a value is the prefix of the file name (excluding path).
 */
public class Prefix implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return file.getName().startsWith((String) first);
    }
}
