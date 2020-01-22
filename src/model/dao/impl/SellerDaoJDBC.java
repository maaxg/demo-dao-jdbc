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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //Se esse proximo resultset no caso o de valor 1 existir
            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller obj = intstantiateSeller(rs, dep);
                return obj;
            }
            return null;

        }catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeConnection(stmt, rs);
        }
    }

    private Seller intstantiateSeller(ResultSet rs, Department dep) throws SQLException {
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

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        /*Objeto department montado*/
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT seller.*,department.Name as DepName "+
                         "FROM seller INNER JOIN department "+
                        "ON seller.DepartmentId = department.Id " +
                        "WHERE DepartmentId = ? "+
                        "ORDER BY Name";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, department.getId());
            rs = stmt.executeQuery();

            List<Seller> list = new ArrayList<>();
                //Key       //Value
            Map<Integer, Department> map = new HashMap<>();

            //Se esse proximo resultset no caso o de valor 1 existir
            while(rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = intstantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;

        }catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }finally {
            DB.closeConnection(stmt, rs);
        }
    }
}
