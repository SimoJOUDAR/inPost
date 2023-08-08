package pl.inpost.recruitmenttask.domain.model

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.R

data class ShipmentWrapper (
    val section: Section,
    val shipmentList: List<ShipmentNetwork>
) {
    enum class Section(
        @StringRes val sectionRes: Int
    ) {
        HIGHLIGHTED(R.string.status_ready_to_pickup),
        OTHER(R.string.status_other);
    }
}