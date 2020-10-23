package de.rki.coronawarnapp.ui.settings.crash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.squareup.inject.assisted.AssistedInject
import de.rki.coronawarnapp.bugreporting.reportProblem
import de.rki.coronawarnapp.bugreporting.storage.repository.DefaultBugRepository
import de.rki.coronawarnapp.crash.CrashReportEntity
import de.rki.coronawarnapp.crash.CrashReportRepository
import de.rki.coronawarnapp.util.viewmodel.CWAViewModel
import de.rki.coronawarnapp.util.viewmodel.SimpleCWAViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsCrashReportViewModel @AssistedInject constructor(
    private val crashReportRepository: CrashReportRepository,
    private val defaultBugRepository: DefaultBugRepository
) : CWAViewModel() {

    val crashReports = crashReportRepository.allCrashReports

    init {
        defaultBugRepository.getAll().observeForever() {
            Timber.d("Bug count ${it.count()}")
        }
    }

    lateinit var selectedCrashReport: LiveData<CrashReportEntity>

    fun deleteAllCrashReports() = viewModelScope.launch(Dispatchers.IO) {
        crashReportRepository.deleteAllCrashReports()
    }

    fun simulateExceptioin() {
        try {
            val a = 2 / 0
        } catch (e: Exception) {
            Timber.e(e, "Msg: ${e.message}")
            e.reportProblem(tag = SettingsCrashReportViewModel::class.java.simpleName)
        }
    }

    fun selectCrashReport(id: Long) {
        Timber.d("Selected crash report $id")
        selectedCrashReport = crashReportRepository.getCrashReportForId(id)
    }

    @AssistedInject.Factory
    interface Factory : SimpleCWAViewModelFactory<SettingsCrashReportViewModel>
}
