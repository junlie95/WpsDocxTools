package xml;

import unzip.LeftBottomJPanel;

import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.*;

public class MItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        try {
            DefaultMutableTreeNode thenode;
            if (e.getSource() == LeftBottomJPanel.addn) {
                TreePath parentPath = LeftBottomJPanel.jtree_3.getSelectionPath();
                thenode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
                if (parentPath != null && thenode != LeftBottomJPanel.root) {
                    DefaultMutableTreeNode addnode = new DefaultMutableTreeNode("name");
                    addnode.setAllowsChildren(true);

                    LeftBottomJPanel.treeModel.insertNodeInto(addnode, thenode, thenode.getChildCount());
                    LeftBottomJPanel.jtree_3.scrollPathToVisible(new TreePath(addnode.getPath()));
                }
            } else if (e.getSource() == LeftBottomJPanel.dele) {
                TreePath treepath = LeftBottomJPanel.jtree_3.getSelectionPath();
                if (treepath != null) {
                    DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) treepath.getLastPathComponent();
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectionNode.getParent();
                    if (parent != null && selectionNode != null && selectionNode != LeftBottomJPanel.xmlfile) {
                        LeftBottomJPanel.treeModel.removeNodeFromParent(selectionNode);
                    }
                }
            } else if (e.getSource() == LeftBottomJPanel.allClear) {
                int i;
                DefaultMutableTreeNode child;
                TreePath treepath = LeftBottomJPanel.jtree_3.getSelectionPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) (treepath.getLastPathComponent());
                Transform tran = new Transform();
                tran.isChild(node);
                if (node == LeftBottomJPanel.root) {
                    LeftBottomJPanel.root.removeAllChildren();
                    LeftBottomJPanel.xmlfile.removeAllChildren();
                    LeftBottomJPanel.treeModel.insertNodeInto(LeftBottomJPanel.xmlfile, LeftBottomJPanel.root, LeftBottomJPanel.root.getChildCount());
                } else if (node != LeftBottomJPanel.xmlfile && tran.flag != LeftBottomJPanel.xmlfile) {
                    for (i = 1; i < LeftBottomJPanel.treeModel.getChildCount(LeftBottomJPanel.root); i++) {
                        child = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, i);

                        if (node == child || tran.flag == child) {
                            child.removeAllChildren();
                            LeftBottomJPanel.treeModel.removeNodeFromParent(child);
                            LeftBottomJPanel.treeModel.insertNodeInto(child, LeftBottomJPanel.root, LeftBottomJPanel.root.getChildCount());
                        }
                    }
                } else {
                    LeftBottomJPanel.xmlfile.removeAllChildren();

                }
                LeftBottomJPanel.treeModel.reload();
            } else if (e.getSource() == LeftBottomJPanel.addt) {
                String content = JOptionPane.showInputDialog("content:");
                TreePath treepath = LeftBottomJPanel.jtree_3.getSelectionPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) (treepath.getLastPathComponent());
                String info = node.getUserObject().toString();

                if (node.isLeaf() || onlyAttributeChild(node)) {
                    DefaultMutableTreeNode father = new DefaultMutableTreeNode("NODE_CONTENT");
                    LeftBottomJPanel.treeModel.insertNodeInto(father, node, node.getChildCount());
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(content);
                    child.setAllowsChildren(false);
                    LeftBottomJPanel.treeModel.insertNodeInto(child, father, father.getChildCount());
                    LeftBottomJPanel.jtree_3.scrollPathToVisible(new TreePath(child.getPath()));
                }
            } else if (e.getSource() == LeftBottomJPanel.addatt) {
                String attribute = JOptionPane.showInputDialog("属性:(名和值用等号(=)连接)");
                int i;
                DefaultMutableTreeNode father = null;
                TreePath treepath = LeftBottomJPanel.jtree_3.getSelectionPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) (treepath.getLastPathComponent());
                String info = node.getUserObject().toString();
                DefaultMutableTreeNode cchild = new DefaultMutableTreeNode(attribute);
                cchild.setAllowsChildren(false);
                for (i = 0; i < node.getChildCount(); i++) {
                    father = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(node, i);
                    if (father.getUserObject().toString().equals("NODE_ATTRIBUTE"))
                        break;
                }
                if (i >= node.getChildCount()) {

                    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("NODE_ATTRIBUTE");
                    LeftBottomJPanel.treeModel.insertNodeInto(child1, node, node.getChildCount());
                    LeftBottomJPanel.treeModel.insertNodeInto(cchild, child1, child1.getChildCount());
                    LeftBottomJPanel.jtree_3.scrollPathToVisible(new TreePath(cchild.getPath()));

                } else {
                    LeftBottomJPanel.treeModel.insertNodeInto(cchild, father, father.getChildCount());
                    LeftBottomJPanel.jtree_3.scrollPathToVisible(new TreePath(cchild.getPath()));
                }
            }
        } catch (Exception ex) {
        }
    }

    public boolean onlyAttributeChild(DefaultMutableTreeNode node) {
        if (node.getChildCount() == 1) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.
                    getChild(node, 0);
            if (n.getUserObject().toString().equals("NODE_ATTRIBUTE"))
                return true;
        }
        return false;
    }
}
