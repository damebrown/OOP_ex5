package Orders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class TypeOrder extends Order {

    /*class constants*/
    /*dot string*/
    private static final String DOT = ".";
    /*empty string*/
    private static final String EMPTY_STRING = "";

    /*constructor*/
    TypeOrder(boolean isReversedInput, ArrayList<File> filteredFiles){
        super(isReversedInput, filteredFiles);
    }

    /*class methods*/

    /**
     * orders the filtered array
     * @return the ordered array
     */
    public ArrayList<File> orderFilteredFiles(){
        //quickTypeSort(filteredFilesArray, 0, filteredFilesArray.size()-1);
        Collections.sort(filteredFilesArray, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                String firstName = file1.getName(), secondName = file2.getName();
                String firstType = getFileExtension(file1), secondType = getFileExtension(file2);
                if (firstType.equals(secondType)){
                    return firstName.compareTo(secondName);
                } return firstType.compareTo(secondType);
            }
        });
        if (isReversed){
            reverseArray();
        }
        return filteredFilesArray;
    }

    private String getFileExtension(File file){
        String fileName = file.getName();
        if ((fileName.lastIndexOf(DOT)!=0)&&(fileName.lastIndexOf(DOT)!=-1)){
            return fileName.substring((fileName.lastIndexOf(DOT))+1);
        } return EMPTY_STRING;
    }
}
