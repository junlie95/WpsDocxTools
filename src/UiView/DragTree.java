package UiView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class DragTree extends JTree {
    FileNode sourceNode;
    public DragTree(DefaultTreeModel defaultTreeModel) {
        super(defaultTreeModel);
        this.setCellRenderer(new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree,
                                                          Object value, boolean selected, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                TreePath tp = tree.getPathForRow(row);
                if (tp != null) {
                    FileNode node = (FileNode) tp.getLastPathComponent();
                    File f = node.getFile();
                    try {
                        Icon icon = FileSystemView.getFileSystemView()
                                .getSystemIcon(f);
                        this.setIcon(icon);
                        this.setLeafIcon(icon);
                        this.setOpenIcon(icon);
                        this.setClosedIcon(icon);
                        this.setDisabledIcon(icon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, leaf, row, hasFocus);
            }

        });

        super.setScrollsOnExpand(true);
    }


    public String getFilename() {
        TreePath path = getLeadSelectionPath();
        FileNode node = (FileNode) path.getLastPathComponent();
        return ((File) node.getUserObject()).getAbsolutePath();
    }

    private void cp(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                boolean ret = dest.mkdir();
                if (ret == false)
                    return;
            }
            File[] fs = src.listFiles();
            for (int i = 0; i < fs.length; i++) {
                cp(fs[i], new File(dest, fs[i].getName()));
            }
            return;
        }
        byte[] buf = new byte[1024];
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dest);
        int len;
        try {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            in.close();
            out.close();
        }
    }

}

class FileNode extends DefaultMutableTreeNode {
    private boolean explored = false;

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

    public boolean isExplored() {
        return explored;
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
