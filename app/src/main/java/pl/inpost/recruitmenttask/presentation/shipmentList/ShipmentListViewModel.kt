package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.repositories.ShipmentsRepository
import pl.inpost.recruitmenttask.domain.model.ShipmentWrapper
import pl.inpost.recruitmenttask.util.wrapShipments
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val repository: ShipmentsRepository
) : ViewModel() {

    private val _wrappedShipmentsStateFlow = MutableStateFlow<ShipmentUiState>(ShipmentUiState.Loading)
    val wrappedShipmentsStateFlow = _wrappedShipmentsStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = ShipmentUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    init {
        refreshData()
    }

    fun refreshData() = viewModelScope.launch(Dispatchers.IO) {
        _wrappedShipmentsStateFlow.emit(ShipmentUiState.Loading)
        val response = repository.getShipmentsFromApi()
        if (response.isSuccessful)
            _wrappedShipmentsStateFlow.emit(ShipmentUiState.Success(response.body.wrapShipments()))
        else {
            val e = response.exception ?: Exception("An unidentified error occurred. We couldn't load the data. Please, check your internet connection.")
            _wrappedShipmentsStateFlow.emit(ShipmentUiState.Error(e))
        }
    }
}

sealed class ShipmentUiState {
    object Loading: ShipmentUiState()
    data class Success(val wrappedShipments : List<ShipmentWrapper>): ShipmentUiState()
    data class Error(val error: Exception): ShipmentUiState()
}
