import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Set;

public class Policy {

    private final String policyId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal deductible;
    private final BigDecimal coverageLimit;
    private final Set<IncidentType> coveredIncidents;

    public Policy(String policyId, LocalDate startDate, LocalDate endDate, BigDecimal deductible, BigDecimal coverageLimit, Set<IncidentType> coveredIncidents) {
        this.policyId = policyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deductible = deductible;
        this.coverageLimit = coverageLimit;
        this.coveredIncidents = coveredIncidents;
    }

    public String getPolicyId() {
        return policyId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public BigDecimal getDeductible() {
        return deductible;
    }
    public BigDecimal getCoverageLimit() {
        return coverageLimit;
    }
    public Set<IncidentType> getCoveredIncidents() {
        return coveredIncidents;
    }

}

