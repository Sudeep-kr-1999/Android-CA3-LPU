package sudeep.example.ca3

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateFormat
import android.text.format.DateFormat.is24HourFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        AdapterView.OnItemSelectedListener {
    lateinit var parentLayout: ConstraintLayout;
    lateinit var meradianSpinner: Spinner;
    lateinit var bookingDate: EditText;
    lateinit var showTime: EditText;
    private lateinit var movieName: String;
    private lateinit var userName: String;
    private lateinit var userPhoneNumber: String;
    private lateinit var userEmailAddress: String;
    private lateinit var theaterNameWithAddress: String
    private lateinit var totalSeatCount: String;
    private var radioSelectedBoolean: Boolean = false;
    private lateinit var radioSelectedText: String;
    private var isCheckedCondition: Boolean = false;
    private lateinit var selectedMeradian: String;
    private var currentBackground: String = "White";
    var day = 0;
    var month = 0;
    var year = 0;
    var hour = 0;
    var minute = 0;
    var savedday = 0;
    var savedmonth = 0;
    var savedyear = 0;
    var savedhour = 0;
    var savedminute = 0;
    private lateinit var selectedDate: String;
    private lateinit var selectedTime: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        parentLayout = findViewById<ConstraintLayout>(R.id.parentLayout)
        val enterMovieName = findViewById<EditText>(R.id.editTextTextPersonName)
        val showMovieName = findViewById<TextView>(R.id.textView3)
        val username = findViewById<EditText>(R.id.editTextTextPersonName2)
        val phoneNumber = findViewById<EditText>(R.id.editTextPhone)
        val emailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val theatreNameWithAddress = findViewById<EditText>(R.id.editTextTextPersonName3)
        bookingDate = findViewById<EditText>(R.id.editTextDate)
        showTime = findViewById<EditText>(R.id.editTextTime)
        val categoryRadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val seatCount = findViewById<EditText>(R.id.editTextNumber)
        val termConditionCheckbox = findViewById<CheckBox>(R.id.checkBox)
        val submitBtn = findViewById<Button>(R.id.button2)
        meradianSpinner = findViewById<Spinner>(R.id.spinner2)

        if (meradianSpinner != null) {
            ArrayAdapter.createFromResource(this, R.array.timeMeradian, android.R.layout.simple_spinner_item)
                    .also {
                        it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        meradianSpinner.adapter = it
                        meradianSpinner.onItemSelectedListener = this;
                    }
        }


        termConditionCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                isCheckedCondition = true;
                val snackBar = Snackbar.make(parentLayout, R.string.termConditionApplied, Snackbar.LENGTH_LONG).setAction("Close") {
                    Toast.makeText(this, R.string.infoverified, Toast.LENGTH_LONG).show()
                }
                snackBar.setTextColor(Color.WHITE);
                snackBar.setActionTextColor(Color.RED);
                snackBar.setBackgroundTint(Color.BLACK);
                snackBar.show();
            } else {
                isCheckedCondition = false;
            }

        }
        enterMovieName.setOnClickListener {
            movieName = enterMovieName.text.toString();
            showMovieName.setText(movieName);
            username.requestFocus();
            enterMovieName.setText("");

        }
        categoryRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            radioSelectedBoolean = true;
        }

        bookingDate.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()


        }

        showTime.setOnClickListener {
            TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this)).show()
        }
        submitBtn.setOnClickListener {
            if (radioSelectedBoolean) {
                var selectButtonId = categoryRadioGroup.checkedRadioButtonId;
                var radioSelected = findViewById<RadioButton>(selectButtonId)
                radioSelectedText = radioSelected.text.toString();
            } else {
                val snackBar = Snackbar.make(parentLayout, R.string.seclectradio, Snackbar.LENGTH_LONG).setAction(R.string.selectRadioClose) {
                    return@setAction;
                }
                snackBar.setTextColor(Color.BLACK);
                snackBar.setActionTextColor(Color.BLUE);
                snackBar.setBackgroundTint(Color.RED);
                snackBar.show();
            }
            if (!isCheckedCondition) {
                val snackBar = Snackbar.make(parentLayout, R.string.checkboxmessagesnacker, Snackbar.LENGTH_LONG).setAction(R.string.snackcheck) {
                    termConditionCheckbox.isChecked = true
                }
                snackBar.setTextColor(Color.BLACK);
                snackBar.setActionTextColor(Color.BLUE);
                snackBar.setBackgroundTint(Color.RED);
                snackBar.show();
            }
            if (!isCheckedCondition && !radioSelectedBoolean) {
                val snackBar = Snackbar.make(parentLayout, R.string.checkboxandradiosnack, Snackbar.LENGTH_LONG).setAction(R.string.snackcheck) {
                    termConditionCheckbox.isChecked = true
                }
                snackBar.setTextColor(Color.BLACK);
                snackBar.setActionTextColor(Color.BLUE);
                snackBar.setBackgroundTint(Color.RED);
                snackBar.show();
            }

            movieName = showMovieName.text.toString()
            userName = username.text.toString()
            userPhoneNumber = phoneNumber.text.toString()
            userEmailAddress = emailAddress.text.toString()
            theaterNameWithAddress = theatreNameWithAddress.text.toString()
            selectedDate = bookingDate.text.toString()
            selectedTime = showTime.text.toString()
            totalSeatCount = seatCount.text.toString()
            if (movieName.isEmpty() || userName.isEmpty() || userPhoneNumber.length != 10 || userEmailAddress.isEmpty()
                    || theaterNameWithAddress.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty() || totalSeatCount.isEmpty() || selectedMeradian.isEmpty()
                    || !isCheckedCondition || !radioSelectedBoolean) {

                Toast.makeText(this, R.string.allinfowarning, Toast.LENGTH_SHORT).show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.confirmation).setMessage(R.string.messageDialog1).setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)

                builder.setPositiveButton(R.string.yes) { dialogInterface, which ->
                    val intent = Intent(this, PaymentActivity::class.java)
                    intent.putExtra("movieName", movieName);
                    intent.putExtra("userName", userName);
                    intent.putExtra("phoneNumber", userPhoneNumber);
                    intent.putExtra("emailAddress", userEmailAddress);
                    intent.putExtra("theatreNameWithAddress", theaterNameWithAddress);
                    intent.putExtra("showDate", selectedDate);
                    intent.putExtra("selectedTime", selectedTime);
                    intent.putExtra("category", radioSelectedText);
                    intent.putExtra("totalSeats", totalSeatCount);
                    intent.putExtra("selectedMeradian", selectedMeradian);
                    intent.putExtra("currentBackground", currentBackground)
                    categoryRadioGroup.clearCheck();
                    termConditionCheckbox.isChecked = false;
                    showMovieName.text = ""
                    username.setText("")
                    phoneNumber.setText("")
                    emailAddress.setText("")
                    theatreNameWithAddress.setText("")
                    bookingDate.setText("")
                    showTime.setText("")
                    seatCount.setText("")
                    startActivity(intent);
                }

                builder.setNeutralButton(R.string.cancel) { dialogInterface, which ->
                    Toast.makeText(applicationContext, R.string.paymentCanceldialog, Toast.LENGTH_LONG).show()
                    categoryRadioGroup.clearCheck();
                    termConditionCheckbox.isChecked = false;
                    showMovieName.text = ""
                    username.setText("")
                    phoneNumber.setText("")
                    emailAddress.setText("")
                    theatreNameWithAddress.setText("")
                    bookingDate.setText("")
                    showTime.setText("")
                    seatCount.setText("")
                    startActivity(intent);
                }

                builder.setNegativeButton(R.string.no) { dialogInterface, which ->
                    categoryRadioGroup.clearCheck();
                    termConditionCheckbox.isChecked = false;
                    showMovieName.text = ""
                    username.setText("")
                    phoneNumber.setText("")
                    emailAddress.setText("")
                    theatreNameWithAddress.setText("")
                    bookingDate.setText("")
                    showTime.setText("")
                    seatCount.setText("")
                    startActivity(intent);
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show();
            }
        }


    }

    override fun onBackPressed() {
        finish();
        return;
    }

    private fun getDateTimeCalendar() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayofMonth: Int) {
        savedday = dayofMonth;
        savedmonth = month;
        savedyear = year;
        bookingDate.setText("${savedday.toString()}-${savedmonth.toString()}-${savedyear.toString()}")

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, Minute: Int) {
        savedhour = hourOfDay;
        savedminute = Minute;
        showTime.setText("${savedhour.toString()}:${savedminute.toString()}")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        selectedMeradian = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        selectedMeradian = "";
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.optionGray -> {
                parentLayout.setBackgroundColor(resources.getColor(R.color.OptionGray));
                currentBackground = "Gray";
                true
            }
            R.id.optionlightBrown -> {
                parentLayout.setBackgroundColor(resources.getColor(R.color.OptionLightBrown));
                currentBackground = "LightBrown"
                true
            }
            R.id.optionwhite -> {
                parentLayout.setBackgroundColor(resources.getColor(R.color.OptionWhite))
                currentBackground = "White"
                true;
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}