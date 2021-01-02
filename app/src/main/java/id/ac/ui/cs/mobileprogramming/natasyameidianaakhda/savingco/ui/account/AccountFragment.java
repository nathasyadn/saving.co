package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.ui.account;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int READ_STORAGE_REQUEST = 3;

    private ImageView imageProfile;
    private byte[] byteImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);

        final EditText profileField = root.findViewById(R.id.fieldNameProfile);
        final EditText numberField = root.findViewById(R.id.fieldPhoneProfile);
        final TextView logout = root.findViewById(R.id.logoutButton);
        final Button saveChanges = root.findViewById(R.id.saveProfile);
        final Button buttonSaveNumber = root.findViewById(R.id.saveContact);
        imageProfile = root.findViewById(R.id.imageProfile);

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.getUserProfile().observe(getViewLifecycleOwner(), new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                profile = userProfile;
                profileField.setText(profile.getName());
                numberField.setText(profile.getNumberPhone());
                if (profile.getImage() != null){
                    imageProfile.setImageBitmap(Utilities.decodeBase64(userProfile.getImage()));
                }
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
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissionToReadExternalStorage();
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

            if (byteImage != null)
                userProfile.setImage(byteImage);
            else if (userProfile.getImage() != null)
                userProfile.setImage(profile.getImage());

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
        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                WRITE_CONTACTS_PERMISSIONS_REQUEST);
    }

    public void getPermissionToReadExternalStorage() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_STORAGE_REQUEST);
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
        } else if (requestCode == READ_STORAGE_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromGallery();
            } else {
                showMessageOKCancel(getString(R.string.saveimg),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            READ_STORAGE_REQUEST);
                                }
                            }
                        });
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(R.string.okay, okListener)
                .setNegativeButton(R.string.cancellation, null)
                .create()
                .show();
    }

    private void pickFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    final Uri imageUri = data.getData();
                    try {
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        bitmap = Utilities.getResizedBitmap(bitmap);

                        imageProfile.setImageBitmap(bitmap);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteImage = stream.toByteArray();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getContext(), getString(R.string.toastMessageContact) + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    break;
            }
    }
}