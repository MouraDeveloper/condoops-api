package com.eduardo.condoops.dto.workOrder;

import java.math.BigDecimal;

public record RegisterWorkOrderCostsDto(
        BigDecimal laborCost,
        BigDecimal materialCost
) {
}
