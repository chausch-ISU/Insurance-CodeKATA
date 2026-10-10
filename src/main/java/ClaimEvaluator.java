import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimEvaluator {

    public  ClaimResult evaluate(Claim claim, Policy policy) {

        LocalDate incidentDate = claim.getIncidentDate();

        if(!policy.getCoveredIncidents().contains(claim.getIncidentType())) {
            ClaimResult result = new ClaimResult(false, BigDecimal.ZERO, ReasonCode.NOT_COVERED);
            return  result;
        }

        if (incidentDate.isBefore(policy.getStartDate())
                || incidentDate.isAfter(policy.getEndDate())) {
            return new ClaimResult(
                    false,
                    BigDecimal.ZERO,
                    ReasonCode.POLICY_INACTIVE
            );
        }

        if (claim.getAmountClaimed().compareTo(policy.getDeductible()) <= 0) {
            return new ClaimResult(
                    false,
                    BigDecimal.ZERO,
                    ReasonCode.ZERO_PAYOUT
            );
        }

        BigDecimal payout = claim.getAmountClaimed().subtract(policy.getDeductible());

        if (payout.compareTo(policy.getCoverageLimit()) > 0) {
            payout = policy.getCoverageLimit();
        }
        ClaimResult result = new ClaimResult(true, payout, ReasonCode.APPROVED);

        return result;

    }

}
