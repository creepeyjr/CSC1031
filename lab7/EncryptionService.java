import java.util.Base64;


// Interface
interface EncryptionStrategy {
    // Contains one method
    String encrypt(String text);
}

// Strategy Classes
class CaesarCipherEncryption implements EncryptionStrategy {
    private final int shift;
    
    // constructor
    public CaesarCipherEncryption(int shift) {
        this.shift = shift;
    }
    
    // provide context
    @Override
    public String encrypt(String text) {
        
        // Copy code from original script
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + shift) % 26 + base);
            }
            result.append(ch);
        }
        return result.toString();
    }
}

class Base64Encryption implements EncryptionStrategy {
    // Provide the contexrtrttt
    @Override
    public String encrypt(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
}

class XOREncryption implements EncryptionStrategy {
    private final char key;
    
    // constructor
    public XOREncryption(char key) {
        this.key = key;
    }
    @Override
    public String encrypt(String text) {
        // Copy code from original script
        StringBuilder result = new StringBuilder();
        
        for (char ch : text.toCharArray()) {
            result.append((char) (ch ^ key));
        }
        return result.toString();
    }
}

class ReverseStringEncryption implements EncryptionStrategy {
    
    @Override
    public String encrypt(String text) {
        String revStr = "";
        
        // loop through letters of text
        for (int i = 0; i < text.length(); i ++) {
            revStr = text.charAt(i) + revStr;
        }
        return revStr;
    }
}

class DuplicateCharacterEncryption implements EncryptionStrategy {
    @Override
    public String encrypt(String text) {
        StringBuilder dupStr = new StringBuilder(); 

        for (char ch : text.toCharArray()) {
            dupStr.append(ch).append(ch);
        }
        return dupStr.toString();
    }
}

// Context class
class EncryptionService {
    private EncryptionStrategy strategy;
    private String text;

    public void setEncryptionStrategy(EncryptionStrategy strategy) {
        this.strategy = strategy;
    }
    public String encrypt(String text) {
        this.text = text;
        return strategy.encrypt(text);
    }
}