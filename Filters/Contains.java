package Filters;

import java.io.File;

/**
 * A filter that returns true if the value is contained in the file name (excluding path).
 */
public class Contains implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return file.getName().contains((String) first);
    }
}
