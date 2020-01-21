package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT seller.*,department.Name as DepName\n" +
                         "FROM seller INNER JOIN department\n" +
                         "ON seller.DepartmentId = department.Id\n" +
                         "WHERE seller.Id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                //Se esse proximo resultset no caso o de valor 1 existir
                Department dep = new Department();
                /*Objeto department montado*/
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));
                Seller obj = new Seller();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                obj.setEmail(rs.getString("Email"));
                obj.setBaseSalary(rs.getDouble("BaseSalary"));
                obj.setBirthDate(rs.getDate("BirthDate"));
                //passagem do department montado para o objeto
                obj.setDepartment(dep);
                return obj;
            }
            return null;

        }catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeConnection(stmt, rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
