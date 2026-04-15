/*
PREFIX TREE
===========

What is a Prefix tree?
----------------------
Tree-based structure that stores a set of strings in a way that allows fast prefix-based lookups.
Each node in the tree represents a single character, and paths from the root to the leaves form words or sequences.

Lab Objectives
--------------
- TrieNode class to represent nodes in Prefix Tree.
- PrefixTree class to manage word insertions and lookups.
- Ensure Prefix-search is effiecient.
- Implement a traverse. 
*/


import java.util.HashMap;
import java.util.Map;

class TrieNode {
    // Attributes
    char ch;
    HashMap<Character, TrieNode> hashMap = new HashMap<>();
    boolean isEnd;

    // Constructor for regular nodes
    public TrieNode(char ch) {
        this.ch = ch;
    }

    // Constructor for root node (No character)
    public TrieNode() {
        this.ch = '\0';
    }

    void markAsLeaf() {
        isEnd = true;
    }
}

class PrefixTree {
    private TrieNode root;  // establish this as the root node (Beginning)

    // Constructor
    public PrefixTree() {
        root = new TrieNode();
    }

    // Traverse the tree from root to leaf node with each character in word.
    void insert(String word) {
        TrieNode current = root;  // set current node as root node

        // loop through tree
        for (int i = 0; i < word.length(); i ++) {
            char ch = word.charAt(i);  // going to cycle through every char in word

            // Checking if the char exists in tree already or create new node
            if (!current.hashMap.containsKey(ch)) {
                // since it doesn't, we create a new node for this char
                TrieNode trieNode = new TrieNode(ch);  // Create new node
                current.hashMap.put(ch, trieNode);  // Associate this new node with this char 
            }
            
            // Move to the next child node, whether it already existed or just made
            current = current.hashMap.get(ch);
        
        }
        // Reached the end of the loop (word), so mark this node as the final letter
        current.markAsLeaf();
    }

    boolean search(String word) {
        TrieNode current = root;  // Start from root node again

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (!current.hashMap.containsKey(ch)) {
                return false;
            }

            current = current.hashMap.get(ch);
        }
        // If we are at this point we confirm that the word exists,
        // but we should also check that the word is marked as End.
        return current.isEnd;
    }

    // Same as Search, but the word doesn't need to be declared as the "end" of itself
    boolean startsWith(String prefix) {
        TrieNode current = root;  // start again from root

        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);

            if (!current.hashMap.containsKey(ch)) {
                return false;
            }

            current = current.hashMap.get(ch);
        }
        // Can just return true, we found that the word exists, somewhere...
        return true;
    }

    void traverse() {
        // Iterate through the HashMap
        for (Map.Entry<Character, TrieNode> entry : root.hashMap.entrySet()) {
            // Start from root's children (level 0)
            traverseLoop(entry.getValue(), 0);
        }
    }
    
    private void traverseLoop(TrieNode node, int level) {

        // print statements to contruct output
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        // Print the character
        System.out.print("  └── " + node.ch);

        // add on end as needed
        if (node.isEnd) {
            System.out.print(" (end)");
        }

        // Flush out print statements
        System.out.println();

        // Iterate through the children of the Current Node
        for (Map.Entry<Character, TrieNode> entry : node.hashMap.entrySet()) {
            traverseLoop(entry.getValue(), level + 1);
        }    
    }
}

/*
  └── c
*/