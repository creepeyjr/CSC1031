/*
Author : Cian McSharry Daly

Understanding the Brief
=======================
- I must have an acute understanding of Mediators and their design patterns.
- I must implement them as such into this program.
- Follow the brief step by step, no jumping. 

Plan
====
1. Create Player inteface.
2. Model interface with described methods and attributes
*/

/*
Start by creating interface for players
These players could be real, ai or spectators, so I will build the interface that these will be based off.

NOTE TO ME Step 2: Implement AbstractPlayer Class

Create an abstract class AbstractPlayer that implements Player:

    Stores String name and a reference to GameLobby.

    Implements sendMessage() and receiveMessage().

    Defines an abstract method getPlayerType().

abstract class AbstractPlayer implements Player {
    protected String name;
    protected GameLobby lobby;

    public AbstractPlayer(String name, GameLobby lobby) { ... }

    @Override
    public void sendMessage(String message) { ... }

    @Override
    public void receiveMessage(String message) { ... }

    public abstract String getPlayerType();
}

WHILE PROGRAMMING.
I WILL LEAVE CLEAR COMMENTS ON MISSING FUNCTIONALITY AND WILL COME BACK TO FINISH THEM WHEN THE TIME IS RIGHT.
Such as expected behaviour.
*/

/*
Whoops why did i do this bahahaha
Maybe to help "envision the code" lmoao

interface Player {
    // Methods
    void joinGame() {

        // registers player with GameLobby mediator. (REQUIRES FUTURE IMPLEMENTATION)
        // GameLobby.registerPlayer.(this); (REQUIRES FUTURE IMPLEMENTATION)
        System.out.println("[GameLobby] <PlayerType> <name> has joined the lobby.");  // (REQUIRES FUTURE IMPLEMENTATION)
    }

    void leaveGame() {

        // Removes player from GameLobby mediator. (REQUIRES FUTURE IMPLEMENTATION)
        // calls GameLobby.removePlayer.(this); (REQUIRES FUTURE IMPLEMENTATION)
        System.out.println("[GameLobby] <PlayerType> <name> has left the lobby."); // (REQUIRES FUTURE IMPLEMENTATION)
    }

    void sendMessage(String message) {
        // Sends Message through GameLobby mediator. (REQUIRES FUTURE IMPLEMENTATION)
        // Calls GameLobby.sendMessage(message, this). (REQUIRES FUTURE IMPLEMENTATION)
        
        System.out.println("[<name>] sends: "<message>"");  // (REQUIRES FUTURE IMPLEMENTATION)
        System.out.println(["GameLobby] Message from <name>: "<message>");
    }

    void receiveMessage(String message) {
        System.out.println([<name>] received: "<message>"");
    }
}
*/

// Adding necessary libraries
import java.util.ArrayList;

// Step 1 : Player Interface
interface Player {
    // Player Interface Functions
    void joinGame();
    void leaveGame();
    void sendMessage(String message);
    void receiveMessage(String message);
    String getPlayerType();
    String getName();  // added to retireve player name from player object
}

// Step 2 : Abstract Player Class
abstract class AbstractPlayer implements Player {
    protected String name;
    protected GameLobby lobby;

    public AbstractPlayer(String name, GameLobby lobby) {
        // Possibly create checks for name and lobby
        this.name = name;
        this.lobby = lobby;
    }
    
    // Override player interface functions
    @Override
    public void joinGame() {
        lobby.registerPlayer(this);
        System.out.println("[GameLobby] " + getPlayerType() + " " + name + " has joined the lobby.");
        // (REQUIRES FUTURE IMPLEMENTATION)
    }

    @Override
    public void leaveGame() {
        lobby.removePlayer(this);
        System.out.println("[GameLobby] " + getPlayerType() + " " + name + " has left the lobby.");
        // (REQUIRES FUTURE IMPLEMENTATION)
    }

    @Override
    public void sendMessage(String message) {
        lobby.sendMessage(message, this);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("[" + name + "] received: \"" + message + "\"");
    }

    public abstract String getPlayerType();

    // Override function here
    @Override
    public String getName() {
        return name;
    }
}

// Step 3 : Create Concrete Player Types
// HumanPlayer
class HumanPlayer extends AbstractPlayer {

    // Constructor
    public HumanPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }

    @Override
    public void joinGame() {
        super.joinGame();
        // (REQUIRES FUTURE IMPLEMENTATION)
    }

    @Override
    public void leaveGame() {
        super.leaveGame();
    }

    @Override
    public String getPlayerType() {
        return "HumanPlayer";
    }
}

// AIPlayer
class AIPlayer extends AbstractPlayer {
    
    // Constructor
    public AIPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }

    @Override
    public void joinGame() {
        super.joinGame();
        // (REQUIRES FUTURE IMPLEMENTATION)
    }

    @Override
    public void leaveGame() {
        super.leaveGame();
    }
    
    @Override
    public String getPlayerType() {
        return "AIPlayer";
    }
}

// Spectator
class Spectator extends AbstractPlayer {
    
    // Constructor
    public Spectator(String name, GameLobby lobby) {
        super(name, lobby);
    }

    @Override
    public void joinGame() {
        super.joinGame();
        // (REQUIRES FUTURE IMPLEMENTATION)
    }

    @Override
    public void leaveGame() {
        super.leaveGame();
    }

    @Override
    public String getPlayerType() {
        return "Spectator";
    }
}

class GameLobby {

    // Declare lobby array here
    private ArrayList<Player> lobby;

    // Constructor off rip
    public GameLobby() {
        this.lobby = new ArrayList<Player>();  // initalise array
    }

    // Declare functions that players have been trying to call
    void registerPlayer(Player player) {
        lobby.add(player);
    }
    void removePlayer(Player player) {
        lobby.remove(player);
    }
    void sendMessage(String message, Player sender) {
        // is spectator check here?
        if (!sender.getPlayerType().equals("Spectator")) {
            System.out.println("[" + sender.getName() + "] sends: \"" + message + "\"");
            System.out.println("[GameLobby] Message from " + sender.getName() + ": \"" + message + "\"");
            
            for (Player player : lobby) {
                if (player != sender) {
                    player.receiveMessage(message);
                }
            }
        }
        else {
            System.out.println("[GameLobby] Spectators cannot send messages.");
        }
    }

    void startMatch() {
        int playerCount = 0;

        // count real players or ai players in lobby
        for (Player player : lobby) {
            if (!player.getPlayerType().equals("Spectator")) {
                playerCount++;
            }
        }

        // decide if there is enough
        if (playerCount < 2) {
            System.out.println("[GameLobby] Not enough players to start a match.");
        }
        else {
            String names = "";
            for (Player player : lobby) {
                if (!player.getPlayerType().equals("Spectator")) {
                    if (!names.isEmpty()) {
                        names += ", ";
                    }
                    names += player.getName();
                }
            }
            System.out.println("[GameLobby] Starting game with players: " + names);
        }
    }
}
