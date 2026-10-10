import java.time.LocalDate;
import java.math.BigDecimal;

public class Claim {

private final String policyId;
private final IncidentType incidentType;
private final BigDecimal amountClaimed;
private final LocalDate incidentDate;

Claim(String policyId, IncidentType incidentType, BigDecimal amountClaimed, LocalDate incidentDate) {
    this.policyId = policyId;
    this.incidentType = incidentType;
    this.amountClaimed = amountClaimed;
    this.incidentDate = incidentDate;
}

public String getPolicyId() {
    return policyId;
}

public IncidentType getIncidentType() {
    return incidentType;
}
public BigDecimal getAmountClaimed() {
    return amountClaimed;
}

public LocalDate getIncidentDate() {
    return incidentDate;
}

}
