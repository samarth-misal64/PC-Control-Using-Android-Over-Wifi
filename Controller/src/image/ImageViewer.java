package image;


import static controller.MainScreenController.mainScreenController;

/**
 *
 * @author karan
 */

public class ImageViewer {
    
    public void showImage(final String name, final String path) {
        mainScreenController.showImage(name, path);
    }
    
    public void closeImageViewer() {
        mainScreenController.closeImageViewer();
    }
}
