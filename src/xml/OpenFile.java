package xml;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;

public class OpenFile {
    //JFileChooser jfc;
    //Container parent;
    public static JTree tree;
    public static String fileName, line;

//    public OpenFile(JMenuItem j) {
//        //jfc = new JFileChooser();
//        //jfc.setFileFilter(new XmlFileFilter());
//        //parent = j.getParent();
//        //jfc.setSize(400, 300);
//    }

//    public void actionPerformed(ActionEvent e) {
//        //choice = jfc.showOpenDialog(parent);
//        //if (choice == JFileChooser.APPROVE_OPTION) {
//            BufferedReader reader;
//
//            //fileName = jfc.getSelectedFile().getAbsolutePath();//返回选中文件的绝对路径
//            LeftBottomJPanel.openFileName = fileName;
//            try {
//                reader = new BufferedReader(new FileReader(fileName));
//                Docx.jTextArea.setText("");
//                while ((line = reader.readLine()) != null) {
//                    Docx.jTextArea.append(line + "\n");
//                }
//                tree = (JTree) new XMLTree(fileName);//xml树目录
//                LeftBottomJPanel.root.removeAllChildren();//将树目录展示在JFrame窗体上
//                if (XMLTree.docType != null)
//                    LeftBottomJPanel.treeModel.insertNodeInto(XMLTree.docTypeNode, LeftBottomJPanel.root, LeftBottomJPanel.root.getChildCount());
//                LeftBottomJPanel.treeModel.insertNodeInto(XMLTree.rootTreeNode, LeftBottomJPanel.root, LeftBottomJPanel.root.getChildCount());
//                LeftBottomJPanel.treeModel.reload();
//                expandAll(LeftBottomJPanel.jtree_3, new TreePath(LeftBottomJPanel.jtree_3.getModel().getRoot()));//调用expandAll函数
//                reader.close();
//            } catch (Exception ex) {
//
//            }
//
//
//        //}
//    }

    public static void expandAll(JTree tree, TreePath path) {
        //assert (tree != null) && (path != null);
        tree.expandPath(path);//tree的路径
        TreeNode node = (TreeNode) path.getLastPathComponent();
        for (Enumeration i = node.children(); i.hasMoreElements(); ) {
            expandAll(tree, path.pathByAddingChild(i.nextElement()));
        }
    }

}