package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.account;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.R;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.persistence.userProfile.UserProfile;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.login.LoginActivity;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.SavingcoConstant;
import id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.util.Utilities;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private UserProfile profile;
    private static final int WRITE_CONTACTS_PERMISSIONS_REQUEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);

        final EditText profileField = root.findViewById(R.id.fieldNameProfile);
        final EditText numberField = root.findViewById(R.id.fieldPhoneProfile);
        final TextView logout = root.findViewById(R.id.logoutButton);
        final Button saveChanges = root.findViewById(R.id.saveProfile);
        final Button buttonSaveNumber = root.findViewById(R.id.saveContact);

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.getUserProfile().observe(getViewLifecycleOwner(), new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                profile = userProfile;
                profileField.setText(profile.getName());
                numberField.setText(profile.getNumberPhone());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = profileField.getText().toString();
                String numberPhoneString = numberField.getText().toString();
                saveChanges(nameString, numberPhoneString);
            }
        });
        buttonSaveNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissionToWriteUserContacts();
                createContact();
            }
        });
        return root;
    }

    private void saveChanges(String name, String number) {
        if (name.isEmpty() || number.isEmpty()) {
            Toast toast = Toast.makeText(getContext(), R.string.toastLogin,
                    Toast.LENGTH_LONG);
            toast.show();
        } else {
            UserProfile userProfile = new UserProfile();
            userProfile.setName(name);
            userProfile.setNumberPhone(number);
            userProfile.setCreatedAt(profile.getCreatedAt());
            userProfile.setModifiedAt(Utilities.getCurrentTimestamp());
            accountViewModel.editUserProfile(userProfile);

            /* Refresh Activity */
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }
    }

    private void logout() {
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(SavingcoConstant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void getPermissionToWriteUserContacts() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS))
                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                        WRITE_CONTACTS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == WRITE_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getContext(), R.string.toastAccount, Toast.LENGTH_SHORT).show();
                View buttonSaveNumber = getView().findViewById(R.id.saveContact);
                buttonSaveNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createContact();
                    }
                });
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS);
                if (!showRationale) {
                    Toast.makeText(getContext(), R.string.toastAccountFailed, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void createContact() {
        String displayName = profile.getName();
        String mobileNumber = profile.getNumberPhone();
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

        operations.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        if (displayName != null) {
            operations.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            displayName).build());
        }

        if (mobileNumber != null) {
            operations.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        try {
            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Toast.makeText(getContext(), R.string.toastContactSuccess, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getString(R.string.toastMessageContact) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}