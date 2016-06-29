package webcrawler.config.persist;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import webcrawler.config.persist.factory.Download;

public class NIODownloader implements Download {

    @Override
    public void downloadIndividualFile(URL url, String fileName) throws IOException {
        try(ReadableByteChannel rbc = Channels.newChannel(url.openStream())){
            try(FileOutputStream fos = new FileOutputStream(fileName)){
               fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); 
            }
        } 
    }    
}
