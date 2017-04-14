/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Account;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Shawn
 */
@Stateless
public class AccountDAO extends AbstractDAO<Account> {

    @PersistenceContext(unitName = "WebApplication3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountDAO() {
        super(Account.class);
    }
    /*
        There is a way to get a single item from the table but that requires the usage of getSingleResult() method.
        This method will throw an exception if there the item that you're trying to find doesn't exist within the database.
        Therefore we use the getResultList() method to retrieve the first account. The list will only have a single item
        everytime if the query does find the account because the username will be unique. If we can't find the account,
        the size of the list will be 0.
    */
    public List getAccountInfoListByUsername(String username) {
        List<Account> accounts;
        Query query = em.createNativeQuery("SELECT * FROM accounts WHERE username='" + username + "';", Account.class);
        accounts = (List<Account>)query.getResultList();
        return accounts;
    }
    
    /*
        Look at getAccountListByUsername() method documentation for why we need minimumAccountListSize.
    */
    public boolean accountExists(String username) {
        int minimumAccountListSize = 1;
        List accounts = getAccountInfoListByUsername(username);
        return accounts.size() >= minimumAccountListSize;
    }
    
    public boolean validateAccountInformation(String username, String password) {
        List<Account> accounts = getAccountInfoListByUsername(username);
        Account databaseAccountInfo = accounts.get(0);
        return databaseAccountInfo.getUsername().equalsIgnoreCase(username) && databaseAccountInfo.getPassword().equalsIgnoreCase(password);
    }
}
