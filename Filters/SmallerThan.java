package Filters;

import java.io.File;

/**
 * A filter that returns true if File size is strictly smaller than the given number (in k-bytes).
 */
public class SmallerThan implements Filter {

    public static final int KILOBYTE = 1024;

    @Override
    public boolean apply(File file, Object first, Object second) {
        String firstDouble = (String) first;
        return (double) file.length() < Double.parseDouble(firstDouble) * KILOBYTE;
    }
}
