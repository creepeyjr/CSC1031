import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class CellTower {
    String id;
    int x;
    int y;
    String operator;
    int coverRad;

    public CellTower(String id, int x, int y, int coverRad) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.coverRad = coverRad;
        this.operator = null; 
    }
}

class Operator {
    String name;
    List<Client> clients = new ArrayList<>();
    List<CellTower> towers = new ArrayList<>();

    Operator(String name) {
        this.name = name;
    }
}

class Client {
    int number;
    int x;
    int y;
    String opName;
    CellTower connectedTower;

    Client(int number, int x, int y, String opName) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.opName = opName;
    }

    void move(int newX, int newY, Network network) {
        this.x = newX;
        this.y = newY;
        CellTower nearest = network.findNearestTowerForClient(this);
        this.connectedTower = nearest;
    }
}

class Network {
    public List<CellTower> towers;
    public List<Client> clients;
    public List<Operator> operators;

    public Network() {
        this.towers = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.operators = new ArrayList<>();
    }

    public void ADD_CLIENT(int number, String opName, int x, int y) {
        Client newClient = new Client(number, x, y, opName);
        this.clients.add(newClient);
        newClient.connectedTower = findNearestTowerForClient(newClient);
    }

    public void REMOVE_CLIENT(int searchNumber) {
        clients.removeIf(client -> client.number == searchNumber);
    }

    public void MOVE_CLIENT(int number, int newX, int newY) {
        for (Client currClient : this.clients) {
            if (currClient.number == number) {
                currClient.move(newX, newY, this);
                break;
            }
        }
    }

    public void ADD_TOWER(String towerId, int x, int y, int coverRad) {
        this.towers.add(new CellTower(towerId, x, y, coverRad));
    }

    public void REMOVE_TOWER(String towerId) {
        CellTower removedTower = null;
        
        // Find and remove from main network list
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).id.equals(towerId)) {
                removedTower = towers.remove(i);
                break;
            }
        }

        if (removedTower != null) {
            // Remove from operators' internal lists
            for (Operator op : operators) {
                op.towers.remove(removedTower);
            }
            
            // Reconnect affected clients to the next best tower
            for (Client client : clients) {
                if (client.connectedTower == removedTower) {
                    client.connectedTower = findNearestTowerForClient(client);
                }
            }
        }
    }

    public int TOWER_CLIENT_COUNT(String towerId) {
        int total = 0;
        for (Client currClient : this.clients) {
            if (currClient.connectedTower != null && currClient.connectedTower.id.equals(towerId)) {
                total++;
            }
        }
        return total;
    }

    public void ADD_OPERATOR(String newOpName) {
        this.operators.add(new Operator(newOpName));
    }

    public int OPERATOR_SUBSCRIBER_COUNT(String searchOpName) {
        int total = 0;
        for (Client currClient : this.clients) {
            if (currClient.opName.equals(searchOpName)) {
                total++;
            }
        }
        return total;
    }

    CellTower findNearestTowerForClient(Client client) {
        Operator clientOp = null;
        for (Operator op : operators) {
            if (op.name.equals(client.opName)) {
                clientOp = op;
                break;
            }
        }
        
        if (clientOp == null || clientOp.towers.isEmpty()) {
            return null;  
        }
        
        CellTower nearest = null;
        double minDistance = Double.MAX_VALUE;
        
        for (CellTower tower : clientOp.towers) {
            double dist = Math.sqrt(Math.pow(client.x - tower.x, 2) + Math.pow(client.y - tower.y, 2));
            
            if (dist <= tower.coverRad && dist < minDistance) {
                minDistance = dist;
                nearest = tower;
            } else if (nearest != null && Math.abs(dist - minDistance) < 0.0001) {
                if (getClientCountForTower(tower) < getClientCountForTower(nearest)) {
                    nearest = tower;
                }
            }
        }
        
        return nearest;
    }
    
    public int getClientCountForTower(CellTower tower) {
        int count = 0;
        for (Client client : clients) {
            if (client.connectedTower == tower) {
                count++;
            }
        }
        return count;
    }
    
    public void REGISTER_OPERATOR_TOWER(String opName, String towerId) {
        Operator targetOp = null;
        CellTower targetTower = null;
        
        for (Operator op : operators) {
            if (op.name.equals(opName)) {
                targetOp = op;
                break;
            }
        }
        
        for (CellTower tower : towers) {
            if (tower.id.equals(towerId)) {
                targetTower = tower;
                break;
            }
        }
        
        if (targetOp != null && targetTower != null && !targetOp.towers.contains(targetTower)) {
            targetOp.towers.add(targetTower);
            targetTower.operator = opName;
            
            // Re-evaluate clients for this operator as they might find this new tower closer
            for (Client client : clients) {
                if (client.opName.equals(opName)) {
                    client.connectedTower = findNearestTowerForClient(client);
                }
            }
        }
    }
    
    public void CHANGE_OPERATOR(int phoneNumber, String newOpName) {
        for (Client client : clients) {
            if (client.number == phoneNumber) {
                client.opName = newOpName;
                client.connectedTower = findNearestTowerForClient(client);
                break;
            }
        }
    }
    
    public void NO_SIGNAL_COUNT() {
        for (Operator op : operators) {
            int noSignalCount = 0;
            for (Client client : clients) {
                if (client.opName.equals(op.name) && client.connectedTower == null) {
                    noSignalCount++;
                }
            }
            System.out.println(op.name + ": " + noSignalCount + " phones without signal.");
        }
    }
}

