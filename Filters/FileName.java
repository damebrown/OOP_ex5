package Filters;

import java.io.File;

/**
 * A filter that returns true if the value equals the file name (excluding path).
 */
public class FileName implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return file.getName().equals(first);
    }
}
