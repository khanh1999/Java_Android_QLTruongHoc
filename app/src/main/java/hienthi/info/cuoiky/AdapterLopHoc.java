package hienthi.info.cuoiky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterLopHoc extends BaseAdapter{
    Activity context;
    ArrayList<LopHoc> listlh;

    public AdapterLopHoc(Activity context, ArrayList<LopHoc> listlh) {
        this.context = context;
        this.listlh = listlh;
    }

    @Override
    public int getCount() {
        return listlh.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.listview_row_lh, null);

        TextView txtmaL = (TextView) row.findViewById(R.id.txtmaL);
        TextView txttenL = (TextView) row.findViewById(R.id.txttenL);
        TextView txtsiso = (TextView) row.findViewById(R.id.txtsiso);
        TextView txtgvcn = (TextView) row.findViewById(R.id.txtgvcn);
        TextView txtgvtoan = (TextView) row.findViewById(R.id.txtgvtoan);
        TextView txtgvly = (TextView) row.findViewById(R.id.txtgvly);
        TextView txtgvhoa = (TextView) row.findViewById(R.id.txtgvhoa);
        ImageView imgAva = (ImageView) row.findViewById(R.id.imgAva);

        Button btnSua = (Button) row.findViewById(R.id.btnSua);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        LopHoc lopHoc = listlh.get(i);
        txttenL.setText(lopHoc.tenL);
        txtsiso.setText(lopHoc.siso +"");
        txtgvcn.setText(lopHoc.gvcn);
        txtgvtoan.setText(lopHoc.gvtoan);
        txtgvly.setText(lopHoc.gvly);
        txtgvhoa.setText(lopHoc.gvhoa);
        txtmaL.setText(lopHoc.maL);

        Bitmap bmAva = BitmapFactory.decodeByteArray(lopHoc.anhL, 0, lopHoc.anhL.length);
        imgAva.setImageBitmap(bmAva);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, updateLH.class);
                intent.putExtra("MaL",lopHoc.maL);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(lopHoc.maL);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(String mal) {
        SQLiteDatabase database = Database.initDatabase(context,"QL_TruongHoc.sqlite");
        database.delete("LopHoc","MaL = ?", new String[] {mal +""});

        listlh.clear();

        Cursor cursor = database.rawQuery("Select * from LopHoc", null);
        while (cursor.moveToNext()){
            String maL = cursor.getString(0);
            String tenL = cursor.getString(1);
            int siso = cursor.getInt(2);
            String gvcn = cursor.getString(3);
            String gvtoan = cursor.getString(4);
            String gvly = cursor.getString(5);
            String gvhoa = cursor.getString(6);
            byte[]anhL = cursor.getBlob(7);

            listlh.add(new LopHoc(maL, tenL, siso, gvcn, gvtoan, gvly ,gvhoa, anhL));
        }
        notifyDataSetChanged();
    }
}
