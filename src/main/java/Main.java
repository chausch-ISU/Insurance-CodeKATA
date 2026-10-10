import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) {
        Policy policy = new Policy(
                "POL123",
                LocalDate.of(2026,1,1),
                LocalDate.of(2026,2,2),
                new BigDecimal("1000"),
                new BigDecimal("10000"),
                EnumSet.of(IncidentType.WATER_DAMAGE)
        );

        Claim claim = new Claim(
                "POL123",
                IncidentType.WATER_DAMAGE,
                new BigDecimal("3000"),
                LocalDate.of(2025, 6, 6)
        );

        ClaimEvaluator evaluator = new ClaimEvaluator();

        ClaimResult result = evaluator.evaluate(claim, policy);

        System.out.println("Claim evaluated successfully");
        System.out.println("Was the claim approved: " + result.isApproved());
        System.out.println("Result ReasonCode: " + result.getReasonCode());
        System.out.println("Payout Amount: " + result.getPayout());
    }

}
