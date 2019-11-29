package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.UserVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class TheaterController extends MasterController {

	@FXML
	private ComboBox<String> combobox;
	@FXML
	private ChoiceBox<String> choicebox;

	private UserVO user;
	
	@FXML
	private void initialize() {
		choicebox.getItems().add("겨울왕국");
		choicebox.getItems().add("라푼젤");
		
//		choicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});

		for (char ch = 'a'; ch <= 'j'; ch++) {
			char alpha = ch;
			for (int i = 0; i < 10; i++) {
				String sit = (i + 1) + "";
				combobox.getItems().add(ch + sit);
			}

		}
				
	}
	
	private void check() {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM movie";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rs.getString("movie");
				rs.getString("sit");
			}
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
		
	}
	
	public void ticketing() {
		MainController mc = (MainController) MainApp.app.getController("main");
		this.user = mc.getUser();
		String movieName =(String) choicebox.getValue();
		String sitNum = (String) combobox.getValue();
		if(user == null) {
			Util.showAlert("에러", "로그인을 해주세요", AlertType.ERROR);
			return;
		}

		if(movieName == null || sitNum == null) {
			Util.showAlert("에러", "영화와 자리를 선택하세요", AlertType.INFORMATION);
			return;
		}
		
		String id = user.getId();
		
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "UPDATE movie SET movie=?, sit = ? WHERE id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, movieName);
			pstmt.setString(2, sitNum);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			user.setMovie(movieName);
			user.setSit(sitNum);
			Util.showAlert("성공", "예매완료됨", AlertType.INFORMATION);
		}catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 입력 중 오류발생", AlertType.ERROR);
			return;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	
	}
	
//	public void 
	

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void cancle() {
		MainApp.app.slideOut(getRoot());
	}
}
