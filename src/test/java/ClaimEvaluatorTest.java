import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;

public class ClaimEvaluatorTest {

    @Test
    void claimPaysAmountMinusDeductible() {

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

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertTrue(result.isApproved());
        assertEquals(ReasonCode.APPROVED, result.getReasonCode());
        assertEquals(0, new BigDecimal("2000").compareTo(result.getPayout()));

    }

    @Test
    void claimDoesNotPayIfWrongIncidentType(){
        Policy policy = new Policy(
                "POL2",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("10000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL2",
                IncidentType.FIRE,
                new BigDecimal("3000"),
                LocalDate.of(2026, 1, 15)
        );
        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertFalse(result.isApproved());
        assertEquals(ReasonCode.NOT_COVERED, result.getReasonCode());
        assertEquals(0, new BigDecimal("0").compareTo(result.getPayout()));
    }

    @Test
    void claimDoesNotPayIfBeforePolicyStart(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("10000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("3000"),
                LocalDate.of(2025, 6, 6)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertFalse(result.isApproved());
        assertEquals(ReasonCode.POLICY_INACTIVE, result.getReasonCode());
        assertEquals(0, new BigDecimal("0").compareTo(result.getPayout()));

    }

    @Test
    void claimDoesNotPayIfAfterPolicyEnd(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("10000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("3000"),
                LocalDate.of(2027, 6, 6)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertFalse(result.isApproved());
        assertEquals(ReasonCode.POLICY_INACTIVE, result.getReasonCode());
        assertEquals(0, new BigDecimal("0").compareTo(result.getPayout()));

    }

    @Test
    void claimPaysIfMadeOnStartDateOfPolicy(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("10000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("3000"),
                LocalDate.of(2026, 1, 1)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertTrue(result.isApproved());
        assertEquals(ReasonCode.APPROVED, result.getReasonCode());
        assertEquals(0, new BigDecimal("2000").compareTo(result.getPayout()));

    }

    @Test
    void claimPaysIfMadeOnEndDateOfPolicy(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("10000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("3000"),
                LocalDate.of(2026, 2, 2)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertTrue(result.isApproved());
        assertEquals(ReasonCode.APPROVED, result.getReasonCode());
        assertEquals(0, new BigDecimal("2000").compareTo(result.getPayout()));

    }

    @Test
    void claimDoesNotPayMoreThanCoverageLimit(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("3000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("6000"),
                LocalDate.of(2026, 1, 15)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertTrue(result.isApproved());
        assertEquals(ReasonCode.APPROVED, result.getReasonCode());
        assertEquals(0, new BigDecimal("3000").compareTo(result.getPayout()));

    }

    @Test
    void claimDeniedIfDeductibleGreaterThanClaim(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1500"),
                new BigDecimal("3000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("1000"),
                LocalDate.of(2026, 1, 15)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertFalse(result.isApproved());
        assertEquals(ReasonCode.ZERO_PAYOUT, result.getReasonCode());
        assertEquals(0, new BigDecimal("0").compareTo(result.getPayout()));

    }

    @Test
    void claimDeniedIfDeductibleEqualToClaim(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("3000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("1000"),
                LocalDate.of(2026, 1, 15)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertFalse(result.isApproved());
        assertEquals(ReasonCode.ZERO_PAYOUT, result.getReasonCode());
        assertEquals(0, new BigDecimal("0").compareTo(result.getPayout()));

    }

    @Test
    void claimedPayoutAboveCoverageLimitDoesNotImmediatelyTriggerPayoutCap(){
        Policy policy = new Policy(
                "POL",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("3500"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("4000"),
                LocalDate.of(2026, 1, 15)
        );

        ClaimResult result = new ClaimEvaluator().evaluate(claim, policy);

        assertTrue(result.isApproved());
        assertEquals(ReasonCode.APPROVED, result.getReasonCode());
        assertEquals(0, new BigDecimal("3000").compareTo(result.getPayout()));
    }

}
