package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.databinding.TransactionFrameBinding;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.transaction.Transaction;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.createTransaction.CreateTransactionActivity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.openGL.OpenGLActivity;

public class HomeFragment extends Fragment {
    private List<Transaction> transactionIncomeList = new ArrayList<>();
    private List<Transaction> transactionOutcomeList = new ArrayList<>();
    private TransactionIncomeListAdapter incomeAdapter;
    private TransactionOutcomeListAdapter outcomeAdapter;
    private HomeViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        incomeAdapter = new TransactionIncomeListAdapter();
        outcomeAdapter = new TransactionOutcomeListAdapter();
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getTransactionIncome().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable List<Transaction> transactions) {
                if (transactions != null) {
                    transactionIncomeList = transactions;
                    incomeAdapter.notifyDataSetChanged();
                }
            }
        });
        viewModel.getTransactionOutcome().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable List<Transaction> transactions) {
                if (transactions != null) {
                    transactionOutcomeList = transactions;
                    outcomeAdapter.notifyDataSetChanged();
                }
            }
        });
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerViewIncome = root.findViewById(R.id.list_transaction_income);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewIncome.setAdapter(incomeAdapter);

        RecyclerView recyclerViewOutcome = root.findViewById(R.id.list_transaction_outcome);
        recyclerViewOutcome.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOutcome.setAdapter(outcomeAdapter);

        final View createTransaction = root.findViewById(R.id.buttonCreateTransaction);
        createTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateTransactionActivity.class);
                startActivity(intent);
            }
        });
        final ImageView homeLogo = root.findViewById(R.id.homeLogo);
        homeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OpenGLActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public class TransactionIncomeListAdapter extends RecyclerView.Adapter<ViewHolder> {
        private LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TransactionFrameBinding transactionFrameBinding = TransactionFrameBinding.inflate(
                    layoutInflater, viewGroup, false);
            return new ViewHolder(transactionFrameBinding.getRoot(), transactionFrameBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Transaction transaction = transactionIncomeList.get(position);
            viewHolder.setData(transaction);
        }

        @Override
        public int getItemCount() {
            return transactionIncomeList.size();
        }
    }

    public class TransactionOutcomeListAdapter extends RecyclerView.Adapter<ViewHolder> {
        private LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TransactionFrameBinding transactionFrameBinding = TransactionFrameBinding.inflate(
                    layoutInflater, viewGroup, false);
            return new ViewHolder(transactionFrameBinding.getRoot(), transactionFrameBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Transaction transaction = transactionOutcomeList.get(position);
            viewHolder.setData(transaction);
        }

        @Override
        public int getItemCount() {
            return transactionOutcomeList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TransactionFrameBinding transactionFrameBinding;

        public ViewHolder(View itemView, TransactionFrameBinding transactionFrameBinding) {
            super(itemView);
            this.transactionFrameBinding = transactionFrameBinding;
        }

        public void setData(Transaction transaction) {
            transactionFrameBinding.setTransaction(transaction);
        }
    }
}