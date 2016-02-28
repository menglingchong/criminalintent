# criminalintent

 Android 编程权威指南，第二个实例！
 
  该实例涉及13章，介绍部分会持续补充！
  
    主要介绍 1.Fragment的使用，Fragment的生命周期，Fragment的管理器。
            2.布局与组件的详细使用介绍。
            3.使用ListFragment显示列表,使用单例来存储数据，单例可以保持数据一直存在，创建ListFragment并使用抽象的activity托管fragment，SingleFragmentActivity类为抽象activity类，可以简化代码的书写。
            4.使用adapter从模型层获取数据，并将数据提供给ListView显示。创建ArrayAdapter类实例，响应列表项的点击事件。
            5.定制列表项，创建定制列表项布局和列表项适配器。创建适配器，覆盖getView()方法，产生定制布局的视图对象，并填充对应的Crime数据
            6.使用fragment argument，从framgent中启动另一个activity，为了获取extra信息，可以简单直接的当式和复杂灵活的方式，简单直接的方式是一牺牲fragment的封装性为代价的，不利于扩展；复杂灵活的方式就是将extra信息保存到fragment的arugment bundle中。使用fragment argument只要是，附加argument给fragment和获取fragment的argument。
            7.使用 ViewPager,创建CrimePagerActivity，实现了详细Crimefragment的左右滑动显示,使用Viewpager并用到其适配器PagerAdapter，即FragmentStatePagerAdapter适配器，主要作用是将返回的fragment添加给托管的activity，并帮助Viewpager找到fragment的视图并一一对应。
            8.创建对话框，将AlertDialog视图封装在DialogFragment中，并且实现在同一个activity中的fragment之间的数据传递。(比较重要！)
            9.操作栏的使用，在操作栏上实现选线菜单和层级式导航(层级菜单)，实现层级式导航使用到NavUtils类，并在mainfest配置文件中配置activity的父activity。通过可选菜单项实现显示或者隐藏操作栏的子标题，并确保子标题在设备旋转后依然能够显示。
            10.存储与加载本地文件，将crime数据转换成JSON格式的数据，即crime对象转换成json对象的序列化，并将其保存到本地文件中。从本地文件中读取crime数据，先从json文件中读取json格式的数据，并先将数据解析为JSONObject类型的string，然后解析为JSONArray，最后解析为crime对象。在模型层，先加载本地的crime数据，若没有则创建空的crime数组列表。
            11.浮动上下文菜单与上下文操作栏。创建上下文菜单，为上下文菜单登记视图并响应菜单项的操作。在新的系统下(android编译版本大于3.2)，是上下文操作栏，该模式是提供上下文操作的主流方式。
            12.使用相机API，实现取景器(即可以直接将要显示的内容输出到设备的屏幕上)，注意SurfaceView，SurfaceHolder,Surface三者之间的关系以及和Camera的关系。拍照并处理照片，即Crime的缩略图和大尺寸的图片展示，缩略图的实现用到了2个不同的ACtivity托管的fragment的之间的通信，大尺寸图片的展示用对话框(DialogFragment)的形式展示，用到了同一个Activity托管的2个不同的fragment之间的通信。
            13.使用隐私intent，开启联系人列表activity和发送信息报告的Activity。隐私Intent的主要组成部分是：要执行的操作，要访问数据的位置，操作设计的数据类型，可选类别。一个隐式的Intent可以包含其中的一部分。在onCreatView()方法中，可以检查隐式intent响应的activity，这样可以防止操作系统找不到匹配的activity，应用会立即崩溃。
   
