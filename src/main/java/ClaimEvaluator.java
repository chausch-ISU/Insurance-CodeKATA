import java.math.BigDecimal;

public class ClaimEvaluator {

    public  ClaimResult evaluate(Claim claim, Policy policy) {
        BigDecimal payout = claim.getAmountClaimed().subtract(policy.getDeductible());
        ClaimResult result = new ClaimResult(true, payout, ReasonCode.APPROVED);

        return result;
    }

}
