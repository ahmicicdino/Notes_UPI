package com.example.notes_upi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_upi.Models.Notes;
import com.example.notes_upi.NotesClickListener;
import com.example.notes_upi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{

    Context context;                               // Deklariranje okoline listenera
    List<Notes> list;                              // Deklariranje liste svih bilješki
    NotesClickListener listener;                   // Deklrariranje listener-a

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {  //Konstruktor za listener-a
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {       //Ova metoda bind-a sve objekte unutar notes_lista
        holder.textView_title.setText(list.get(position).getTitle());               //dohvaćanje naslova
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(list.get(position).getNotes());       // dohvaćanje teksta

        holder.textView_date.setText(list.get(position).getDate());         //dohvaćanje datuma bilješke
        holder.textView_date.setSelected(true);

        if(list.get(position).isPinned()){                      // dohvaćanje stanja prikvačenja
            holder.imageView_pin.setImageResource(R.drawable.pin);
        }else{
            holder.imageView_pin.setImageResource(0);
        }
        int color_code = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container;                                   //Objekt notes_container iz notes_list datoteke
    TextView textView_title;                                    //Objekt naslova bilješke iz notes_list datoteke
    TextView textView_notes;                                    //Objekt tekst bilješke iz notes_list datoteke
    TextView textView_date;                                     //Objekt datum bilješke iz notes_list datoteke
    ImageView imageView_pin;                                    //Objekt pinned iz notes_list datoteke (ne koristi se)

    public NotesViewHolder(@NonNull View itemView) {            //inicijalizacija svih objekata iz notes_list
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);      //inicijalizacija kontzejnera
        textView_title = itemView.findViewById(R.id.textView_title);        //inicijalizacija naslova
        textView_notes = itemView.findViewById(R.id.textView_notes);        //inicijalizacija teksta
        textView_date = itemView.findViewById(R.id.textView_date);          //inicijalizacija datuma
        imageView_pin = itemView.findViewById(R.id.imageView_pin);          //inicijalizacija prikvačenja
    }
}
