package lv.rtu.dip701.gifsercher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GifAdapter(private var gifs: List<Gif>) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(gifs[position])
    }

    override fun getItemCount() = gifs.size

    fun updateGifs(newGifs: List<Gif>) {
        gifs = newGifs
        notifyDataSetChanged()
    }

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.gifImageView)

        fun bind(gif: Gif) {
            Glide.with(itemView.context)
                .asGif()
                .load(gif.images.original.url)
                .into(imageView)
        }
    }
}
