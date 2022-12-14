import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @Object: Puzzle()
 * @Function: This OOP class will help set up the puzzle objects that is needed from the puzzle.txt when parsing information.
 * This class will interact and solve the puzzles to receive items. The puzzle will track variables such as puzzle names,
 * puzzle ID, question, answer, hint, attempts, rewards, and the command methods for the puzzles as the player interact with them.
 * @author(s) Shianne Lesure
 * @added 10/17/2022
 */
public class Puzzle implements Serializable {
    private int puzzleID;
    private String puzzleName;
    private String puzzleQuestion;
    private String hint;
    private String answer;
    private int attempts;
    private String reward1;
    private String reward2;
    private String puzzleCode;

    // accessing the game console class
    GameConsole game = new GameConsole();

    private ArrayList<Puzzle> puzzles;
    private ArrayList<Item> items;
    private ArrayList<Room> rooms;


    Item itemObject;
    Puzzle puzzleGame;
    transient Scanner input;


    /*-----------------------------------------------Puzzle Constructors----------------------------------------------*/
    /**
     * @Function: This is a no - arg constructor that will access the puzzle, room, and item's parse methods and add their data into
     * their object arraylist.
     * @author(s) Shianne Lesure
     * @added 10/18/2022
     */
    public Puzzle() {
        // will take the player's input
        input = new Scanner(System.in);

        // an arraylist that will hold the puzzle, room, and item's data
        puzzles = new ArrayList<>();
        items = new ArrayList<>();
        rooms = new ArrayList<>();

        // putting the data into the arraylist
        game.readPuzzleTxt(puzzles);
        GameConsole.readItems(items,rooms);
        GameConsole.readRooms(rooms);
    }

    /**
     * @param puzzleID
     * @param puzzleName
     * @param puzzleQuestion
     * @param hint
     * @param answer
     * @param attempts
     * @param reward1
     * @param reward2
     * @param puzzleCode
     * @Function: constructor for pre existing data from the puzzle text file
     * @author(s) Shianne Lesure
     * @added: 10/17/2022
     */
    public Puzzle(int puzzleID, String puzzleName, String puzzleQuestion, String hint, String answer, int attempts, String reward1, String reward2, String puzzleCode) {
        this.puzzleID = puzzleID;
        this.puzzleName = puzzleName;
        this.puzzleQuestion = puzzleQuestion;
        this.hint = hint;
        this.answer = answer;
        this.attempts = attempts;
        this.reward1 = reward1;
        this.reward2 = reward2;
        this.puzzleCode = puzzleCode;
    }


    /*------------------------------------Getters & Setters for Puzzle variables--------------------------------------*/
    public int getPuzzleID() {
        return puzzleID;
    }

