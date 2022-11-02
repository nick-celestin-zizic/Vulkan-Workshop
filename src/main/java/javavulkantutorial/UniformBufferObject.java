package javavulkantutorial;

import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public class UniformBufferObject {

    public static final int MAX_INSTANCES = 128;
    public static final int NUM_FLOATS    = (2 + MAX_INSTANCES) * 16;
    public static final int MAT_SIZE      = 16 * Float.BYTES;
    public static final int SIZEOF = (NUM_FLOATS * Float.BYTES) + (MAX_INSTANCES * Integer.BYTES);


    public Matrix4f viewMatrix;
    public Matrix4f projectionMatrix;
    public Matrix4f modelMatrices[];

    public int      textureIds[];

    public UniformBufferObject() {
        viewMatrix       = new Matrix4f();
        projectionMatrix = new Matrix4f();
        modelMatrices    = new Matrix4f[MAX_INSTANCES];
        textureIds       = new int[MAX_INSTANCES];

        for (int i = 0; i < MAX_INSTANCES; ++i) {
            modelMatrices[i] = new Matrix4f();
            textureIds   [i] = 1;
        }
    }
}
