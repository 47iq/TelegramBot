package game;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component("image_base")
public class ImageBaseImpl implements ImageBase{

    Map<ImageIdentifier, String> paths;

    public ImageBaseImpl(Map<ImageIdentifier, String> paths) {
        this.paths = paths;
    }

    @Override
    public File getImage(ImageIdentifier imageIdentifier) {
        return new File(paths.get(imageIdentifier));
    }
}
