package Filters;

import java.io.File;

/**
 * A filter that returns true if the file has execution permission.
 */
public class Executable implements Filter {

    @Override
    public boolean apply(File file, Object first, Object second) {
        return ((first.equals("YES") && file.canExecute()) ||
                (first.equals("NO") && !file.canExecute()));
    }
}
