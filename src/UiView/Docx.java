package UiView;

import unzip.LeftBottomJPanel;
import unzip.UnZipFile;
import xml.DocxFileFilter;
import xml.XMLTree;
import xml.XmlFileFilter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.Date;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static unzip.LeftBottomJPanel.jtree_3;
import static xml.OpenFile.*;

public class Docx extends JFrame {
    private JMenuBar jMenuBar;
    private JMenu fileJMenu, editorJMenu, toolJMenu, helpMenu;
    private JMenuItem newFile, openFile, saveFile, exitSystem, notepad, calculator, copyItem, pasteItem, cutItem, selectAll, colorItem, aboutItem, xmlSpy, wpsItem;
    public static File file_2;
    public static JTextArea jTextArea;
    public static JFileChooser jFileChooser;
    Color color = Color.black;

    String oldValue;   //存放编辑区原来的内容，用于比较文本是否有改动
    boolean isNewFile = true;    //是否新文件(未保存过的)
    File currentFile;     //当前文件名
    JLabel statusLabel;//状态栏标签

    public static JTree jTree;
    public static DefaultTreeModel newModel;//一棵树
    public static DefaultMutableTreeNode Node;//节点
    static DefaultMutableTreeNode temp;
    public static String path = null;//需要遍历的目录
    public LeftBottomJPanel leftBottomJPanel;
    public LeftTopJPanel leftTopJPanel;

