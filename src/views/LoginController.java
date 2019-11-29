package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class LoginController extends MasterController {

	public void reset() {
	}

	@FXML
	private TextField txtId;
	@FXML
	private PasswordField pass;

	public void login() {
		String id = txtId.getText();
		String password = pass.getText();
		
		if(id.isEmpty() || password.isEmpty()) {
			Util.showAlert("에러", "아이디나 비밀번호는 공백일 수 없습니다", AlertType.ERROR);
		}
		
		UserVO user = checkLogin(id, password);

		if(user != null) {
			MainApp.app.slideOut(getRoot());
			MainController mc = (MainController)MainApp.app.getController("main");
			mc.setLoginInfo(user);
		}else {
			Util.showAlert("에러", "존재하지 않는 아이디이거나 비밀번호가 틀립니다.", AlertType.ERROR);
		}
	}
	
	
	private UserVO checkLogin(String id, String pass) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM movie WHERE id = ? AND pass = PASSWORD(?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println(rs);
				UserVO user = new UserVO();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setMovie(rs.getString("movie"));
				user.setPass(rs.getString("pass"));
				user.setSit(rs.getString("sit"));
				return user;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 처리중 오류 발생. 인터넷 확인 필요", AlertType.ERROR);
			return null;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}
	public void cancel() {
		MainApp.app.slideOut(getRoot());
	}
}
