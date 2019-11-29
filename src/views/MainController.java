package views;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import util.Util;

public class MainController extends MasterController {

	public void reset() {
		// TODO Auto-generated method stub

	}

	@FXML
	private Button register;
	@FXML
	private Button ticket;
	@FXML
	private Button cancel;
	@FXML
	private Button check;
	@FXML
	private Button admin;
	@FXML
	private Button login;
	@FXML
	private Label loginInfo;

	private UserVO user = null;

	public void register() {
		MainApp.app.loadPane("register");
	}

	public void login() {
		MainApp.app.loadPane("login");
	}

	public void ticket() {
		MainApp.app.loadPane("theater");
	}

	public void ticketcancle() {
		MainController mc = (MainController) MainApp.app.getController("main");
		this.user = mc.getUser();
		if(user == null) {
			Util.showAlert("에러", "로그인을 해주세요", AlertType.ERROR);
			return;
		}
		MainApp.app.loadPane("cancle");
		CancleController cc = (CancleController) MainApp.app.getController("cancle");
		cc.getInfo();
	}

	public void setLoginInfo(UserVO user) {
		this.user = user;
		loginInfo.setText(user.getName() + "[" + user.getId() + "]");

	}

	public UserVO getUser() {
		return user;
	}
}
