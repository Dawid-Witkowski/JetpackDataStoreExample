package simplyquotes.example.jetpackdatastoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import simplyquotes.example.jetpackdatastoreexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        binding.readFromPreferencesButton.setOnClickListener {
            val usersInput = binding.readFromPreferencesEditText.text.toString()
            viewModel.read(usersInput)
        }

        binding.saveButton.setOnClickListener {

            // getting the value from textFields
            val usersKey = binding.keyEditText.text.toString()
            val usersValue = binding.valueEditText.text.toString()

            // saving (duh)
            viewModel.save(usersKey, usersValue)

            // just to show that it was saved
            Snackbar.make(binding.root, "Saved", Snackbar.LENGTH_SHORT).show()
            binding.keyEditText.text?.clear()
            binding.valueEditText.text?.clear()
        }

        viewModel.observableLiveData.observe(this) {
            binding.resultTextView.text = it
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}