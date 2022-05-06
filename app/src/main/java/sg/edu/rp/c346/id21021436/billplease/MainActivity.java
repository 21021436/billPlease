package sg.edu.rp.c346.id21021436.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;



class multipliers {
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

        //linking field variables to UI components in layout

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


        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (service.isChecked()) multipliers.multiplyService = 1.1;
               else multipliers.multiplyService = 1;
            }
        });

        gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gst.isChecked()) multipliers.multiplyGST = 1.07;
                else multipliers.multiplyGST = 1;
            }
        });





        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String billString = billAmount.getText().toString();
                double billDouble = Double.parseDouble(billString);

                String paxString = numPax.getText().toString();
                double paxDouble = Double.parseDouble(paxString);

                String discountString = discount.getText().toString();
                if (!discountString.equals("")){
                    double discountDouble = Double.parseDouble(discountString);
                    multipliers.multiplyDiscount = (100 - discountDouble)/100;
                }
                else multipliers.multiplyDiscount = 1;

                double adjustedBill = multipliers.multiplyService*multipliers.multiplyGST*multipliers.multiplyDiscount*billDouble;
                String adjustedBillString = String.format("%.2f", adjustedBill);

                double eachPay = adjustedBill/paxDouble;
                String eachPayString = String.format("%.2f", eachPay);

                String payMethodString;

                int checkPaymentRadioId = payMethodGroup.getCheckedRadioButtonId();

                if (checkPaymentRadioId == R.id.payMethodCash) {
                    payMethodString = " in cash.";
                }
                else {
                    payMethodString = " via PayNow to 912345678.";
                }



                String outputString = "Total Bill: " + adjustedBillString + "\n" + "Each Pays: " + eachPayString + payMethodString;

                output.setText(outputString);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billAmount.getText().clear();
                numPax.getText().clear();
                discount.getText().clear();
                service.setChecked(false);
                gst.setChecked(false);
                cash.setChecked(false);
                payNow.setChecked(false);
                output.setText(null);
            }
        });




    }
}