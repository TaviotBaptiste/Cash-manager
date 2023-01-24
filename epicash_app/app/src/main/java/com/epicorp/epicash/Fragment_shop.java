package com.epicorp.epicash;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.epicorp.epicash.databinding.FragmentShopBinding;
import com.epicorp.epicash.objects.Product;
import com.epicorp.epicash.placeholder.ShopAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class Fragment_shop extends Fragment {

    private static String TAG = "MainResponse";
    ArrayList<Product> data  = new ArrayList<>();
    RecyclerView shop_recyclerView;
    ShopAdapter adapter;

    private @NonNull FragmentShopBinding binding;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Get_products("http://51.210.102.51:4211/api/products");
        shop_recyclerView = view.findViewById(R.id.shop_recyclerView);
        adapter = new ShopAdapter(view.getContext(),data);
        shop_recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        DividerItemDecoration did = new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL);
        did.setDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.divider));
        shop_recyclerView.addItemDecoration(did);
        shop_recyclerView.setAdapter(adapter);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.EpicashShopButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_shop.this)
                        .navigate(R.id.action_fragment_shop_to_homeFragment);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void Get_products(String url){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Log.d(TAG, "onResponse: "+response);
                        String stringResponse = response.toString();
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            Product[] products = mapper.readValue(stringResponse, Product[].class);
                            for(int i = 0 ; i < products.length ; i++){
                                data.add(
                                        new Product(
                                        products[i].getLibelle(),
                                        products[i].getPrix(), products[i].getImage(),products[i].getDesc()
                                        )
                                );
                                //Log.d(TAG, "onResponse"+data.get(i).getLibelle());


                            }

                            adapter.notifyDataSetChanged();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: "+ error);
                    }
                }
        );

        Volley.newRequestQueue(this.getContext()).add(request);
    }


}






