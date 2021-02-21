package Filters;

import java.io.File;

/**
 * A filter that returns true if a value is the suffix of the file name (excluding path).
 */
public class Suffix implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return file.getName().endsWith((String) first);
    }
}
