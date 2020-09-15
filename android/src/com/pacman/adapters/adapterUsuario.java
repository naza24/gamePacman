package com.pacman.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacman.entidades.Usuario;
import com.pacman.R;

//import com.example.android.jugadorandroid.R;
//import com.example.android.jugadorandroid.entidades.Usuario;

import java.util.List;

public class adapterUsuario extends RecyclerView.Adapter<adapterUsuario.viewHolderUsuario> {

    private List<Usuario> lista_usuarios;
    TextView name, pass, score;

    public adapterUsuario(List<Usuario> usuarios){
        lista_usuarios= usuarios;
    }

    @NonNull
    @Override
    /*Enlaza el adaptador con el item de la lista de items...*/
    public viewHolderUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item,null,false);

        name = (TextView) vista.findViewById(R.id.txName);
        pass = (TextView) vista.findViewById(R.id.txPassword);
        score = (TextView) vista.findViewById(R.id.txScore);

        return new viewHolderUsuario(vista);
    }

    @Override
    /*Establece comunicacion entre el adaptador y la clase debajo viewHolderPersona*/
    public void onBindViewHolder(@NonNull viewHolderUsuario holder, int position) {
        // como esta seccion es el enlace entre el adaptador y el viewHolder
        // y los cmapos del viewHolder al no tener restriccion de private,
        // puedo setear los campos desde este metodo, holder es una instancia de viewHolder

        String nameText = name.getText().toString();
        String passText = pass.getText().toString();
        String scoreText = score.getText().toString();

        // falta acomodar y agregar el contenido que hay en el laout como por ejemplo Name:
        holder.nombre.setText(nameText+" "+this.lista_usuarios.get(position).getNombre());
        holder.contrasenia.setText(passText+" "+this.lista_usuarios.get(position).getContrasenia());
        holder.puntaje.setText(scoreText+" "+this.lista_usuarios.get(position).getPuntajeMaximo()+"");

    }

    @Override
    public int getItemCount() {
        return lista_usuarios.size();
    }

    public class viewHolderUsuario extends RecyclerView.ViewHolder {

        TextView nombre, contrasenia, puntaje;

        public viewHolderUsuario(@NonNull View itemView) {
            super(itemView);

            this.nombre = (TextView)itemView.findViewById(R.id.txName);
            this.contrasenia = (TextView)itemView.findViewById(R.id.txPassword);
            this.puntaje = (TextView)itemView.findViewById(R.id.txScore);

        }
    }
}
