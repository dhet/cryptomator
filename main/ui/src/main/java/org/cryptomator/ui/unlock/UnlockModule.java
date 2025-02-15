package org.cryptomator.ui.unlock;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.cryptomator.ui.common.DefaultSceneFactory;
import org.cryptomator.ui.common.FXMLLoaderFactory;
import org.cryptomator.ui.common.FxController;
import org.cryptomator.ui.common.FxControllerKey;
import org.cryptomator.ui.common.FxmlFile;
import org.cryptomator.ui.common.FxmlScene;
import org.cryptomator.ui.forgetPassword.ForgetPasswordComponent;

import javax.inject.Named;
import javax.inject.Provider;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

@Module(subcomponents = {ForgetPasswordComponent.class})
abstract class UnlockModule {

	@Provides
	@UnlockWindow
	@UnlockScoped
	static FXMLLoaderFactory provideFxmlLoaderFactory(Map<Class<? extends FxController>, Provider<FxController>> factories, DefaultSceneFactory sceneFactory, ResourceBundle resourceBundle) {
		return new FXMLLoaderFactory(factories, sceneFactory, resourceBundle);
	}

	@Provides
	@UnlockWindow
	@UnlockScoped
	static Stage provideStage(ResourceBundle resourceBundle, @Named("windowIcon") Optional<Image> windowIcon) {
		Stage stage = new Stage();
		stage.setTitle(resourceBundle.getString("unlock.title"));
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		windowIcon.ifPresent(stage.getIcons()::add);
		return stage;
	}

	@Provides
	@FxmlScene(FxmlFile.UNLOCK)
	@UnlockScoped
	static Scene provideUnlockScene(@UnlockWindow FXMLLoaderFactory fxmlLoaders) {
		return fxmlLoaders.createScene("/fxml/unlock.fxml");
	}

	@Provides
	@FxmlScene(FxmlFile.UNLOCK_SUCCESS)
	@UnlockScoped
	static Scene provideUnlockSuccessScene(@UnlockWindow FXMLLoaderFactory fxmlLoaders) {
		return fxmlLoaders.createScene("/fxml/unlock_success.fxml");
	}


	// ------------------

	@Binds
	@IntoMap
	@FxControllerKey(UnlockController.class)
	abstract FxController bindUnlockController(UnlockController controller);

	@Binds
	@IntoMap
	@FxControllerKey(UnlockSuccessController.class)
	abstract FxController bindUnlockSuccessController(UnlockSuccessController controller);


}
