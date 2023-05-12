import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.JTextArea;

public class model {
    public static void main(String[] args)

    {
        model main1 = new model();
        main1.go();

    }

    void go()

    {
        JFrame frame = new JFrame("你好世界");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("点我测试");

        button.addActionListener(new buttonListener());

        JTextArea textArea = new JTextArea(10, 20);

        JPanel mainpanel = new JPanel();

        mainpanel.add(button);

        frame.getContentPane().add(BorderLayout.CENTER, mainpanel);

        frame.setSize(450, 550);

        frame.setVisible(true);

    }

    class buttonListener implements ActionListener {
        @Override

        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根

            // int res = JOptionPane.showConfirmDialog(null, "是否继续", "我要提示你",

            // JOptionPane.YES_NO_OPTION);
            String[] options = { "A选项", "B选项", "C选项", "D选项" };
            int res = JOptionPane.showOptionDialog(null, "请选择你的选项：", "提示", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]); // 选择对话框*/
            System.out.printf("%d\n", res);
            if (res == JOptionPane.YES_OPTION)

            {
                System.out.println("点击了YES");

            }

            else

            {
                System.out.println("点击了NO");

            }

        }

    }

}