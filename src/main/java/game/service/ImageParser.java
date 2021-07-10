package game.service;

import game.entity.ImageIdentifier;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Interface that provides image parsing methods
 */

public interface ImageParser {

    /**
     * Method that gets an image by image identifier
     *
     * @param imageIdentifier image identifier
     * @return image
     */

    File getImage(ImageIdentifier imageIdentifier);
}
