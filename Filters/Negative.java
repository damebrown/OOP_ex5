package Filters;

import java.io.File;

/**
 * A class that receives a filter and applies the opposite filter.
 */
public class Negative implements Filter{
    Filter filter;

    /**
     * Constructor
     * @param filter the opposite of the filter to apply
     */
    Negative(Filter filter){
        this.filter = filter;
    }

    @Override
    public boolean apply(File file, Object first, Object second){
        return !(filter.apply(file, first, second));
    }
}
