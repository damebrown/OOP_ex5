package Filters;

import java.io.File;

/**
 * A filter that returns true if the file has writing permission (for the current user).
 */
public class Writable implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return ((first.equals("YES") && file.canWrite()) ||
                (first.equals("NO") && !file.canWrite()));
    }
}
