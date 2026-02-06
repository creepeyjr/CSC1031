// Based on GeeksforGeeks Solution
// Uses Sieve of Eratosthenes algorithm

import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {
        // Collect input
        Scanner scanner = new Scanner(System.in);
        // Read input
        int n = scanner.nextInt();
        // call sieve function
        System.out.println(sieve(n));
        scanner.close();
    }

    static int sieve(int n) {
        // Initialise a boolean array sized n + 1, with all entries set to true,
        // assuming all nums are prime intially.
        boolean[] prime = new boolean[n + 1];
        for (int i = 0; i <= n; i++) {
            prime[i] = true;
        }

        // Start with p = 2, first prime num, for each p, eliminate all its
        // multiples from the list of primes.
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                // marking as false
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        // count number of primes
        int count = 0;
        for (int p = 2; p <= n; p++) {
            if (prime[p])
                count++;
        }

        // return count
        return count;
    }
}

/*
public class PrimeNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.close();

        // Read the upper limit
        long limit = scanner.nextInt();

    
        long cnt = 0;

        // Write your code
        // for loop through numbers till limit
        for (int i = 1; i <= limit; i ++) {
            // on every ideration, call function and check if it returns true
            if (isPrime(i) == true) {
                cnt++;
            }
        // System.err.println(cnt);
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
*/