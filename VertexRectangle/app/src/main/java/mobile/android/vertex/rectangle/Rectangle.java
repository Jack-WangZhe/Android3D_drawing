package mobile.android.vertex.rectangle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Rectangle {

	private FloatBuffer rectangleBuffer;
	//定义了四个顶点和对应的 Android 顶点定义
	private float[] rectangleVertices = new float[] 
			{ 
				-1.5f, 2f, 0,
				 1.5f, 1.5f, 0,
				 1.5f,-1.5f, 0,
				-1.5f,-1.5f, 0
			};
	//为了提高性能，通常将这些数组存放到 java.io 中定义的 Buffer 类中
	public Rectangle() {
		ByteBuffer byteBuffer = ByteBuffer
				.allocateDirect(rectangleVertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		rectangleBuffer = byteBuffer.asFloatBuffer();
		rectangleBuffer.put(rectangleVertices);
		rectangleBuffer.position(0);
	}

	public void drawSelf(GL10 gl) {
		//将坐标装在到内存中
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectangleBuffer);
		//绘制矩形
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
	}

}