    public Docx() {
        //菜单栏
        jMenuBar = new JMenuBar();
        //菜单
        fileJMenu = new JMenu("文件(F)");
        editorJMenu = new JMenu("编辑(E)");
        toolJMenu = new JMenu("工具(T)");
        helpMenu = new JMenu("帮助(H)");
        //工具栏
        initToolBar();
        //菜单项及快捷键设置
        newFile = new JMenuItem("新建(N)");
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));//快捷键设置

        openFile = new JMenuItem("打开(O)");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

        saveFile = new JMenuItem("保存(S)");
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        exitSystem = new JMenuItem("退出(X)");
        notepad = new JMenuItem("记事本");
        calculator = new JMenuItem("计算器");

        copyItem = new JMenuItem("复制(C)");
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

        pasteItem = new JMenuItem("粘贴(V)");
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

        cutItem = new JMenuItem("剪切(T)");
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

        selectAll = new JMenuItem("全选");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        colorItem = new JMenuItem("颜色");
        aboutItem = new JMenuItem("关于");
        xmlSpy = new JMenuItem("XmlSpy");
        wpsItem = new JMenuItem("WPS");
        //将菜单追加到菜单栏的末尾
        jMenuBar.add(fileJMenu);
        jMenuBar.add(editorJMenu);
        jMenuBar.add(toolJMenu);
        jMenuBar.add(helpMenu);
        //为fileJMenu菜单添加菜单项
        fileJMenu.add(newFile);
        fileJMenu.addSeparator();
        fileJMenu.add(openFile);
        fileJMenu.addSeparator();
        fileJMenu.add(saveFile);
        fileJMenu.addSeparator();
        fileJMenu.add(exitSystem);
        //为editorJMenu菜单添加菜单项
        editorJMenu.add(copyItem);
        editorJMenu.addSeparator();//为菜单添加分隔线
        editorJMenu.add(pasteItem);
        editorJMenu.addSeparator();
        editorJMenu.add(cutItem);
        editorJMenu.addSeparator();
        editorJMenu.add(selectAll);
        editorJMenu.addSeparator();
        editorJMenu.add(colorItem);
        //为toolJMenu菜单添加菜单项
        toolJMenu.add(notepad);
        toolJMenu.addSeparator();
        toolJMenu.add(calculator);
        toolJMenu.addSeparator();
        toolJMenu.add(xmlSpy);
        toolJMenu.addSeparator();
        toolJMenu.add(wpsItem);
        //为helpMenu菜单添加菜单项
        helpMenu.add(aboutItem);
        //为窗体设置菜单栏
        this.setJMenuBar(jMenuBar);
        this.setTitle("wps_docx助手");
        this.setBounds(400, 300, 1250, 700);
        //this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        //改变标题栏窗口左侧默认图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.createImage(getClass().getResource("/images/Notepad.gif"));
        this.setIconImage(image);

        //左边面板
        LeftPane leftJPane = new LeftPane();

        //文本区域
        jTextArea = new JTextArea(30, 50);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);//为文本区域jTextArea设置滚动条jScrollPane
        jScrollPane.setRowHeaderView(new LineNumberHeaderView());//为文本区域设置行号

        /**
         对Docx窗体左右分割
         */
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftJPane, jScrollPane /*new JScrollPane(jTextArea)*/);
        this.add(jSplitPane, BorderLayout.CENTER);
        jSplitPane.setOneTouchExpandable(true);
        jTextArea.setBackground(Color.white); //设置编辑区默认背景色
        jTextArea.setForeground(Color.black);
        //jSplitPane.setDividerSize(8);
        //jSplitPane.setDividerLocation(0.3);

        jSplitPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                jSplitPane.setDividerLocation(1.0 / 5.0);
            }
        });

        /**
         对leftJPane面板上下分割
         */
        leftTopJPanel = new LeftTopJPanel();
        JScrollPane jScrollPane1_3 = new JScrollPane(leftTopJPanel);
        leftBottomJPanel = new LeftBottomJPanel();//左下角面板
        JScrollPane jScrollPane_4 = new JScrollPane(leftBottomJPanel);//带滚动条的左下角面板leftBottomJPanel
        JSplitPane jSplitPane1_2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane1_3, jScrollPane_4);
        leftJPane.add(jSplitPane1_2, BorderLayout.CENTER);//将上下分割的分隔条添加到左边的面板容器中
        jSplitPane1_2.setOneTouchExpandable(true);

        jSplitPane1_2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                jSplitPane1_2.setDividerLocation(1.0 / 2.0);
            }
        });

        this.setVisible(true);//显示窗体

        //为菜单项设置监听器
        newFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                jTextArea.setText(date.toString());
                file_2 = null;
            }
        });
        openFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                /*
                   文件过滤器
                */
                jFileChooser.setFileFilter(new DocxFileFilter());//优先选择docx文件
                jFileChooser.setFileFilter(new XmlFileFilter());//选择XML文件

                if (file_2 == null) {
                    jFileChooser.setSelectedFile(file_2);
                }

                int returnVal = jFileChooser.showOpenDialog(Docx.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file_2 = jFileChooser.getSelectedFile();//返回选定的文件

                    //如果是docx文档或者zip文档，直接解压
                    if (file_2.getName().endsWith(".docx") || file_2.getName().endsWith(".zip")) {
                        try {
                            String strPath = "F:\\Android\\";
                            UnZipFile.unZipFiles(file_2, strPath);
                            path = strPath;
                            init();//遍历目录，生成JTree目录树
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    //如果是XML文件，调用打开XML格式文件的函数
                    else if (file_2.getName().endsWith(".xml")) {
                        XmlOpenFile();
                    }
                    //其余为普通文本文件
                    else {
                        openFileText();
                    }

                }

            }
        });
        saveFile.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (file_2 != null)
                    jFileChooser.setSelectedFile(file_2);

                int returnVal = jFileChooser.showSaveDialog(Docx.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file_2 = jFileChooser.getSelectedFile();
                    saveFileText();
                }
            }
        });
        exitSystem.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e)  //当鼠标点击时
            {
                System.exit(0);//退出
            }
        });
        copyItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.copy();
            }
        });
        pasteItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.paste();
            }
        });
        cutItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.cut();
            }
        });
        selectAll.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.selectAll();
            }
        });
        colorItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = JColorChooser.showDialog(Docx.this, "颜色选择", color);
                jTextArea.setForeground(color);
            }
        });
        notepad.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "notepad.exe";
                    Runtime.getRuntime().exec(command);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        calculator.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "calc.exe";
                    Runtime.getRuntime().exec(command);
                } catch (IOException ex2) {
                    ex2.printStackTrace();
                }
            }
        });
        xmlSpy.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "C:/Program Files (x86)/Altova/XMLSpy2013/XMLSpy.exe";
                    Process process = Runtime.getRuntime().exec(command);
                    System.err.println("process==" + process.getInputStream());
                } catch (IOException ex3) {
                    ex3.printStackTrace();
                }
            }
        });
        wpsItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "C:\\Users\\junlie\\AppData\\Local\\Kingsoft\\WPS Office\\11.1.0.7693\\office6\\wpsoffice.exe";
                    Process process = Runtime.getRuntime().exec(command);
                    System.err.println("process==" + process.getInputStream());
                } catch (IOException ex4) {
                    ex4.printStackTrace();
                }
            }
        });
        aboutItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(aboutItem, "欢迎使用君烈的docx助手！！！", "关于", INFORMATION_MESSAGE);
            }
        });

    }

    //打开处理XML文件
    void XmlOpenFile() {
        BufferedReader reader;
        fileName = jFileChooser.getSelectedFile().getAbsolutePath();//返回选中文件的绝对路径
        LeftBottomJPanel.openFileName = fileName;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            Docx.jTextArea.setText("");
            jTextArea.setLineWrap(true);//换行
            while ((line = reader.readLine()) != null) {
                Docx.jTextArea.append(line + "\n");
            }
            tree = (JTree) new XMLTree(fileName);//xml树目录
            LeftBottomJPanel.root.removeAllChildren();//将树目录展示在窗体上
            if (XMLTree.docType != null)
                LeftBottomJPanel.treeModel.insertNodeInto(XMLTree.docTypeNode, LeftBottomJPanel.root, LeftBottomJPanel.root.getChildCount());
            LeftBottomJPanel.treeModel.insertNodeInto(XMLTree.rootTreeNode, LeftBottomJPanel.root, LeftBottomJPanel.root.getChildCount());
            LeftBottomJPanel.treeModel.reload();
            expandAll(jtree_3, new TreePath(jtree_3.getModel().getRoot()));//调用expandAll函数
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //docx文件生成JTree目录树
    public void init() {
        Node = traverseFolder(path);
        newModel = new DefaultTreeModel(Node);
        jTree = new JTree(newModel);
        //靠近左边
        leftTopJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        leftTopJPanel.setLayout(new BorderLayout(0, 0));
        leftTopJPanel.add(jTree);//直接使用LeftTopJPanel的滚动条
        XmlTreeClick();
    }

    //遍历解压后的docx目录
    public static DefaultMutableTreeNode traverseFolder(String path) {
        DefaultMutableTreeNode fujiedian = new DefaultMutableTreeNode(new File(path).getName());
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                if (file.isDirectory()) {//如果是空文件夹
                    DefaultMutableTreeNode dn = new DefaultMutableTreeNode(file.getName(), false);
                    return dn;
                }
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //是目录的话，生成节点，并添加里面的节点
                        fujiedian.add(traverseFolder(file2.getAbsolutePath()));//递归
                    } else {
                        //是文件的话直接生成节点，并把该节点加到对应父节点上
                        temp = new DefaultMutableTreeNode(file2.getName());
                        fujiedian.add(temp);
                    }
                }
            }
        } else {//文件不存在
            return null;
        }
        return fujiedian;
    }

    public void XmlTreeClick() {
        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
                    if (path == null) {
                        return;
                    } else {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        //String file = ((File) node.getUserObject()).getAbsolutePath().toString();
                        String file = (node.getUserObject().toString());//获取文件路径名
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (selectedNode.isLeaf()) {
                            XmlOpenFile();
                            //openGetFile(file);
                            //openFileText();

                        }
                    }
                }
