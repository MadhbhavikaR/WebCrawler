package webcrawler.config.persist;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import webcrawler.config.persist.factory.Download;

public class StreamDownloader implements Download {

    @Override
    public void downloadIndividualFile(URL url, String fileName) throws IOException {
         try (FileOutputStream os = new FileOutputStream(fileName)) {
            try (InputStream is = url.openStream()) {
                byte[] buf = new byte[1048576];
                int n = is.read(buf);
                while (n != -1) {
                    os.write(buf, 0, n);
                    n = is.read(buf);
                }
            }
        }
    }    
}
