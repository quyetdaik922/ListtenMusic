package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.PlayVideoActivity;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHat> arrbaihat;

    public VideosAdapter(Context context, ArrayList<BaiHat> arrbaihat) {
        this.context = context;
        this.arrbaihat = arrbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_videos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat=arrbaihat.get(position);
        holder.tTenbaihatvideo.setText(baiHat.getTenBaiHat());
        holder.tTenCasivideo.setText(baiHat.getCaSi());
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imVideo);
    }

    @Override
    public int getItemCount() {
        return arrbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView imVideo,imYeuThichVideo;
TextView tTenbaihatvideo,tTenCasivideo;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imVideo=(ImageView)itemView.findViewById(R.id.imVideo) ;
        imYeuThichVideo=(ImageView)itemView.findViewById(R.id.imyeuthichvideo) ;
        tTenbaihatvideo=(TextView) itemView.findViewById(R.id.tTenBaiHatVideo) ;
        tTenCasivideo=(TextView) itemView.findViewById(R.id.tTenCaSiVideo) ;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PlayVideoActivity.class);
                intent.putExtra("videos",arrbaihat.get(getPosition()));
                context.startActivity(intent);
            }
        });
        imYeuThichVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imYeuThichVideo.setImageResource(R.drawable.icon_love_true);
                Dataservice dataservice= APIService.getService();
                Call<String> callback=dataservice.UpdateLuotThich("1",arrbaihat.get(getPosition()).getIDBaiHat());
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String kq=response.body();
                        if(kq.equals("ok")){
                            Toast.makeText(context,"Đã thích",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Bị lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                imYeuThichVideo.setEnabled(false);
            }
        });
    }
}
}
