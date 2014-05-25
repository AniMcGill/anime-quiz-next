package org.msac.quiz;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import static org.loadui.testfx.Assertions.assertNodeExists;

/**
 * Created by Natsumi on 2014-05-25.
 */
public class MainTest extends GuiTest {
    @Override
    protected Parent getRootNode() {
        return Main.getStage().getScene().getRoot();
    }

    @BeforeClass
    public static void startApplication(){
        FXTestUtils.launchApp(Main.class);
    }

    @Test
    public void testCreateGame(){
        click("#fileMenu");
        click("#createGameMenu");
        assertNodeExists("Create a new game file...");
    }
}
