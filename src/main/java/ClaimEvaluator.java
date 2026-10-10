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

        BigDecimal payout = claim.getAmountClaimed().subtract(policy.getDeductible());
        ClaimResult result = new ClaimResult(true, payout, ReasonCode.APPROVED);

        return result;

    }

}
