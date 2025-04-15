    package com.example.mobileapp.ui.access_logs_screen

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.mobileapp.data.repo.AccessLogsRepository
    import com.example.mobileapp.data.table.AccessLogs
    import kotlinx.coroutines.flow.SharingStarted
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.map
    import kotlinx.coroutines.flow.stateIn

    class AccessLogViewModel(accessLogsRepository: AccessLogsRepository) : ViewModel() {

        val accessLogUiState: StateFlow<AccessLogUiState> = accessLogsRepository.getAllLogs()
            .map { AccessLogUiState(it) }
            .stateIn(
            scope = viewModelScope,
            started =  SharingStarted.WhileSubscribed(5_000L),
            initialValue = AccessLogUiState()
            )

    }

    data class AccessLogUiState(
        val logList: List<AccessLogs> = listOf()
    )