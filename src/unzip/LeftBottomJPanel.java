package unzip;

import xml.MItemListener;
import xml.MyMouseListener;
import xml.Transform;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class LeftBottomJPanel extends JPanel implements TreeModelListener, TreeSelectionListener {

    public static String openFileName = "";
    public static DefaultTreeModel treeModel = null;
    public static DefaultMutableTreeNode root, xmlfile;
    public static JTree jtree_3;

    public static JMenuItem allClear, addn, addt, dele, addatt;//右键菜单
    public static JPopupMenu popupMenu;

    public LeftBottomJPanel() {
        super();
        setBackground(Color.white);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        init_2();
        poPup();
    }

    void init_2() {
        root = new DefaultMutableTreeNode("XML");
        treeModel = new DefaultTreeModel(root);
        xmlfile = new DefaultMutableTreeNode("root");
        treeModel.insertNodeInto(xmlfile, root, root.getChildCount());
        jtree_3 = new JTree(treeModel);
        jtree_3.setEditable(true);
        this.setLayout(new BorderLayout());//布局

        // 创建一个DefaultTreeCellRender对象
        DefaultTreeCellRenderer cellRender = new DefaultTreeCellRenderer();
        // 设置非选定节点的背景色。
        cellRender.setBackgroundNonSelectionColor(new Color(220, 220, 220));
        // 设置节点在选中状态下的背景颜色。
        cellRender.setBackgroundSelectionColor(new Color(140, 140, 140));
        // 设置选中状态下节点的边框颜色。
        cellRender.setBorderSelectionColor(Color.BLACK);
        // 设置处于折叠状态下非叶子节点的图标。
        cellRender.setClosedIcon(new ImageIcon("./src/images/close.gif"));
        // 设置节点文本的字体。
        // cellRender.setFont(new Font("SansSerif" , Font.BOLD , 16));
        // 设置叶子节点的图标。
        cellRender.setLeafIcon(new ImageIcon("./src/images/leaf.png"));
        // 设置处于展开状态下非叶子节点的图标。
        cellRender.setOpenIcon(new ImageIcon("./src/images/open.gif"));
        // 设置绘制非选中状态下节点文本的颜色。
        cellRender.setTextNonSelectionColor(new Color(0, 0, 255));
        // 设置绘制选中状态下节点文本的颜色。
        cellRender.setTextSelectionColor(new Color(255, 0, 0));
        jtree_3.setCellRenderer(cellRender);

        this.add(jtree_3);
    }

    void poPup() {
        popupMenu = new JPopupMenu();//“右拉式 (pull-right)”菜单
        allClear = new JMenuItem("清除所有");
        addn = new JMenuItem("添加节点");
        addt = new JMenuItem("添加目录");
        dele = new JMenuItem("删除节点");
        addatt = new JMenuItem("添加属性");
        allClear.addActionListener(new MItemListener());
        addn.addActionListener(new MItemListener());
        addt.addActionListener(new MItemListener());
        dele.addActionListener(new MItemListener());
        addatt.addActionListener(new MItemListener());

        popupMenu.add(addn);
        popupMenu.add(dele);
        popupMenu.addSeparator();
        popupMenu.add(addt);
        popupMenu.add(addatt);
        popupMenu.addSeparator();
        popupMenu.add(allClear);

        jtree_3.addMouseListener(new MyMouseListener());
        jtree_3.addTreeSelectionListener(this);
        treeModel.addTreeModelListener(this);
    }

    public void valueChanged(TreeSelectionEvent e) {
        try {
            new Transform().toXML();
        } catch (Exception xe) {
        }
    }

    public void treeNodesChanged(TreeModelEvent e) {
        try {
            new Transform().toXML();
        } catch (Exception xe) {
        }
    }

    public void treeNodesInserted(TreeModelEvent e) {
        try {
            new Transform().toXML();
        } catch (Exception xe) {
        }
    }

    public void treeNodesRemoved(TreeModelEvent e) {
        try {
            new Transform().toXML();
        } catch (Exception xe) {
        }
    }

    public void treeStructureChanged(TreeModelEvent e) {
        try {
            new Transform().toXML();
        } catch (Exception xe) {
        }
    }

}
