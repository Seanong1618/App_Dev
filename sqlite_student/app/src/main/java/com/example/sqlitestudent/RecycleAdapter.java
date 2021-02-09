package com.example.sqlitestudent;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.StudentViewer> {

    List<POJO> studentDeltails;
    Context context;
    OpenHelper dbhelper;
    SQLiteDatabase sqLiteDatabase;

    public RecycleAdapter(List<POJO> studentDeltails) {
        this.studentDeltails = studentDeltails;
    }

    @NonNull
    @Override
    public StudentViewer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View iteView = inflater.inflate(R.layout.student_item, parent, false);
        StudentViewer viewHolder = new StudentViewer(iteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.StudentViewer holder, final int position) {
        final POJO pojo = studentDeltails.get(position);

        holder.pID.setText("ID: " + pojo.getP_id());
        holder.pIdno.setText("ID number: " + pojo.getP_idno());
        holder.pName.setText("Name: " + pojo.getP_name());
        holder.pAddress.setText("Address: " + pojo.getP_address());
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pId = pojo.getP_id();
                dbhelper = new OpenHelper(context);
                sqLiteDatabase = dbhelper.getWritableDatabase();
                sqLiteDatabase.delete(DatabaseInfo.TABLE_NAME, DatabaseInfo._ID+ " = " + pId,null);
                notifyItemRangeChanged(position,studentDeltails.size());
                studentDeltails.remove(position);
                notifyItemRemoved(position);
                sqLiteDatabase.close();
            }
        });

        holder.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, update_student.class);
                intent.putExtra("stuId", pojo.getP_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentDeltails.size();
    }

    public class StudentViewer extends RecyclerView.ViewHolder {
        TextView pID;
        TextView pIdno;
        TextView pName;
        TextView pAddress;
        TextView txtDelete;
        TextView txtUpdate;

        public StudentViewer(View itemView) {
            super(itemView);
            pID = itemView.findViewById(R.id.txtid);
            pIdno = itemView.findViewById(R.id.txtidno);
            pName = itemView.findViewById(R.id.txtname);
            pAddress = itemView.findViewById(R.id.txtaddress);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtUpdate = itemView.findViewById(R.id.txtUpdate);
        }
    }
    public void deleteStudent() {

    }
}
