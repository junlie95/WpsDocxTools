package UiView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.Enumeration;
import java.util.NoSuchElementException;

class DragTree extends JTree {

    public DragTree(DefaultTreeModel defaultTreeModel) {
        super(defaultTreeModel);
    }

//    public String getFilename() {
//        TreePath path = getLeadSelectionPath();
//        FileNode node = (FileNode) path.getLastPathComponent();
//        return ((File) node.getUserObject()).getAbsolutePath();
//    }

}

/**
 * A node in the file tree.
 *
 * @author Kirill Grouchnikov
 */
class FileTreeNode implements TreeNode {
    /**
     * Node file.
     */
    private File file;

    /**
     * Children of the node file.
     */
    private File[] children;

    /**
     * Parent node.
     */
    private TreeNode parent;

    /**
     * Indication whether this node corresponds to a file system root.
     */
    private boolean isFileSystemRoot;
    transient protected Object userObject;

    /**
     * Creates a new file tree node.
     *
     * @param file             Node file
     * @param isFileSystemRoot Indicates whether the file is a file system root.
     * @param parent           Parent node.
     */
    public FileTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
        this.file = file;
        this.isFileSystemRoot = isFileSystemRoot;
        this.parent = parent;
        this.children = this.file.listFiles();
        if (this.children == null)
            this.children = new File[0];
    }


    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    public Object getUserObject() {
        return userObject;
    }


    public String toString() {
        if (file != null)
            return file.getAbsolutePath();
        else
            return "";
    }

    /**
     * Creates a new file tree node.
     *
     * @param children Children files.
     */
    public FileTreeNode(File[] children) {
        this.file = null;
        this.parent = null;
        this.children = children;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#children()
     */
    public Enumeration<?> children() {
        final int elementCount = this.children.length;
        return new Enumeration<File>() {
            int count = 0;

            /*
             * (non-Javadoc)
             *
             * @see java.util.Enumeration#hasMoreElements()
             */
            public boolean hasMoreElements() {
                return this.count < elementCount;
            }

            /*
             * (non-Javadoc)
             *
             * @see java.util.Enumeration#nextElement()
             */
            public File nextElement() {
                if (this.count < elementCount) {
                    return FileTreeNode.this.children[this.count++];
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        };

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#getChildAt(int)
     */
    public TreeNode getChildAt(int childIndex) {
        return new FileTreeNode(this.children[childIndex],
                this.parent == null, this);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#getChildCount()
     */
    public int getChildCount() {
        return this.children.length;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
     */
    public int getIndex(TreeNode node) {
        FileTreeNode ftn = (FileTreeNode) node;
        for (int i = 0; i < this.children.length; i++) {
            if (ftn.file.equals(this.children[i]))
                return i;
        }
        return -1;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#getParent()
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    public boolean isLeaf() {
        return (this.getChildCount() == 0);
    }
}


class FileNode extends DefaultMutableTreeNode implements TreeNode {

    File file;

    FileNode(File file) {
        this.file = file;
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
        if (file != null) {
            return file.getAbsolutePath();
        } else
            return "";
    }

//    public String toString() {
//        File file = (File) getUserObject();
//        String filename = file.toString();
//        int index = filename.lastIndexOf(File.separator);
//
//        return (index != -1 && index != filename.length() - 1) ? filename
//                .substring(index + 1) : filename;
//    }

}