    public void setPuzzleID(int puzzleID) {
        this.puzzleID = puzzleID;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public String getPuzzleQuestion() {
        return puzzleQuestion;
    }

    public void setPuzzleQuestion(String puzzleQuestion) {
        this.puzzleQuestion = puzzleQuestion;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getReward1() {
        return reward1;
    }

    public void setReward1(String reward1) {
        this.reward1 = reward1;
    }

    public String getReward2() {
        return reward2;
    }

    public void setReward2(String reward2) {
        this.reward2 = reward2;
    }

    public String getPuzzleCode() { return puzzleCode; }

    public void setPuzzleCode(String puzzleCode) { this.puzzleCode = puzzleCode; }

    @Override
    public String toString() {
        return puzzleID + "\n" + puzzleName + "\n" + puzzleQuestion + "\n" + hint + "\n" + answer +
                "\n" + attempts + "\n" + reward1 + "\n" + reward2 + "\n" + puzzleCode;
    }


    /*-------------------------------Puzzle Methods for implementing the game-----------------------------------------*/
    /**
     * @param puzzleLocationID
     * @param currentRoom
     * @param prizeItem
     * @Method: dropRewardsItem()
     * @Function: This method will drop the reward items into the current room the player is in.
     * @author(s): Shianne Lesure, Carlton Napier
     * @added: 10/27/2022
     */
    public void dropRewardsItem(int puzzleLocationID, Room currentRoom, String prizeItem) {
        for (Item itemGame : items) {
            itemObject = itemGame;
            for(Puzzle puzzle : puzzles) {
                if (puzzleLocationID == puzzle.getPuzzleID()) { // if the puzzle is in the current room
                    if (itemObject.getItemName().equals(prizeItem)) {
                        currentRoom.roomItemAdd(itemObject); // add the prize item to the room
                        break;
                    }
                }
            }
        }
    }

    /**
     * @Method: removeRewardsItem()
     * @param currentRoom
     * @param prizeItem
     * @Function: This method will remove the prize items from the current room the player is in.
     * @author(s): Shianne Lesure
     * @added: 11/3/2022
     */
    public void removeRewardsItem(Room currentRoom, String prizeItem){
        String[] parts = prizeItem.split(" "); // taking player's input and splitting it to get the item's name
        for(Item itemGame: items){
            itemObject = itemGame;
            if(parts[1].equalsIgnoreCase(itemObject.getItemName())){
                currentRoom.roomItemRemove(itemObject); // remove the prize item from current room
                break;
            }
        }
    }

    /**
     * @Method: addPuzzleCodes()
     * @param playerCodes
     * @param puzzleCodes
     * @Function: This method will add the codes the game gets from the puzzle and add them the player's code inventory.
     * @author(s): Shianne Lesure
     * @added: 10/27/2022
     */
    public void addPuzzleCodes(Player playerCodes, String puzzleCodes){
        playerCodes.codeInventoryAdd(puzzleCodes);
    }

    /**
     * @Mehtod: inspectPuzzle()
     * @param puzzleLocationID
     * @param currentRoom
     * @param playerCode
     * @Function: This method will allow the player to see the question of the puzzle as well if they would like to solve the puzzle
     * @author(s): Shianne Lesure
     * @added: 10/27/2022
     */
    public void inspectPuzzle(int puzzleLocationID, Room currentRoom, Player playerCode) {
        for (Puzzle puzzle : puzzles) {
            puzzleGame = puzzle;
            if (puzzleLocationID == puzzle.getPuzzleID()) {
                System.out.println(puzzle.getPuzzleQuestion()); // print out puzzle question
                System.out.println("If you would like to solve the puzzle type: [solve puzzle]");
                answer = input.nextLine(); // take player's input
                if (answer.equalsIgnoreCase("solve puzzle")) { // if player's input is solve puzzle, then the solve puzzle method is called
                    solvePuzzle(puzzleLocationID, currentRoom, playerCode);
                }
                break;
            }
        }
    }

        /**
         * @Mthod: solvePuzzle()
         * @param puzzleLocationID
         * @param currentRoom
         * @param playerCode
         * @Function: This method will allow for the player to answer the current puzzle to reviews prizes
         * @author(s): Shianne Lesure
         * @added: 10/27/2022
         */
        public void solvePuzzle(int puzzleLocationID, Room currentRoom, Player playerCode){
            for (Puzzle puzzleSolve : puzzles) {
                puzzleGame = puzzleSolve;
                if (puzzleLocationID == puzzleSolve.getPuzzleID()) {
                    System.out.println("What is your answer?");
                    String playerAnswer = input.nextLine(); // take player's answer
                    int resetAttempts = puzzleSolve.getAttempts(); // reset the puzzle attempts
                    while (puzzleSolve.getAttempts() != 0) {
                        if (playerAnswer.equalsIgnoreCase(puzzleSolve.getAnswer()) && puzzleSolve.getAttempts() == 3) { // if player solve the puzzle on their 1st try
                            System.out.println("You solve the puzzle correctly! You can now claim your prizes! \n" + puzzleSolve.reward1 + "\n" + puzzleSolve.reward2 + "\nCODE: " + puzzleSolve.puzzleCode);
                            dropRewardsItem(puzzleLocationID, currentRoom, puzzleSolve.reward1); // will drop the 1st prize
                            dropRewardsItem(puzzleLocationID, currentRoom, puzzleSolve.reward2); // will drop the 2nd prize
                            addPuzzleCodes(playerCode,puzzleSolve.puzzleCode); // will show the puzzle code within the room
                            puzzleSolve.setAttempts(resetAttempts); // reset puzzle attempts
                            currentRoom.setRoomPuzzle(-1); // will take the puzzle out the current room
                            break;
                        } else if (playerAnswer.equalsIgnoreCase(puzzleSolve.getAnswer()) && puzzleSolve.getAttempts() < 3) { // if player solve the puzzle on their 2nd & 3rd try
                            System.out.println("You solve the puzzle correctly! You can now claim your prize!\n" + puzzleSolve.reward2 + "\nCODE: " + puzzleSolve.puzzleCode);
                            dropRewardsItem(puzzleLocationID, currentRoom, puzzleSolve.reward2); // will drop the 2nd prize
                            addPuzzleCodes(playerCode,puzzleSolve.puzzleCode); // will show the puzzle code within the room
                            puzzleSolve.setAttempts(resetAttempts); // reset puzzle attempts
                            currentRoom.setRoomPuzzle(-1); // will take the puzzle out the current room
                            break;
                        } else if (!playerAnswer.equalsIgnoreCase(puzzleSolve.getAnswer())) { // if player doesn't solve the puzzle correctly
                            if (playerAnswer.equalsIgnoreCase("get hint")) {
                                hint(puzzleLocationID); // print out the puzzle's hint
                                puzzleSolve.setAttempts(puzzleSolve.getAttempts() + 1); // will add an attempts back to the player
                            }
                            if (playerAnswer.equalsIgnoreCase("exit puzzle")) {
                                System.out.println("You have exited out the puzzle.");
                                puzzleSolve.setAttempts(puzzleSolve.getAttempts() + 1);
                                puzzleSolve.setAttempts(resetAttempts); // reset puzzle attempts
                                break; // will break out the method
                            }
                            if (puzzleSolve.getAttempts() == 1) { // if player runs out of attempts
                                System.out.println("Failed to solve this puzzle.");
                                puzzleSolve.setAttempts(resetAttempts); // set the number of attempts back to 3
                                break;
                            }
                            puzzleSolve.setAttempts(puzzleSolve.getAttempts() - 1); // remove their 1 attempt from player
                            System.out.println("The answer you provided is wrong. You still have " + puzzleSolve.getAttempts() + " attempts left.\nWhat is your answer?");
                            playerAnswer = input.nextLine();
                        }
                    }
                }
            }
        }

        /**
         * @Method: hint()
         * @param puzzleLocationID
         * @Function: This method will give the player the current hint attach to the current puzzle
         * @author(s): Shianne Lesure
         * @added: 10/27/2022
         */
        public void hint(int puzzleLocationID){
            for (Puzzle puzzle : puzzles) {
                if (puzzleLocationID == puzzle.getPuzzleID()) {
                    System.out.println("Hint: " + puzzle.getHint()); //will print out current puzzle's hint
                    break;
                }
            }
        }

        /**
         * @Method: retryPuzzle()
         * @param puzzleLocationID
         * @param currentRoom
         * @param playerCode
         * @Function: This method will allow for the player to retry the puzzle if they have failed
         * @author(s): Shianne Lesure
         * @added: 10/27/2022
         */
        public void retryPuzzle(int puzzleLocationID, Room currentRoom, Player playerCode){
            for(Puzzle puzzles : puzzles) {
                if (puzzleLocationID == puzzles.getPuzzleID()) {
                    inspectPuzzle(puzzleLocationID, currentRoom, playerCode); // go back to the inspect puzzle method
                    break;
                }
            }
        }
}