//                if (e.getSource() == jTree && e.getClickCount() == 2) {
//                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
//                    if (treeNode.isLeaf()) {
//                        XmlOpenFile();
//                    }
//                }

            }
        });
    }

    //打开文件
    void openFileText() {
        try {
            FileReader fileReader = new FileReader(file_2);
            int len = (int) file_2.length();
            char[] buffer = new char[len];
            fileReader.read(buffer, 0, len);
            fileReader.close();
            jTextArea.setText(new String(buffer));
            jTextArea.setLineWrap(true);//换行
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保存文件
    void saveFileText() {
        try {
            FileWriter fileWriter = new FileWriter(file_2);
            fileWriter.write(jTextArea.getText());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JToolBar toolbar = new JToolBar();
    JButton[] buttons = new JButton[]
            {
                    new JButton("", new ImageIcon("./src/images/copy.jpg")),

                    new JButton("", new ImageIcon("./src/images/paste.jpg")),

                    new JButton("", new ImageIcon("./src/images/cut.jpg")),

                    new JButton("", new ImageIcon("./src/images/MS1.jpg")),

                    new JButton("", new ImageIcon("./src/images/MS2.jpg")),
            };

    //工具栏
    void initToolBar() {
        for (int i = 0; i < buttons.length; i++)
            toolbar.add(buttons[i]);

        buttons[0].setToolTipText("复制");
        buttons[0].addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jTextArea.copy();
            }
        });

        buttons[1].setToolTipText("粘贴");
        buttons[1].addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jTextArea.paste();
            }
        });

        buttons[2].setToolTipText("剪切");
        buttons[2].addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jTextArea.cut();
            }
        });

        buttons[3].setToolTipText("记事本");
        buttons[3].addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "notepad.exe";
                    Process child = Runtime.getRuntime().exec(command);
                } catch (IOException ex) {
                }
            }
        });

        buttons[4].setToolTipText("计算器");
        buttons[4].addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "calc.exe";
                    Process child = Runtime.getRuntime().exec(command);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.getContentPane().add(toolbar, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
        JSplashWindowEx splash = new JSplashWindowEx();
        splash.start();
        try {
            Thread.sleep(800);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置界面为WindowsLookAndFeel,使用Windows风格外观
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }

        new Docx();//创建Docx对象
    }

}

