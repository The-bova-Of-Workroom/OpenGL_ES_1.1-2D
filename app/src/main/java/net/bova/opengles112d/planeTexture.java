package net.bova.opengles112d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.opengl.GLES11;
import android.opengl.GLUtils;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL11;


public class planeTexture {
    private FloatBuffer vertexBuffer;
    private FloatBuffer uvsBuffer;
    private int id;
    private Bitmap bm;


    public planeTexture(float width, float height, String path, Context context) {
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

        float[] uvs = {
                0.0F, 0.0F,
                0.0F, 1.0F,
                1.0F, 0.0F,

                1.0F, 0.0F,
                0.0F, 1.0F,
                1.0F, 1.0F      };
        bb= ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());

        fb= bb.asFloatBuffer();
        fb.put(uvs).position(0);

        uvsBuffer= fb;

        id= 0;

        try {
                BufferedInputStream bis = new BufferedInputStream(context.getAssets().open(path));
                bm= BitmapFactory.decodeStream(bis);
                bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set() {
        if (id != 0) {
            int[] d = new int[]{id, 0 };
            GLES11.glDeleteTextures(1,d,0);
        }

        int[] c = new int[1];
        GLES11.glGenTextures(1,c,0);
        GLES11.glBindTexture(GL11.GL_TEXTURE_2D,c[0]);

        GLES11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_NEAREST);
        GLES11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_NEAREST);
        GLES11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT);
        GLES11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_T,GL11.GL_REPEAT);

        GLUtils.texImage2D(GL11.GL_TEXTURE_2D,0,bm,0);
        id= c[0];
        // bm.recycle();
    }

    public void draw(Float x, Float y) {
        GLES11.glEnableClientState(GLES11.GL_VERTEX_ARRAY);
        GLES11.glVertexPointer(3,GLES11.GL_FLOAT,0,vertexBuffer);

        GLES11.glEnableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
        GLES11.glTexCoordPointer(2,GLES11.GL_FLOAT,0,uvsBuffer);

        GLES11.glEnable(GLES11.GL_TEXTURE_2D);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D,id);

        GLES11.glLoadIdentity();
        GLES11.glTranslatef(x,y,0F);

        GLES11.glDrawArrays(GLES11.GL_TRIANGLES,0,6);
        GLES11.glDisable(GLES11.GL_TEXTURE_2D);

        GLES11.glDisableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
        GLES11.glDisableClientState(GLES11.GL_VERTEX_ARRAY);
    }
}
