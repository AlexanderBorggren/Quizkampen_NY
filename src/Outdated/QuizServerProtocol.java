package Outdated;

public class QuizServerProtocol {

    private static final int WAITING = 0;
    private static final int SENTQUESTION = 1;
    private static final int SENTANSWER = 2;
    private static final int ANOTHER = 3;

    private int state = WAITING;

    private int questionNr = 1;
    private int tryNr = 0;

    private String[] questions = {"Vem uppfann elektriciteten?", "Om 1 öl är 0 öl\n\n och 2 öl är 1 öl. \n Hur många öl är 5 öl?"};
    private String[][] answerAlternatives = {{"Batman", "Ingen", "Tesla", "Gud"},{"3","5","0","4"}};
    private String[][] answer = {{"Juste...eller?", "Japp", "Han kan ha blivit träffad av blixten..", "Jessus..."},
            {"Nja","You wish...","Det är vad jag säger\n till min fru!","Skål"}};

    public QuizServerProtocol() {

    }

    public String sendQuestion(){
        return questions[questionNr];
    }

    public String[] sendAnswerAlternatives(){
        String[] answers = new String[4];
        for(int i=0; i<4; i++){
            answers[i]=answerAlternatives[questionNr][i];
        }
        return answers;
    }

    public String processInput(String dataIn) {
        String dataOut = "No match";
       /* if (state == WAITING) {
            dataOut = questions[0];
            state = SENTQUESTION;
        }
        if (state == SENTQUESTION) {
            //.....
            state = SENTANSWER;
        }
        if (state == SENTANSWER) {
            //....
            state = WAITING;
        }*/

        for (int i=0; i<answerAlternatives[0].length; i++){
            if(dataIn.equals(answerAlternatives[questionNr][i])){
                return answer[questionNr][i];
            }
        }
        if(tryNr==3){

        }
        return dataOut;
    }
}
