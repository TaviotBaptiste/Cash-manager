package com.epicorp.epicash;


import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.epicorp.epicash.databinding.FragmentCardBinding;
import com.epicorp.epicash.objects.Account;
import com.epicorp.epicash.placeholder.CardListAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class CardFragment extends Fragment {
    private RecyclerView cardList;
    private CardListAdapter adapter;
    private ArrayList<Account> cardsArray = new ArrayList<>();
    String userID = MyData.getInstance().getIdUser();
    private FragmentCardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String apiUrlGet = getResources().getString(R.string.Route_get_accounts_by_userID) + userID;
        getCards(apiUrlGet);

        cardList = view.findViewById(R.id.listcard);
        adapter = new CardListAdapter(view.getContext(), cardsArray);
        cardList.setLayoutManager(new GridLayoutManager(view.getContext(),1));
        cardList.setAdapter(adapter);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText newCardName = view.findViewById(R.id.Epicash_card_name);
        EditText newCardNumber = view.findViewById(R.id.Epicash_card_numcard);
        EditText newCardDate = view.findViewById(R.id.Epicash_card_date);
        EditText newCardCCV = view.findViewById(R.id.Epicash_card_cvv);

        binding.EpicashCardButtonAjout.setOnClickListener(button -> {

            if (newCardName.getText().toString().isEmpty()
                    || newCardNumber.getText().toString().isEmpty()
                    || newCardDate.getText().toString().isEmpty()
                    || newCardCCV.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(), "Merci d'entrer les informations requises", Toast.LENGTH_SHORT).show();
            }
            else if (newCardNumber.getText().toString().replace(" ", "").length() == 16
                    && newCardCCV.getText().toString().length() == 3)
            {
                Toast.makeText(view.getContext(), newCardNumber.getText().toString(), Toast.LENGTH_SHORT).show();

                PostNewCardData(
                        view,
                        newCardName.getText().toString(),
                        newCardNumber.getText().toString().replace(" ", ""),
                        newCardDate.getText().toString());
            }
            else {
                Toast.makeText(view.getContext(), "le format et incorrect", Toast.LENGTH_SHORT).show();
            }
        });


        binding.EpicashCardButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CardFragment.this)
                        .navigate(R.id.action_cardFragment_to_homeFragment);
            }
        });
    }

    private void PostNewCardData(@NonNull View view, String newCardName, String newCardNumber, String newCardDate) {
        EditText cardName = view.findViewById(R.id.Epicash_card_name);
        EditText cardNumber = view.findViewById(R.id.Epicash_card_numcard);
        EditText cardDate = view.findViewById(R.id.Epicash_card_date);
        EditText cardCCV = view.findViewById(R.id.Epicash_card_cvv);

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JSONObject body = new JSONObject();
        try {
            body.put("card_number", newCardNumber);
            body.put("validation_date", newCardDate);
            body.put("name", newCardName);
            body.put("fk_user", this.userID);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                getResources().getString(R.string.Route_post_create_account),
                body,
                response -> {
                    cardName.setText("");
                    cardNumber.setText("");
                    cardDate.setText("");
                    cardCCV.setText("");
                    Toast.makeText(view.getContext(), "Carte enregistrée", Toast.LENGTH_SHORT).show();
                },
                error -> System.out.println("ERROR: " + error)
        );
        queue.add(jsonObjectRequest);
    }

    private void getCards(String apiUrl) {
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    String strResponse = response.toString();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Account[] accounts = mapper.readValue(strResponse, Account[].class);
                        for (Account account : accounts) {
                            cardsArray.add(
                                    new Account(
                                            account.getID(),
                                            account.getCard_number(),
                                            account.getValidation_date(),
                                            account.getName(),
                                            account.getFk_user(),
                                            account.getCreatedAt(),
                                            account.getUpdatedAt()
                                    )
                            );
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> System.out.println("ERROR: " + error));
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}