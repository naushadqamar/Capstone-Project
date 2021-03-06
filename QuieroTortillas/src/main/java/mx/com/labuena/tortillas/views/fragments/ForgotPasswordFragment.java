package mx.com.labuena.tortillas.views.fragments;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import mx.com.labuena.tortillas.R;
import mx.com.labuena.tortillas.events.ResetPasswordFailureEvent;
import mx.com.labuena.tortillas.events.ResetPasswordSuccessfulEvent;
import mx.com.labuena.tortillas.presenters.ForgotPasswordPresenter;
import mx.com.labuena.tortillas.setup.LaBuenaModules;

import static mx.com.labuena.tortillas.R.id.progressBar;


/**
 * Created by moracl6 on 8/8/2016.
 */

public class ForgotPasswordFragment extends BaseFragment {
    @Inject
    EventBus eventBus;

    @Inject
    ForgotPasswordPresenter forgotPasswordPresenter;

    private EditText userEmailEditText;
    private ProgressBar resetingPasswordProgressBar;
    private TextInputLayout imputEmailLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.forgot_password_fragment;
    }

    @Override
    protected void injectDependencies(LaBuenaModules modules) {
        modules.inject(this);
    }


    @Override
    protected void initView(View rootView) {
        userEmailEditText = (EditText) rootView.findViewById(R.id.emailEditText);
        resetingPasswordProgressBar = (ProgressBar) rootView.findViewById(progressBar);
        imputEmailLayout = (TextInputLayout) rootView.findViewById(R.id.inputEmailAddress);
        loadControlEvents(rootView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!eventBus.isRegistered(this))
            eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResetPasswordSuccessfulEvent(ResetPasswordSuccessfulEvent event) {
        resetingPasswordProgressBar.setVisibility(View.GONE);
        userEmailEditText.setText(StringUtils.EMPTY);
        String message = String.format(getString(R.string.reset_password_email_sent), event.getEmail());
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResetPasswordFailureEvent(ResetPasswordFailureEvent event) {
        resetingPasswordProgressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), getString(R.string.error_resetting_password), Toast.LENGTH_LONG).show();
    }

    private void loadControlEvents(View rootView) {
        View newUserButton = rootView.findViewById(R.id.resetPasswordButton);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmailEditText.getText().toString();
                if (StringUtils.isBlank(email)) {
                    userEmailEditText.setError(getString(R.string.email_address_required));
                    return;
                }
                resetingPasswordProgressBar.setVisibility(View.VISIBLE);
                forgotPasswordPresenter.resetPassword(email);
            }
        });
    }
}
