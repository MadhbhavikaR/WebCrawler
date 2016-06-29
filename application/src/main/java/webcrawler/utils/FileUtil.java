
package webcrawler.utils;

import java.io.File;

public class FileUtil {
    
    public static boolean deleteAndCreateDirectory(String destinationTestFolder) {
        File destinationFolder = new File(destinationTestFolder);
        return destinationFolder.delete() && destinationFolder.mkdir();
    }
    
    public static int findNumberOfFilesInADirectory(String path){
        File destinationFolder = new File(path);
        int numberOfFilesWritten = 0;
        try {
            numberOfFilesWritten = destinationFolder.list().length;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return numberOfFilesWritten;
    }
}
