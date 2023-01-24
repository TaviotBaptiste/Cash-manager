package com.epicorp.epicash;

import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.epicorp.epicash.databinding.FragmentHomeBinding;
import com.epicorp.epicash.objects.Product;
import com.epicorp.epicash.placeholder.PanierApdater;
import com.epicorp.epicash.placeholder.ShopAdapter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.DataInput;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

public class HomeFragment extends Fragment {

        private FragmentHomeBinding binding;
        private ArrayList<Product> productsArray = new ArrayList<>();
        private RecyclerView recyclerView;
        private PanierApdater adapter;

        // Qtt produits
        int firstProduct = 0;
        int secondProduct = 0;
        int thirdProduct = 0;
        int totalPrice = 0;

        String stringResponse;
        Product[] products;
        ObjectMapper mapper;

        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState
        ) {
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View view = binding.getRoot();

            // Panier
            recyclerView = view.findViewById(R.id.list);
            adapter = new PanierApdater(view.getContext(), productsArray);
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
            DividerItemDecoration did = new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL);
            did.setDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.divider));
            recyclerView.addItemDecoration(did);
            recyclerView.setAdapter(adapter);
            return binding.getRoot();
        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Récupérer l'ID utilisateur pour GET ses informations
            MyData userId = MyData.getInstance();

            String urlPoducts = getResources().getString(R.string.Route_post_get_all_products);
            String urlUser = getResources().getString(R.string.Route_get_user_by_id) + userId.getIdUser();
            RequestQueue queue = Volley.newRequestQueue(view.getContext());

            // Affichage des produits
            TextView firstPrice = (TextView) view.findViewById(R.id.Epicash_home_price1_data);
            TextView secondPrice = (TextView) view.findViewById(R.id.Epicash_home_price2_data);
            TextView thirdPrice = (TextView) view.findViewById(R.id.Epicash_home_price3_data);
            ImageView firstPicture = (ImageView) view.findViewById(R.id.Epicash_home_image1);
            ImageView secondPicture = (ImageView) view.findViewById(R.id.Epicash_home_image2);
            ImageView thirdPicture = (ImageView) view.findViewById(R.id.Epicash_home_image3);

            // Ajout d'un produit au panier
            TextView fistQtt = (TextView) view.findViewById(R.id.Epicash_home_price1_quantite);
            TextView secondQtt = (TextView) view.findViewById(R.id.Epicash_home_price2_quantite);
            TextView thirdQtt = (TextView) view.findViewById(R.id.Epicash_home_price3_quantite);
            TextView totalPriceText = (TextView) view.findViewById(R.id.Epicash_home_price_data);

            // Solde de l'utisaliteur
            TextView solde = (TextView) view.findViewById(R.id.Epicash_home_solde_data);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlPoducts, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    stringResponse = response.toString();
                    mapper = new ObjectMapper();

                    try {
                        products = mapper.readValue(stringResponse, Product[].class);

                        if (products.length >= 2) {
                            firstPrice.setText(products[0].getPrix() + " EUR");
                            secondPrice.setText(products[1].getPrix() + " EUR");
                            thirdPrice.setText(products[2].getPrix() + " EUR");
                            Picasso.get().load(products[0].getImage()).into(firstPicture);
                            Picasso.get().load(products[1].getImage()).into(secondPicture);
                            Picasso.get().load(products[2].getImage()).into(thirdPicture);
                        } else {
                            System.out.println("Pas assez de produits dans la liste.");

                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR: " + error);
                }
            });

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlUser, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        solde.setText(response.getString("solde") + " EUR");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR: " + error);
                }
            });

            // First product
            binding.EpicashHomePrice1Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstProduct ++;
                    if (firstProduct < 0) {
                        fistQtt.setText("0");
                        firstProduct = 0;
                    } else {
                        fistQtt.setText(String.valueOf(firstProduct));
                        try {
                            productsArray.add(products[0]);
                            adapter.notifyDataSetChanged();
                        } catch(NumberFormatException e) {
                            Toast.makeText(view.getContext(), "Ajout au panier impossible", Toast.LENGTH_SHORT).show();
                        }
                        totalPrice = totalPrice(productsArray, totalPrice);
                        totalPriceText.setText(String.valueOf(totalPrice));
                    }
                }
            });
            binding.EpicashHomePrice1Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstProduct --;
                    if (firstProduct < 0) {
                        fistQtt.setText("0");
                        firstProduct = 0;
                    } else {
                        fistQtt.setText(String.valueOf(firstProduct));
                        productsArray.remove(products[0]);
                        adapter.notifyDataSetChanged();
                        totalPrice = totalPrice - Integer.parseInt(products[0].getPrix());
                        totalPriceText.setText(String.valueOf(totalPrice));
                    }
                }
            });

            // Second product
            binding.EpicashHomePrice2Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondProduct ++;
                    if (secondProduct < 0) {
                        secondQtt.setText("0");
                        secondProduct = 0;
                    } else {
                        secondQtt.setText(String.valueOf(secondProduct));
                        try {
                            productsArray.add(products[1]);
                            adapter.notifyDataSetChanged();
                        } catch(NumberFormatException e) {
                            Toast.makeText(view.getContext(), "Ajout au panier impossible", Toast.LENGTH_SHORT).show();
                        }
                        totalPrice = totalPrice(productsArray, totalPrice);
                        totalPriceText.setText(String.valueOf(totalPrice));
                    }
                }
            });
            binding.EpicashHomePrice2Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondProduct --;
                    if (secondProduct < 0) {
                        secondQtt.setText("0");
                        secondProduct = 0;
                    } else {
                        secondQtt.setText(String.valueOf(secondProduct));
                        productsArray.remove(products[1]);
                        adapter.notifyDataSetChanged();
                        totalPrice = totalPrice - Integer.parseInt(products[1].getPrix());
                        totalPriceText.setText(String.valueOf(totalPrice));
                    }
                }
            });

            // Third product
            binding.EpicashHomePrice3Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thirdProduct ++;
                    if (thirdProduct < 0) {
                        thirdQtt.setText("0");
                        thirdProduct = 0;
                    } else {
                        thirdQtt.setText(String.valueOf(thirdProduct));
                        try {
                            productsArray.add(products[2]);
                            adapter.notifyDataSetChanged();
                        } catch(NumberFormatException e) {
                            Toast.makeText(view.getContext(), "Ajout au panier impossible", Toast.LENGTH_SHORT).show();
                        }
                        totalPrice = totalPrice(productsArray, totalPrice);
                        totalPriceText.setText(String.valueOf(totalPrice));
                    }
                }
            });
            binding.EpicashHomePrice3Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thirdProduct --;
                    if (thirdProduct < 0) {
                        thirdQtt.setText("0");
                        thirdProduct = 0;
                    } else {
                        thirdQtt.setText(String.valueOf(thirdProduct));
                        productsArray.remove(products[2]);
                        adapter.notifyDataSetChanged();
                        totalPrice = totalPrice - Integer.parseInt(products[2].getPrix());
                        totalPriceText.setText(String.valueOf(totalPrice));
                    }
                }
            });

            queue.add(jsonArrayRequest);
            queue.add(jsonObjectRequest);

            binding.EpicashHomeButtonProfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_profileFragment);
                }
            });
            binding.EpicashHomeButtonCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_cardFragment);
                }
            });
            binding.EpicashHomeButtonPaiement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_billFragment);
                }
            });
            binding.EpicashHomeSeeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_fragment_shop);
                }
            });
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

        public static int totalPrice(ArrayList<Product> productsArray, int totalPrice) {

            totalPrice = 0;

            for (Product p : productsArray) {
                totalPrice += Integer.parseInt(p.getPrix());
            }

            return totalPrice;
        }
    }