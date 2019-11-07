package mx.tec.tacu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.tec.tacu.R
import mx.tec.tacu.model.Taqueria

class ElementoAdapter(var list: ArrayList<Taqueria>): RecyclerView.Adapter<ElementoAdapter.ViewHolder>() {

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

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bindItems(data:Taqueria){

            val nombre: TextView =itemView.findViewById(R.id.nameTaqueria)
            val calificacion: TextView =itemView.findViewById(R.id.ratingBarTaqueria)
            val image: ImageView =itemView.findViewById(R.id.imageViewTaqueria)

            nombre.text=data.nombre
            calificacion.text=data.calificacion.toString()
            Picasso.get().load(data.imagen).into(image)

        }

    }

}