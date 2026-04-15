public class PrefixTreeUsage {
    public static void main(String[] args) {
      PrefixTree trie = new PrefixTree();

      trie.insert("cat");
      trie.insert("car");
      trie.insert("dog");

      System.out.println("Search 'cat': " + trie.search("cat")); // true
      System.out.println("Search 'bat': " + trie.search("bat")); // false
      System.out.println("StartsWith 'ca': " + trie.startsWith("ca")); // true
      System.out.println("StartsWith 'do': " + trie.startsWith("do")); // true
      System.out.println("StartsWith 'bo': " + trie.startsWith("bo")); // false

      System.out.println("Trie Structure:");
      trie.traverse();
      }
}