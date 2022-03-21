package repositories;

import models.Customer;
import models.Login;
import models.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginRepository implements BaseRepository<Login>{
    private Connection connection;

    public LoginRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(Login login) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement(" INSERT INTO login (user_name,password,status) " +
                            "VALUES (?, ?, ?) returning id");
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ps.setString(3, String.valueOf(login.getUserType()));
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public Login read(Login login) {
        try {
            String find = "SELECT * FROM login WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(find);
            preparedStatement.setLong(1, login.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTo(resultSet);
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public List<Login> readAll() {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM login ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Login login) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE login SET user_name = ? , password = ? , status = ? WHERE id = ?");
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ps.setString(3, String.valueOf(login.getUserType()));
            ps.setLong(4,login.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Login login) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM login WHERE id = ?");
            ps.setLong(1, login.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Login mapTo(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new Login(resultSet.getLong("id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        UserType.valueOf(resultSet.getString("status")));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Login> mapToList(ResultSet resultSet) {
        List<Login> loginList = new ArrayList<>();
        try {
            if (resultSet.next()) {
                loginList.add(new Login(resultSet.getLong("id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password"),
                        UserType.valueOf(resultSet.getString("status"))));
            }
            return loginList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
