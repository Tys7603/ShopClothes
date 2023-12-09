package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityUpdateInforAccountBinding;
import com.example.shopclothes.model.User;
import com.example.shopclothes.utils.UIUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

public class UpdateAccountActivity extends AppCompatActivity implements UpdateAccountContract.View {
    private ActivityUpdateInforAccountBinding mBinding;
    private UpdateAccountContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Uri mUri;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUpdateInforAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        onClick();
        mPresenter = new UpdateAccountPresenter(this);
        mUser = (User) getIntent().getSerializableExtra("user");
        updateUI(mUser);
    }


    @Override
    public void onClick() {
        mBinding.openGalleryButton.setOnClickListener(view -> choseImgFromGallery());
        mBinding.btnSaveUpInfor.setOnClickListener(view -> {

          if (mBinding.etFullnameUpdate.getText().toString().isEmpty()){
              UIUtils.showMessage(mBinding.getRoot(), AppConstants.NAME_IS_EMPTY);
          }else {
              String fullName = mBinding.etFullnameUpdate.getText().toString();
              if (!isValidFullName(fullName)) {
                  showToastMessage("Tên không hợp lệ");
                  return;
              }
              mProgressDialog = ProgressDialog.show(this,"",AppConstants.LOADING);
              if (mUri != null){
                  // nếu đường dẫn ko rỗng thì update theo đường dẫn ảnh
                  mPresenter.uploadImageToFirebaseStorage(mUri, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), mBinding.etFullnameUpdate.getText().toString());
              }else {
                  // nêu rỗng update ảnh có sẵn
                  mPresenter.updateUserInformation(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), mBinding.etFullnameUpdate.getText().toString(), mUser.getAnh());
              }
          }


        });
        mBinding.ivBackInfor.setOnClickListener(view -> onBackPressed());
    }



    @Override
    public void updateUI(User mUser) {
        Glide.with(this)
                .load(mUser.getAnh())
                .centerCrop()
                .placeholder(R.drawable.pick_image)
                .into(mBinding.circleImageView);
        mBinding.etFullnameUpdate.setText(mUser.getName());
    }

    @Override
    public void onMessage(String message) {
        onBackPressed();
    }

    @Override
    public void choseImgFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, AppConstants.PICK_IMAGE));
    }

    // nhận uri khi chọn ảnh từ thư viện
    private final ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (data == null){
                        return;
                    }
                    mUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                        mBinding.circleImageView.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

    public boolean isValidFullName(String fullName) {
        // Loại bỏ khoảng trắng ở đầu và cuối chuỗi
        fullName = fullName.trim();

        // Loại bỏ các dấu diacritic (dấu thanh, dấu mũ) trong chuỗi tiếng Việt
        fullName = Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Biểu thức chính quy để kiểm tra chuỗi có chứa ký tự không phải là chữ cái (bao gồm cả dấu tiếng Việt) hoặc khoảng trắng không
        Pattern pattern = Pattern.compile("^[\\p{L} ]+$");

        // Kiểm tra chuỗi tên với biểu thức chính quy đã tạo
        return pattern.matcher(fullName).matches();
    }
    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}