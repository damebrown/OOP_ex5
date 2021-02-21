package Orders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class AbsoluteOrder extends Order {

    /*class constructor*/

    AbsoluteOrder(boolean isReversedInput, ArrayList<File> filteredFiles){
        super(isReversedInput, filteredFiles);
    }

    /*class methods*/

    /**
     * this function calls the order and if nessecery, the reverse function
     * @return returns the ordered and filtered array
     */
    public ArrayList<File> orderFilteredFiles(){
        Collections.sort(filteredFilesArray, Comparator.comparing(File::getName));        //, 0, filteredFilesArray.length-1);
        if (isReversed){
            reverseArray();
        }
        return filteredFilesArray;
    }

}
