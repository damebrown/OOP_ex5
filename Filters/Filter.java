package Filters;

import java.io.File;

/**
 * A filter with a single method. If the file meets the criterion, return true.
 */
public interface Filter {

    /**
     * Apply the filter.
     * @param file the file to apply the filter on
     * @param first the first argument
     * @param second the second argument
     * @return true if the file passed the filter, otherwise false.
     */
    boolean apply(File file, Object first, Object second);
}
