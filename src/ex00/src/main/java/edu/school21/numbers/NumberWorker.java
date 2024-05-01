package edu.school21.numbers;

import edu.school21.numbers.exception.IllegalNumberException;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) throw new IllegalNumberException("Incorrect number.");
        for (int i = 2; i * i < number; ++i) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        int counter = 0;
        while (number > 0) {
            counter += number % 10;
            number /= 10;
        }
        return counter;
    }
}