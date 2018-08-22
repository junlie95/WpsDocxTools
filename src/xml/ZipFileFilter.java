package xml;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ZipFileFilter extends FileFilter {
    public boolean accept(File file){
        if(file.isDirectory())
            return true;
        else
            return file.getName().endsWith(".zip");
    }
    public String getDescription(){
        return "*.zip";
    }
}
