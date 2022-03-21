package repositories;

import models.Account;
import models.AccountType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements BaseRepository<Account>{
    private Connection connection;

    public AccountRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(Account account) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(" INSERT INTO account(branch_code,national_code,account_number,balance,status) " +
                            "VALUES (?, ?, ?, ?, ?) returning id");
            preparedStatement.setString(1, account.getBranchCode());
            preparedStatement.setString(2, account.getNationalCode());
            preparedStatement.setString(3, account.getAccountNumber());
            preparedStatement.setDouble(4, account.getBalance());
            preparedStatement.setString(5, String.valueOf(account.getAccountType()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Account read(Account account) {
        try {
            String find = "SELECT * FROM account WHERE accountnumber = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, account.getAccountNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM account ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Account account) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE account SET branch_code = ? , national_code = ? , " +
                            " account_number = ? , balance = ? , status = ? WHERE id = ?");
            ps.setString(1, account.getBranchCode());
            ps.setString(2, account.getNationalCode());
            ps.setString(3, account.getAccountNumber());
            ps.setDouble(4, account.getBalance());
            ps.setString(5, String.valueOf(account.getAccountType()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Account account) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM account WHERE id = ?");
            ps.setLong(1, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Account mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new Account(resultSet.getLong("id"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("national_code"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"),
                        AccountType.valueOf(resultSet.getString("status")));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> mapToList(ResultSet resultSet) {
        List<Account> accounts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                accounts.add(new Account(resultSet.getLong("id"),
                        resultSet.getString("branch_code"),
                        resultSet.getString("national_code"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"),
                        AccountType.valueOf(resultSet.getString("status"))));
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
