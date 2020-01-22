package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("=== Teste 1: Department FindById ===");
        Department dep = departmentDao.findById(2);
        System.out.println(dep);

        System.out.println("==== Teste 2: Department FindAll ===");
        List<Department> list =  departmentDao.findAll();
        for(Department obj : list){
            System.out.println(obj);
        }

        System.out.println("==== Teste 3: Department Insert ===");
        Department newDepartment = new Department(null, "Shoes");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! new ID = " + newDepartment.getId());

        System.out.println("==== Teste 4: Department update ===");
        dep = departmentDao.findById(7);
        dep.setName("Bottles");
        departmentDao.update(dep);

        System.out.println("Update completed!");

        System.out.println("==== Teste  4: Department Delete===");

        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete complete");

    }
}
