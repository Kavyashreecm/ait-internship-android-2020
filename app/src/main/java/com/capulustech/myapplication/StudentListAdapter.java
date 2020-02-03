package com.capulustech.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>
{
    private final LayoutInflater mInflater;
    ArrayList<String> students;
    Context context;

    public StudentListAdapter(Context mContext, ArrayList<String> mStudents)
    {
        context = mContext;
        mInflater = LayoutInflater.from(context);
        students = mStudents;
    }


    class StudentViewHolder extends RecyclerView.ViewHolder
    {
        TextView studentNameTV;
        CardView cardView;

        public StudentViewHolder(@NonNull View itemView)
        {
            super(itemView);
            studentNameTV = itemView.findViewById(R.id.studentNameTV);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public StudentListAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.student_list_item,
                parent, false);
        return new StudentViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position)
    {
        holder.studentNameTV.setText(students.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(context, students.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return students.size();
    }
}
