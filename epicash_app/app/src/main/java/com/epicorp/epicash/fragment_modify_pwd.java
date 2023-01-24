package com.epicorp.epicash;

import android.os.Bundle;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.epicorp.epicash.databinding.FragmentModifyPwdBinding;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class fragment_modify_pwd extends Fragment {

    private FragmentModifyPwdBinding binding;
    public String oldPassGet;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentModifyPwdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyData userId = MyData.getInstance();


        //Initialisation des variables
        EditText oldPass = (EditText) view.findViewById(R.id.Epicash_modifypwd_ancienpwd);
        EditText newPass = (EditText) view.findViewById(R.id.Epicash_modifypwd_nouveaupwd);
        EditText newPassVerif = (EditText) view.findViewById(R.id.Epicash_modifypwd_saisir_encore_pwd);

        //Initialisation url et queue GET
        String urlGet = getResources().getString(R.string.Route_get_user_by_id) + userId.getIdUser();
        RequestQueue queueGet = Volley.newRequestQueue(view.getContext());


        //Initialisation url et queue PUT
        String urlPut = getResources().getString(R.string.Route_put_user_by_id) + userId.getIdUser();
        RequestQueue queuePut = Volley.newRequestQueue(view.getContext());


        //Get de l'ancien mot de passe
        JsonObjectRequest jsonObjectRequestGet = new JsonObjectRequest(Request.Method.GET, urlGet, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    oldPassGet = response.getString("password");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Error get data",Toast.LENGTH_SHORT).show();
            }
        });
        //Ajout de la queue Get
        queueGet.add(jsonObjectRequestGet);

        //Boutton Enregistrer + Put du nouveau mot de passe
            binding.EpicashModifypwdButtonEnregistrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (newPass.getText().toString().equals(newPassVerif.getText().toString())) {

                        final JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("password", newPass.getText().toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        StringRequest putRequest = new StringRequest(Request.Method.PUT, urlPut,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        NavHostFragment.findNavController(fragment_modify_pwd.this)
                                                .navigate(R.id.action_fragment_modify_pwd_to_profileFragment);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json");
                                headers.put("Accept", "application/json");
                                return headers;
                            }
                            @Override
                            public byte[] getBody() {
                                try {
                                    Log.i("json", jsonObject.toString());
                                    return jsonObject.toString().getBytes("UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        };
                        //Ajout de la queue Put
                        queuePut.add(putRequest);
                    }
                    else{
                        Toast.makeText(view.getContext(),"Erreur mot de passe",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Boutton de retour
        binding.EpicashModifypwdButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fragment_modify_pwd.this)
                        .navigate(R.id.action_fragment_modify_pwd_to_profileFragment);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}