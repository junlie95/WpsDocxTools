package xml;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class DocxFileFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        else
            return f.getName().endsWith(".docx");
    }

    public String getDescription() {
        return "*.docx";
    }
}
