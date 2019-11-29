package main;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.CancleController;
import views.LoginController;
import views.MainController;
import views.MasterController;
import views.RegisterController;
import views.TheaterController;

public class MainApp extends Application {

	public static MainApp app;

	private StackPane mainPage;

	private Map<String, MasterController> controllerMap = new HashMap<>();

	public MasterController getController(String name) {
		return controllerMap.get(name);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		app = this;
		try {
			FXMLLoader mainLoader = new FXMLLoader();
			mainLoader.setLocation(getClass().getResource("/views/MainLayout.fxml"));
			mainPage = mainLoader.load();
			// 硫붿씤
			MainController mc = mainLoader.getController();
			mc.setRoot(mainPage);
			controllerMap.put("main", mc);
			// �쉶�썝媛��엯
			FXMLLoader RegisterLoader = new FXMLLoader();
			RegisterLoader.setLocation(getClass().getResource("/views/RegisterLayout.fxml"));
			AnchorPane RegisterPage = RegisterLoader.load();

			RegisterController rc = RegisterLoader.getController();

			rc.setRoot(RegisterPage);
			controllerMap.put("register", rc);

			// 濡쒓렇�씤
			FXMLLoader LoginLoader = new FXMLLoader();
			LoginLoader.setLocation(getClass().getResource("/views/LoginLayout.fxml"));
			AnchorPane LoginPage = LoginLoader.load();

			LoginController lg = LoginLoader.getController();

			lg.setRoot(LoginPage);
			controllerMap.put("login", lg);

			FXMLLoader TheaterLoader = new FXMLLoader();
			TheaterLoader.setLocation(getClass().getResource("/views/TheaterLayout.fxml"));
			AnchorPane TheaterPage = TheaterLoader.load();

			TheaterController tc = TheaterLoader.getController();

			tc.setRoot(TheaterPage);
			controllerMap.put("theater", tc);

			FXMLLoader CancleLoader = new FXMLLoader();
			CancleLoader.setLocation(getClass().getResource("/views/CancleLayout.fxml"));
			AnchorPane CanclePage = CancleLoader.load();

			CancleController cc = CancleLoader.getController();

			cc.setRoot(CanclePage);
			controllerMap.put("cancle", cc);

			Scene scene = new Scene(mainPage);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�삤瑜�");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadPane(String name) {
		MasterController c = controllerMap.get(name);
		c.reset();
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);

		pane.setTranslateX(-800);
		pane.setOpacity(0);

		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 0);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	public void slideOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 800);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 0);

		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), (e) -> {
			mainPage.getChildren().remove(pane);
		}, toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
}
