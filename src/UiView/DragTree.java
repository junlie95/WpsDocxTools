package UiView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.File;

class DragTree extends JTree{

    public DragTree(DefaultTreeModel defaultTreeModel) {
        super(defaultTreeModel);
    }

    public String getFilename() {
        TreePath path = getLeadSelectionPath();
        FileNode node = (FileNode) path.getLastPathComponent();
        return ((File) node.getUserObject()).getAbsolutePath();
    }


}

class FileNode extends DefaultMutableTreeNode {

    public FileNode(File file) {
        setUserObject(file);
    }

    public boolean getAllowsChildren() {
        return isDirectory();
    }

    public boolean isLeaf() {
        return !isDirectory();
    }

    public File getFile() {
        return (File) getUserObject();
    }

    public boolean isDirectory() {
        File file = getFile();
        return file.isDirectory();
    }

    public String toString() {
        File file = (File) getUserObject();
        String filename = file.toString();
        int index = filename.lastIndexOf(File.separator);

        return (index != -1 && index != filename.length() - 1) ? filename
                .substring(index + 1) : filename;
    }

}
