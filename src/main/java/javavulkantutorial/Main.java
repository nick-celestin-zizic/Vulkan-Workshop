package javavulkantutorial;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    static final float PLAYER_JOLT_SPEED = 5f;
    static final float PLAYER_DRAG = 0.009f;
    static final float PLAYER_SCALE = .5f;
    static final float FUNNY_SPIN_CONSTANT = 10f;
    static boolean gameRunning = true;
    static float dt = 0f;
    static float playerRotation = 0f;
    static Vector2f playerPos = new Vector2f();
    static Vector2f playerVel = new Vector2f();
    static int bgId, playerId, otherId;

    public static void main(String[] args) {
        Engine.start(1000, 1000, "test", keyCallback);

        bgId      = Engine.createRenderEntity("vulkan.png");
        playerId  = Engine.createRenderEntity("guy.jpg");
        otherId   = Engine.createRenderEntity("texture.jpg");

        // initialize timing stuff
        float oldTime = (float) glfwGetTime();
        float newTime;

        while(gameRunning) {
            if (!Engine.processInput()) break;

            // timing stuff
            newTime = (float) glfwGetTime();
            dt      = newTime - oldTime;
            oldTime = newTime;

            // game goes here
            playerPos.add(new Vector2f(playerVel).mul(dt));
            playerRotation += ((playerVel.x) + Math.copySign(playerVel.y, playerVel.x))/2f * dt * FUNNY_SPIN_CONSTANT;
            playerVel.mul(1.0f - PLAYER_DRAG);

            // rendering consists of updating the uniform buffers and calling drawFrame
            Engine.setEntityModel(playerId, (m) -> m
                    .identity()
                    .scale(PLAYER_SCALE)
                    .translate(playerPos.x, playerPos.y, 0.0f)
                    .rotate(playerRotation, 0.0f, 0.0f, 1.0f));

            Engine.setEntityModel(otherId, (m) -> m
                    .identity()
                    .scale(PLAYER_SCALE * 0.75f)
                    .translate(-playerPos.y, playerPos.x, 0f)
                    .rotate(-playerRotation, 0.0f, 0.0f, 1.0f));

            Engine.setEntityModel(bgId, (m) -> m
                    .identity()
                    .rotate(playerRotation * 0.25f, 0.0f, 0.0f, 1.0f));

            Engine.drawFrame();
        }

        Engine.end();
    }
    static GLFWKeyCallbackI keyCallback = (window, key, scanCode, action, mods) -> {
        if (action == GLFW_PRESS) {
            switch (key) {
                case GLFW_KEY_Q:
                case GLFW_KEY_ESCAPE:
                    gameRunning = false;
                    break;
                case GLFW_KEY_SPACE:
                    Engine.setEntityTexture(bgId, "guy.jpg");
                    break;

                case GLFW_KEY_LEFT:
                    playerVel.x -= PLAYER_JOLT_SPEED;
                    break;
                case GLFW_KEY_RIGHT:
                    playerVel.x += PLAYER_JOLT_SPEED;
                    break;
                case GLFW_KEY_UP:
                    playerVel.y -= PLAYER_JOLT_SPEED;
                    break;
                case GLFW_KEY_DOWN:
                    playerVel.y += PLAYER_JOLT_SPEED;
                    break;
            }
        }
    };

}