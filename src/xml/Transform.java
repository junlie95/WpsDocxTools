package xml;

import java.util.*;
import java.io.*;
import javax.swing.tree.*;

import UiView.Docx;
import org.jdom.*;
import org.jdom.output.*;
import unzip.LeftBottomJPanel;

public class Transform {
    Element nElement;
    int index, i, j, n;
    DefaultMutableTreeNode t;
    Element rootElement;
    Document doc;
    BufferedReader reader;
    String line;
    DefaultMutableTreeNode flag;

    public void toXML() {
        t = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, 0);
        TreePath treepath = LeftBottomJPanel.jtree_3.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (treepath.getLastPathComponent());
        if (LeftBottomJPanel.treeModel.getChildCount(LeftBottomJPanel.root) != 1) {
            if (node == LeftBottomJPanel.root) {
                t = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, 0);
                rootElement = new Element(t.getUserObject().toString());
            } else {
                DefaultMutableTreeNode child;
                for (i = 0; i < LeftBottomJPanel.treeModel.getChildCount(LeftBottomJPanel.root); i++) {
                    child = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, i);
                    isChild(node);
                    if (node == child || flag == child) {
                        t = child;
                        rootElement = new Element(t.getUserObject().toString());
                        break;
                    }
                    if (i >= LeftBottomJPanel.treeModel.getChildCount(LeftBottomJPanel.root))
                        t = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, 0);
                    rootElement = new Element(t.getUserObject().toString());
                }
            }
        } else {
            t = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, 0);
            rootElement = new Element(t.getUserObject().toString());
        }
        doc = new Document(rootElement);
        try {
            addElement(rootElement, t);
        } catch (Exception e) {
        }
        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat().setEncoding("gb2312"));

        try {
            Docx.jTextArea.setText("");
            File file = new File("/my.xml");
            FileWriter writer = new FileWriter(file);
            xmlOut.output(doc, writer);
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                Docx.jTextArea.append(line + "\n");
            }
            reader.close();
            writer.close();
            file.delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void isChild(DefaultMutableTreeNode node) {
        if (node != LeftBottomJPanel.root && (DefaultMutableTreeNode) node.getParent() != LeftBottomJPanel.root) {
            DefaultMutableTreeNode father = (DefaultMutableTreeNode) node.getParent();
            for (j = 0; j < LeftBottomJPanel.treeModel.getChildCount(LeftBottomJPanel.root); j++) {
                DefaultMutableTreeNode n = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(LeftBottomJPanel.root, j);
                if (father == n) {
                    flag = father;
                    break;
                }
            }
            if (j >= LeftBottomJPanel.treeModel.getChildCount(LeftBottomJPanel.root))
                isChild(father);
        }
    }

    public void addElement(Element ele, DefaultMutableTreeNode node) {
        for (index = 0; index < LeftBottomJPanel.treeModel.getChildCount(node); index++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(node, index);
            if (n.getUserObject().toString().equals("NODE_ATTRIBUTE")) {
                for (int i = 0; i < n.getChildCount(); i++) {
                    DefaultMutableTreeNode attnode = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(n, i);
                    String s = attnode.toString();
                    String string[] = new String[2];
                    string = getAttrAndValue(s);
                    ele.setAttribute(string[0], string[1]);
                }
            } else if (n.getUserObject().toString().equals("NODE_CONTENT")) {
                DefaultMutableTreeNode contentNode = (DefaultMutableTreeNode) LeftBottomJPanel.treeModel.getChild(n, 0);
                String s = contentNode.toString();
                ele.addContent(s);
            } else {
                nElement = new Element(n.getUserObject().toString());
                ele.addContent(nElement);

                if (LeftBottomJPanel.treeModel.getChildCount(n) > 0)
                    addElement(nElement, n);
            }
            index = LeftBottomJPanel.treeModel.getIndexOfChild(node, n);
        }
    }

    public String[] getAttrAndValue(String s) {
        StringTokenizer anynize = new StringTokenizer(s, "=");
        String[] string = new String[2];
        string[0] = anynize.nextToken();
        string[1] = anynize.nextToken();
        return string;
    }
}

