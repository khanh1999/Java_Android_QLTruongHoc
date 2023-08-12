package hienthi.info.cuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainLopHoc extends AppCompatActivity {
    final String DATABASE_NAME = "QL_TruongHoc.sqlite";
    SQLiteDatabase database;

    ListView listViewLh;
    ArrayList<LopHoc> listlh;
    AdapterLopHoc adapterLopHoc;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lop_hoc);

        addControls();
        readData();
    }

    private void addControls() {
        listViewLh =(ListView) findViewById(R.id.listViewLH);
        listlh = new ArrayList<>();
        adapterLopHoc = new AdapterLopHoc(this, listlh);
        listViewLh.setAdapter(adapterLopHoc);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLopHoc.this,AddLH.class);
                startActivity(intent);
            }
        });
    }

    private void readData(){
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select * from LopHoc", null);
        listlh.clear();

        for (int i =0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String maL = cursor.getString(0);
            String tenL = cursor.getString(1);
            int siso = cursor.getInt(2);
            String gvcn = cursor.getString(3);
            String gvtoan = cursor.getString(4);
            String gvly = cursor.getString(5);
            String gvhoa = cursor.getString(6);
            byte[]anh = cursor.getBlob(7);
            listlh.add(new LopHoc(maL, tenL, siso, gvcn, gvtoan, gvly, gvhoa, anh));
        }
        adapterLopHoc.notifyDataSetChanged();

    }
}