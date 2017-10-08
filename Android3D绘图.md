# 安卓3D绘图-OpenGL ES
## 关于3D绘图
#### 提到3D绘图，几乎所有的人都会想起游戏，从最初的单机2D游戏，到现在的网络3D游戏，无论在现实效果上，还是娱乐性上都已有了显著的改善，最重要的便是3D图形库。
### 目前常用的图形库：Windows的DirectX与跨平台的OpenGL
#### 所以，使用3D图形库开发手机游戏变得普遍，学习掌握OpenGL ES技术显得尤为重要，而且现在也正是OpenGL ES蓬勃发展的时期。
## 关于OpenGL ES
#### OpenGL（开放式图形库） 定义了一个跨编程语言、跨操作系统的性能卓越的三维图形标准。任何语言都能够实现此标准下的接口。几乎所有语言都实现了OpenGL；
#### 例如，Java、C/C++、C#、Delphi、Python、Ruby、Perl等。
#### OpenGL ES（OpenGL for Embedded Systems） 专为嵌入和移动设备设计的2D/3D轻量图形库。基于OpenGL API设计的，为OpenGL三维图形API的子集，最新版本为OpenGL ES 3.0，即裁剪定制而来，去除了很多OpenGL中的特性；
#### 例如，四边形（GL_QUADS）、多边形（GL_POLYGONS）
## 如何绘制3D图形
#### 在OpenGL ES与Canvas上绘制图形类似，都需在一个绘制方法中完成主要工作，在OpenGL ES环境中，需要在onDrawFrame方法完成，onDrawFrame是Renderer接口的方法，所以，核心的绘图类要实现Renderer接口。
### 在绘图前，需做如下工作：
* 设置绘图窗口的大小；
* 设置矩阵模式；
* 设置投影类型，将视图切换至投影模式；
* 清空屏幕；
* 打开相应开关。
## Render接口开关
* onDrawFrame：用于绘制3D图形。
* onSurfaceChanged：当绘制窗口（也称视口）变化时调用。
* onSurfaceCreated：创建绘图窗口时调用。
* 一般情况下，设置视口大小、投影类型、矩阵模式、切换投影类型在
	1. onSurfaceChanged方法中完成；
	2. 对开关的操作、绘制3D图形、设置顶点坐标在onDrawFrame方法中完成；
	3. 初始化工作在onSurfaceCreated方法中完成。

## glFrustum介绍
### glFrustum是opengl类库中的函数，将当前矩阵与一个透视矩阵相乘，把当前矩阵转变成透视矩阵，在使用它之前，通常会先调用glMatrixMode(GL_PROJECTION). 
### void glFrustum(Gldouble left, Gldouble right, Gldouble bottom, Gldouble top, Gldouble nearVal, Gldouble farVal)
* left,right指明相对于垂直平面的左右坐标位置
* bottom,top指明相对于水平剪切面的下上位置
* nearVal,farVal指明相对于深度剪切面的远近的距离，两个必须为正数
## 如何绘制三角形
### 在重写的onDrawFrame方法内，使用glVertexPointer方法将三角形的3个顶点坐标（共9个值）装载到内存中，并调用glDrawArrays方法根据内存中的顶点坐标绘制三角形。
	{	
	0, one,0,
	-one,-one,0,
	one, -one,0
	}
	int one = 0x10000;
#### 如上定义的坐标值中，z轴的坐标值都为0，而X和Y坐标分别在坐标系的4个象限中，在OpenGL ES中，只使用int类型值的高16位作为坐标值，因此，坐标值为1需要使用0x10000。
#### ratio参数：假设设备的width和height分别为400像素和800像素，则ratio为0.5，因此投影坐标的x轴和y轴的刻度之比也为0.5。
### 构建3D开发的基本框架
* GLSurfaceView继承自SurfaceView
* GLSurfaceView中定义了一个渲染器接口GLSurfaceView.Renderer

		public class RectangleRenderer implements Renderer {}
		public void onSurfaceChanged(GL10 gl, int width, int height)
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		public void onDrawFrame(GL10 gl)
### onDrawFrame中绘制2D/3D图形，首先做准备工作：
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清屏和深度缓存：
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	gl.glLoadIdentity();
	gl.glTranslatef(0.0f, 0.0f, -2.0f);
	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
### 深度缓存——对物体进入屏幕内部的深度进行跟踪（create中实现）
	gl.glEnable(GL10.GL_CULL_FACE);
	gl.glShadeModel(GL10.GL_SMOOTH);
	gl.glEnable(GL10.GL_DEPTH_TEST);
### 当窗口大小发生变化时调用GLSurfaceChanged，程序开始时至少运行一次，所以加入OpenGL场景大小：
	gl.glViewport(0, 0, width, height);
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glFrustumf(-ratio * 2, ratio * 2, -2, 2, 1, 10);
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	注：glFrustumf方法前4个参数用于确定窗口大小，而后两个是在场景中所能绘制深度的起点和终点。	
### 显示GLSurfaceView：
	GLSurfaceView glSurfaceView = new GLSurfaceView(this);
	glSurfaceView.setRenderer(new RectangleRenderer());
	setContentView(glSurfaceView);


