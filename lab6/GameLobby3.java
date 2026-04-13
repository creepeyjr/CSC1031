/*
Author : Cian McSharry Daly
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

// Admin
class AdminPlayer extends AbstractPlayer {
    public AdminPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }

    @Override
    public String getPlayerType() {
        return "AdminPlayer";
    }

    public void kickPlayer(String name) {
        lobby.kickPlayer(name, this);
    }
}

// Player Factory Class
class PlayerFactory {
    public static Player createPlayer(String type, String name, GameLobby lobby) {
        switch (type.toLowerCase()) {
            case "human":
                return new HumanPlayer(name, lobby);
            case "ai":
                return new AIPlayer(name, lobby);
            case "spectator":
                return new Spectator(name, lobby);
            case "admin":
                return new AdminPlayer(name, lobby);
        }
    // return nothing I suppose baha
    return null;
    }
    
}

// Step 4 : GameLobby
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
            if (!player.getPlayerType().equals("Spectator") && !player.getPlayerType().equals("AdminPlayer")) {
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
                if (!player.getPlayerType().equals("Spectator") && !player.getPlayerType().equals("AdminPlayer")) {
                    if (!names.isEmpty()) {
                        names += ", ";
                    }
                    names += player.getName();
                }
            }
            System.out.println("[GameLobby] Starting game with players: " + names);
        }
    }

    void kickPlayer(String name, AdminPlayer admin) {
        for (Player player : lobby) {
            if (!admin.getName().equals(name) && player.getName().equals(name)) {
                lobby.remove(player);
                System.out.println("[GameLobby] Admin " + admin.name + " kicked "+ player.getPlayerType() + " " + player.getName() + " from the lobby.");
                System.out.println("[GameLobby] " + player.getPlayerType() + " " + player.getName() + " has left the lobby.");
                return;
            }
        }

        System.out.println("[GameLobby] Player " + name + " not found.");
        

        
    }
}
/* 
public class GameLobby3 {
    public static void main(String[] args) {
        GameLobby lobby = new GameLobby();

        Player alice = PlayerFactory.createPlayer("human", "Alice", lobby);
        Player bot = PlayerFactory.createPlayer("ai", "BotX", lobby);
        Player bob = PlayerFactory.createPlayer("spectator", "Bob", lobby);
        Player admin = PlayerFactory.createPlayer("admin", "Charlie", lobby);

        alice.joinGame();
        bot.joinGame();
        bob.joinGame();
        admin.joinGame();

        admin.sendMessage("Hello, everyone!");
        ((AdminPlayer) admin).kickPlayer("Bob");

        lobby.startMatch();
    }
}
*/