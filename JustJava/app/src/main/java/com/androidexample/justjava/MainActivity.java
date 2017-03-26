/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.androidexample.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {

        if(quantity==100){
            Toast.makeText(this,"You cannot order more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        displayQuantity(quantity);

          }

    public void decrement(View view) {

        if(quantity==1){
            Toast.makeText(this,"You cannot order less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);


    }

    /**
     * This method is called when the order button is clicked.
     *
     * @return total price
     */

    public void submitOrder(View view) {
        EditText nameField=(EditText)findViewById(R.id.name_Field);
        String name=nameField.getText().toString();
        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_check_box);
        Boolean hasWhippedCream= whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_check_box);
        Boolean hasChocolate= chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate,name);
        /**
         *@return total price
         */

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject,name));
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate) {

        int basePrice=5;
        if(hasWhippedCream==true)
            basePrice+=1;
        if(hasChocolate==true)
            basePrice+=2;
        return basePrice*quantity;

    }

    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate, String name) {
            String priceMessage = getString(R.string.order_summary_name,name) + getString(R.string.order_summary_whipped_cream,addChocolate) + getString(R.string.order_summary_chocolate,addChocolate) + getString(R.string.order_summary_quantity,quantity)+ getString(R.string.order_summary_price,price);
            priceMessage = priceMessage + "\n"+getString(R.string.thank_you);
            return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffes) {
         TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffes);
    }
}

