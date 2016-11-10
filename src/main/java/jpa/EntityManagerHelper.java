package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import business.RProgram;
import business.User;

import java.util.ArrayList;


public class EntityManagerHelper {

	private static final EntityManagerFactory emf;
	private static final ThreadLocal<EntityManager> threadLocal;

	static {
		emf = Persistence.createEntityManagerFactory("mysql");
		threadLocal = new ThreadLocal<EntityManager>();
	}

	public static EntityManager getEntityManager() {
		EntityManager em = threadLocal.get();

		if (em == null) {
			System.out.println("Creating New Entity");
			em = emf.createEntityManager();
			threadLocal.set(em);
		}
		return em;
	}

	public static void closeEntityManager() {
		EntityManager em = threadLocal.get();
		if (em != null) {
			em.close();
			threadLocal.set(null);
		}
	}

	public static void closeEntityManagerFactory() {
		emf.close();
	}

	public static void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	public static void rollback() {
		getEntityManager().getTransaction().rollback();
	}

	public static void commit() {
		getEntityManager().getTransaction().commit();
	}


	public  static void addUser(User newUser){

		beginTransaction();
		EntityManagerHelper.getEntityManager().persist(newUser);
		commit();
	}

	public  static void addRprogram(RProgram newRprogram){

		beginTransaction();
		EntityManagerHelper.getEntityManager().persist(newRprogram);
		commit();
	}

	public static User findUserWithUserName(String name) {
		return (User) EntityManagerHelper.getEntityManager().createQuery(
				"SELECT user FROM User user WHERE user.username LIKE :custName")
				.setParameter("custName", name)
				.setMaxResults(1)
				.getSingleResult();
	}

	public static ArrayList<RProgram> findProgramsOfUser(String username){
		return (ArrayList<RProgram>)EntityManagerHelper.getEntityManager().createQuery(
				"SELECT program FROM RProgram program WHERE program.author LIKE :custName")
				.setParameter("custName", username)
				.setMaxResults(100)
				.getResultList();
	}
	public  static void deleteProgramFromUser(int id){
		EntityManager em = EntityManagerHelper.getEntityManager();
		RProgram program = EntityManagerHelper.findProgramOfUser(id);

		beginTransaction();
		em.remove(program);
		commit();
		System.out.println("END");

	}
	public static RProgram findProgramOfUser(int id){

		RProgram program = (RProgram) EntityManagerHelper.getEntityManager().createQuery(
				"SELECT program FROM RProgram program WHERE program.id="+id)
				.getSingleResult();
		return program;
	}
	public static RProgram findProgramOfUserByFilename(String filename){
		return (RProgram) EntityManagerHelper.getEntityManager().createQuery(
				"SELECT program FROM RProgram program WHERE program.filename LIKE :custName")
				.setParameter("custName", filename)
				.getSingleResult();
	}
	public static void updateFilename(int id,String filename){

		RProgram program = findProgramOfUser(id);
		beginTransaction();
		program.setName(filename);
		commit();
	}

	public static void updateProgramcode(int id,String programCode){
		RProgram program = findProgramOfUser(id);
		beginTransaction();
		program.setProgram(programCode);
		commit();
	}

	public static void updateProgramresult(int id,String programResult ){
		RProgram program = findProgramOfUser(id);
		beginTransaction();
		program.setResult(programResult);
		commit();
	}
	public static void updateTimeStamps(int id){
		RProgram program = findProgramOfUser(id);
		beginTransaction();
		program.updateTimeStamps();
		commit();
	}

}