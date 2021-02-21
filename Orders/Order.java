package Orders;

import java.io.File;
import java.util.ArrayList;

public abstract class Order {

    /*received filtered files array*/
    static ArrayList<File> filteredFilesArray;
    /*is reversed*/
    boolean isReversed;


    Order(boolean isReversedInput, ArrayList<File> filteredFiles){
        isReversed=isReversedInput;
        filteredFilesArray = filteredFiles;
    }

    public abstract ArrayList<File> orderFilteredFiles();


    /**
     * reverse an array of files, if needed
     */
    void reverseArray(){
        for (int index=0; index<(int)((Math.floor(filteredFilesArray.size())/2)); index++){
            if (filteredFilesArray.size()%2!=0){
                if (index==((Math.floor(filteredFilesArray.size())/2)+1)){
                    return;
                }
            } else {
                File tempFile = filteredFilesArray.get(index);
                filteredFilesArray.set(index, filteredFilesArray.get(filteredFilesArray.size()-(index+1)));
                filteredFilesArray.set(filteredFilesArray.size()-(index+1), tempFile);
            }
        }
    }

}
