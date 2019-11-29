package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class CancleController extends MasterController {
	
	@FXML 
	private Label label;

	private UserVO user;
	 
	public void getInfo() {
		MainController mc = (MainController) MainApp.app.getController("main");
		this.user = mc.getUser();
		if(user.getMovie() == null) {
			label.setText("예매 정보 없음");
		}else {
			label.setText(user.getMovie() + " , " + user.getSit());

		}

	}
	
	public void ticketCancle() {
		String id = user.getId();
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "UPDATE movie SET movie=?, sit = ? WHERE id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, null);
			pstmt.setString(2, null);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			user.setMovie(null);
			user.setSit(null);
			Util.showAlert("성공", "예매취소됨", AlertType.INFORMATION);
		}catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 입력 중 오류발생", AlertType.ERROR);
			return;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
		
		label.setText("예매 정보 없음");

	}

	@Override
	public void reset() {

	}

	public void cancle() {
		MainApp.app.slideOut(getRoot());
	}

}