public class NetworkSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Network network = new Network();
        
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (command.isEmpty()) continue;
            
            // Fallback for mashed strings like "ADD_OPERATOROperatorA"
            if (command.startsWith("ADD_OPERATOR") && command.length() > 12) {
                String newOpName = command.substring(12).trim();
                network.ADD_OPERATOR(newOpName);
                continue;
            }
            
            switch (command) {
                case "ADD_CLIENT":
                    int number = Integer.parseInt(scanner.nextLine().trim());
                    String opName = scanner.nextLine().trim();
                    int x = Integer.parseInt(scanner.nextLine().trim());
                    int y = Integer.parseInt(scanner.nextLine().trim());
                    network.ADD_CLIENT(number, opName, x, y);
                    break;
                    
                case "REMOVE_CLIENT":
                    int removeNumber = Integer.parseInt(scanner.nextLine().trim());
                    network.REMOVE_CLIENT(removeNumber);
                    break;
                    
                case "MOVE_CLIENT":
                    int moveNumber = Integer.parseInt(scanner.nextLine().trim());
                    int newX = Integer.parseInt(scanner.nextLine().trim());
                    int newY = Integer.parseInt(scanner.nextLine().trim());
                    network.MOVE_CLIENT(moveNumber, newX, newY);
                    break;
                    
                case "ADD_TOWER":
                    String towerId = scanner.nextLine().trim();
                    int towerX = Integer.parseInt(scanner.nextLine().trim());
                    int towerY = Integer.parseInt(scanner.nextLine().trim());
                    int radius = Integer.parseInt(scanner.nextLine().trim());
                    network.ADD_TOWER(towerId, towerX, towerY, radius);
                    break;
                    
                case "REMOVE_TOWER":
                    String removeTowerId = scanner.nextLine().trim();
                    network.REMOVE_TOWER(removeTowerId);
                    break;
                    
                case "TOWER_CLIENT_COUNT":
                    String countTowerId = scanner.nextLine().trim();
                    System.out.println(network.TOWER_CLIENT_COUNT(countTowerId));
                    break;
                    
                case "ADD_OPERATOR":
                    String nextOpName = scanner.nextLine().trim();
                    network.ADD_OPERATOR(nextOpName);
                    break;
                    
                case "OPERATOR_SUBSCRIBER_COUNT":
                    String searchOpName = scanner.nextLine().trim();
                    System.out.println(network.OPERATOR_SUBSCRIBER_COUNT(searchOpName));
                    break;
                    
                case "REGISTER_OPERATOR_TOWER":
                    String regOpName = scanner.nextLine().trim();
                    String regTowerId = scanner.nextLine().trim();
                    network.REGISTER_OPERATOR_TOWER(regOpName, regTowerId);
                    break;
                    
                case "CHANGE_OPERATOR":
                    int changeNumber = Integer.parseInt(scanner.nextLine().trim());
                    String changeOpName = scanner.nextLine().trim();
                    network.CHANGE_OPERATOR(changeNumber, changeOpName);
                    break;
                    
                case "NO_SIGNAL_COUNT":
                    network.NO_SIGNAL_COUNT();
                    break;
                    
                default:
                    // Ignore unknown commands
                    break;
            }
        }
        scanner.close();
    }
}