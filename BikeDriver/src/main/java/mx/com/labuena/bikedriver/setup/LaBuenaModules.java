package mx.com.labuena.bikedriver.setup;

import javax.inject.Singleton;

import dagger.Component;
import mx.com.labuena.bikedriver.services.BikerInstanceIdService;
import mx.com.labuena.bikedriver.services.BikerLocationUpdateJobService;
import mx.com.labuena.bikedriver.services.BikerUpdateIntentService;
import mx.com.labuena.bikedriver.services.FetchAddressIntentService;
import mx.com.labuena.bikedriver.views.activities.HomeActivity;
import mx.com.labuena.bikedriver.views.fragments.LoginFragment;
import mx.com.labuena.bikedriver.views.fragments.OrdersToDeliverFragment;

/**
 * Created by clerks on 8/6/16.
 */
@Singleton
@Component(modules = {LaBuenaApplicationModules.class})
public interface LaBuenaModules {
    void inject(HomeActivity homeActivity);

    void inject(BikerInstanceIdService bikerInstanceIdService);

    void inject(LoginFragment loginFragment);

    void inject(BikerUpdateIntentService bikerUpdateIntentService);

    void inject(FetchAddressIntentService fetchAddressIntentService);

    void inject(OrdersToDeliverFragment ordersToDeliverFragment);

    void inject(BikerLocationUpdateJobService bikerLocationUpdateJobService);
}
