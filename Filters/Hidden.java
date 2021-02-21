package Filters;

import java.io.File;

/**
 * A filter that returns true if the file is hidden.
 */
public class Hidden implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return ((first.equals("YES") && file.isHidden()) ||
                (first.equals("NO") && !file.isHidden()));
    }
}
