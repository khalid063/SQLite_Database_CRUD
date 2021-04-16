package com.example.sqlitdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;
    private EditText editText_name, editText_age, editText_gander, editText_id;
    private Button button_add_data, button_show_data, button_updata_data,button_delete_data;
    private TextView textView_show_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);  // ???
        SQLiteDatabase sqLiteDatabase =  myDatabaseHelper.getWritableDatabase();   // ????

        editText_name = findViewById(R.id.et_name);
        editText_age = findViewById(R.id.et_age);
        editText_gander = findViewById(R.id.et_gander);
        editText_id = findViewById(R.id.et_ID);

        button_add_data = findViewById(R.id.but_add_data);
        button_show_data = findViewById(R.id.but_show_data);
        button_updata_data = findViewById(R.id.but_update_data);
        button_delete_data = findViewById(R.id.but_delete_data);

        textView_show_data = findViewById(R.id.tv_show_data);

        button_add_data.setOnClickListener(this);
        button_show_data.setOnClickListener(this);
        button_updata_data.setOnClickListener(this);
        button_delete_data.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String name = editText_name.getText().toString();
        String age = editText_age.getText().toString();
        String gander = editText_gander.getText().toString();
        String id = editText_id.getText().toString();

        if (v.getId() == R.id.but_add_data){

            editText_name.setText("");
            editText_age.setText("");
            editText_gander.setText("");

            long rowId = myDatabaseHelper.insertData(name,age,gander);
            if (rowId == -1){
                Toast.makeText(getApplicationContext(), "Un Successfull", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Row "+rowId+" is successfully inserted", Toast.LENGTH_SHORT).show();
            }
        }

        else if (v.getId() == R.id.but_show_data){
            Toast.makeText(this, "show data is clicked", Toast.LENGTH_SHORT).show();
            Cursor cursor = myDatabaseHelper.displayAllData();
            if (cursor.getCount() == 0){
                //showData("Error","No Datta Found");
                Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()){
//                stringBuffer.append("ID : "+cursor.getString(0));
//                stringBuffer.append("Name : "+cursor.getString(1));
//                stringBuffer.append("Age : "+cursor.getString(2));
//                stringBuffer.append("Gender : "+cursor.getString(3));

                textView_show_data.setText(stringBuffer.append("ID : "+cursor.getString(0)+"\n"));
                textView_show_data.setText(stringBuffer.append("Name : "+cursor.getString(1)+"\n"));
                textView_show_data.setText(stringBuffer.append("Age : "+cursor.getString(2)+"\n"));
                textView_show_data.setText(stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n\n"));
            }
            //showData("resultSet",stringBuffer.toString());        // data show with diolog box
        }

        else  if (v.getId() == R.id.but_update_data){
            Toast.makeText(this, "updat but is clicked", Toast.LENGTH_SHORT).show();
            Boolean isUpdated = myDatabaseHelper.updataData(id,name,age,gander);
            if (isUpdated == true){
                Toast.makeText(this, "Data is UpDated", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Data is Not UpDated", Toast.LENGTH_SHORT).show();
            }
        }

        else if (v.getId() == R.id.but_delete_data){
            Toast.makeText(this, "deleat bat is clicked", Toast.LENGTH_SHORT).show();
            int valure = myDatabaseHelper.deleteData(id);
            if (valure > 0){
                Toast.makeText(this, "Data is delete", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Data is not delete", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void showData(String title, String massage){                         // data show with diolog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(massage);
        builder.setCancelable(true);
        builder.show();
    }
}