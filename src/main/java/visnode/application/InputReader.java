package visnode.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import org.paim.commons.Image;
import org.paim.commons.ImageFactory;
import org.paim.commons.ImageHelper;
import org.paim.commons.Range;
import org.paim.examsio.ExamLoader;
import org.paim.examsio.ExamLoaderException;

/**
 * Class responsible for read input files
 */
public class InputReader {

    private static final String DICOM = "application/dicom";
    
    /**
     * Reads the file
     * 
     * @param file The file
     * @return Image
     * @throws IOException Impossible read the file 
     */
    public Image read(File file) throws IOException {
        String fileType = Files.probeContentType(file.toPath());
        if (isDiacom(fileType)) {
            return readDiacom(file);
        }
        return readImage(file);
    }

    /**
     * Returns true if the file is a DICOM
     * 
     * @param filename
     * @return boolean
     */
    private boolean isDiacom(String fileType) {
        return DICOM.equals(fileType);
    }
    
    private Image readDiacom(File file) throws IOException {
        try {
            return ImageHelper.create(ExamLoader.load(file).getExamSlice(0).getCoefficientMatrix(), new Range<>(-4000, 4000));
        } catch (ExamLoaderException ex) {
            throw new IOException(ex);
        }
    }
    
    private Image readImage(File file) throws IOException {
        return ImageFactory.buildRGBImage(ImageIO.read(file));
    }
    
}
