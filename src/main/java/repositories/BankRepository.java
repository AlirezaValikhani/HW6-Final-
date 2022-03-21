package repositories;

import models.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankRepository {
    private Connection connection;

    public BankRepository(Connection connection) {
        this.connection = connection;
    }

    public Long save(Bank bank) {
        try {
            String insertBank = "INSERT INTO bank (nameBank) VALUES (?) returning id ";
            PreparedStatement preparedStatement = connection.prepareStatement(insertBank);
            preparedStatement.setString(1, bank.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public List<Bank> read(Long id) {
        List<Bank>banks = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM bank WHERE id = ? ");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                banks.add(new Bank(resultSet.getLong("id"),
                        resultSet.getString("bank_name")));
            }
            return banks;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
