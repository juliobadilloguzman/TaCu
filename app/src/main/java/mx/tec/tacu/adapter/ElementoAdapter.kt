package mx.tec.tacu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.tec.tacu.R
import mx.tec.tacu.model.Taqueria
import org.w3c.dom.Text

class ElementoAdapter(var list: ArrayList<Taqueria>): RecyclerView.Adapter<ElementoAdapter.ViewHolder>() {

    var onItemClick: ((Taqueria) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v= LayoutInflater.from(parent.context).inflate(R.layout.template_list_taquerias,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }
        }

        fun bindItems(data:Taqueria){

            val nombre: TextView =itemView.findViewById(R.id.nameTaqueria)
            val calificacion: RatingBar =itemView.findViewById(R.id.ratingBarTaqueria)
            val image: ImageView =itemView.findViewById(R.id.imageViewTaqueria)
            val average: TextView = itemView.findViewById(R.id.textViewAverage)



            nombre.text=data.nombre
            average.text=data.calificacion.toString()
            calificacion.rating=data.calificacion.toFloat()
            Picasso.get().load(data.imagen).into(image)

            calificacion.isFocusable = false

        }

    }

}