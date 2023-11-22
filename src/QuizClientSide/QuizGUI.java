package QuizClientSide;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuizGUI extends JFrame {

    //The different cards in CardLayout
    protected final String WELCOME = "0";
    protected final String CATEGORY = "1";
    protected final String PLAY = "2";
    protected final String SCORE = "3";

    int screenX = 400;
    int screenY = 600;
    Color panelsBackgroundColor = Color.ORANGE;
    Dimension panelsDimension = new Dimension(screenX,screenY);

    //Avatar images
    Icon avatar1 = new ImageIcon("Images/Avatar_1.jpg");
    Icon avatar2 = new ImageIcon("Images/Avatar_2.jpg");

    //Category Images
    Icon categoryIcon = new ImageIcon("Images/Kategori1.jpg");
    Icon questionBackground = new ImageIcon("Images/Question.jpg");

    //CardLayout Panel
    JPanel mainQuizPanel = new JPanel();
    CardLayout cardLo = new CardLayout();

    //------------ Welcome Window ------------------------
    JPanel welcomePanelMain = new JPanel();

    JPanel welcomePanel = new JPanel();
    JPanel welcomeInputPanel = new JPanel();
    JLabel quizLabel = new JLabel("QUIZKAMPEN");
    JLabel nameText = new JLabel("Namn: ");
    JTextField welcomeInput = new JTextField(30);
    JButton welcomeStartButton = new JButton("Start Game");


    //------------ Play Window ---------------------------
    JPanel playerPanelMain = new JPanel();
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
    //LayoutManager playersLayout = new BorderLayout();
    LayoutManager playersLayout = new GridLayout(3,3);
    LayoutManager answerLayout = new GridLayout(2,2);
    Font f = new Font(null, 3, 20);
    Color answButtonColor = new Color(20,40,100);
    //-----------------------------------------------------


    //---------- Category window --------------------------
    JPanel categoryMainPanel = new JPanel();
    JLabel categoryLabelPickCategory = new JLabel("Välj en kategori");
    JPanel categoryButtonPanel = new JPanel();
    JButton[] categoryButtons = {new JButton("Cat 1"),new JButton("Cat 22"),new JButton("Cat 33333")};

    //GridLayout layoutCategoryButtons = new GridLayout(categoryButtons.length, 1);

    public QuizGUI(){

        setTitle("QUIZKAMPEN");
        //Nytt för CardLayout
        mainQuizPanel.setLayout(cardLo);
        mainQuizPanel.setPreferredSize(panelsDimension);


        initWelcomeWindow();
        //---------------

        initPlayWindow();

        //Category window
        //Funkar inte med pack(), separera Aron's window till en funktion om han behöver pack

        initCategoryWindow();

        mainQuizPanel.add(welcomePanelMain, WELCOME);
        mainQuizPanel.add(playerPanelMain, PLAY);
        mainQuizPanel.add(categoryMainPanel, CATEGORY);
        cardLo.show(mainQuizPanel, WELCOME);
        add(mainQuizPanel);
        /*
        mainPanel.add(panel1, "1");
        mainPanel.add(panel2, "2");
        mainPanel.add(panel3, "3");

        cl.show(mainPanel, "1");

        add(mainPanel);

         */

        pack();
        setLocationRelativeTo(null);
        //setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void initWelcomeWindow(){

        welcomePanelMain.setPreferredSize(panelsDimension);
        welcomePanelMain.setBackground(Color.orange);
        welcomePanel.setPreferredSize(new Dimension((int)(screenX*0.8), (int)(screenY*0.8)));
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBackground(Color.orange);
        welcomePanel.add(quizLabel,BorderLayout.NORTH);
        welcomeInputPanel.setLayout(new FlowLayout());
        welcomeInputPanel.add(nameText);
        welcomeInputPanel.add(welcomeInput);
        welcomeInputPanel.setPreferredSize(new Dimension((int)(screenX*0.5), (int)(screenY*0.2)));
        Color c = new Color(210,170,30);
        welcomeInputPanel.setBackground(c);
        welcomePanel.add(welcomeInputPanel, BorderLayout.CENTER);

        welcomePanel.add(welcomeStartButton,BorderLayout.SOUTH);

        quizLabel.setHorizontalAlignment(JLabel.CENTER);
        quizLabel.setFont(f);
        nameText.setFont(f);
        welcomeStartButton.setFont(f);
        welcomePanelMain.add(welcomePanel);

    }

    public void initPlayWindow(){
        setPlayerPanelProperties();
        setQuestionPanelProperties();
        setAnswerPanelProperties();
        playerPanelMain.setLayout(new BorderLayout());
        playerPanelMain.setPreferredSize(panelsDimension);
        playerPanelMain.setBackground(Color.orange);
        playerPanelMain.add(playerPanel,BorderLayout.NORTH);
        playerPanelMain.add(questionPanel, BorderLayout.CENTER);
        //add(Box.createVerticalStrut(10));
        playerPanelMain.add(answerPanel, BorderLayout.SOUTH);

    }

    public void setPlayerPanelProperties(){
        playerPanel.setPreferredSize(new Dimension(screenX-60,screenX/2));
        playerPanel.setBackground(panelsBackgroundColor);
        playerPanel.setLayout(playersLayout);
        playerPanel.add(player1,BorderLayout.WEST);
        playerPanel.add(category,BorderLayout.CENTER);
        playerPanel.add(player2,BorderLayout.EAST);

    }

    public void setQuestionPanelProperties(){
        String questionText = "Fråga?";
        //questionPanel.setPreferredSize(new Dimension(300,200));
        questionLabel.setPreferredSize(new Dimension(screenX/2,screenX/2));
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
        progressBar.setPreferredSize(new Dimension(screenX,16));
        progressBar.setForeground(Color.RED);
        progressBar.setBackground(Color.GRAY);
        questionPanel.add(questionLabel,BorderLayout.NORTH);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(progressBar,BorderLayout.SOUTH);

    }

    public void setAnswerPanelProperties(){
        String answerText = "Svar";
        answerPanel.setPreferredSize(new Dimension(screenX/2,screenX/2));
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
        welcomeStartButton.addActionListener(aListener);
    }

    public void setQuestionLabelText(String text){
        if(text.length()>32){
            text=splitText(text);
        }
        questionLabel.setText(text);
    }

    public String splitText(String text){
        String[] splittedText;
        int index = text.indexOf(" ", 30);
        splittedText = text.split(" ", 3);
        return text;
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





    //Category window
    public void initCategoryWindow(){
        //Change
        categoryMainPanel.setPreferredSize(panelsDimension);
        categoryMainPanel.setBackground(panelsBackgroundColor);
        categoryLabelPickCategory.setBackground(panelsBackgroundColor);
        categoryButtonPanel.setBackground(panelsBackgroundColor);
        //MAIN FRAME
        //setSize(panelsDimension);
        //setResizable(false);
        //setLocationRelativeTo(null); // Center main window to screen

        // MAIN PANEL
        categoryMainPanel.setLayout(new BoxLayout(categoryMainPanel, BoxLayout.PAGE_AXIS));
        //categoryMainPanel.setBackground(Color.WHITE);

        // PICK A CATEGORY LABEL
        //categoryLabelPickCategory.setBackground(Color.WHITE);
        categoryLabelPickCategory.setOpaque(true);
        categoryLabelPickCategory.setFont(f);
        categoryLabelPickCategory.setAlignmentX(0.5f);

        // BUTTONS
        categoryButtonPanel.setLayout(new BoxLayout(categoryButtonPanel, BoxLayout.Y_AXIS));
        for(int i = 0; i < categoryButtons.length; i++){
            categoryButtons[i].setBackground(answButtonColor);
            categoryButtons[i].setOpaque(true);
            categoryButtons[i].setFont(f);
            categoryButtons[i].setLayout(new BorderLayout());
            categoryButtons[i].setBackground(answButtonColor);
            categoryButtons[i].setForeground(Color.WHITE);
            categoryButtons[i].setFocusPainted(false);
            categoryButtons[i].setAlignmentX(0.5f);
            categoryButtonPanel.add(categoryButtons[i].getText(), categoryButtons[i]);
            categoryButtonPanel.add(Box.createRigidArea(new Dimension(0,10)));
        }

        categoryMainPanel.add(Box.createRigidArea(new Dimension(0,150)));
        categoryMainPanel.add(categoryLabelPickCategory);
        categoryMainPanel.add(Box.createRigidArea(new Dimension(0,100)));
        categoryMainPanel.add(categoryButtonPanel);
        //add(categoryMainPanel);
    }

    public void initCategoryButtonListener(ActionListener aListener)
    {
        for(JButton jB: categoryButtons){
            jB.addActionListener(aListener);
        }
    }

    public void changeWindow(String category){
        cardLo.show(mainQuizPanel, category);
    }

    //-------For testing
    public static void main(String[] args) {
        new QuizGUI();
    }
}
