package net.bova.opengles112d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES11;


public class plane {
    private FloatBuffer vertexBuffer;


    public plane(float width, float height) {
        float[] vertex = {
                0.0F,  0.0F,   0.0F,
                0.0F,  height, 0.0F,
                width, 0.0F,   0.0F,

                width, 0.0F,   0.0F,
                0.0F,  height, 0.0F,
                width, height, 0.0F     };
        ByteBuffer bb = ByteBuffer.allocateDirect(vertex.length * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(vertex).position(0);

        vertexBuffer= fb;
    }

    public void draw(Float x, Float y) {
        GLES11.glEnableClientState(GLES11.GL_VERTEX_ARRAY);
        GLES11.glVertexPointer(3,GLES11.GL_FLOAT,0,vertexBuffer);

        GLES11.glLoadIdentity();
        GLES11.glTranslatef(x,y,0F);

        GLES11.glDrawArrays(GLES11.GL_TRIANGLES,0,6);

        GLES11.glDisableClientState(GLES11.GL_VERTEX_ARRAY);
    }


}
