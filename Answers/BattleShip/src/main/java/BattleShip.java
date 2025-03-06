import java.util.Scanner;
import java.util.Random;

/**
 The BattleShip class manages the gameplay of the Battleship game between two players.
 It includes methods to manage grids, turns, and check the game status.
 */
public class BattleShip {

    // Grid size for the game
    static final int GRID_SIZE = 10;

    // Player 1's main grid containing their ships
    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];

    // Player 2's main grid containing their ships
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];

    // Player 1's tracking grid to show their hits and misses
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    // Player 2's tracking grid to show their hits and misses
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    // Scanner object for user input
    static Scanner scanner = new Scanner(System.in);
    // static String allshots1 ="";
    //static String allshots2 ="";
    static String[] allshots1 = {""};
    static String[] allshots2 = {""};

    /**
     The main method that runs the game loop.
     It initializes the grids for both players, places ships randomly, and manages turns.
     The game continues until one player's ships are completely sunk.
     */
    public static void main(String[] args) {
        // Initialize grids for both players

        String [] placeship1 = new String[6];
        for (int i = 0; i < placeship1.length; i++) {
            placeship1[i] = "";
        }
        String[] placeship2 = new String[6];
        for (int i = 0; i < placeship2.length; i++) {
            placeship2[i] = "";
        }

        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        // Place ships randomly on each player's grid
        placeShips(player1Grid,placeship1);
        placeShips(player2Grid,placeship2);

        // Variable to track whose turn it is
        boolean player1Turn = true;
        boolean isgameover =true;

        // Main game loop, runs until one player's ships are all sunk
        while (isgameover) {
            if (player1Turn) {
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid,placeship2,allshots1);
                if(allShipsSunk(player1TrackingGrid)){
                    isgameover=!isgameover;
                }
            } else {
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid,placeship1,allshots2);
                if(allShipsSunk(player2TrackingGrid)){
                    isgameover=!isgameover;
                }
            }
            player1Turn = !player1Turn;
        }

        System.out.println("Game Over!");
    }

    /**
     Initializes the grid by filling it with water ('~').

     @param grid The grid to initialize.
     */
    static void initializeGrid(char[][] grid) {
        //todo
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j <10 ; j++) {
                grid[i][j]= '~';
            }

        }
    }

    /**
     Places ships randomly on the given grid.
     This method is called for both players to place their ships on their respective grids.

     @param grid The grid where ships need to be placed.
     */
    static void placeShips(char[][] grid,String []theshipplace) {
        //todo
        Random random = new Random();

        int size=2;
        boolean a = true;
        while (size <= 5){
            while (a){
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();
                if(canPlaceShip(grid,row,col,size,horizontal)){
                    a =!a;
                    if (horizontal){
                        for (int i = col; i < col+size ; i++) {
                            grid[row][i]='s';
                            int alphabet =i+65;
                            int number   =row+48;
                            theshipplace[size] +=(char)alphabet+""+(char)number;
                        }
                    }
                    if(!horizontal){
                        for (int i =row ; i < row+size ; i++) {
                            grid[i][col]='s';
                            int alphabet =col+65;
                            int number =i+48;
                            theshipplace[size] +=(char)alphabet+""+(char)number;
                        }
                    }
                }

            }
            ++size;
            a = !a;
        }
        System.out.println(theshipplace[2]);
        System.out.println(theshipplace[3]);
    }

    /**
     Checks if a ship can be placed at the specified location on the grid.
     This includes checking the size of the ship, its direction (horizontal or vertical),
     and if there's enough space to place it.

     @param grid The grid where the ship is to be placed.
     @param row The starting row for the ship.
     @param col The starting column for the ship.
     @param size The size of the ship.
     @param horizontal The direction of the ship (horizontal or vertical).
     @return true if the ship can be placed at the specified location, false otherwise.
     */
    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
        //todo
        if (horizontal) {
            if (col + size < 10) {
                for (int i = col ; i < col + size; i++) {
                    if (grid[row][i] == '~') {
                        return true;
                    }
                }
                return false;
            }
        }
        if (!horizontal) {
            if (row + size < 10) {
                for (int i = row ; i < row + size ; i++) {
                    if (grid[i][col] == '~') {
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }

    /**
     Manages a player's turn, allowing them to attack the opponent's grid
     and updates their tracking grid with hits or misses.

     @param opponentGrid The opponent's grid to attack.
     @param trackingGrid The player's tracking grid to update.
     */
    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid,String[] theplaceship,String [] allshots) {
        //todo
        for (int k = 2; k <6 ; k++) {
            if (allshots[0].contains(theplaceship[k])){
                System.out.println("ship with the size of<<"+k+">> sunk :)");
            }

        }
        System.out.print("shoot(like:C4 or A3): ");
        String s = scanner.nextLine();
        allshots[0] += s;
        if(isValidInput(s)){
            int i = (int)s.charAt(0)-65;
            int j = (int)s.charAt(1)-48;
            if(opponentGrid[j][i]=='s'){
                System.out.println("Hit");
                trackingGrid[j][i]='x';
            }
            else {
                trackingGrid[j][i]='o';
                System.out.println("Miss");

            }
        }
        else{
            System.out.println("invali input! ");
        }

    }

    /**
     Checks if all ships have been destroyed on a given grid.
     @param grid The grid to check for destroyed ships.
     @return true if all ships are sunk, false otherwise.
     */
    static boolean allShipsSunk(char[][] grid) {
        //todo;
        int counter = 0;
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j] == 'x') {//char 'x' is for hit
                    ++counter;
                }
            }
        }
        if(counter==14){
            return true;
        }
        return false;
    }
    /**
     Validates if the user input is in the correct format (e.g., A5).
     @param input The input string to validate.
     @return true if the input is in the correct format, false otherwise.
     */
    static boolean isValidInput(String input) {
        //todo
        if(!(input.length()==2)){
            return false;
        }

        if(Character.isLetter(input.charAt(0)) && Character.isUpperCase(input.charAt(0)) && Character.isDigit(input.charAt(1)))
        {
            if('A'<=input.charAt(0)&&input.charAt(0)<='J' && '0' <= input.charAt(1) && input.charAt(1) <= '9'){
                return true;
            }
        }
        return false;
    }

    /**
     Prints the current state of the player's tracking grid.
     This method displays the grid, showing hits, misses, and untried locations.

     @param grid The tracking grid to print.
     */
    static void printGrid(char[][] grid) {
        //todo
        System.out.print("  ");
        for (int i =65; i <=74 ; i++) {
            System.out.print((char)i+" ");
        }
        System.out.println();
        for (int i = 0; i <10 ; i++) {
            System.out.print(i+" ");
            for (int j = 0; j <10 ; j++) {
                System.out.print(grid[i][j] +" ");
            }
            System.out.println();

        }

    }
}
