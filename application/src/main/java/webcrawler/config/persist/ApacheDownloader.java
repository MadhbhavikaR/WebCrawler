package webcrawler.config.persist;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import webcrawler.config.persist.factory.Download;

public class ApacheDownloader implements Download {

    @Override
    public void downloadIndividualFile(URL url, String fileName) throws IOException{
        FileUtils.copyURLToFile(url,new File(fileName)); 
    }
    
}
