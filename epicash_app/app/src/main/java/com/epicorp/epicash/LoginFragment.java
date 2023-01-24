package com.epicorp.epicash;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.epicorp.epicash.databinding.FragmentLoginBinding;
import com.epicorp.epicash.objects.Product;
import com.epicorp.epicash.objects.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyData userId = MyData.getInstance();
        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        TextView email = (TextView) view.findViewById(R.id.Epicash_login_data_mail);
        TextView password = (TextView) view.findViewById(R.id.Epicash_login_data_password);
        String url = getResources().getString(R.string.Route_get_user_id);

        binding.EpicashConnexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final JSONObject jsonObject = new JSONObject();
                try {
                    if(!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        jsonObject.put("email", email.getText().toString());
                        jsonObject.put("password", password.getText().toString());
                    }
                    else {
                        Toast.makeText(view.getContext(), "Email ou mot de passe vide", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // Création de la requête
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String stringResponse = response.toString();
                        try {
                            //System.out.println( "RETOUR" + stringResponse);
                            if (response.getString("status").equals("true")) {
                                userId.setIdUser(response.getString("id"));
                                NavHostFragment.findNavController(LoginFragment.this)
                                        .navigate(R.id.action_loginFragment_to_homeFragment);
                            } else {
                                Toast.makeText(view.getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
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
                 queue.add(jsonObjectRequest);
            }
        });
        binding.EpicashCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_cguFragment);
            }
        });
        binding.EpicashForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragmentBinding_to_forgotPwd);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}