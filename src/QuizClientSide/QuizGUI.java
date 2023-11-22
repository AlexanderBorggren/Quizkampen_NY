package QuizClientSide;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuizGUI extends JFrame {

    Icon avatar1 = new ImageIcon("Images/Avatar_1.jpg");
    Icon avatar2 = new ImageIcon("Images/Avatar_2.jpg");
    Icon categoryIcon = new ImageIcon("Images/Kategori1.jpg");
    Icon questionBackground = new ImageIcon("Images/Questions.jpg");

    JPanel playerPanel = new JPanel();
    JPanel questionPanel = new JPanel();
    JPanel scores = new JPanel();
    JPanel answerPanel = new JPanel();
    JLabel player1 = new JLabel(avatar1);
    JLabel player2 = new JLabel(avatar2);
    JLabel category = new JLabel(categoryIcon);
    //JLabel questionField = new JLabel(questionBackground);
    JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    JButton[] answerButtons = {new JButton(),new JButton(),new JButton(),new JButton()};


    JProgressBar progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
    LayoutManager playersLayout = new BorderLayout();
    LayoutManager answerLayout = new GridLayout(2,2);
    Font f = new Font(null, 3, 20);
    Color answButtonColor = new Color(20,40,100);

    public QuizGUI(){

        setPlayerPanelProperties();
        setQuestionPanelProperties();
        setAnswerPanelProperties();

        add(playerPanel,BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        //add(Box.createVerticalStrut(10));
        add(answerPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void setPlayerPanelProperties(){
        playerPanel.setPreferredSize(new Dimension(100,170));
        playerPanel.setLayout(playersLayout);
        playerPanel.add(player1,BorderLayout.WEST);
        playerPanel.add(category,BorderLayout.CENTER);
        playerPanel.add(player2,BorderLayout.EAST);

    }

    public void setQuestionPanelProperties(){
        String questionText = "Fråga?";
        //questionPanel.setPreferredSize(new Dimension(300,200));
        questionLabel.setPreferredSize(new Dimension(300,200));
        questionPanel.setLayout(new BorderLayout());
        questionLabel.setBackground(Color.BLUE);
        questionLabel.setText(questionText);
        questionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        questionLabel.setVerticalTextPosition(SwingConstants.CENTER);
        questionLabel.setOpaque(true);
        questionLabel.setFont(f);


        progressBar.setMinimum(0);
        progressBar.setMaximum(3);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(300,16));
        progressBar.setForeground(Color.RED);
        progressBar.setBackground(Color.GRAY);
        questionPanel.add(questionLabel,BorderLayout.NORTH);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(progressBar,BorderLayout.SOUTH);

    }

    public void setAnswerPanelProperties(){
        String answerText = "Svar";
        answerPanel.setPreferredSize(new Dimension(200,200));
        answerPanel.setLayout(answerLayout);
        SoftBevelBorder border = new SoftBevelBorder(SoftBevelBorder.RAISED);
        //Border border1 = new Rou
        for(JButton jb: answerButtons){
            jb.setBackground(answButtonColor);
            jb.setOpaque(true);
            jb.setFont(f);
            jb.setText(answerText);
            jb.setLayout(new BorderLayout());
            jb.setHorizontalTextPosition(JLabel.CENTER);
            jb.setVerticalTextPosition(JLabel.CENTER);
            jb.setBackground(answButtonColor);
            jb.setForeground(Color.WHITE);
            jb.setBorder(border);
            jb.setFocusPainted(false);
            //jb.setFocusable(false);
            //jb.setPreferredSize(new Dimension(150,150));
            answerPanel.add(jb);
        }
    }

    void addButtonListener(ActionListener aListener){
        for(JButton jB: answerButtons){
            jB.addActionListener(aListener);
        }
    }

    public void setQuestionLabelText(String text){
        questionLabel.setText(text);
    }

    public void setAnswerButtonText(String[] text){
        for(int i=0; i<answerButtons.length; i++){
            answerButtons[i].setText(text[i]);
        }
    }

    public String getButtonText(int buttonNr){
        return answerButtons[buttonNr].getText();
    }
/*
    public static void main(String[] args) {
        new PlayerGUI();
    }*/
}
