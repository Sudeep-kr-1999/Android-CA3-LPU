package sudeep.example.ca3

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar


class PaymentActivity : AppCompatActivity() {
    lateinit var innerLayout: ConstraintLayout;
    lateinit var movieName: TextView;
    lateinit var userName: TextView;
    lateinit var phoneNumber: TextView;
    lateinit var emailAddress: TextView;
    lateinit var theatreNameWithAddress: TextView;
    lateinit var showDate: TextView;
    lateinit var showTime: TextView;
    lateinit var category: TextView;
    lateinit var totalSeats: TextView;
    lateinit var submitBtn: Button;
    lateinit var cancelBtn: Button;
    lateinit var movieNamefromIntent: String;
    lateinit var userNamefromIntent: String;
    lateinit var phoneNumberfromIntent: String;
    lateinit var emailAddressfromIntent: String;
    lateinit var theaterNameWithAddressfromIntent: String;
    lateinit var showDatefromIntent: String;
    lateinit var showTimefromIntent: String;
    lateinit var categoryfromIntent: String;
    lateinit var totalSeatsfromIntent: String;
    lateinit var selectedMeradianFromIntent: String;
    private var fixedRateGold = 100;
    private var fixedRateSilver = 150;
    private var fixedRateDimaond = 200;
    private var fixedRatePlatinum = 300;
    private var totalPriceAmount = 0;
    private lateinit var currentBackgroundFromIntent: String;

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        movieName = findViewById(R.id.textView9);
        userName = findViewById(R.id.textView18);
        phoneNumber = findViewById(R.id.textView19);
        emailAddress = findViewById(R.id.textView20);
        theatreNameWithAddress = findViewById(R.id.textView21);
        showDate = findViewById(R.id.textView22);
        showTime = findViewById(R.id.textView23);
        category = findViewById(R.id.textView24);
        totalSeats = findViewById(R.id.textView25);
        submitBtn = findViewById(R.id.button);
        cancelBtn = findViewById(R.id.button3);
        innerLayout = findViewById(R.id.innerConstraint2)
        var intentBundle: Bundle? = intent.extras;
        movieNamefromIntent = intentBundle?.get("movieName").toString();
        userNamefromIntent = intentBundle?.get("userName").toString();
        phoneNumberfromIntent = intentBundle?.get("phoneNumber").toString();
        emailAddressfromIntent = intentBundle?.get("emailAddress").toString();
        theaterNameWithAddressfromIntent = intentBundle?.get("theatreNameWithAddress").toString();
        showDatefromIntent = intentBundle?.get("showDate").toString();
        showTimefromIntent = intentBundle?.get("selectedTime").toString();
        categoryfromIntent = intentBundle?.get("category").toString();
        totalSeatsfromIntent = intentBundle?.get("totalSeats").toString();
        selectedMeradianFromIntent = intentBundle?.get("selectedMeradian").toString();
        currentBackgroundFromIntent = intentBundle?.getString("currentBackground").toString();
        userName.text = userNamefromIntent;
        phoneNumber.text = phoneNumberfromIntent;
        emailAddress.text = emailAddressfromIntent;
        theatreNameWithAddress.text = theaterNameWithAddressfromIntent;
        showDate.text = showDatefromIntent;
        showTime.text = "$showTimefromIntent $selectedMeradianFromIntent";
        category.text = categoryfromIntent;
        totalSeats.text = totalSeatsfromIntent;

        if (currentBackgroundFromIntent === "Gray") {
            innerLayout.setBackgroundColor(resources.getColor(R.color.OptionGray));

        } else if (currentBackgroundFromIntent === "White") {
            innerLayout.setBackgroundColor(resources.getColor(R.color.OptionWhite))
        } else if (currentBackgroundFromIntent == "LightBrown") {
            innerLayout.setBackgroundColor(resources.getColor(R.color.OptionLightBrown))
        }

        if (categoryfromIntent == "Gold") {
            totalPriceAmount = fixedRateGold * totalSeatsfromIntent.toInt();
        } else if (categoryfromIntent == "Silver") {
            totalPriceAmount = fixedRateSilver * totalSeatsfromIntent.toInt();
        } else if (categoryfromIntent == "Diamond") {
            totalPriceAmount = fixedRateDimaond * totalSeatsfromIntent.toInt();
        } else {
            totalPriceAmount = fixedRatePlatinum * totalSeatsfromIntent.toInt();
        }
        submitBtn.setText("$totalPriceAmount");
        submitBtn.setOnClickListener {
            val popup = PopupMenu(this, submitBtn);
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu);
            popup.setOnMenuItemClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.paymentConfirmationrefcode).setMessage("${userNamefromIntent.replace(' ', '0')}${totalSeatsfromIntent}${totalPriceAmount}").setPositiveButton(R.string.paymentConfirmationOK) { dialog, which ->
                    goToHomePage();
                }.setCancelable(false).show()

                true;
            }
            popup.show()
        }
        cancelBtn.setOnClickListener {
            goToHomePage()
        }
    }

    private fun goToHomePage() {
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent)
    }
}