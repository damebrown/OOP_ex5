package Filters;

import java.io.File;

/**
 * A filter that returns true if File size is between (inclusive) the given numbers (in k-bytes).
 */
public class Between implements Filter {

    public static final int KILOBYTE = 1024;

    @Override
    public boolean apply(File file, Object first, Object second) {
        String firstDouble = (String) first, secondDouble = (String) second;
        return (double) file.length() >= (Double.parseDouble(firstDouble)) * KILOBYTE &&
                (double) file.length() <= (Double.parseDouble(secondDouble)) * KILOBYTE;
    }
}
