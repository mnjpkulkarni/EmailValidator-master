package mnjpk.validator.email.app.emailvalidator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends Activity {

    Button validateButton;
    String emailId;
    ArrayList<String> domainArray;
    AutoCompleteTextView autoCompleteTextView;
    EmailData emailData;
    TextView validationTextView, validateDomainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validateButton=(Button) findViewById(R.id.validate_button);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoEmail_textView);
        validationTextView = (TextView) findViewById(R.id.validation_textView);
        validateDomainTextView = (TextView) findViewById(R.id.validateDomain_textView);


        autoTextComplete();

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String baseUrl="https://api.kickbox.com/v2/verify?email="+emailId+"&apikey=live_f50cd73b421292492dcdb688677c8f024b4bbd968998da518f217cf4877b6f90";
                RestTask restTask = new RestTask();
                    restTask.execute(baseUrl);
            }
        });

    }


    //Autocomplete Operations
    public void autoTextComplete(){

        domainArray = new ArrayList<String>();

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().contains("@")){

                    domainArray.clear();
                    //Seperating the name from the entered string
                    String name;
                    String[] tokens = charSequence.toString().split("@");
                    name = tokens[0];

                    domainArray.add(name+"@gmail.com");
                    domainArray.add(name+"@yahoo.com");
                    domainArray.add(name+"@outlook.com");
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, domainArray);
                    autoCompleteTextView.setAdapter(stringArrayAdapter);
                    emailId = autoCompleteTextView.getText().toString();
                }
                if(charSequence.toString().contains("@g")){

                    domainArray.clear();
                    //Seperating the name from the entered string
                    String name;
                    String[] tokens = charSequence.toString().split("@");
                    name = tokens[0];

                    domainArray.clear();
                    domainArray.add(name+"@gmail.com");
                    domainArray.add(name+"@gmail.co.uk");
                    domainArray.add(name+"@gmail.co.in");
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, domainArray);
                    autoCompleteTextView.setAdapter(stringArrayAdapter);
                    emailId = autoCompleteTextView.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public class RestTask extends AsyncTask<String,Void,String> {

        String urlToServer;

        @Override
        protected String doInBackground(String... strings) {

            urlToServer = strings[0];
            String resultString="";
            String inputLine;

            try{

                //Making HTTP Request
                URL url = new URL(urlToServer);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("header-param_3", "value-3");
                connection.setRequestProperty("header-param_4", "value-4");
                connection.setRequestMethod("GET");
                connection.connect();

                // Checking the connection status.
                int status = connection.getResponseCode();

                if (status == 200) {
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }
                    resultString = stringBuilder.toString();

                    //Parsing the obtained JSON

                    JSONObject jsonObject = new JSONObject(resultString);
                    String result = jsonObject.getString("result");
                    String reason = jsonObject.getString("reason");
                    String validEmail = jsonObject.getString("did_you_mean");
                    String email = jsonObject.getString("email");
                    String user = jsonObject.getString("user");
                    String domain = jsonObject.getString("domain");

                    emailData = new EmailData(result, reason, validEmail, email, user, domain);
                    Log.d("Details", "doInBackground: "+resultString);


                } else {
                    Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_SHORT).show();
                    System.out.println("Error in Connection");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return resultString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Setting the text views
            String validationString = emailData.getResult().toString();
            validationTextView.setText(validationString);

            String validateDomainString = emailData.getReason().toString();
            validateDomainTextView.setText(validateDomainString);
        }
    }


}
