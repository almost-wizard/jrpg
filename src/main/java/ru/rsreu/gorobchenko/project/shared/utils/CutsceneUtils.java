package ru.rsreu.gorobchenko.project.shared.utils;

import com.almasb.fxgl.core.asset.AssetType;
import com.almasb.fxgl.cutscene.Cutscene;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.InventoryManager;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getCutsceneService;

public class CutsceneUtils {
    public static void start(String filepath) {
        List<String> lines = getAssetLoader().load(AssetType.TEXT, filepath);
        var activeInventories = InventoryManager.getInstance().getInventoriesOnSceneExcludePlayer();
        for (var inventory : activeInventories) {
            inventory.close();
            inventory.unFocus();
        }
        var playerInventory = PlayerManager.getInstance().getInventory();
        playerInventory.close();
        playerInventory.close();
        getCutsceneService().startCutscene(new Cutscene(lines));
    }
}
