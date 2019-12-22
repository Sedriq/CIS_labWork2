package db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.entity.*;
import sample.ExcelController;

/**
 * Provides methods for work with DataBase.
 * 
 * @author Ilya Kruhlenko
 *
 */
public class DBManager {

	private FileInputStream fis;
	private static DBManager instance;

	private String url = "jdbc:postgresql://localhost:5432/postgres";
	private String login = "postgres";
	private String pass = "admin";

	// ======== QUERIES ==============

	//USER
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM public.user WHERE name=?";
	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM public.user WHERE id=?";
	private static final String SQL_INSERT_USER="INSERT INTO public.user(name, email, password, authority_id) VALUES (?,?,?,?)";
	private static final String SQL_UPDATE_USER="UPDATE public.user SET name=?,email=?,password=?,authority_id=? WHERE id=?";
	private static final String SQL_DELETE_USER="DELETE FROM public.user WHERE name=?";

	//DEPARTMENT
	private static final String SQL_INSERT_DEPARTMENT="INSERT INTO department(name) VALUES (?)";
	private static final String SQL_GET_ALL_DEPARTMENTS = "SELECT * FROM department";
	private static final String SQL_DELETE_DEPARTMENT ="DELETE FROM department where id = ?";
	private static final String SQL_GET_DEPARTMENT_BY_NAME = "SELECT * FROM department WHERE name = ?";
	private static final String SQL_UPDATE_DEPARTMENT ="UPDATE department SET name = ? where id = ?";


	//SCIENCE_TYPE
	private static final String SQL_INSERT_SCIENCE_TYPE="INSERT INTO science_type(name) VALUES (?)";
    private static final String SQL_GET_ALL_SCIENCE_TYPES = "SELECT * FROM science_type";

	//SPECIALITY
	private static final String SQL_INSERT_SPECIALITY="INSERT INTO speciality(name) VALUES (?)";
	private static final String SQL_GET_ALL_SPECIALTIES = "SELECT * FROM speciality";
	//===============================

	// SUBJECTS
	public static final String SQL_GET_SUBJECTS_BY_TYPE = "SELECT * FROM subjects WHERE type = ?";
	public static final String SQL_GET_ALL_SUBJECTS = "SELECT * FROM subjects";
	public static final String SQL_INSETR_SUBJECT = "INSERT INTO subjects(name, type, department_name) VALUES (?,?,?)";
	public static final String SQL_UPDATE_SUBJECT = "UPDATE subjects SET name = ?, type = ?, department name = ?, where id = ?";



	private DBManager() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Singletone.
	 * 
	 * @return instance of this class.
	 */
	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	/**
	 * Returns a DB connection from the connection pool.
	 * 
	 * @return DB connection.
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(url, login, pass);
		return con;
	}

	// ========== CLOSE METHODS ============

	private void close(Connection con, Statement st, ResultSet rs) {
		close(rs);
		close(st);
		close(con);
	}

	private void close(Connection con, Statement st) {
		close(st);
		close(con);
	}

	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

	private void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
			}
		}
	}

	// ========== USER ===========
	/**
	 * Returns user found by his login.
	 * 
	 * @param login - user login.
	 * @return user entity.
	 */
	public User findUserByLogin(String login) {
		User user = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next()) {
				user = getUser(rs);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st, rs);
		}
		return user;
	}

	public User findUserById(Long id) {
		User user = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_FIND_USER_BY_ID);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				user = getUser(rs);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st, rs);
		}
		return user;
	}

	/**
	 * Inserts new row into `user` table in DB.
	 * 
	 * @param user - user to insert.
	 * @return true if new row has been added, false either.
	 */
	public boolean insertUser(User user) {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_INSERT_USER);
			con.setAutoCommit(false);
			st.setString(1, user.getName());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			st.setLong(4, user.getAuthority_id());
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st);
		}
		return true;
	}


	public boolean updateDepartment(String newName,Department department){
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_UPDATE_DEPARTMENT);
			st.setString(1, newName);
			st.setLong(2, department.getId());
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st);
		}
		return true;
	}

	public boolean insertDepartment(String department){
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_INSERT_DEPARTMENT);
			st.setString(1, department);
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st);
		}
		return true;
	}

	/**
	 * Updates `user` table row in DB.
	 * 
	 * @param user - user to update.
	 * @return true if row has been updated, false either.
	 */
	public boolean updateUser(User user) {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_UPDATE_USER);
			st.setString(1, user.getName());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			st.setLong(4, user.getAuthority_id());
			st.setLong(5,  user.getId());
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st);
		}
		return true;
	}

	/**
	 * Deletes `user` table row in DB.
	 * 
	 * @param login - user login.
	 * @return true if row has been deleted, false either.
	 */
	public boolean deleteUser(String login) {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_DELETE_USER);
			st.setString(1, login);
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st);
		}
		return true;
	}

	public boolean deleteDepartment(Department department){
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_DELETE_DEPARTMENT);
			st.setLong(1, department.getId());
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st);
		}
		return true;
	}


	/**
	 * Extracts data from result set and fills user entity by it.
	 * 
	 * @param rs - ResultSet of query
	 * @return user entity
	 * @throws SQLException
	 */
	private User getUser(ResultSet rs) throws SQLException {
		User ins = new User();
		ins.setAuthority_id(rs.getLong(Fields.AUTHORITY_ID));
		ins.setEmail(rs.getString(Fields.EMAIL));
		ins.setId(rs.getLong(Fields.ID));
		ins.setName(rs.getString(Fields.NAME));
		ins.setPassword(rs.getString(Fields.PASSWORD));
		return ins;
	}
	
	public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL_GET_ALL_DEPARTMENTS);
            rs = st.executeQuery();
            while (rs.next()) {
                departments.add(getDepartment(rs));
            }
            con.commit();
        } catch (SQLException e) {
            rollback(con);
        } finally {
            close(con, st, rs);
        }
        return departments;
    }

	public List<Specialty> getAllSpecialties() {
        List<Specialty> specialties = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL_GET_ALL_SPECIALTIES);
            rs = st.executeQuery();
            while (rs.next()) {
                specialties.add(getSpecialty(rs));
            }
            con.commit();
        } catch (SQLException e) {
            rollback(con);
        } finally {
            close(con, st, rs);
        }
        return specialties;
    }

	public List<ScienceType> getAllScienceTypes() {
        List<ScienceType> scienceTypes = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL_GET_ALL_SCIENCE_TYPES);
            rs = st.executeQuery();
            while (rs.next()) {
                scienceTypes.add(getScienceType(rs));
            }
            con.commit();
        } catch (SQLException e) {
            rollback(con);
        } finally {
            close(con, st, rs);
        }
        return scienceTypes;
    }

	//========== TEACHER =============
	
	/**
	 * Returns all teachers.
	 * 
	 * @return list of teacher entities.
	 */

