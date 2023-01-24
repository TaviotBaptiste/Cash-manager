package com.epicorp.epicash;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.epicorp.epicash.databinding.FragmentProfileBinding;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyData userId = MyData.getInstance();
        // URL de la requête (route)
        String url = getResources().getString(R.string.Route_get_user_by_id) + userId.getIdUser();
        // Créer une queue de requête
        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        // Récupérer le textView dans lequel j'affiche mon résultat (fonctionne pour récupérer les infos à passer en paramètres)
        TextView profil_data_nom = (TextView) view.findViewById(R.id.Epicash_profil_data_nom);
        TextView profil_data_prenom = (TextView) view.findViewById(R.id.Epicash_profil_data_prenom);
        TextView profil_data_mail = (TextView) view.findViewById(R.id.Epicash_profil_data_mail);

        // Création de la requête
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Action réalisée quand une réponse est reçue
                try {
                    profil_data_nom.setText(response.getString("lastname"));
                    profil_data_prenom.setText(response.getString("firstname"));
                    profil_data_mail.setText(response.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Action réalisée lorsqu'une erreur est levée
                profil_data_nom.setText(error.toString());
                profil_data_mail.setText(error.toString());
                profil_data_prenom.setText(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };

        binding.EpicashProfilButtonPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_fragment_modify_pwd);
            }
        });
        binding.EpicashProfilButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_LoginFragmentBinding);
            }
        });
        binding.EpicashProfilButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                //Message a afficher
                builder.setMessage("Voulez-vous vraiment supprimer votre compte ?");
                //titre de l alerte
                builder.setTitle("Attention !");
                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);
                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // URL de la requête (route)
                    String url = getResources().getString(R.string.Route_delete_user_by_id) + "/8";
                    // Créer une queue de requête
                    RequestQueue queueDelete = Volley.newRequestQueue(view.getContext());
                    // Création de la requête
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Action réalisée quand une réponse est reçue
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Action réalisée lorsqu'une erreur est levée
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            headers.put("Accept", "application/json");
                            return headers;
                        }


                    };
                    //Redirection
                    NavHostFragment.findNavController(ProfileFragment.this)
                            .navigate(R.id.action_profileFragment_to_LoginFragmentBinding);
                    //Queue delete user
                    queueDelete.add(jsonObjectRequest);
                });
                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });
                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });
        binding.EpicashProfilButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_homeFragment);
            }
        });
        //ajout de la requete
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}