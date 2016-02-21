# criminalintent

 Android 编程权威指南，第二个实例！
 
  该实例涉及13章，介绍部分会持续补充！
  
    主要介绍 1.Fragment的使用，Fragment的生命周期，Fragment的管理器。
            2.布局与组件的详细使用介绍。
            3.使用ListFragment显示列表,使用单例来存储数据，单例可以保持数据一直存在，创建ListFragment并使用抽象的activity托管fragment，SingleFragmentActivity类为抽象activity类，可以简化代码的书写。
            4.使用adapter从模型层获取数据，并将数据提供给ListView显示。创建ArrayAdapter类实例，响应列表项的点击事件。
            5.定制列表项，创建定制列表项布局和列表项适配器。创建适配器，覆盖getView()方法，产生定制布局的视图对象，并填充对应的Crime数据
            6.使用fragment argument，从framgent中启动另一个activity，为了获取extra信息，可以简单直接的当式和复杂灵活的方式，简单直接的方式是一牺牲fragment的封装性为代价的，不利于扩展；复杂灵活的方式就是将extra信息保存到fragment的arugment bundle中。使用fragment argument只要是，附加argument给fragment和获取fragment的argument。
            7.使用 ViewPager,创建CrimePagerActivity，实现了详细Crimefragment的左右滑动显示,使用Viewpager并用到其适配器PagerAdapter，即
   FragmentStatePagerAdapter适配器，主要作用是将返回的fragment添加给托管的activity，并帮助Viewpager找到fragment的视图并一一对应。
