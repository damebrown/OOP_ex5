package Orders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


class SizeOrder extends Order {


    SizeOrder(boolean isReversedInput, ArrayList<File> filteredFiles){
        super(isReversedInput, filteredFiles);
    }


    public ArrayList<File> orderFilteredFiles(){
        Collections.sort(filteredFilesArray, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                if(file1.length()>file2.length()){
                    return 1;
                } else if (file1.length()<file2.length()){
                    return -1;
                }
                return file1.getName().compareTo(file2.getName());
            }
        });
        if (isReversed){
            reverseArray();
        }
        return filteredFilesArray;
    }

}

