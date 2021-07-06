package game.service;

import game.entity.ImageIdentifier;
import org.springframework.stereotype.Service;

import java.io.File;

public interface ImageParser {
    File getImage(ImageIdentifier imageIdentifier);
}
