
// #import来包含头文件，优点是可以自动防止同一个头文件被包含多次
#import <Foundation/Foundation.h>


// 类的声明使用 @interface 和 @end
//      由于没有package的概念，类名必须全局唯一。
//      objective-C 中 class 也是一个 Object
// 方法
//      `+` 代表是类方法(静态方法)
//      `-` 代表是实例方法
//      在.h中声明的所有方法作用域都是public类型，不能更改
// 成员变量
//      @public     全局都可以访问
//      @protected  (默认)只能在类内部和子类中访问
//      @private    只能在类内部访问
//      成员变量只能写在花括号 {} 之间

@interface MyDog : NSObject{
    // 年龄
    int age;
    
    @public
    // id
    int id;
    // 身高
    float height;
    
    @private
    // 体重
    float weight;
    
}


// 一个冒号:对应一个参数
// -(returnType) methodName;
// -(returnType) methodName: (arg1Type) arg1Name;
// -(returnType) methodName: (arg1Type) arg1Name: (arg2Type) arg2Name;


// getter
- (int) age;
// setter
- (void) setAge: (int) newAge;


// 为了简化属性的声明，可以使用 @property，会自动创建下划线开头的成员变量，并生成 getter，setter。
@property NSString *color;
@property(readonly) int w;


// 自定义构造函数
- (id)initWithAge:(int)age;

- (void) speak;
@end
