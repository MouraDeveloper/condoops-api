package com.eduardo.condoops.dto.asset;

import com.eduardo.condoops.entity.enums.AssetStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAssetStatusRequest(
        @NotNull(message = "Status is required.")
        AssetStatus status
) {
}