//	public void exportAllReportsForXsl(int id) {
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//			con = getConnection();
//			st = con.prepareStatement(SQL_GET_REPORT);
//            st.setInt(1,id);
//			rs = st.executeQuery();
//
//			saveToDirectory(rs);
//		} catch (SQLException | IOException e) {
//			rollback(con);
//		}
//		finally {
//			close(con, st, rs);
//		}
//	}
//
//    private void saveToDirectory(ResultSet rs) throws IOException, SQLException {
//        Stage stage = new Stage();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select directory for save");
//        fileChooser.setInitialFileName("report");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.xlsx", "*.xlsx"));
//        File file = fileChooser.showSaveDialog(stage);
//
//		if (file != null) {
//			saveTextToFile(file, rs);
//		}
//    }
//
//    private void saveTextToFile(File file, ResultSet rs) throws IOException, SQLException {
//			Workbook wb = new XSSFWorkbook();
//			Sheet sheet = wb.createSheet("Лист 1");
//            Reports reports = null;
//
//			Row header = sheet.createRow(0);
//			Row main = sheet.createRow(2);
//
//			main.createCell(0).setCellValue("№");
//			main.createCell(1).setCellValue("Найменування заходів");
//			main.createCell(2).setCellValue("Строк");
//			main.createCell(3).setCellValue("Виконавці");
//			main.createCell(4).setCellValue("Відмітка виконання");
//
//			sheet.setColumnWidth(0, 50 * 25);
//			sheet.setColumnWidth(1, 250 * 25); //250 - character width
//			sheet.setColumnWidth(2, 150 * 25);
//			sheet.setColumnWidth(3, 200 * 25);
//			sheet.setColumnWidth(4, 150 * 25);
//
//			sheet.setZoom(110); // scale 110%
//
//			int index = 3;
//			while (rs.next()) {
//				Row row = sheet.createRow(index);
//                Report r = getReport(rs);
//                reports = DBManager.getInstance().getReportsById((int) r.getReport_id());
//				Activity a = DBManager.getInstance().getActivityById(r.getActivity_id());
//				Teacher t = DBManager.getInstance().getTeacherById(r.getUser_id());
//
//				row.createCell(0).setCellValue(r.getId());
//				row.createCell(1).setCellValue(a.getName());
//				row.createCell(2).setCellValue(rs.getString("date"));
//				row.createCell(3).setCellValue(t.getName());
//				row.createCell(4).setCellValue(r.getStatus());
//				index++;
//			}
//            header.createCell(0).setCellValue(reports.getName());
//
//			FileOutputStream fileOut = new FileOutputStream(file);
//			wb.write(fileOut);
//
//			fileOut.close();
//			wb.close();
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information Dialog");
//            alert.setHeaderText(null);
//            alert.setContentText("Reports Exported to Excel Sheet.");
//            alert.showAndWait();
//	}

		/**
         * Extracts data from result set and fills teacher entity by it.
         *
         * @param rs - ResultSet of query
         * @return teacher entity
         * @throws SQLException
         */
	private Teacher getTeacher(ResultSet rs) throws SQLException {
		Teacher ins = new Teacher();
		ins.setId(rs.getLong(Fields.ID));
		ins.setName(rs.getString(Fields.NAME));
		ins.setDepartment_id(rs.getLong(Fields.DEPARTMENT_ID));
		ins.setPosition_id(rs.getLong(Fields.POSITION_ID));
		return ins;
	}

	private Reports getReports(ResultSet rs) throws  SQLException{
		Reports reports = new Reports();
		reports.setId(rs.getLong(Fields.ID));
		reports.setName(rs.getString(Fields.NAME));
		reports.setDepartment_id(rs.getLong(Fields.DEPARTMENT_ID));
		reports.setDate(rs.getDate(Fields.DATE));
		return reports;
	}

	private Report getReport(ResultSet rs) throws  SQLException{
		Report report = new Report();
		report.setId(rs.getLong(Fields.ID));
		report.setStatus(rs.getString(Fields.STATUS));
		report.setDate(rs.getDate(Fields.DATE));
		report.setReport_id(rs.getLong(Fields.REPORT_ID));
		report.setActivity_id(rs.getLong(Fields.ACTIVITY_ID));
		report.setUser_id(rs.getLong(Fields.USER_ID));

		return report;
	}
    
    private Activity getActivity(ResultSet rs) throws SQLException {
        Activity ins = new Activity();
        ins.setId(rs.getLong(Fields.ID));
        ins.setName(rs.getString(Fields.NAME));
        return ins;
    }
    
    private Department getDepartment(ResultSet rs) throws SQLException {
		Department ins = new Department();
		ins.setId(rs.getLong(Fields.ID));
		ins.setName(rs.getString(Fields.NAME));
		return ins;
	}

	private Subjects getSubject(ResultSet rs) throws SQLException{
		Subjects ins = new Subjects();
		ins.setName(rs.getString(Fields.NAME));
		ins.setType(rs.getInt(Fields.TYPE));
		ins.setDepartment_name(rs.getInt(Fields.DEPARTMENT_NAME));
		return ins;
	}

    private Specialty getSpecialty(ResultSet rs) throws SQLException {
        Specialty ins = new Specialty();
        ins.setId(rs.getLong(Fields.ID));
        ins.setName(rs.getString(Fields.NAME));
        return ins;
    }

    private ScienceType getScienceType(ResultSet rs) throws SQLException {
        ScienceType ins = new ScienceType();
        ins.setId(rs.getLong(Fields.ID));
        ins.setName(rs.getString(Fields.NAME));
        return ins;
    }

	private Position getPosition(ResultSet rs) throws SQLException {
		Position ins = new Position();
		ins.setId(rs.getLong(Fields.ID));
		ins.setName(rs.getString(Fields.NAME));
		return ins;
	}

	ExcelController ex = new ExcelController();
	public void importFromExcel() throws IOException, SQLException {
		ex.importDataFromExcel();
	}

	public void exportInExcel() throws IOException {
		ex.exportDataInExcel();
	}



	public Department getDepartmentById(long id) {
		Department department = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_GET_ALL_DEPARTMENTS);
			//st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				department = getDepartment(rs);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st, rs);
		}
		return department;
	}

	public Department getDepartmentByName(String name) {
		Department department = null;
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_GET_DEPARTMENT_BY_NAME);
			st.setString(1, name);
			rs = st.executeQuery();
			if (rs.next()) {
				department = getDepartment(rs);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st, rs);
		}
		return department;
	}

	public List<Department> getDepartment() {
		List<Department> department = new ArrayList<>();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(SQL_GET_ALL_DEPARTMENTS);
			rs = st.executeQuery();
			while (rs.next()) {
				department.add(getDepartment(rs));
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, st, rs);
		}
		return department;
	}

//	public List<Subjects> getSubjectsByType(int type){
//		List<Subjects> subjects = new ArrayList<>();
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try{
//			con = getConnection();
//			st = con.prepareStatement(SQL_GET_SUBJECTS_BY_TYPE);
//			st.setInt(1, type);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				department.add(getSubject(rs));
//			}
//			con.commit();
//		} catch (SQLException e) {
//			rollback(con);
//		} finally {
//			close(con, st, rs);
//		}
//		return subjects;
//	}
}