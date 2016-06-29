package webcrawler.config.persist.factory;

import java.io.IOException;
import java.net.URL;

public interface Download {       
    void downloadIndividualFile(URL url, String fileName) throws IOException;
}
