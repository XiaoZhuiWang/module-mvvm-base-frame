#### Module结构


#### 组件间通信规范
- 组件间方法调用
组件间页面路由、获取另一个组件的信息，都通过组件间方法调用实现。使用ARouter框架
- 组件消息总线
组件间是订阅关系的通信（如其他组件订阅登录组件登录成功事件），则使用LiveEventBus事件总线。严格控制事件总线的使用，禁止滥用。
LiveEventBus的可以值前面都加上组件名前缀。

#### 基类规范
基类禁止任意修改，需要修改最后经过团队协商审核
所有的Activity、Fragment都要继承BaseActivity、BaseFragment

#### 类放置规范
- 通用的util放在utils Module下面，非通用的util放在各自Module的util包下面

#### 资源规范
- 图标都采用xxhdpi，放在drawable-xxhdpi文件夹下
- 色值、全局共用图标放在base_library下的widget中
- String资源放在各自的模块中
- 文件资源（布局文件、图片、其他xml文件）、值资源（放在values文件夹下的文件里面的各个值，属性名除外）都必须使用模块名前缀命名。

#### View的圆角
圆角View统一使用RoundCorners库


#### 混淆规范
- 所有Bean必须实现IBean接口，以防被混淆

