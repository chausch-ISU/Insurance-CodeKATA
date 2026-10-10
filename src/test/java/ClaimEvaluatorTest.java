import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;

public class ClaimEvaluatorTest {

    @Test
    void coveredClaimPaysAmountMinusDeductible() {

        Policy policy = new Policy(
            "POL1",
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 2, 2),
            new BigDecimal("1000"),
            new BigDecimal("10000"),
            EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL1",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("3000"),
                LocalDate.of(2026, 1, 15)
        );

        //assume method will be called evaluate
        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertTrue(result.isApproved());
        assertEquals(ReasonCode.APPROVED, result.getReasonCode());
        assertEquals(0, new BigDecimal("2000").compareTo(result.getPayout()));

    }

}
