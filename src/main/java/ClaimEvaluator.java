import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimEvaluator {

    public  ClaimResult evaluate(Claim claim, Policy policy) {

        LocalDate incidentDate = claim.getIncidentDate();

        /*Checks for discrepancies in order of incident type -> date range -> payout amount
        * This order should make sure the most significant reason code is used for rejections
        * since only one can be returned
        * */

        //check if the correct incident is covered
        if(!policy.getCoveredIncidents().contains(claim.getIncidentType())) {
            ClaimResult result = new ClaimResult(false, BigDecimal.ZERO, ReasonCode.NOT_COVERED);
            return  result;
        }

        //check to see if incident is in correct date window, start and end date inclusive
        if (incidentDate.isBefore(policy.getStartDate()) || incidentDate.isAfter(policy.getEndDate())) {
            return new ClaimResult(
                    false,
                    BigDecimal.ZERO,
                    ReasonCode.POLICY_INACTIVE
            );
        }

        //make sure a valid amount will be payed out
        if (claim.getAmountClaimed().compareTo(policy.getDeductible()) <= 0) {
            return new ClaimResult(
                    false,
                    BigDecimal.ZERO,
                    ReasonCode.ZERO_PAYOUT
            );
        }

        //calculate payout, deductible subtracted from claim before policy amount is taken into consideration
        BigDecimal payout = claim.getAmountClaimed().subtract(policy.getDeductible());

        //make sure payout is not larger than the policy coverage limit
        if (payout.compareTo(policy.getCoverageLimit()) > 0) {
            payout = policy.getCoverageLimit();
        }

        //final result given everything has worked properly
        ClaimResult result = new ClaimResult(true, payout, ReasonCode.APPROVED);

        return result;

    }

}
