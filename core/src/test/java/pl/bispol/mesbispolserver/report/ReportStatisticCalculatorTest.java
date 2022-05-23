package pl.bispol.mesbispolserver.report;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportStatisticCalculatorTest {

    @ParameterizedTest
    @CsvSource({"12, 915000, 76250", "12, 385000, 32083", "9.92, 766000, 77217", "10.0, 337270, 33727"})
    void countExpectedProductionPerHour(float hoursAmount, int targetAmount, int expectedValue) {
        //given
        //when
        int result = ReportStatisticCalculator.countProductionPerHour(targetAmount, hoursAmount);
        //then
        assertEquals(expectedValue, result);
    }

    @ParameterizedTest
    @CsvSource({"442700, 12, 915000, 12, 48", "765000, 11.67, 915000, 12, 85"})
    void countOee(long goodPieces, float workingTime, long targetAmount, float hoursAmount, int expectedValue) {
        //given
        //when
        int result = ReportStatisticCalculator.countOee(goodPieces, workingTime, targetAmount, hoursAmount);
        //then
        assertEquals(expectedValue, result);
    }
}