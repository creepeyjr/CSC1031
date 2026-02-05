import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the upper limit
        long limit = scanner.nextInt();

        // Counter of prime number
        long cnt = 0;

        // Write your code
        // for loop through numbers till limit
        for (int i = 1; i <= limit; i ++) {
            // on every ideration, call function and check if it returns true
            if (isPrime(i) == true) {
                cnt++;
            }
        System.err.println(cnt);
        }

        // Print the results
        System.out.println(cnt);
    }

    
    // Function to check if it is prime
    private static boolean isPrime(long num) {
        
        // iterate through all numbers from 2 to n-1, and
        // for every number, check if it divides n.
        // if any number that divides, return false

        // corner case, if the number is 1 or less
        if (num <= 1) {
            return false;
        }

        // Check from 2 to n-1
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
    
}