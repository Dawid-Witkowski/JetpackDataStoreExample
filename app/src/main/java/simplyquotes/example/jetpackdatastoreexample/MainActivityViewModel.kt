package simplyquotes.example.jetpackdatastoreexample

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    // passing context here could've been done with DI but this works as well
    private val Context.dataStore by preferencesDataStore("Example")
    private val dataStore = application.dataStore

    val observableLiveData = MutableLiveData<String>()

    fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        viewModelScope.launch {
            dataStore.edit { example ->
                example[dataStoreKey] = value
            }
        }
    }

    fun read(key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        viewModelScope.launch {
            val preferences = dataStore.data.first()
            observableLiveData.postValue(preferences[dataStoreKey] ?: "Not found")
        }
    }
}