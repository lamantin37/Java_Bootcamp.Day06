package edu.school21.numbers;

import edu.school21.numbers.exception.IllegalNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {

    private NumberWorker numberWorker;

    @BeforeEach
    void createClass() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 43, 89, 109, 163})
    void testIsPrime(int number) {
        Assertions.assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 222, 14, 196, 20})
    void testIsNotPrime(int number) {
        Assertions.assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-7, 0, 1, -222})
    void testException(int number) {
        Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1, delimiter = ',')
    void testSumDigits(int number, int expected) {
        Assertions.assertEquals(numberWorker.digitsSum(number), expected);
    }
}
