package game.service;

import game.entity.ImageIdentifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

public class ImageParserImpl implements ImageParser {

    Map<ImageIdentifier, String> paths;

    public ImageParserImpl() {
    }

    public ImageParserImpl(Map<ImageIdentifier, String> paths) {
        this.paths = paths;
    }

    @Override
    public File getImage(ImageIdentifier imageIdentifier) {
        return new File(paths.get(imageIdentifier));
    }
}
