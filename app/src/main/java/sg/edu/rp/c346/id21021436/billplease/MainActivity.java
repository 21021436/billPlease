package sg.edu.rp.c346.id21021436.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

//Define a class Multipliers that contains public static variables called multipliers for each of service charge, gst and discount. Default is 1.
class Multipliers {
    public static double multiplyService = 1;
    public static double multiplyGST = 1;
    public static double multiplyDiscount = 1;
}
public class MainActivity extends AppCompatActivity {

    //Declare field variables
    EditText billAmount;
    EditText numPax;
    ToggleButton service;
    ToggleButton gst;
    EditText discount;
    RadioGroup payMethodGroup;
    RadioButton cash;
    RadioButton payNow;
    Button split;
    Button reset;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link field variables to UI components in layout
        billAmount = findViewById(R.id.amount);
        numPax = findViewById(R.id.numPax);
        service = findViewById(R.id.btnSvs);
        gst = findViewById(R.id.btnGST);
        discount = findViewById(R.id.disc);
        payMethodGroup = findViewById(R.id.payMethodGroup);
        cash = findViewById(R.id.payMethodCash);
        payNow = findViewById(R.id.payMethodPN);
        split = findViewById(R.id.split);
        reset = findViewById(R.id.reset);
        output = findViewById(R.id.output);

        //Check Cash radio button by default
        payMethodGroup.check(R.id.payMethodCash);

        //Service charge toggle button handling
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (service.isChecked()) Multipliers.multiplyService = 1.1;
               else Multipliers.multiplyService = 1;
            }
        });

        gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If GST toggle is checked (on), GST multiplier is 1.07 (7%)
                if (gst.isChecked()) Multipliers.multiplyGST = 1.07;
                else Multipliers.multiplyGST = 1;
            }
        });

        //Calculate/Split button handling which will handle the main part of the calculation and output
        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String trimming may not be strictly necessary, but is a good practice
                String billString = billAmount.getText().toString().trim();
                //Check for no entry or zero entry in Amount EditText field. The order of the conditions is important to prevent crashing.
                if (TextUtils.isEmpty(billString) || (Double.parseDouble(billString) == 0)){
                    //The message will be a short toast notification. The return statement breaks out of the if-condition and returns control to the main program flow.
                    Toast.makeText(MainActivity.this, "Bill amount cannot be empty or zero!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Interpret the Bill Amount as a double variable (to allow dollars and cents)
                double billDouble = Double.parseDouble(billString.trim());

                String paxString = numPax.getText().toString().trim();
                if (TextUtils.isEmpty(paxString)|| (Integer.parseInt(paxString) == 0)){
                    Toast.makeText(MainActivity.this, "Number of diners cannot be empty or zero!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Interpret the Number of Pax (diners) as an integer
                int paxInt = Integer.parseInt(paxString);

                String discountString = discount.getText().toString().trim();
                if (!discountString.equals("")){
                    //Interpret the discount field string as a double (only if not empty). If left empty, assume no discount (full price)
                    double discountDouble = Double.parseDouble(discountString);
                    //The discount multiplier will be calculated based on the discount
                    Multipliers.multiplyDiscount = (100 - discountDouble)/100;
                }
                else Multipliers.multiplyDiscount = 1;

                //Calculate the adjusted bill by serially multiplying the bill by all the multipliers to take into account service charge, GST and discount
                double adjustedBill = Multipliers.multiplyService* Multipliers.multiplyGST* Multipliers.multiplyDiscount*billDouble;
                //Format adjusted bill string to 2 decimal places to allow for dollars and cents
                String adjustedBillString = String.format("%.2f", adjustedBill);

                //Amount each diner pays. Division between double and int is fine as long as output is stored as double.
                double eachPay = adjustedBill/paxInt;
                String eachPayString = String.format("%.2f", eachPay);

                //Initialise a String variable to handle the output text
                String payMethodString;

                //Determine which payment method was selected via radio button and handle the cases
                int checkPaymentRadioId = payMethodGroup.getCheckedRadioButtonId();

                if (checkPaymentRadioId == R.id.payMethodPN) {
                    payMethodString = " via PayNow to 91234567.";
                }
                else {
                    payMethodString = " in cash.";
                }

                //String concatenation to finalise output String and send to output
                String outputString = "Total Bill: $" + adjustedBillString + "\n" + "Each Pays: $" + eachPayString + payMethodString;
                output.setText(outputString);
            }
        });

        //Reset button handling. Restore all fields to default values
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billAmount.getText().clear();
                numPax.getText().clear();
                discount.getText().clear();
                service.setChecked(false);
                gst.setChecked(false);
                cash.setChecked(true);
                payNow.setChecked(false);
                output.setText(null);
            }
        });




    }
}