package eu.tkacas.smartalert.viewmodel.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.cloud.getStatisticsPerYear
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsViewModel: ViewModel() {
    private var _data = mutableMapOf<String, Any>()
    private var _years = MutableLiveData<List<String>>(listOf())
    private var _months = MutableLiveData<List<String>>(listOf())

    private var _selectedYear: String? = null
    private var _selectedMonth: String? = null

    var selectedYear: String?
        get() = _selectedYear
        set(value) {
            _selectedYear = value
        }

    var selectedMonth: String?
        get() = _selectedMonth
        set(value) {
            _selectedMonth = value
        }

    var years: MutableLiveData<List<String>>
        get() = _years
        set(value) {
            _years = value
        }

    var months: MutableLiveData<List<String>>
        get() = _months
        set(value) {
            _months = value
        }

    init {
        fetchData()
    }

    fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            getStatisticsPerYear { success, dataRetrieved, error ->
                if (success) {
                    _data = dataRetrieved?.toMutableMap() ?: mutableMapOf()
                    _years.postValue(fetchYears())
                    _months.postValue(fetchMonths())
                } else {
                    println("Error fetching data: $error")
                }
            }
        }
    }

    private fun fetchYears(): List<String> {
        val years = mutableListOf<String>()
        _data.keys.forEach { key ->
            years.add(key)
        }
        return years
    }

    private fun fetchMonths(): List<String> {
        val months = mutableSetOf<String>()
        _data.values.forEach { value ->
            if (value is Map<*, *>) {
                val sumPerMonth = value["sumPerMonth"] as? Map<String, Any>
                sumPerMonth?.keys?.forEach { month ->
                    months.add(month)
                }
            }
        }
        return months.toList()
    }
}