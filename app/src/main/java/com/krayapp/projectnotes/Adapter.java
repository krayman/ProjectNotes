package com.krayapp.projectnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krayapp.projectnotes.data.NoteInfo;
import com.krayapp.projectnotes.data.NoteSource;

import java.text.SimpleDateFormat;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private NoteSource dataSource;
    private MyClickListener myClickListener;
    private final OnRegisterMenu fragment;

    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    public Adapter( OnRegisterMenu fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(NoteSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(MyClickListener itemClickListener) {
        myClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.frag_list_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(dataSource.getNoteInfo(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, NoteInfo note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleList);
            description = itemView.findViewById(R.id.descriptionList);
            date = itemView.findViewById(R.id.dateList);

            registerContextMenu(itemView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                myClickListener.onItemClick(position, dataSource.getNoteInfo(position));
            });
        }

        private void registerContextMenu(View itemView) {
            if(fragment != null){
                itemView.setOnLongClickListener(v ->{
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu();
                    return true;
                });
                fragment.onRegister(itemView);
            }
        }

        private void onBind(NoteInfo noteInfo) {
            title.setText(noteInfo.getTitle());
            description.setText(noteInfo.getDescription());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(noteInfo.getDate()));

        }
    }
}

