/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

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

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name=nameField .getText().toString();
        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox chocolateCreamCheckBox =(CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolateCream=chocolateCreamCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolateCream);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolateCream);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent. setData(Uri.parse("mailto:"));//only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }
    /**
     * calculate the price of the order
     *
     * @param addWhippedCream is whether or not user wants whipped cream or not
     *  @param addChocolate is whether or not user wants whipped cream or not
     *@return price of order
     */

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate) {
        //price of 1 cup of coffee
        int basePrice = 5;
        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        //Add $2 if the user wants chocolate
        if(addChocolate){
            basePrice = basePrice + 2;
        }
        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     *create summary of the order
     *
     * @param price of the order
     * @param addWhippedCream is whether or not user wants whipped cream topping
     * @param addChocolateCream is whether or not user wants chocolate cream topping
     * @return text summary
     */
   private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolateCream){
       String priceMessage =getString(R.string.order_summary_name, name) ;
       priceMessage +=  "\nAdd whipped cream?" + addWhippedCream;
       priceMessage +=  "\nAdd chocolate cream?" + addChocolateCream;
       priceMessage +=  "\nQuantity :" + quantity;
       priceMessage +=  "\nTotal :" + price ;
       priceMessage += "\n" + getString(R.string.thank_you);
       priceMessage += "\nyou were served by charity";
       priceMessage += "\nwelcome back!";

    return priceMessage ;
   }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this, "you cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            //Exit this method early because ther is nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(this, "you cannot have less than 1 coffees",Toast.LENGTH_SHORT).show();
            //Exit this method early because ther is nothing left to do
            display (quantity);
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }
}