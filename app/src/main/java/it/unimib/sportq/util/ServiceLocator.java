package it.unimib.sportq.util;

import android.app.Application;

import it.unimib.sportq.data.repository.user.IUserRepository;
import it.unimib.sportq.data.repository.user.UserRepository;
import it.unimib.sportq.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.sportq.data.source.user.BaseUserLocalDataSource;
import it.unimib.sportq.data.source.user.UserAuthenticationRemoteDataSource;
import it.unimib.sportq.data.source.user.UserLocalDataSource;

public class ServiceLocator {
    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    /**
     * Returns an instance of ServiceLocator class.
     * @return An instance of ServiceLocator.
     */
    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public IUserRepository getUserRepository(Application application) {


        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource = new UserAuthenticationRemoteDataSource();
        BaseUserLocalDataSource userLocalDataSource = new UserLocalDataSource();

        return new UserRepository(userRemoteAuthenticationDataSource, userLocalDataSource);
    }
}
