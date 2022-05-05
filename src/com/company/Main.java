package com.company;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        // 1. 创建一个顶层容器（窗口）
        JFrame jf = new JFrame("测试窗口");          // 创建窗口
        jf.setSize(850, 250);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

        // 2. 创建中间容器（面板容器）
        JPanel panel = new JPanel();                // 创建面板容器，使用默认的布局管理器

        // 3. 创建一个基本组件（按钮），并添加到 面板容器 中
        JButton btn = new JButton("选择文件：");
        JButton btn2 = new JButton("发送");
        JTextField textField = new JTextField();
        btn2.setEnabled(false);
        JLabel label = new JLabel("选择的文件：");
        panel.add(btn);
        panel.add(label);
        panel.add(textField);
        AtomicReference<String> filePath = new AtomicReference<>();

        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        jf.setContentPane(panel);

        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);

        btn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(jf);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                filePath.set(file.getAbsolutePath());
                label.setText("选择文件是: " + filePath.get());
                btn2.setEnabled(true);
            }
        });
        btn2.addActionListener(e->{
            try {
                String filePathTemp = filePath.get();
                String ip = textField.getText();
                Process process = Runtime.getRuntime().exec("pscp "+filePathTemp+" swc010@"+ip+":/home/swc010/ftp");

                OutputStream out = process.getOutputStream();
                out.write("swc1234".getBytes());
                out.flush();

            } catch (IOException e1) { // 改自己的异常类
                e1.printStackTrace();
            }
        });
    }

}

