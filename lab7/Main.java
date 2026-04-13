public class Main {
    public static void main(String[] args) {
        EncryptionService service = new EncryptionService();

        String message = "HelloWorld";

        System.out.println("Caesar Cipher: " + service.encrypt(message, "Caesar"));
        System.out.println("Base64 Encoding: " + service.encrypt(message, "Base64"));
        System.out.println("XOR Encryption: " + service.encrypt(message, "XOR")); 
    }
}