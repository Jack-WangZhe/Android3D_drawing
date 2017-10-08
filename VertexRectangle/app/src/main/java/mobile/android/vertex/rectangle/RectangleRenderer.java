package mobile.android.vertex.rectangle;

import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RectangleRenderer implements Renderer {
    private Rectangle rectangle;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        rectangle = new Rectangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        gl.glViewport(0, 0, width, height);//设置视口大小

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio * 2, ratio * 2, -2, 2, 1, 10);//将当前矩阵与透明矩阵相乘，把当前矩阵变成透明矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);

    }

    //绘制图形
    @Override
    public void onDrawFrame(GL10 gl) {

        /**清空当前缓冲区的值
         * GL_COLOR_BUFFER_BIT:    当前可写的颜色缓冲
           GL_DEPTH_BUFFER_BIT:    深度缓冲
           GL_ACCUM_BUFFER_BIT:   累积缓冲
         　GL_STENCIL_BUFFER_BIT: 模板缓冲
         */

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glLoadIdentity();//恢复初始坐标系,重置当前指定的矩阵为单位矩阵
        gl.glTranslatef(0.0f, 0.0f, -2.0f);
        rectangle.drawSelf(gl);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

}
