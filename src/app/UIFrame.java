package app;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * ZIP压缩，解压缩
 */
public class UIFrame {

    private JFrame jf;

    private JTextField srcFiles;
    private JTextField destZip;
    private JTextField srcZip;
    private JTextField destFiles;

    JButton btn_srcFiles, btn_destZip, compact, btn_srcZip, btn_destFiles, unCompact;
    JCheckBox checkBox;

    JFileChooser fileChooser = new JFileChooser();
    ActionHandle handle = new ActionHandle();

    File src = null;
    File dest = null;
    File src2 = null;
    File dest2 = null;

    public UIFrame() {

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//可选择目录

        jf = new JFrame("Zip-Tools 3.0");
        jf.setSize(750, 410);
        //jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // center
        JPanel centerP = new JPanel();
        jf.getContentPane().add(centerP, BorderLayout.CENTER);

        JLabel label = new JLabel("\u5F85\u538B\u7F29\u6587\u4EF6\u6E90\uFF1A");//待压缩的文件源

        srcFiles = new JTextField();
        srcFiles.setColumns(10);

        //浏览srcFiles--压缩
        btn_srcFiles = new JButton("\u6D4F\u89C8");//浏览

        JLabel label_1 = new JLabel("\u538B\u7F29\u5230\uFF1A");//压缩到

        destZip = new JTextField();
        destZip.setColumns(10);

        //view destZip
        btn_destZip = new JButton("\u6D4F\u89C8");//浏览

        //压缩
        compact = new JButton("\u538B\u7F29");


        JLabel label_2 = new JLabel("\u5F85\u89E3\u538B\u6587\u4EF6\u6E90\uFF1A");

        srcZip = new JTextField();
        srcZip.setColumns(10);

        //浏览--srcZip
        btn_srcZip = new JButton("\u6D4F\u89C8");

        JLabel label_3 = new JLabel("\u89E3\u538B\u5230\uFF1A");

        destFiles = new JTextField();
        destFiles.setColumns(10);

        //浏览--destFiles
        btn_destFiles = new JButton("\u6D4F\u89C8");

        //解压
        unCompact = new JButton("\u89E3\u538B");

        btn_srcFiles.addActionListener(handle);
        btn_destZip.addActionListener(handle);
        btn_srcZip.addActionListener(handle);
        btn_destFiles.addActionListener(handle);

        compact.addActionListener(handle);
        unCompact.addActionListener(handle);

        //解压后删除源
        checkBox = new JCheckBox("\u89E3\u538B\u540E\u5220\u9664\u6E90");


        GroupLayout gl_centerP = new GroupLayout(centerP);
        gl_centerP.setHorizontalGroup(
                gl_centerP.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_centerP.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_centerP.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(label_3)
                                        .addComponent(label_2)
                                        .addComponent(label_1)
                                        .addComponent(label))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(destFiles)
                                        .addComponent(srcZip)
                                        .addComponent(destZip)
                                        .addComponent(srcFiles, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_centerP.createSequentialGroup()
                                                .addComponent(btn_srcZip)
                                                .addContainerGap())
                                        .addGroup(gl_centerP.createParallelGroup(Alignment.TRAILING)
                                                .addGroup(gl_centerP.createSequentialGroup()
                                                        .addComponent(btn_srcFiles)
                                                        .addContainerGap(206, Short.MAX_VALUE))
                                                .addGroup(gl_centerP.createSequentialGroup()
                                                        .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
                                                                .addComponent(btn_destZip)
                                                                .addComponent(btn_destFiles))
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(checkBox)
                                                        .addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                                        .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
                                                                .addComponent(unCompact)
                                                                .addComponent(compact))
                                                        .addGap(15)))))
        );
        gl_centerP.setVerticalGroup(
                gl_centerP.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_centerP.createSequentialGroup()
                                .addGap(47)
                                .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(srcFiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_srcFiles))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_1)
                                        .addComponent(destZip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_destZip)
                                        .addComponent(compact))
                                .addGap(59)
                                .addGroup(gl_centerP.createParallelGroup(Alignment.LEADING)
                                        .addComponent(label_2)
                                        .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(srcZip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_srcZip)))
                                .addGap(18)
                                .addGroup(gl_centerP.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label_3)
                                        .addComponent(destFiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_destFiles)
                                        .addComponent(unCompact)
                                        .addComponent(checkBox))
                                .addContainerGap(184, Short.MAX_VALUE))
        );
        centerP.setLayout(gl_centerP);

        //改变标题栏窗口左侧默认图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.createImage(getClass().getResource("/images/Url.gif"));
        jf.setIconImage(image);

        jf.setVisible(true);
    }

    /**
     * 主界面菜单--事件监听
     */
    class ActionHandle implements ActionListener {

        File f = null;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btn_srcFiles) {//选择要压缩的文件路径
                fileChooser.showOpenDialog(jf);
                src = fileChooser.getSelectedFile();
                System.out.println("zip source: " + src);
                srcFiles.setText(src.getAbsolutePath());
            }
            if (e.getSource() == btn_destZip) {//选择压缩的文件--目的路径
                fileChooser.showOpenDialog(jf);
                f = fileChooser.getSelectedFile();
                System.out.println("zip target: " + f.toString());
                destZip.setText(f.getAbsolutePath());
            }
            if (e.getSource() == compact) {//压缩文件
                dest = new File(f.getAbsolutePath(), src.getName() + ".zip");
                System.out.println("包含的文件: " + dest.getName());
                new CompactAlgorithm(dest).zipFiles(src);
                JOptionPane.showMessageDialog(jf, "压缩完毕");
            }
            if (e.getSource() == btn_srcZip) {//选择要解缩的文件
                fileChooser.showOpenDialog(jf);
                src2 = fileChooser.getSelectedFile();
                System.out.println("unzip source: " + src2);
                srcZip.setText(src2.getAbsolutePath());
            }
            if (e.getSource() == btn_destFiles) {//选择解缩文件--目的路径
                fileChooser.showOpenDialog(jf);
                dest2 = fileChooser.getSelectedFile();
                System.out.println("unzip target：" + dest2);
                destFiles.setText(dest2.getAbsolutePath());
            }
            if (e.getSource() == unCompact) {//解压文件
                File src22 = new File(src2.getAbsolutePath());
                src2 = null;
                System.gc();
                try {
                    UnZipFile.unZipFiles(src22, dest2.getAbsolutePath().replaceAll("\\*", "/") + "/");
                    if (checkBox.isSelected()) {
                        boolean b = src22.delete();//解压后删除源
                        System.out.println("解压后删除源" + b);
                    }
                    JOptionPane.showMessageDialog(jf, "解压完毕");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(jf, "解压Error" + e1.getMessage());
                }
            }

        }
    }

}
