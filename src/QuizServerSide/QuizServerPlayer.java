package QuizServerSide;

import QuizServerSide.Questions.ArrayOfQuestions;
import QuizServerSide.Questions.Questions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class QuizServerPlayer extends Thread implements Serializable {

    private final int LOBBY=0;
    private final int CATEGORY = 1;
    private final int GAME = 2;
    private final int SCORE = 3;

    //Testing------------------
    int status = LOBBY;

    //-------------------------


    //NETWORK
    transient Socket socket;
    transient ObjectInputStream input;
    transient ObjectOutputStream output;
    QuizServerGame game;
    NetworkProtocolServer serverProtocol;


    //GAME
    int score;
    boolean categoryPicker;
    QuizServerPlayer opponent;
    String playerName;
    Questions currentQuestion;
    boolean ready;
    boolean newQuestionGenerated;
    int lastAnsweredButtonIndex;
    int currentQuestionWithinRound;



    public QuizServerPlayer(Socket socket, QuizServerGame game, boolean categoryPicker) {
        this.game = game;
        this.socket = socket;
        this.serverProtocol = new NetworkProtocolServer(this);
        this.categoryPicker = categoryPicker;
        try {
            output = new ObjectOutputStream(this.socket.getOutputStream());
            input = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public QuizServerPlayer getOpponent() {
        return opponent;
    }

    public void setOpponent(QuizServerPlayer opponent) {
        this.opponent = opponent;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) throws IOException {
        this.score += score;
        getNetworkProtocolServer().sendScore(output,score);
    }

    //Cannot be named getName cause inherit from Thread class
    public String getPlayerName() {
        return playerName;
    }
    public NetworkProtocolServer getNetworkProtocolServer() {
        return serverProtocol;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public QuizServerGame getGame() {
        return game;
    }
    public ObjectOutputStream getOutputStream() {
        return output;
    }
    public ObjectInputStream getInputStream() {
        return input;
    }
    public boolean getReady() {
        return ready;
    }
    public void setReady(boolean ready) {
        this.ready = ready;
    }
    public boolean getCategoryPicker() {
        return categoryPicker;
    }
    public void setCategoryPicker(boolean categoryPicker) {
        this.categoryPicker = categoryPicker;
    }
    public void switchCategoryPicker(){
        if (categoryPicker) {
            categoryPicker = false;
        } else {
            categoryPicker = true;
        }
    }
    public boolean isNewQuestionGenerated() {
        return newQuestionGenerated;
    }

    public void setNewQuestionGenerated(boolean newQuestionGenerated) {
        this.newQuestionGenerated = newQuestionGenerated;
    }

    public void startNewQuestion(boolean whosTurn) throws IOException, ClassNotFoundException, InterruptedException {
        // Plocka nästa fråga från databas
        while (!isNewQuestionGenerated() && !whosTurn) {
            Thread.sleep(1);
        }
        if (whosTurn) {
            System.out.println("PICK A QUESTION FROM CATEGORY: " + game.getCurrentCategory());
            currentQuestion = game.getAq().generateRandomQuestion(game.getCurrentCategory());
            opponent.currentQuestion = currentQuestion;
            setNewQuestionGenerated(true);
            opponent.setNewQuestionGenerated(true);
            System.out.println("New question generated");
        }
        //skickar fråga och alternativ
        serverProtocol.sendQuestion(output, currentQuestion);
        setReady(false);
        //ta emot svar
        Object lastReadObject = input.readObject();
        if (lastReadObject instanceof NetworkMessage) {
            boolean correctAnswer = serverProtocol.parseAnswerQuestion(input, this);
            if (correctAnswer){
                setScore(1);

            }
            System.out.println("correctAnswer: " + correctAnswer);
            //validera svar mot correctanswer och skicka tillbaks
            serverProtocol.sendAnswerResult(output, correctAnswer);
        }
        // SLEEP THREAD(3-5sec?) SO WE CAN CHECK OUR RESULT
        while(true) {
            Thread.sleep(1);
            if (getReady() && (opponent.getReady())) {
                break;
            }
        }
        if(whosTurn){
            setNewQuestionGenerated(false);
            opponent.setNewQuestionGenerated(false);
        }
        Thread.sleep(3000);

    }

    public void run()
    {
        try {
            int i = 0;
            while (true) {

                if (status == LOBBY) {
                    //Player set name
                    Object lastReadObject = input.readObject();
                    System.out.println(lastReadObject);
                    if (lastReadObject instanceof NetworkMessage) {
                        serverProtocol.parseSetPlayerName(input, this);
                        serverProtocol.sendOpponentNotReady(output);

                       /* Flyttad till CATEGORY
                        if(getCategoryPicker()){
                            //TODO: Send choose a category (Has to update GUI, add category buttons and have label "Choose a category")
                            serverProtocol.sendIsPlayerToChooseCategory(output, categoryPicker);
                            //serverProtocol.sendChooseCategory();
                        }
                        else {
                            //TODO: Send waiting for opponent to pick category (Has to update GUI, remove buttons and just have label "Waiting for opponent to pick category")
                            //serverProtocol.sendWaitingForCategoryPick();
                        }*/
                        serverProtocol.sendChangeWindow(output, "1");

                        //Stall gameloop until both players have connected and set their name
                        while (true) {
                            //System.out.println("Players ready?: " + getReady() + " | " + opponent.getReady());
                            //Need to sleep(or any code, even print above will make it stop bug), else 1st client don't update opponent namelabel (doesn't execute sendOpponentName)
                            //Because compiler optimization?
                            Thread.sleep(1);
                            if(getReady() && ((opponent != null) && opponent.getReady())) {
                                break;
                            }
                        }

                        //System.out.println("SHOULD SEND OPPONENT NAME NOW");
                        //Send opponent name to update GUI label
                        serverProtocol.sendOpponentName(output, opponent.getPlayerName());

                        status = CATEGORY;
                    }
                }


                if (status == CATEGORY) {

                    if(categoryPicker) {
                        game.categories = game.aq.randomizeCategoryAlternatives(game.nrOfCategories);
                        serverProtocol.sendCategories(output, game.categories);
                    }

                    if(opponent.getReady()) {
                        serverProtocol.sendIsPlayerToChooseCategory(output, categoryPicker);
                     /*   If we want to send categories to both players
                        if(categoryPicker) {
                            game.categories = game.aq.randomizeCategoryAlternatives(game.nrOfCategories);
                            game.categoriesGotten = true;
                        }
                        if(!categoryPicker){
                            while(!game.categoriesGotten){
                                Thread.sleep(1);
                            }
                        }
                        serverProtocol.sendCategories(output, game.categories);*/


                    }
                   //
                    //We now already have our category window set up correctly

                    //Parse chosen category (add a currentCategory variable inside QuizServerGame and set it in this parse)
                    //This way we can use currentCategory in generateRandomQuestion() as argument
                    if(getCategoryPicker()) {
                        Object lastReadObject = input.readObject();
                        if (lastReadObject instanceof NetworkMessage) {
                            serverProtocol.parseChosenCategory(input, this);

                            //Change window to answer question window for both players
                            // (These can be coupled inside parseChosenCategory, but probably shouldn't to more easily follow code logic)
                            serverProtocol.sendChangeWindow(output, "2");
                            opponent.getNetworkProtocolServer().sendChangeWindow(opponent.getOutputStream(), "2");
                            //Send Current Category to Opponent
                            opponent.getNetworkProtocolServer().sendCategoryToOpponent(opponent.getOutputStream(), game.currentCategory);
                        }
                    }

                    //NetworkProtocolServer.sendQuestion(output, "Bajskorv");
                    status = GAME;

                }

                if (status == GAME) {
                    //CHECK startNewQuestion FUNCTION IF NEED TO CODE MORE STUFF
                    // (Purpose: A function we can call everytime we want to start a new question instead of copy/paste same code)
                    // It should have all code required to operate a complete question cycle (Even handling send answer result and such)
                    System.out.println("status game" + i);
                    startNewQuestion(getCategoryPicker());
                    currentQuestionWithinRound++;
                    // Send player to score/result window


                    if (currentQuestionWithinRound == game.numberOfQuestionsPerRound) {
                        serverProtocol.sendChangeWindow(output, "3");
                        status = SCORE;
                    }
                    i++;
                }

                if(status == SCORE) {
                    switchCategoryPicker();
                    //Check if last question was final question of round

                    //Check if last round was played
                    // If no then we have to start new round
                    // This is steps to do so(Make a function of all these steps, and we can just call that one function):
                    // 1. Wait for both players to press start new round? (DESIGN CHOICE) - Just some way to know we're about to start a new round
                    // 2. Start a new round - startNewCategory(); - This should be a function we make with previous code created from category status
                    //    that will handle everything up to startNewQuestion(); - And then we just call startNewQuestion();

                    //So end result of above comment should just be following 2 lines
                    //startNewCategory();
                    //startNewQuestion();

                    //else if last round was played then (We are now sitting in score/result window)
                    //adjust the start new round button to send us to lobby and fix textlabel
                    //adjust lobby to not allow new game to be started or setname field to exist?
                    //No meaning sitting in lobby unless next line is made so we might want to just end game completely or something else
                    //lobby should have 2 buttons added to it ("Check highscores" and "Settings")? - Settings let's us adjust GUI color and the other client stuff
                }

                output.flush(); // True?: One flush per loop should be more than enough if not too much, don't call flush more than once per loop
            }
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
