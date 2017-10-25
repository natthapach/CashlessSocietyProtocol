package database;

import exceptions.IdNotFoundException;
import exceptions.InsufficientFundException;

/**
 * Created by PC301 on 25/10/2560.
 */
public interface AccountManager {
    void deposit(String uid, double amt) throws IdNotFoundException;
    void withdraw(String uid, double amt) throws InsufficientFundException, IdNotFoundException;
    double getBalance(String uid) throws IdNotFoundException;
}
