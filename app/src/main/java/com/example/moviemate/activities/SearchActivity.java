package com.example.moviemate.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.moviemate.R;
import com.example.moviemate.adapters.MovieSearchAdapter;
import com.example.moviemate.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private EditText searchMovies;
    private RecyclerView searchResultRecycler;
    private MovieSearchAdapter searchResultAdapter;
    private List<Movie> filteredMovies;
    private List<Movie> allMoviesList;
    private ImageButton clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Nhận danh sách phim từ Intent
        allMoviesList = (List<Movie>) getIntent().getSerializableExtra("allMovies");

        // Khởi tạo view
        backBtn = findViewById(R.id.BackBtn);
        searchMovies = findViewById(R.id.search_movies);
        clearButton = findViewById(R.id.clear_button);
        searchResultRecycler = findViewById(R.id.search_result_recycler);

        // Thiết lập RecyclerView
        searchResultRecycler.setLayoutManager(new LinearLayoutManager(this));
        filteredMovies = new ArrayList<>();
        searchResultAdapter = new MovieSearchAdapter(this, filteredMovies);
        searchResultRecycler.setAdapter(searchResultAdapter);


        backBtn.setOnClickListener(v -> finish());
        // Bắt sự kiện thay đổi văn bản trong ô tìm kiếm
        searchMovies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovies.setText(""); // Xoá nội dung trong EditText
                filteredMovies.clear();   // Xoá danh sách kết quả tìm kiếm
                searchResultAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            }
        });

    }

    private void filterMovies(String query) {
        filteredMovies.clear();
        for (Movie movie : allMoviesList) {
            String genres = String.join(", ", movie.getGenre()); // Chuyển đổi List<String> thành String
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    genres.toLowerCase().contains(query.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        searchResultAdapter.notifyDataSetChanged();
    }
}