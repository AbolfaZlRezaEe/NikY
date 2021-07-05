package com.abproject.niky.model.dataclass

import androidx.annotation.StringRes

/**
 * this data class responsible for each empty states in the
 * application.
 */
data class EmptyState(
    val mustShowEmptyState: Boolean,
    @StringRes val emptyStateMessage: Int = 0,
)
