package com.example.movieticketapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketapp.Activity.Notification.EditNotificationActivity;
import com.example.movieticketapp.Activity.Notification.NotificationDetailActivity;
import com.example.movieticketapp.Model.ExtraIntent;
import com.example.movieticketapp.Model.NotificationModel;
import com.example.movieticketapp.Model.Users;
import com.example.movieticketapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(Notifications.get(position).getHeading(),Notifications.get(position).getHeading());
        holder.Description.setText(Notifications.get(position).getDescription());
        holder.Heading.setText(Notifications.get(position).getHeading());
        holder.PostAuthor.setText("By : "+Notifications.get(position).getPostAuthor());
        Date date = Notifications.get(position).getPostTime().toDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH) + 1;
        int Minute = calendar.get(Calendar.MINUTE);
        int Hour = calendar.get(Calendar.HOUR);
        int Day = calendar.get(Calendar.DATE);
        String PostTimeString = String.valueOf(Hour)+":"+String.valueOf(Minute)+" - "+String.valueOf(Day)+"/"+String.valueOf(Month)+"/"+String.valueOf(Year);
        holder.PostTime.setText(PostTimeString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), NotificationDetailActivity.class);
                i.putExtra(ExtraIntent.notification, Notifications.get(position));
                holder.itemView.getContext().startActivity(i);
            }
        });
        //if user
        try{
            if(Users.currentUser!=null)
                if((!(Users.currentUser.getAccountType().toString()).equals("admin")))
                {
                   holder.PromoMenu.setVisibility(View.INVISIBLE);
                }
        }
        catch (Exception e)
        {
            holder.PromoMenu.setVisibility(View.INVISIBLE);
        }
        finally {
            holder.PromoMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), holder.PromoMenu);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.promo_menu);
                    SpannableString s = new SpannableString("Edit");
                    s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    SpannableString delete = new SpannableString("Delete");
                    delete.setSpan(new ForegroundColorSpan(Color.RED), 0, delete.length(), 0);
                    popup.getMenu().getItem(1).setTitle(delete);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.promo_edit:
                                {
                                    Intent i = new Intent(holder.itemView.getContext(), EditNotificationActivity.class);
                                    i.putExtra(ExtraIntent.notification, Notifications.get(position));
                                    holder.itemView.getContext().startActivity(i);
                                }
                                return true;
                                case R.id.promo_delete:
                                {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext(), R.style.CustomAlertDialog);
                                    LayoutInflater factory = LayoutInflater.from(holder.itemView.getContext());
                                    final View deleteDialogView = factory.inflate(R.layout.yes_no_dialog, null);
                                    alertDialog.setView(deleteDialogView);
                                    AlertDialog OptionDialog = alertDialog.create();
                                    OptionDialog.show();
                                    TextView Cancel = deleteDialogView.findViewById(R.id.Cancel_Button);
                                    TextView Delete = deleteDialogView.findViewById(R.id.DeleteButton);
                                    Delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            CollectionReference PromoRef = db.collection(NotificationModel.CollectionName);
                                            PromoRef.document(Notifications.get(position).getID()).delete();
                                            OptionDialog.dismiss();
                                        }
                                    });
                                    Cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            OptionDialog.dismiss();
                                        }
                                    });
                                }
                                return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
            });
        }
     }
    List<NotificationModel> Notifications = new ArrayList<NotificationModel>();


    public NotificationAdapter(List<NotificationModel> Notifications) {
        this.Notifications = Notifications;
    }

    @Override
    public int getItemCount() {
        return Notifications.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Heading;
        TextView Description;
        TextView PostAuthor;
        TextView PostTime;
        ImageView PromoMenu;

        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Heading = itemView.findViewById(R.id.Heading);
            Description = itemView.findViewById(R.id.Description);
            PromoMenu=itemView.findViewById(R.id.PromoMenu);
            PostAuthor= itemView.findViewById(R.id.PostAuthor);
            PostTime= itemView.findViewById(R.id.PostTime);
        }
    }
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.ViewHolder(itemView);
    }
}
