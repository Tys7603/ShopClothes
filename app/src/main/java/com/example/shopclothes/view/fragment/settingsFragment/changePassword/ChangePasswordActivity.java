package com.example.shopclothes.view.fragment.settingsFragment.changePassword;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityChangePasswordBinding;
import com.example.shopclothes.utils.UIUtils;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View {
    private ActivityChangePasswordBinding mBinding;
    private ChangePasswordContract.Presenter mPresenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new ChangePasswordPresenter(this);
        setListener();
    }
    public void setListener(){
        mBinding.ivBack.setOnClickListener(view -> onBackPressed());
        mBinding.btnSave.setOnClickListener(view -> changePass());
        mBinding.ivHidingPass.setOnClickListener(view -> onPasswordOldToggleImageClick());
        mBinding.ivHidingPassNew.setOnClickListener(view -> onPasswordNewToggleImageClick());
        mBinding.ivHidingPassAgain.setOnClickListener(view -> onPasswordAgainToggleImageClick());
    }

    public void changePass(){
        String password = mBinding.etPasswordOld.getText().toString();
        String passwordNew = mBinding.etPasswordNew.getText().toString();
        String passwordAgain = mBinding.etPasswordNewAgain.getText().toString();
        if (isValidate(password, passwordNew, passwordAgain)) return;
        String changePass = "Đổi mật khẩu";
        progressDialog = ProgressDialog.show(this, changePass, AppConstants.LOADING);
        mPresenter.doChange(password, passwordNew, passwordAgain);
    }

    private boolean isValidate(String password, String passwordNew, String passwordAgain) {
        if (!UIUtils.isPasswordValid(password)) {
            UIUtils.showMessage(mBinding.getRoot(), "Mật khẩu cũ phải lớn hơn " + UIUtils.MIN_PASSWORD_LENGTH + " kí tự");
            return true;
        }
        if (!UIUtils.isPasswordValid(passwordNew)) {
            UIUtils.showMessage(mBinding.getRoot(), "Mật khẩu mới phải lớn hơn " + UIUtils.MIN_PASSWORD_LENGTH + " kí tự");
            return true;
        }
        if (!passwordNew.equals(passwordAgain)) {
            UIUtils.showMessage(mBinding.getRoot(), "Mật khẩu không trùng khớp");
            return true;
        }
        return false;
    }

    public void onPasswordOldToggleImageClick(){
        UIUtils.togglePasswordVisibleWithImage(
                mBinding.etPasswordOld,
                mBinding.ivHidingPass);
    }
    public void onPasswordNewToggleImageClick(){
        UIUtils.togglePasswordVisibleWithImage(
                mBinding.etPasswordNew,
                mBinding.ivHidingPassNew);
    }
    public void onPasswordAgainToggleImageClick(){
        UIUtils.togglePasswordVisibleWithImage(
                mBinding.etPasswordNewAgain,
                mBinding.ivHidingPassAgain );
    }

    @Override
    public void onMessage(String message) {
        progressDialog.dismiss();
        UIUtils.clearText(mBinding.etPasswordNew, mBinding.etPasswordOld, mBinding.etPasswordNewAgain);
        UIUtils.showMessage(mBinding.getRoot(), message);
    }
}