import java.math.BigDecimal;

public class ClaimResult {
private final boolean approved;
private final BigDecimal payout;
private final ReasonCode reasonCode;

public ClaimResult(boolean approved, BigDecimal payout, ReasonCode reasonCode) {
    this.approved = approved;
    this.payout = payout;
    this.reasonCode = reasonCode;
}

public boolean isApproved() {
    return approved;
}
public BigDecimal getPayout() {
    return payout;
}
public ReasonCode getReasonCode() {
    return reasonCode;
}

}
