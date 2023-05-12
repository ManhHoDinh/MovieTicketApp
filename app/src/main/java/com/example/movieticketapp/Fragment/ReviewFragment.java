package com.example.movieticketapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieticketapp.Adapter.CommentAdapter;
import com.example.movieticketapp.Model.Comment;
import com.example.movieticketapp.Model.FilmModel;
import com.example.movieticketapp.R;
import com.example.movieticketapp.databinding.FragmentReviewBinding;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReviewFragment extends Fragment {

    FilmModel film;
    public ReviewFragment( FilmModel f) {
        // Required empty public constructor
        film = f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView commentList = getView().findViewById(R.id.ReviewList);

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("https://scontent.fsgn5-2.fna.fbcdn.net/v/t39.30808-6/313415110_2142806752775286_5295907426187620423_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=Ma1M1LVYzJUAX9B_XwR&_nc_ht=scontent.fsgn5-2.fna&oh=00_AfADrJq-0QMPuhhObEPOHJWqOvmoQXDFQWyO3OpnEzhKNg&oe=64626949", "Duy Phạm Nhật Nguyễn", "Phim này hay vl", 100, 0));

        CommentAdapter commentAdapter = new CommentAdapter(getView().getContext(), R.layout.review_comment_view, comments);
        commentList.setAdapter(commentAdapter);
    }
}