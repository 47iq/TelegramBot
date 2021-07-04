package game;

import org.springframework.stereotype.Service;
import util.ImageIdentifier;

import java.io.File;

@Service
public interface ImageBase {
    File getImage(ImageIdentifier imageIdentifier);
}
