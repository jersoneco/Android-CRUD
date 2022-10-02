package com.sven.crudapp;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    LinearLayout linearLayout;
    EditText name, section, course, year;
    TextView Name, Section, Course, Year, modalHeader;
    TableRow tableRow;
    TableLayout tableLayout;
    Button buttonUpdate, buttonCreate, buttonDelete;
    Spinner spinner;
    StringBuilder stringName = new StringBuilder(),
            stringSection = new StringBuilder(),
            stringCourse = new StringBuilder(),
            stringYear = new StringBuilder();
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = findViewById(R.id.modal);
        name = findViewById(R.id.student_name);
        section = findViewById(R.id.student_section);
        course = findViewById(R.id.student_course);
        year = findViewById(R.id.student_year);
        tableLayout = findViewById(R.id.tableMain);
        buttonUpdate = findViewById(R.id.buttonForUpdate);
        buttonCreate = findViewById(R.id.buttonForCreate);
        buttonDelete = findViewById(R.id.buttonForDelete);
        modalHeader = findViewById(R.id.modalHeader);
        spinner = findViewById(R.id.spinnerOption);
        databaseHelper = new DatabaseHelper(this);

    }

    private void emptyEdittext(){
        name.setText("");
        section.setText("");
        course.setText("");
        year.setText("");
    }

    private void emptyString(){
        stringName.delete(0, -1);
        stringYear.delete(0, -1);
        stringCourse.delete(0, -1);
        stringSection.delete(0, -1);
    }

    public void Read(View view){
        Cursor cursor = databaseHelper.readData();
        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No Data. ", Toast.LENGTH_LONG).show();
            return;
        }

        int childCount = tableLayout.getChildCount();
        if (childCount > 1)
            tableLayout.removeViews(1, childCount - 1);

        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()){
            buffer.append(cursor.getString(1)).append("\n");
            buffer.append(cursor.getString(2)).append("\n");
            buffer.append(cursor.getString(3)).append("\n");
            buffer.append(cursor.getString(4)).append("\n");
        }

        String data = buffer.toString();
        String[] sliceData = data.split("\n");

        for (int i=0;i<sliceData.length;i++){
            TableRow.LayoutParams param = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f);
            tableRow = new TableRow(this);
            Name = new TextView(this);
            Name.setTextColor(Color.BLACK);
            Name.setGravity(Gravity.CENTER);
            Name.setLayoutParams(param);
            Section = new TextView(this);
            Section.setTextColor(Color.BLACK);
            Section.setGravity(Gravity.CENTER);
            Section.setLayoutParams(param);
            Course = new TextView(this);
            Course.setTextColor(Color.BLACK);
            Course.setGravity(Gravity.CENTER);
            Course.setLayoutParams(param);
            Year = new TextView(this);
            Year.setTextColor(Color.BLACK);
            Year.setGravity(Gravity.CENTER);
            Year.setLayoutParams(param);

            Name.setText(sliceData[i]);
            Section.setText(sliceData[i+1]);
            Course.setText(sliceData[i+2]);
            Year.setText(sliceData[i+3]);

            tableRow.addView(Name);
            tableRow.addView(Section);
            tableRow.addView(Course);
            tableRow.addView(Year);
            tableLayout.addView(tableRow);
            i+=3;
        }
        databaseHelper.close();
    }

    @SuppressLint("SetTextI18n")
    public void Create(View view){
        modalHeader.setText("Create Student Data");
        buttonUpdate.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        name.setVisibility(View.VISIBLE);
        buttonCreate.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }
    @SuppressLint("SetTextI18n")
    public void Update(View view){
        Cursor cursor = databaseHelper.readData();
        emptyEdittext();
        emptyString();
        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No Data. ", Toast.LENGTH_LONG).show();
            return;
        }
        while (cursor.moveToNext()){
            stringName.append(cursor.getString(1)).append("\n");
            stringSection.append(cursor.getString(2)).append("\n");
            stringCourse.append(cursor.getString(3)).append("\n");
            stringYear.append(cursor.getString(4)).append("\n");
        }
        String dataName = stringName.toString();
        String[] sliceDataName = dataName.split("\n");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sliceDataName);
        spinner.setAdapter(adapter);

        modalHeader.setText("Update Student Data");
        buttonCreate.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.VISIBLE);
        name.setVisibility(View.INVISIBLE);
        buttonUpdate.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num = i;
                String dataName = stringName.toString();
                String dataSection = stringSection.toString();
                String dataCourse = stringCourse.toString();
                String dataYear = stringYear.toString();
                String[] sliceDataName = dataName.split("\n");
                String[] sliceDataSection = dataSection.split("\n");
                String[] sliceDataCourse = dataCourse.split("\n");
                String[] sliceDataYear = dataYear.split("\n");
                for (int x=0;x<sliceDataName.length;x++){
                    if (sliceDataName[i].equals(sliceDataName[x])){
                        section.setText(sliceDataSection[x]);
                        course.setText(sliceDataCourse[x]);
                        year.setText(sliceDataYear[x]);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void Delete(View view){
        modalHeader.setText("Delete Student Data");
        buttonCreate.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        name.setVisibility(View.INVISIBLE);
        buttonUpdate.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);

        emptyEdittext();
        emptyString();
        Cursor cursor = databaseHelper.readData();
        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No Data. ", Toast.LENGTH_LONG).show();
            return;
        }
        while (cursor.moveToNext()){
            stringName.append(cursor.getString(1)).append("\n");
            stringSection.append(cursor.getString(2)).append("\n");
            stringCourse.append(cursor.getString(3)).append("\n");
            stringYear.append(cursor.getString(4)).append("\n");
        }
        String dataName = stringName.toString();
        String[] sliceDataName = dataName.split("\n");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sliceDataName);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                num = i;
                String dataName = stringName.toString();
                String dataSection = stringSection.toString();
                String dataCourse = stringCourse.toString();
                String dataYear = stringYear.toString();
                String[] sliceDataName = dataName.split("\n");
                String[] sliceDataSection = dataSection.split("\n");
                String[] sliceDataCourse = dataCourse.split("\n");
                String[] sliceDataYear = dataYear.split("\n");
                for (int x=0;x<sliceDataName.length;x++){
                    if (sliceDataName[i].equals(sliceDataName[x])){
                        section.setText(sliceDataSection[x]);
                        course.setText(sliceDataCourse[x]);
                        year.setText(sliceDataYear[x]);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Cancel(View view){
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void createConfirm(View view){
        String studentName = name.getText().toString();
        String studentSection = section.getText().toString();
        String studentCourse = course.getText().toString();
        String studentYear = year.getText().toString();
        if (studentCourse.isEmpty()&&studentSection.isEmpty()&&studentName.isEmpty()&&studentYear.isEmpty()){
            Toast.makeText(MainActivity.this, "Please fill the blank!", Toast.LENGTH_LONG).show();
        }else {
            boolean check = databaseHelper.createData(studentName, studentSection, studentCourse, studentYear);
            if (check){
                Toast.makeText(MainActivity.this, "Creating Successfully!", Toast.LENGTH_LONG).show();
                emptyEdittext();
                linearLayout.setVisibility(View.INVISIBLE);
            } else
                Toast.makeText(MainActivity.this, "Creating Abort!", Toast.LENGTH_LONG).show();
        }
        databaseHelper.close();
    }

    public void updateConfirm(View view){
        String dataName = stringName.toString();
        String[] sliceDataName = dataName.split("\n");
        String studentName = sliceDataName[num];
        String studentSection = section.getText().toString();
        String studentCourse = course.getText().toString();
        String studentYear = year.getText().toString();
        if (studentCourse.isEmpty()&&studentSection.isEmpty()&&studentName.isEmpty()&&studentYear.isEmpty()){
            Toast.makeText(MainActivity.this, "Please fill the blank!", Toast.LENGTH_LONG).show();
        }else {
            boolean check = databaseHelper.updateData(studentName, studentSection, studentCourse, studentYear);
            if (check){
                Toast.makeText(MainActivity.this, "Updated Successfully!", Toast.LENGTH_LONG).show();
                emptyEdittext();
                linearLayout.setVisibility(View.INVISIBLE);
            } else
                Toast.makeText(MainActivity.this, "Updated Abort!", Toast.LENGTH_LONG).show();
        }
        databaseHelper.close();
    }

    public void deleteConfirm(View view){
        String dataName = stringName.toString();
        String[] sliceDataName = dataName.split("\n");
        String studentName = sliceDataName[num];
        boolean check = databaseHelper.deleteData(studentName);
        if (check){
            Toast.makeText(MainActivity.this, "Delete Successfully!", Toast.LENGTH_LONG).show();
            emptyEdittext();
            linearLayout.setVisibility(View.INVISIBLE);
        } else
            Toast.makeText(MainActivity.this, "Delete Abort!", Toast.LENGTH_LONG).show();

        databaseHelper.close();
    }


}