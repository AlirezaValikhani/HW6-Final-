package repositories;

import models.Account;
import models.AccountType;
import models.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditCartRepository implements BaseRepository<CreditCard>{
    private Connection connection;

    public CreditCartRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(CreditCard creditCard) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(" INSERT INTO credit_card (account_number,card_number,cvv2,password,expire_date,status) " +
                            "VALUES (?, ?, ?, ?, ?, ?) returning id");
            preparedStatement.setString(1, creditCard.getAccountNumber());
            preparedStatement.setString(2, creditCard.getCardNumber());
            preparedStatement.setString(3, creditCard.getCvv2());
            preparedStatement.setString(4, creditCard.getPassword());
            preparedStatement.setString(5, creditCard.getExpireDate());
            preparedStatement.setString(6, String.valueOf(creditCard.getAccountType()));
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
    public CreditCard read(CreditCard creditCard) {
        try {
            String find = "SELECT * FROM credit_card WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, creditCard.getAccountNumber());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<CreditCard> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM credit_card ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(CreditCard creditCard) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE credit_card SET account_number = ? , card_number = ? , " +
                            " cvv2 = ? , password = ? , expire_date = ? , status = ? WHERE id = ?");
            ps.setString(1, creditCard.getAccountNumber());
            ps.setString(2, creditCard.getCardNumber());
            ps.setString(3, creditCard.getCvv2());
            ps.setString(4, creditCard.getPassword());
            ps.setString(5, String.valueOf(creditCard.getAccountType()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(CreditCard creditCard) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM credit_card WHERE id = ?");
            ps.setLong(1, creditCard.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public CreditCard mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new CreditCard(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv2"),
                        resultSet.getString("password"),
                        resultSet.getString("expire_date"),
                        AccountType.valueOf(resultSet.getString("status")));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CreditCard> mapToList(ResultSet resultSet) {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            if (resultSet.next()) {
                creditCards.add(new CreditCard(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv2"),
                        resultSet.getString("password"),
                        resultSet.getString("expire_date"),
                        AccountType.valueOf(resultSet.getString("status"))));
            }
            return creditCards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
