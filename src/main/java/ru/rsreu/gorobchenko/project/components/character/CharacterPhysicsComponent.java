package ru.rsreu.gorobchenko.project.components.character;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.Filter;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;

public class CharacterPhysicsComponent extends Component {
    private PhysicsComponent physics;
    private final Filter filter;

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
    }

    public CharacterPhysicsComponent(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void onAdded() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.setGravityScale(0);
        bodyDef.setType(BodyType.DYNAMIC);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.setFilter(filter);

        physics = new PhysicsComponent();
        physics.setBodyDef(bodyDef);
        physics.setFixtureDef(fixtureDef);

        entity.addComponent(physics);
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }
}
