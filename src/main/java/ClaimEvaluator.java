import java.math.BigDecimal;

public class ClaimEvaluator {

    public  ClaimResult evaluate(Claim claim, Policy policy) {


        if(policy.getCoveredIncidents().contains(claim.getIncidentType())) {
            BigDecimal payout = claim.getAmountClaimed().subtract(policy.getDeductible());
            ClaimResult result = new ClaimResult(true, payout, ReasonCode.APPROVED);

            return result;
        }
        ClaimResult result = new ClaimResult(false, BigDecimal.ZERO, ReasonCode.NOT_COVERED);
        return  result;

    }

}