//启动屏
class JSplashWindowEx extends JWindow implements Runnable {
    Thread splashThread = null;
    private JProgressBar progress;

    public JSplashWindowEx() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        JPanel splash = new JPanel(new BorderLayout());
        URL url = getClass().getResource("/images/winter.jpg");

        if (url != null) {
            splash.add(new JButton(new ImageIcon(url)),
                    BorderLayout.CENTER);
        }
        progress = new JProgressBar(1, 100);
        progress.setStringPainted(true);
        progress.setBorderPainted(false);
        progress.setString("请稍后，程序正在加载...");
        progress.setBackground(Color.white);
        splash.add(progress, BorderLayout.SOUTH);
        setContentPane(splash);

        Dimension screen = getToolkit().getScreenSize();
        pack();
        setLocation((screen.width - getSize().width) / 2,
                (screen.height - getSize().height) / 2);
    }

    public void start() {
        this.toFront();
        splashThread = new Thread(this);
        splashThread.start();
    }

    public void run() {
        //show();
        setVisible(true);
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(15);
                progress.setValue(progress.getValue() + 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dispose();
    }
}

/**
 * 之前主函数存在的位置
 */
//public class DocxUi {
//    public static void main(String[] args) {
//        JSplashWindowEx splash = new JSplashWindowEx();
//        splash.start();
//        try {
//            Thread.sleep(800);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //设置界面为WindowsLookAndFeel,使用Windows风格外观
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception e) {
//        }
//
//        new Docx();//创建Docx对象
//    }
//
//}

